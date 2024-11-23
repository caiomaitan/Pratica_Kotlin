package com.example.trabalho_pratica

// Importa dependências necessárias
import AutenticacaoController
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trabalho_pratica.databinding.ActivityCardapioBinding
import com.google.firebase.firestore.FirebaseFirestore

// Classe da Activity que exibe o cardápio e gerencia o pedido do usuário
class CardapioActivity : AppCompatActivity() {

    // Variáveis para binding, adapter e integração com Firebase Firestore
    private lateinit var binding: ActivityCardapioBinding
    private lateinit var adapter: CardapioAdapter
    private val firestore = FirebaseFirestore.getInstance()

    // Lista de itens adicionados ao pedido e controle do total
    private val pedidoList = mutableListOf<CardapioItem>()
    private var totalPedido = 0.0

    // Instância do controlador de autenticação
    private lateinit var autenticacaoController: AutenticacaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura o layout da Activity com o binding
        binding = ActivityCardapioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o controlador de autenticação
        autenticacaoController = AutenticacaoController()

        // Configura o RecyclerView para exibir os itens do cardápio
        binding.rvCardapio.layoutManager = LinearLayoutManager(this)
        adapter = CardapioAdapter(emptyList()) { item ->
            adicionarAoPedido(item) // Define a ação ao clicar em um item do cardápio
        }
        binding.rvCardapio.adapter = adapter

        // Define a ação do botão "Comprar"
        binding.btnComprar.setOnClickListener {
            realizarPedido() // Finaliza o pedido
        }

        // Define a ação do botão de logout
        binding.txtLogout.setOnClickListener {
            autenticacaoController.logout() // Realiza logout do usuário
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Fecha a Activity atual
        }

        // Carrega os itens do cardápio do Firestore
        loadCardapioItems()
    }

    // Adiciona um item ao pedido e atualiza o total
    private fun adicionarAoPedido(item: CardapioItem) {
        pedidoList.add(item) // Adiciona o item à lista de pedidos
        totalPedido += item.preco // Soma o preço do item ao total
        atualizarResumoPedido() // Atualiza a exibição do pedido
    }

    // Remove um item do pedido e atualiza o total
    private fun removerDoPedido(item: CardapioItem) {
        pedidoList.remove(item) // Remove o item da lista de pedidos
        totalPedido -= item.preco // Subtrai o preço do item do total
        atualizarResumoPedido() // Atualiza a exibição do pedido
    }

    // Atualiza o resumo do pedido na interface
    private fun atualizarResumoPedido() {
        binding.llResumoPedido.removeAllViews() // Remove todos os elementos existentes

        // Itera sobre os itens do pedido para exibi-los
        pedidoList.forEach { item ->
            val itemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL // Layout horizontal para item e botão
            }

            val itemText = TextView(this).apply {
                // Exibe o nome e o preço do item
                text = "${item.nome} - R$ %.2f".format(item.preco)
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val btnRemover = Button(this).apply {
                text = "Remover" // Botão para remover o item
                setOnClickListener {
                    removerDoPedido(item) // Chama a função para remover o item
                }
            }

            // Adiciona o texto do item e o botão ao layout do item
            itemLayout.addView(itemText)
            itemLayout.addView(btnRemover)

            // Adiciona o layout do item à lista de resumo
            binding.llResumoPedido.addView(itemLayout)
        }

        // Atualiza o texto exibindo o total do pedido
        binding.txtTotal.text = "Total: R$ %.2f".format(totalPedido)
    }

    // Finaliza o pedido e reinicia os dados do pedido
    private fun realizarPedido() {
        Toast.makeText(this, "Pedido feito com sucesso!", Toast.LENGTH_LONG).show()
        pedidoList.clear() // Limpa a lista de itens do pedido
        totalPedido = 0.0 // Zera o total do pedido
        atualizarResumoPedido() // Atualiza a interface
    }

    // Carrega os itens do cardápio armazenados no Firestore
    private fun loadCardapioItems() {
        firestore.collection("lanches")
            .get()
            .addOnSuccessListener { documents ->
                // Converte os documentos Firestore para objetos CardapioItem
                val cardapioItems = documents.map { document ->
                    document.toObject(CardapioItem::class.java)
                }
                // Atualiza o adaptador do RecyclerView com os itens carregados
                adapter.updateCardapioItems(cardapioItems)
            }
            .addOnFailureListener { e ->
                // Loga erros caso a leitura do Firestore falhe
                Log.e("CardapioActivity", "Erro ao buscar itens do cardápio", e)
            }
    }
}

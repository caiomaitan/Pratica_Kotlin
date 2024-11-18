package com.example.trabalho_pratica

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trabalho_pratica.databinding.ActivityCardapioBinding
import com.google.firebase.firestore.FirebaseFirestore



class CardapioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardapioBinding
    private lateinit var adapter: CardapioAdapter
    private val firestore = FirebaseFirestore.getInstance()
    private val pedidoList = mutableListOf<CardapioItem>()
    private var totalPedido = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardapioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o RecyclerView
        binding.rvCardapio.layoutManager = LinearLayoutManager(this)
        adapter = CardapioAdapter(emptyList()) { item ->
            adicionarAoPedido(item)
        }
        binding.rvCardapio.adapter = adapter

        // Configura o botão de compra
        binding.btnComprar.setOnClickListener {
            realizarPedido()
        }

        // Carrega os itens do cardápio
        loadCardapioItems()
    }

    private fun adicionarAoPedido(item: CardapioItem) {
        pedidoList.add(item)
        totalPedido += item.preco
        atualizarResumoPedido()
    }

    private fun removerDoPedido(item: CardapioItem) {
        pedidoList.remove(item)
        totalPedido -= item.preco
        atualizarResumoPedido()
    }

    private fun atualizarResumoPedido() {
        // Limpa o resumo atual
        binding.llResumoPedido.removeAllViews()

        // Exibe cada item com o nome, preço e botão de remoção
        pedidoList.forEach { item ->
            val itemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
            }

            val itemText = TextView(this).apply {
                text = "${item.nome} - R$ %.2f".format(item.preco)
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val btnRemover = Button(this).apply {
                text = "Remover"
                setOnClickListener {
                    removerDoPedido(item)
                }
            }

            itemLayout.addView(itemText)
            itemLayout.addView(btnRemover)

            binding.llResumoPedido.addView(itemLayout)
        }

        // Atualiza o total
        binding.txtTotal.text = "Total: R$ %.2f".format(totalPedido)
    }

    private fun realizarPedido() {
        // Mostra uma mensagem de confirmação
        Toast.makeText(this, "Pedido feito com sucesso!", Toast.LENGTH_LONG).show()

        // Limpa o pedido
        pedidoList.clear()
        totalPedido = 0.0
        atualizarResumoPedido()
    }

    private fun loadCardapioItems() {
        firestore.collection("lanches")
            .get()
            .addOnSuccessListener { documents ->
                val cardapioItems = documents.map { document ->
                    document.toObject(CardapioItem::class.java)
                }
                adapter.updateCardapioItems(cardapioItems)
            }
            .addOnFailureListener { e ->
                Log.e("CardapioActivity", "Erro ao buscar itens do cardápio", e)
            }
    }
}


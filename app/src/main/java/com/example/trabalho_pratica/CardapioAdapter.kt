package com.example.trabalho_pratica

// Importa dependências necessárias
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trabalho_pratica.databinding.ItemCardapioBinding

// Classe do adaptador para o RecyclerView que exibe itens do cardápio
class CardapioAdapter(
    private var cardapioList: List<CardapioItem>, // Lista de itens do cardápio
    private val onAddToOrder: (CardapioItem) -> Unit // Callback para adicionar itens ao pedido
) : RecyclerView.Adapter<CardapioAdapter.CardapioViewHolder>() {

    // Classe interna que representa o ViewHolder para cada item do RecyclerView
    class CardapioViewHolder(val binding: ItemCardapioBinding) : RecyclerView.ViewHolder(binding.root)

    // Método para criar o ViewHolder inflando o layout de um item do cardápio
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardapioViewHolder {
        val binding = ItemCardapioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardapioViewHolder(binding)
    }

    // Método que vincula os dados do item atual ao ViewHolder
    override fun onBindViewHolder(holder: CardapioViewHolder, position: Int) {
        val item = cardapioList[position] // Obtém o item correspondente à posição atual

        // Define o nome, descrição e preço do item no layout
        holder.binding.txtTitulo.text = item.nome
        holder.binding.txtDescricao.text = item.descricao
        holder.binding.txtPreco.text = "R$ ${item.preco}"

        // Usa a biblioteca Glide para carregar a imagem do item a partir da URL
        Glide.with(holder.itemView.context)
            .load(item.imagem) // URL da imagem
            .into(holder.binding.imgItem) // Exibe a imagem no ImageView correspondente

        // Define a ação do botão "Adicionar" para enviar o item ao pedido
        holder.binding.btnAdicionar.setOnClickListener {
            onAddToOrder(item) // Chama o callback com o item selecionado
        }
    }

    // Retorna o número de itens na lista
    override fun getItemCount(): Int = cardapioList.size

    // Atualiza a lista de itens no adaptador e notifica o RecyclerView para se atualizar
    fun updateCardapioItems(newItems: List<CardapioItem>) {
        cardapioList = newItems
        notifyDataSetChanged() // Atualiza a exibição com os novos dados
    }
}

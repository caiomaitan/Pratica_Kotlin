package com.example.trabalho_pratica

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trabalho_pratica.databinding.ItemCardapioBinding

class CardapioAdapter(
    private var cardapioList: List<CardapioItem>,
    private val onAddToOrder: (CardapioItem) -> Unit
) : RecyclerView.Adapter<CardapioAdapter.CardapioViewHolder>() {

    class CardapioViewHolder(val binding: ItemCardapioBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardapioViewHolder {
        val binding = ItemCardapioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardapioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardapioViewHolder, position: Int) {
        val item = cardapioList[position]

        // Configura os campos corretamente
        holder.binding.txtTitulo.text = item.nome  // Nome do produto
        holder.binding.txtDescricao.text = item.descricao
        holder.binding.txtPreco.text = "R$ ${item.preco}"

        // Carrega a imagem usando o Glide
        Glide.with(holder.itemView.context)
            .load(item.imagem)
            .into(holder.binding.imgItem)

        // Configura o clique no botão de adicionar ao pedido
        holder.binding.btnAdicionar.setOnClickListener {
            onAddToOrder(item)
        }
    }

    override fun getItemCount(): Int = cardapioList.size

    // Função para atualizar os itens do cardápio
    fun updateCardapioItems(newItems: List<CardapioItem>) {
        cardapioList = newItems
        notifyDataSetChanged()
    }
}


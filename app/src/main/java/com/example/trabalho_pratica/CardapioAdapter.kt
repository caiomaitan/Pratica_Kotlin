package com.example.trabalho_pratica

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trabalho_pratica.databinding.ItemCardapioBinding

class CardapioAdapter(private val cardapioList: List<CardapioItem>) :
    RecyclerView.Adapter<CardapioAdapter.CardapioViewHolder>() {

    class CardapioViewHolder(val binding: ItemCardapioBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardapioViewHolder {
        val binding = ItemCardapioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardapioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardapioViewHolder, position: Int) {
        val item = cardapioList[position]
        holder.binding.txtTitulo.text = item.titulo
        holder.binding.imgItem.setImageResource(item.imagem)
    }

    override fun getItemCount(): Int {
        return cardapioList.size
    }
}

package com.example.trabalho_pratica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trabalho_pratica.databinding.ActivityCardapioBinding

class CardapioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardapioBinding
    private lateinit var adapter: CardapioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCardapioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o RecyclerView
        binding.rvCardapio.layoutManager = LinearLayoutManager(this)
        adapter = CardapioAdapter(getCardapioItems())
        binding.rvCardapio.adapter = adapter
    }

    // Função para retornar os dados do cardápio
    private fun getCardapioItems(): List<CardapioItem> {
        return listOf(
            CardapioItem("Pizza", R.drawable.pizza),

        )
    }
}

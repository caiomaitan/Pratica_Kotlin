package com.example.trabalho_pratica

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trabalho_pratica.databinding.ActivityCardapioBinding
import com.google.firebase.firestore.FirebaseFirestore

class CardapioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardapioBinding
    private lateinit var adapter: CardapioAdapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardapioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o RecyclerView
        binding.rvCardapio.layoutManager = LinearLayoutManager(this)
        adapter = CardapioAdapter(emptyList())
        binding.rvCardapio.adapter = adapter

        // Carrega os itens do Firestore
        loadCardapioItems()
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

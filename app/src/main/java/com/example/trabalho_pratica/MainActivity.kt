package com.example.trabalho_pratica

import AutenticacaoController
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho_pratica.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ctrl: AutenticacaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ctrl = AutenticacaoController()

        binding.btnEntrar.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val senha = binding.txtSenha.text.toString()

            try {
                ctrl.login(email, senha) { sucesso, erro ->
                    if (sucesso) {
                        // Após o login bem-sucedido, navega para a CardapioActivity
                        val intent = Intent(this, CardapioActivity::class.java)
                        startActivity(intent)
                        finish()  // Para evitar voltar para a tela de login
                    } else {
                        val errorMessage = erro ?: "Erro desconhecido"
                        Toast.makeText(this, "Erro no login: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Erro no login", e)
                Toast.makeText(this, "Erro ao tentar fazer login", Toast.LENGTH_LONG).show()
            }
        }

        binding.txtCadastrar.setOnClickListener {
            val intent = Intent(this, CadastrarActivity::class.java)
            startActivity(intent)
        }

        binding.txtEsqueceuSenha.setOnClickListener {
            val intent = Intent(this, EsqueceuSenhaActivity::class.java)
            startActivity(intent)
        }
    }
}

package com.example.trabalho_pratica
import AutenticacaoController
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho_pratica.databinding.ActivityCadastrarBinding

class CadastrarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastrarBinding
    private lateinit var ctrl: AutenticacaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ctrl = AutenticacaoController()

        binding.btnSalvar.setOnClickListener {
            val email = binding.txtEmail.text.toString()
            val senha = binding.txtSenha.text.toString()

            try {
                ctrl.criarUsuario(email, senha) { sucesso, erro ->
                    if (sucesso) {
                        Toast.makeText(this, "Usuário criado com sucesso", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        val errorMessage = erro ?: "Erro desconhecido"
                        Toast.makeText(this, "Erro ao criar usuário: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("CadastrarActivity", "Erro ao criar usuário", e)
                Toast.makeText(this, "Erro ao criar usuário", Toast.LENGTH_LONG).show()
            }
        }
    }
}

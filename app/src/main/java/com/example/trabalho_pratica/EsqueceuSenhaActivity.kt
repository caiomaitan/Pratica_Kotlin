package com.example.trabalho_pratica
import AutenticacaoController
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho_pratica.databinding.ActivityEsqueceuSenhaBinding

class EsqueceuSenhaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEsqueceuSenhaBinding
    private lateinit var ctrl: AutenticacaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEsqueceuSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ctrl = AutenticacaoController()

        binding.btnEnviar.setOnClickListener {
            val email = binding.txtEmail.text.toString()

            ctrl.esqueceuSenha(email) { sucesso, erro ->
                if (sucesso) {
                    Toast.makeText(this, "Um e-mail de redefinição de senha foi enviado para $email.", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    val errorMessage = erro?.toString() ?: "Falha desconhecida"  // Usar .toString() para tratar a falta de message
                    Toast.makeText(this, "Falha ao enviar e-mail de redefinição de senha: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}


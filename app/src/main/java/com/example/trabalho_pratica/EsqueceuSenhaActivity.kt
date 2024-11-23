package com.example.trabalho_pratica

// Importa dependências necessárias
import AutenticacaoController
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho_pratica.databinding.ActivityEsqueceuSenhaBinding

// Classe responsável pela tela de recuperação de senha
class EsqueceuSenhaActivity : AppCompatActivity() {
    // Inicializa o binding para manipular os elementos de interface da Activity
    private lateinit var binding: ActivityEsqueceuSenhaBinding
    private lateinit var ctrl: AutenticacaoController // Controlador para ações de autenticação

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Infla o layout da Activity usando View Binding
        binding = ActivityEsqueceuSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instancia o controlador de autenticação
        ctrl = AutenticacaoController()

        // Configura o clique do botão de enviar
        binding.btnEnviar.setOnClickListener {
            val email = binding.txtEmail.text.toString() // Obtém o email digitado pelo usuário

            // Chama a função de recuperação de senha no controlador
            ctrl.esqueceuSenha(email) { sucesso, erro ->
                if (sucesso) {
                    // Se o envio do email foi bem-sucedido, exibe uma mensagem de sucesso
                    Toast.makeText(
                        this,
                        "Um e-mail de redefinição de senha foi enviado para $email.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish() // Finaliza a Activity, retornando para a tela anterior
                } else {
                    // Caso haja falha, exibe uma mensagem com o erro retornado
                    val errorMessage = erro?.toString() ?: "Falha desconhecida" // Trata erros nulos
                    Toast.makeText(
                        this,
                        "Falha ao enviar e-mail de redefinição de senha: $errorMessage",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho_pratica.databinding.ActivityCadastrarBinding
import com.example.trabalho_pratica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ctrl: AutenticacaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntrar.setOnClickListener {
            val email = binding.txtEmail.text.toString();
            val senha = binding.txtSenha.text.toString();
            ctrl = AutenticacaoController()
            ctrl.login(email, senha) { sucesso, erro ->
                if (sucesso) {
                    val intent = Intent(this, TarefasActivity::class.java)
                    startActivity(intent)
                } else {
                    println("Erro no login: $erro")
                }
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

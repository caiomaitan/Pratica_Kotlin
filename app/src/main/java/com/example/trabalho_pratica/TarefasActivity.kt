import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho_pratica.databinding.ActivityTarefasBinding

class TarefasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTarefasBinding
    private lateinit var ctrl : AutenticacaoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTarefasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ctrl = AutenticacaoController()
        binding.txtUsuarioAutenticado.text = ctrl.usuarioAutenticado()

        binding.txtSair.setOnClickListener {
            ctrl.logout()
            val it = Intent(
                this@TarefasActivity,
                MainActivity::class.java
            )
            startActivity(it)
        }

    }
}

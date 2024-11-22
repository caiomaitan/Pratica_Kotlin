import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AutenticacaoController {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()


    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        try {

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid
                        if (userId != null) {

                            firestore.collection("usuarios").document(userId)
                                .get()
                                .addOnSuccessListener { document ->
                                    if (document.exists()) {
                                        onResult(true, null) // Email encontrado no Firestore
                                    } else {
                                        onResult(false, "Email não encontrado no banco de dados")
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Log.e("AutenticacaoController", "Erro ao verificar email no Firestore", e)
                                    onResult(false, "Erro ao verificar email no banco de dados")
                                }
                        }
                    } else {
                        val errorMessage = task.exception?.message
                        onResult(false, errorMessage) // Caso o login falhe
                    }
                }
        } catch (e: Exception) {
            Log.e("AutenticacaoController", "Erro no login", e)
            onResult(false, "Erro ao tentar fazer login")
        }
    }


    fun criarUsuario(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                        val currentDate = dateFormat.format(Date())

                        val userData = hashMapOf(
                            "email" to email,
                            "dataCriacao" to currentDate  // Armazena a data em formato legível
                        )


                        userId?.let {
                            firestore.collection("usuarios").document(it)
                                .set(userData)
                                .addOnSuccessListener {
                                    Log.d("AutenticacaoController", "Usuário salvo no Firestore com sucesso")
                                    onResult(true, null)
                                }
                                .addOnFailureListener { e ->
                                    Log.e("AutenticacaoController", "Erro ao salvar usuário no Firestore", e)
                                    onResult(false, "Erro ao salvar dados do usuário")
                                }
                        } ?: onResult(false, "Erro ao obter ID do usuário")
                    } else {
                        val errorMessage = task.exception?.message
                        onResult(false, errorMessage)
                    }
                }
        } catch (e: Exception) {
            Log.e("AutenticacaoController", "Erro ao criar usuário", e)
            onResult(false, "Erro ao criar usuário")
        }
    }


    fun esqueceuSenha(email: String, onResult: (Boolean, String?) -> Unit) {
        try {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true, null)
                    } else {
                        val errorMessage = task.exception?.message
                        onResult(false, errorMessage)
                    }
                }
        } catch (e: Exception) {
            Log.e("AutenticacaoController", "Erro ao enviar e-mail de redefinição", e)
            onResult(false, "Erro ao enviar e-mail de redefinição")
        }
    }


    fun usuarioAutenticado(): String? {
        return try {
            firebaseAuth.currentUser?.email
        } catch (e: Exception) {
            Log.e("AutenticacaoController", "Erro ao obter usuário autenticado", e)
            null
        }
    }


    fun logout() {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {
            Log.e("AutenticacaoController", "Erro ao fazer logout", e)
        }
    }
}

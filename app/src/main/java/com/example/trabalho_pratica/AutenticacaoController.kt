import com.google.firebase.auth.FirebaseAuth

import android.util.Log

class AutenticacaoController {

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        val firebaseAuth = FirebaseAuth.getInstance()
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true, null)
                    } else {
                        val errorMessage = task.exception?.message
                        onResult(false, errorMessage)
                    }
                }
        } catch (e: Exception) {
            Log.e("AutenticacaoController", "Erro no login", e)
            onResult(false, "Erro ao tentar fazer login")
        }
    }

    fun criarUsuario(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        val firebaseAuth = FirebaseAuth.getInstance()
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true, null)
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
        val firebaseAuth = FirebaseAuth.getInstance()
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
        val firebaseAuth = FirebaseAuth.getInstance()
        return try {
            firebaseAuth.currentUser?.email
        } catch (e: Exception) {
            Log.e("AutenticacaoController", "Erro ao obter usuário autenticado", e)
            null
        }
    }

    fun logout() {
        try {
            FirebaseAuth.getInstance().signOut()
        } catch (e: Exception) {
            Log.e("AutenticacaoController", "Erro ao fazer logout", e)
        }
    }

    fun idUsuarioAutenticado() {

    }
}

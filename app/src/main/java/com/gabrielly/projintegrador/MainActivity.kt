import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.content.Context
import android.widget.*
import com.gabrielly.projintegrador.MainActivity2
import com.gabrielly.projintegrador.R




class LoginActivity : AppCompatActivity() {

    private lateinit var editTextMatricula: EditText
    private lateinit var editTextSenha: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonCriarConta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextMatricula = findViewById(R.id.editTextMatricula)
        editTextSenha = findViewById(R.id.editTextSenha)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonCriarConta = findViewById(R.id.buttonAjuda)

        val prefs = getSharedPreferences("UsuariosPrefs", Context.MODE_PRIVATE)


        buttonLogin.setOnClickListener {
            val matricula = editTextMatricula.text.toString()
            val senha = editTextSenha.text.toString()

            val senhaSalva = prefs.getString(matricula, null)

            if (senhaSalva != null && senhaSalva == senha) {
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                // Salva o usuário logado
                val prefsLogin = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
                prefsLogin.edit().putString("usuarioLogado", matricula).apply()

                // Vai para a próxima tela
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login ou senha incorretos!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
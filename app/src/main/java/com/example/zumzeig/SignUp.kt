package com.example.zumzeig
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import network.MyStringRequest

class SignUp : AppCompatActivity() {
    private lateinit var emailEt: EditText
    private lateinit var nameEt: EditText
    private lateinit var lastnameEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var repeatpasswordEt: EditText
    private lateinit var queue: RequestQueue
    private lateinit var loginBtn: Button
    private lateinit var sharedPreferences: SharedPreferences
    private var url: String="http://192.168.56.1/zumzeig/save.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPreferences=getSharedPreferences("UserInfo", MODE_PRIVATE)
        queue = Volley.newRequestQueue(this)
        emailEt = findViewById(R.id.email)
        nameEt = findViewById(R.id.name)
        lastnameEt = findViewById(R.id.lastName)
        passwordEt = findViewById(R.id.password)
        repeatpasswordEt = findViewById(R.id.repeatPassword)
        loginBtn = findViewById(R.id.loginBtn)

        if(sharedPreferences.getString("logged","false").equals("true")){
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        loginBtn.setOnClickListener {
            var name: String = nameEt.getText().toString()
            var email: String = emailEt.getText().toString()
            var lastName: String = lastnameEt.getText().toString()
            var pass: String = passwordEt.getText().toString()
            var passRepeat: String = repeatpasswordEt.getText().toString()



            if (!passwordValidate(this, pass, passRepeat)) {
                return@setOnClickListener
            }
            // Email validation
            // If not in email format
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEt.setError(getString(R.string.invalid_email))
            } else if (pass.length < 6) {
                passwordEt.setError(getString(R.string.short_password))
            } else {
                registerPlayer(email,name,lastName,pass)
            }

        }

    }
    fun registerPlayer(email: String, name: String, lastname: String, password: String){
        val params = mapOf(
            "name" to name,
            "last_name" to lastname,
            "email" to email,
            "password" to password
        )
        val stringRequest = MyStringRequest(
        Request.Method.POST,url,params,
        Response.Listener<String> { response ->
            // Manejar la respuesta exitosa
            if (response == "Email already exists") {
                // Mostrar un AlertDialog si el correo ya existe
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage("Email already exists")
                builder.setPositiveButton("Accept", null)
                val dialog = builder.create()
                dialog.show()
            }else{
                Toast.makeText(this, "User created", Toast.LENGTH_LONG).show()
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("logged","true")
                editor.putString("name",name)
                editor.putString("email ",email)

                editor.apply()
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }

        },
        Response.ErrorListener { error ->
            // Manejar el error
            Toast.makeText(this, "User not created", Toast.LENGTH_LONG).show()

        }
    )
        queue.add(stringRequest)
    }

        fun passwordValidate(context: Context, pass1: String, pass2: String): Boolean {
        return if (pass1 == pass2) {
            true // Passwords match
        } else {
            // Passwords don't match, show alert dialog
            showAlert(context)
            false
        }

    }
    fun showAlert(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.error_title))
        builder.setMessage(context.getString(R.string.error_message))
        builder.setPositiveButton(context.getString(R.string.accept_button), null)
        val dialog = builder.create()
        dialog.show()
    }

}

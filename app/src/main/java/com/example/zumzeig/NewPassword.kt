package com.example.zumzeig

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import org.json.JSONException

class NewPassword : AppCompatActivity() {
    private var EMAIL: String =""

    private lateinit var otpET: EditText
    private lateinit var passwordET: EditText
    private lateinit var repeatpasswordET: EditText
    private lateinit var sendBtn: Button
    private lateinit var queue: RequestQueue
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/new-password.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var intent:Bundle? = getIntent().extras
        EMAIL=intent?.get("email").toString()
        otpET=findViewById(R.id.code)
        passwordET=findViewById(R.id.password)
        repeatpasswordET=findViewById(R.id.repeatPassword)
        sendBtn=findViewById(R.id.send)
        queue = Volley.newRequestQueue(this)


        sendBtn.setOnClickListener {
            val otp: String = otpET.text.toString()
            val password: String = passwordET.text.toString()
            val repeatpassword: String = repeatpasswordET.text.toString()
            if (password.length < 6) {
                passwordET.setError(getString(R.string.short_password))
            } else
            if (!passwordValidate(this, password, repeatpassword)) {
                return@setOnClickListener
            }
            val params = mapOf(
                "email" to EMAIL,
                "otp" to otp,
                "new-password" to password
            )
            val stringRequest = MyStringRequest(
                Request.Method.POST, url, params,
                Response.Listener<String> { response ->
                    try {
                        if (response == "success") {
                            Toast.makeText(this,"New password Set", Toast.LENGTH_LONG).show()
                            // Iniciar la siguiente actividad
                            val intent = Intent(this, login::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this,response, Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                }
            )

            queue.add(stringRequest)

        }
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
    //hola

    fun showAlert(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.error_title))
        builder.setMessage(context.getString(R.string.error_message))
        builder.setPositiveButton(context.getString(R.string.accept_button), null)
        val dialog = builder.create()
        dialog.show()
    }
}
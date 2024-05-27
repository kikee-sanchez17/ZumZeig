package com.example.zumzeig

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import network.MyStringRequest
import org.json.JSONException
import org.json.JSONObject

class ForgotPassword : AppCompatActivity() {
    private lateinit var emailEt: EditText
    private lateinit var sendBtn: Button
    private lateinit var queue: RequestQueue
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/reset-password.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        emailEt=findViewById(R.id.email)
        sendBtn=findViewById(R.id.send)
        queue = Volley.newRequestQueue(this)


        sendBtn.setOnClickListener {
            val email: String = emailEt.text.toString()

                val params = mapOf(
                    "email" to email
                )

                val stringRequest = MyStringRequest(
                    Request.Method.POST, url, params,
                    Response.Listener<String> { response ->
                        Log.d("Response", response)
                        val lastWord = response.substringAfterLast(" ")
                        Toast.makeText(this,lastWord,Toast.LENGTH_LONG).show()
//hola
                        try {
                            if (response.contains("success.EmailSENT"))  {
                                // Iniciar la siguiente actividad
                                val intent = Intent(this, NewPassword::class.java)
                                intent.putExtra("email",email)
                                startActivity(intent)
                                finish()
                            }else{
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
}
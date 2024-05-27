package com.example.zumzeig

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
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
import org.json.JSONObject

class login : AppCompatActivity() {
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/login.php"
    private lateinit var queue: RequestQueue
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        emailEt = findViewById(R.id.email)
        passwordEt = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login)
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        queue = Volley.newRequestQueue(this)
        if(sharedPreferences.getString("logged","false").equals("true")){
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        loginBtn.setOnClickListener {
            val email: String = emailEt.text.toString()
            val pass: String = passwordEt.text.toString()

            // Validación de email y contraseña
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEt.error = getString(R.string.invalid_email)
            } else if (pass.length < 6) {
                passwordEt.error = getString(R.string.short_password)
            } else {
                val params = mapOf(
                    "email" to email,
                    "password" to pass
                )

                val stringRequest = MyStringRequest(
                    Request.Method.POST, url, params,
                    Response.Listener<String> { response ->
                        try {
                            val jsonResponse = JSONObject(response)
                            val status = jsonResponse.getString("status")
                            if (status == "success") {
                                val userInfo = jsonResponse.getJSONObject("user_info")
                                val name = userInfo.getString("name")
                                val lastName = userInfo.getString("last_name")
                                val country = userInfo.getString("country")
                                val postalCode = userInfo.getString("postal_code")
                                val birthday = userInfo.getString("birthday")
                                val phoneNumber = userInfo.getString("phone_number")

                                // Guardar la información del usuario en SharedPreferences
                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                editor.putString("logged", "true")
                                editor.putString("email", email)
                                editor.putString("name", name)
                                editor.putString("last_name", lastName)
                                editor.putString("country", country)
                                editor.putString("postal_code", postalCode)
                                editor.putString("birthday", birthday)
                                editor.putString("phone_number", phoneNumber)
                                editor.apply()

                                // Iniciar la siguiente actividad
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                val message = jsonResponse.getString("message")
                                showAlertDialog("Error", message)
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            showAlertDialog("Error", "An error occurred")
                        }
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        showAlertDialog("Error", "Network error")
                    }
                )

                queue.add(stringRequest)
            }
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Accept", null)
        val dialog = builder.create()
        dialog.show()
    }
}

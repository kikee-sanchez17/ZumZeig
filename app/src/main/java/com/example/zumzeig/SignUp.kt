package com.example.zumzeig

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import fragments.HomeFragment
import libraries.FunctionUtility
import network.MyStringRequest
import org.json.JSONException
import org.json.JSONObject

class SignUp : AppCompatActivity() {
    // Declare views
    private lateinit var emailEt: EditText
    private lateinit var nameEt: EditText
    private lateinit var lastnameEt: EditText
    private lateinit var loginaccesEt: TextView
    private lateinit var passwordEt: EditText
    private lateinit var repeatpasswordEt: EditText
    private lateinit var loginBtn: Button

    // Declare variables for SharedPreferences and network request
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var queue: RequestQueue
    private var url: String="https://enricsanchezmontoya.cat/zumzeig/save.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set layout from XML file
        setContentView(R.layout.activity_sign_up)

        // Adjust padding to account for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        loginaccesEt=findViewById(R.id.loginAccess)
        emailEt = findViewById(R.id.email)
        nameEt = findViewById(R.id.name)
        lastnameEt = findViewById(R.id.lastName)
        passwordEt = findViewById(R.id.password)
        repeatpasswordEt = findViewById(R.id.repeatPassword)
        loginBtn = findViewById(R.id.register)

        // Initialize SharedPreferences and Volley request queue
        sharedPreferences=getSharedPreferences("UserInfo", MODE_PRIVATE)
        queue = Volley.newRequestQueue(this)

        // Check if user is already logged in
        if(sharedPreferences.getString("logged","false").equals("true")){
            val intent= Intent(this, HomeFragment::class.java)
            startActivity(intent)
            finish()
        }

        // Click listener for login access text view
        loginaccesEt.setOnClickListener {
            val intent= Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        // Click listener for register button
        loginBtn.setOnClickListener {
            var name: String = nameEt.getText().toString()
            var email: String = emailEt.getText().toString()
            var lastName: String = lastnameEt.getText().toString()
            var pass: String = passwordEt.getText().toString()
            var passRepeat: String = repeatpasswordEt.getText().toString()

            // Validate password using FunctionUtility class
            if (!FunctionUtility().passwordValidate(this, pass, passRepeat)) {
                return@setOnClickListener
            }

            // Email validation
            // If not in email format
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEt.setError(getString(R.string.invalid_email))
            } else if (!FunctionUtility().checkPassword(pass)) {
                passwordEt.setError(getString(R.string.short_password))
            } else {
                // Call method to register player
                registerPlayer(email,name,lastName,pass)
            }
        }
    }

    // Method to register player
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
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")
                    if (status == "success") {
                        val userId = jsonResponse.getString("user_id")
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("logged", "true")
                        editor.putString("name", name)
                        editor.putString("last_name", lastname)
                        editor.putString("email", email)
                        editor.putString("country", "null")
                        editor.putString("postal_code", "null")
                        editor.putString("birthday", "null")
                        editor.putString("phone_number", "null")
                        // Si necesitas el ID de usuario en algún lugar, puedes guardarlo en SharedPreferences también
                        editor.putString("user_ID", userId.toString())
                        editor.apply()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Si hay un error, muestra el mensaje de error
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    // Manejar cualquier error de análisis JSON aquí
                    Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                // Handle error
                Toast.makeText(this, "User not created", Toast.LENGTH_LONG).show()
                //Log.e("SignUp", "Error: ${error.message}")
            }
        )
        queue.add(stringRequest)
    }
}

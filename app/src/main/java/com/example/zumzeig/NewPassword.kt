package com.example.zumzeig

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import libraries.FunctionUtility
import network.MyStringRequest
import org.json.JSONException

class NewPassword : AppCompatActivity() {
    // Email variable to store the email passed from the previous activity
    private var EMAIL: String =""

    // Declare views
    private lateinit var otpET: EditText
    private lateinit var passwordET: EditText
    private lateinit var repeatpasswordET: EditText
    private lateinit var sendBtn: Button

    // Declare variables for network request
    private lateinit var queue: RequestQueue
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/new-password.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set layout from XML file
        setContentView(R.layout.activity_new_password)

        // Adjust padding to account for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve email from previous activity
        var intent: Bundle? = getIntent().extras
        EMAIL = intent?.get("email").toString()

        // Initialize views
        otpET = findViewById(R.id.code)
        passwordET = findViewById(R.id.password)
        repeatpasswordET = findViewById(R.id.repeatPassword)
        sendBtn = findViewById(R.id.send)

        // Initialize Volley request queue
        queue = Volley.newRequestQueue(this)

        // Set click listener for send button
        sendBtn.setOnClickListener {
            val otp: String = otpET.text.toString()
            val password: String = passwordET.text.toString()
            val repeatpassword: String = repeatpasswordET.text.toString()

            // Check if password length is less than 6 characters
            if (password.length < 6) {
                passwordET.setError(getString(R.string.short_password))
            } else {
                // Validate password using FunctionUtility class
                if (!FunctionUtility().passwordValidate(this, password, repeatpassword)) {
                    return@setOnClickListener
                }

                // Create parameters for the POST request
                val params = mapOf(
                    "email" to EMAIL,
                    "otp" to otp,
                    "new-password" to password
                )

                // Create string request
                val stringRequest = MyStringRequest(
                    Request.Method.POST, url, params,
                    Response.Listener<String> { response ->
                        try {
                            if (response == "success") {
                                // Show success message
                                Toast.makeText(this, "New password Set", Toast.LENGTH_LONG).show()

                                // Start login activity
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // Show response message
                                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                                Log.d("holaa", response)
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                    }
                )

                // Add request to the queue
                queue.add(stringRequest)
            }
        }
    }
}

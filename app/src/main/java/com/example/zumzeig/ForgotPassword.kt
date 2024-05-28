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
import network.MyStringRequest
import org.json.JSONException

class ForgotPassword : AppCompatActivity() {
    private lateinit var emailEt: EditText
    private lateinit var sendBtn: Button
    private lateinit var queue: RequestQueue
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/reset-password.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set layout from XML file
        setContentView(R.layout.activity_forgot_password)

        // Adjust padding to account for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        emailEt = findViewById(R.id.email)
        sendBtn = findViewById(R.id.send)

        // Initialize Volley request queue
        queue = Volley.newRequestQueue(this)

        // Set click listener for send button
        sendBtn.setOnClickListener {
            val email: String = emailEt.text.toString()

            // Define parameters for the POST request
            val params = mapOf(
                "email" to email
            )

            // Create string request
            val stringRequest = MyStringRequest(
                Request.Method.POST, url, params,
                Response.Listener<String> { response ->
                    // Log response for debugging
                    Log.d("Response", response)

                    // Show response message in toast
                    val lastWord = response.substringAfterLast(" ")
                    Toast.makeText(this, lastWord, Toast.LENGTH_LONG).show()

                    // Handle response
                    try {
                        if (response.contains("success.EmailSENT")) {
                            // If email was sent successfully, start NewPassword activity
                            val intent = Intent(this, NewPassword::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        } else {
                            // Handle other cases if needed
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    // Log error for debugging
                    error.printStackTrace()
                }
            )

            // Add request to the queue
            queue.add(stringRequest)
        }
    }
}

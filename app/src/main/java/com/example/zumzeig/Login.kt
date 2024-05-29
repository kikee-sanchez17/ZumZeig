package com.example.zumzeig

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
import org.json.JSONObject

class Login : AppCompatActivity() {
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var forgotpasswordEt: TextView
    private lateinit var noaccountEt: TextView
    private lateinit var loginBtn: Button
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/login.php"
    private lateinit var queue: RequestQueue
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout from XML file
        setContentView(R.layout.activity_login)

        // Adjust padding to account for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        emailEt = findViewById(R.id.email)
        noaccountEt = findViewById(R.id.textNoAccount)
        forgotpasswordEt = findViewById(R.id.loginForgotPassword)
        passwordEt = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)

        // Initialize Volley request queue
        queue = Volley.newRequestQueue(this)

        // Check if the user is already logged in
        if(sharedPreferences.getString("logged","false").equals("true")){
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Click listener for forgot password text view
        forgotpasswordEt.setOnClickListener{
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
            finish()
        }

        // Click listener for no account text view
        noaccountEt.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }

        // Click listener for login button
        loginBtn.setOnClickListener {
            val email: String = emailEt.text.toString()
            val pass: String = passwordEt.text.toString()

            // Validation of email and password
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEt.error = getString(R.string.invalid_email)
            } else if (!FunctionUtility().checkPassword(pass)) {
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

                                val user_ID = userInfo.getString("user_ID")
                                val name = userInfo.getString("name")
                                val lastName = userInfo.getString("last_name")
                                val country = userInfo.getString("country")
                                val postalCode = userInfo.getString("postal_code")
                                val birthday = userInfo.getString("birthday")
                                val phoneNumber = userInfo.getString("phone_number")

                                // Save user information in SharedPreferences
                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                editor.putString("logged", "true")
                                editor.putString("user_ID",user_ID)
                                editor.putString("email", email)
                                editor.putString("name", name)
                                editor.putString("last_name", lastName)
                                editor.putString("country", country)
                                editor.putString("postal_code", postalCode)
                                editor.putString("birthday", birthday)
                                editor.putString("phone_number", phoneNumber)
                                editor.apply()

                                // Start the next activity
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                val message = jsonResponse.getString("message")
                                FunctionUtility().showAlertDialog(this,"Error", message)
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            FunctionUtility().showAlertDialog(this,"Error", "An error occurred")
                        }
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        FunctionUtility().showAlertDialog(this,"Error", "Network error")
                    }
                )

                queue.add(stringRequest)
            }
        }
    }
}

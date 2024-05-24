package com.example.zumzeig

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    private lateinit var fetchBtn: Button
    private lateinit var logoutBtn: Button
    private lateinit var emailEt: TextView
    private lateinit var nameEt: TextView
    private lateinit var userInfoEt: TextView
    private lateinit var queue: RequestQueue
    private lateinit var sharedPreferences: SharedPreferences
    private var url: String="http://192.168.56.1/zumzeig/logout.php"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        emailEt = findViewById(R.id.emailEt)
        nameEt = findViewById(R.id.nameEt)
        userInfoEt = findViewById(R.id.userinfo)
        queue = Volley.newRequestQueue(this)
        fetchBtn = findViewById(R.id.fetch)
        logoutBtn = findViewById(R.id.logout)
        emailEt.setText(sharedPreferences.getString("email",""))
        nameEt.setText(sharedPreferences.getString("name",""))
        Toast.makeText(this, sharedPreferences.getString("name",""), Toast.LENGTH_LONG).show()

        logoutBtn.setOnClickListener{
                        Toast.makeText(this, "logged out", Toast.LENGTH_LONG).show()
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("logged","")
                        editor.putString("name","")
                        editor.putString("email","")
                        editor.putString("last_name", "")
                        editor.putString("country", "")
                        editor.putString("postal_code", "")
                        editor.putString("birthday", "")
                        editor.putString("phone_number", "")
                        editor.apply()
                        val intent= Intent(this, login::class.java)
                        startActivity(intent)
                        finish()
        }
        fetchBtn.setOnClickListener{
            Toast.makeText(this, "fetch", Toast.LENGTH_LONG).show()
// Obtenemos todos los campos almacenados en SharedPreferences
            val allFields: Map<String, *> = sharedPreferences.all

            // Creamos un StringBuilder para construir el string resultante
            val stringBuilder = StringBuilder()

            // Iteramos sobre todos los campos y los agregamos al StringBuilder
            for ((key, value) in allFields) {
                stringBuilder.append("$key: $value\n")
            }
            userInfoEt.setText(stringBuilder)
            userInfoEt.visibility= View.VISIBLE

        }


    }
}
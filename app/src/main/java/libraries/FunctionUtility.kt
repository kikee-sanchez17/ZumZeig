package libraries

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.zumzeig.Login
import com.example.zumzeig.R
import org.json.JSONObject

// Utility functions for common tasks
class FunctionUtility {
    // Validate if two passwords match
    fun passwordValidate(context: Context, pass1: String, pass2: String): Boolean {
        return if (pass1 == pass2) {
            true // Passwords match
        } else {
            // Passwords don't match, show alert dialog
            showAlert(context)
            false
        }

    }

    // Check if password meets certain criteria
    fun checkPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}\$")
        return regex.matches(password)
    }

    // Show alert dialog
    fun showAlert(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.error_title))
        builder.setMessage(context.getString(R.string.error_message))
        builder.setPositiveButton(context.getString(R.string.accept_button), null)
        val dialog = builder.create()
        dialog.show()
    }

    // Show alert dialog for Login activity
    fun showAlertDialog(context: Login, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Accept", null)
        val dialog = builder.create()
        dialog.show()
    }

    // Load a fragment into a layout container
    fun loadFragment(fragmentManager: FragmentManager, fragment: androidx.fragment.app.Fragment, addToBackStack: Boolean) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    // Fetch user information and update SharedPreferences
    fun getUserInfoAndUpdateSharedPreferences(email: String, queue: RequestQueue, sharedPreferences: SharedPreferences, callback: () -> Unit) {
        val url = "https://enricsanchezmontoya.cat/zumzeig/getuserinfo.php?email=$email"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getInt("success") == 1) {
                        val editor = sharedPreferences.edit()
                        editor.putString("name", jsonObject.getString("name"))
                        editor.putString("last_name", jsonObject.getString("last_name"))
                        editor.putString("country", jsonObject.getString("country"))
                        editor.putString("postal_code", jsonObject.getString("postal_code"))
                        editor.putString("birthday", jsonObject.getString("birthday"))
                        editor.putString("phone_number", jsonObject.getString("phone_number"))
                        editor.apply()
                        callback()
                    } else {
                       // Log.e("getUserInfo", "Failed to fetch user info")
                    }
                } catch (e: Exception) {
                  //  Log.e("getUserInfo", "Error: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                //Log.e("getUserInfo", "Volley Error: ${error.message}")
            }
        )
        queue.add(stringRequest)
    }

    // Check if user is logged in, if not, redirect to login page
    fun checkUserLoggedIn(context: Context, sharedPreferences: SharedPreferences, onLoggedIn: () -> Unit) {
        if (sharedPreferences.getString("logged", "false") == "false") {
            val intent = Intent(context, Login::class.java)
            context.startActivity(intent)
            if (context is Activity) {
                context.finish()
            }
        } else {
            onLoggedIn()
        }
    }
}

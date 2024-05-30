package libraries

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.zumzeig.Login
import com.example.zumzeig.R

class FunctionUtility {
    fun passwordValidate(context: Context, pass1: String, pass2: String): Boolean {
        return if (pass1 == pass2) {
            true // Passwords match
        } else {
            // Passwords don't match, show alert dialog
            showAlert(context)
            false
        }

    }
    fun checkPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}\$")
        return regex.matches(password)
    }

    fun showAlert(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.error_title))
        builder.setMessage(context.getString(R.string.error_message))
        builder.setPositiveButton(context.getString(R.string.accept_button), null)
        val dialog = builder.create()
        dialog.show()
    }
     fun showAlertDialog(context: Login, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Accept", null)
        val dialog = builder.create()
        dialog.show()
    }
    // Cambio en el método loadFragment: se pasa el FragmentManager como argumento
    fun loadFragment(fragmentManager: FragmentManager, fragment: Fragment, isAppInitialized: Boolean) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frameLayout, fragment)
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment)
        }

        fragmentTransaction.commit()
    }

}
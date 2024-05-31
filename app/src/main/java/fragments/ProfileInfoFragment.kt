package fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.zumzeig.R
import libraries.FunctionUtility
import network.MyStringRequest
import org.json.JSONException
import org.json.JSONObject

class ProfileInfoFragment(private val fragmentManager: FragmentManager) : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var queue: RequestQueue
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/updateuserinfo.php"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
        queue = Volley.newRequestQueue(requireContext())
        val emailUser: String = sharedPreferences.getString("email", "false").toString()

        val editButton: Button = view.findViewById(R.id.editButton)
        val saveButton: Button = view.findViewById(R.id.saveButton)

        val nameEditText: EditText = view.findViewById(R.id.nameEditText)
        val lastNameEditText: EditText = view.findViewById(R.id.lastNameEditText)
        val countryEditText: EditText = view.findViewById(R.id.countryEditText)
        val postalCodeEditText: EditText = view.findViewById(R.id.postalCodeEditText)
        val birthdayEditText: EditText = view.findViewById(R.id.birthdayEditText)
        val phoneNumberEditText: EditText = view.findViewById(R.id.phoneNumberEditText)

        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val lastNameTextView: TextView = view.findViewById(R.id.lastNameTextView)
        val countryTextView: TextView = view.findViewById(R.id.countryTextView)
        val postalCodeTextView: TextView = view.findViewById(R.id.postalCodeTextView)
        val birthdayTextView: TextView = view.findViewById(R.id.birthdayTextView)
        val phoneNumberTextView: TextView = view.findViewById(R.id.phoneNumberTextView)

        nameTextView.text = getString(R.string.nameUser) + " " + (sharedPreferences.getString("name", null) ?: "-")
        lastNameTextView.text = getString(R.string.lastNameUser) + " " + (sharedPreferences.getString("last_name", null) ?: "-")
        if(sharedPreferences.getString("country","false").toString()=="null"){
            countryTextView.text = getString(R.string.countryUser) + " -"

        }else{
            countryTextView.text = getString(R.string.countryUser) + " " + sharedPreferences.getString("country", "false").toString()

        }
        if(sharedPreferences.getString("postal_code","false").toString()=="null"){
            postalCodeTextView.text = getString(R.string.postalCodeUser) + " -"

        }else{
            postalCodeTextView.text = getString(R.string.postalCodeUser) + " " + sharedPreferences.getString("postal_code", "false").toString()

        }
        if(sharedPreferences.getString("birthday","false").toString()=="null"){
            birthdayTextView.text = getString(R.string.birthdayUser) + " -"

        }else{
            birthdayTextView.text = getString(R.string.birthdayUser) + " " + sharedPreferences.getString("birthday", "false").toString()

        }
        if(sharedPreferences.getString("phone_number","false").toString()=="null"){
            phoneNumberTextView.text = getString(R.string.phoneNumberUser) + " -"

        }else{
            phoneNumberTextView.text = getString(R.string.phoneNumberUser) + " " + sharedPreferences.getString("phone_number", "false").toString()

        }

        editButton.setOnClickListener {
            nameEditText.visibility = View.VISIBLE
            lastNameEditText.visibility = View.VISIBLE
            countryEditText.visibility = View.VISIBLE
            postalCodeEditText.visibility = View.VISIBLE
            birthdayEditText.visibility = View.VISIBLE
            phoneNumberEditText.visibility = View.VISIBLE
            saveButton.visibility = View.VISIBLE

            editButton.visibility = View.GONE
        }

        saveButton.setOnClickListener {
            val nameUser: String = nameEditText.text.toString()
            val lastNameUser: String = lastNameEditText.text.toString()
            val countryUser: String = countryEditText.text.toString()
            val postalCodeUser: String = postalCodeEditText.text.toString()
            val birthdayUser: String = birthdayEditText.text.toString()
            val phoneNumberUser: String = phoneNumberEditText.text.toString()

            val params = mapOf(
                "name" to nameUser,
                "last_name" to lastNameUser,
                "country" to countryUser,
                "postal_code" to postalCodeUser,
                "birthday" to birthdayUser,
                "phone_number" to phoneNumberUser,
                "email" to emailUser
            )

            val stringRequest = MyStringRequest(
                Request.Method.POST,
                url,params,
                Response.Listener { response ->
                    try {
                        val jsonResponse = JSONObject(response)
                        val status = jsonResponse.getString("status")
                        if (status == "success") {
                            Log.d("Response", "Profile updated successfully")
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("name", nameUser)
                            editor.putString("last_name", lastNameUser)
                            editor.putString("country", countryUser)
                            editor.putString("postal_code", postalCodeUser)
                            editor.putString("birthday", birthdayUser)
                            editor.putString("phone_number", phoneNumberUser)
                            editor.apply()

                            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_LONG).show()

                            val userFragment = fragmentManager.findFragmentByTag("UserFragment") as UserFragment?
                            userFragment?.refreshUserInfo()

                            FunctionUtility().loadFragment(fragmentManager, UserFragment(fragmentManager), false)

                        } else {
                            Log.d("Response", "Failed to update profile")
                            Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(requireContext(), "Error updating profile", Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    Toast.makeText(requireContext(), "Error updating profile", Toast.LENGTH_LONG).show()
                },

                )
            queue.add(stringRequest)
        }
    }
}
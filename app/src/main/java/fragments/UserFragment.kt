package fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.zumzeig.Login
import com.example.zumzeig.R
import libraries.FunctionUtility

class UserFragment(private val fragmentManager: FragmentManager) : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var nameUserTv: TextView
    private lateinit var emailUserTv: TextView
    private lateinit var userInfoBtn: Button
    private lateinit var changePasswordBtn: Button
    private lateinit var feesBtn: Button
    private lateinit var legalNotesBtn: Button
    private lateinit var logOutBtn: Button
    private lateinit var queue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailUserTv = view.findViewById(R.id.emailTv)
        nameUserTv = view.findViewById(R.id.nameTv)
        userInfoBtn = view.findViewById(R.id.infoUserBtn)
        changePasswordBtn = view.findViewById(R.id.changePasswordBtn)
        feesBtn = view.findViewById(R.id.feesBtn)
        legalNotesBtn = view.findViewById(R.id.legal_notesBtn)
        logOutBtn = view.findViewById(R.id.logOutBtn)

        queue = Volley.newRequestQueue(requireContext())
        sharedPreferences = requireContext().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)

        FunctionUtility().checkUserLoggedIn(requireContext(),sharedPreferences){

            updateUI()

            logOutBtn.setOnClickListener {
                logOut()
            }

            userInfoBtn.setOnClickListener {
                FunctionUtility().loadFragment(
                    fragmentManager,
                    ProfileInfoFragment(fragmentManager),
                    true
                )
            }
        }
    }



    private fun updateUI() {
        emailUserTv.text = sharedPreferences.getString("email", "false")
        nameUserTv.text = sharedPreferences.getString("name", "false")
    }

    private fun logOut() {
        Toast.makeText(requireContext(), "logged out", Toast.LENGTH_LONG).show()
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        val intent = Intent(requireContext(), Login::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


        updateUI()

        logOutBtn.setOnClickListener {
            logOut()
        }

        userInfoBtn.setOnClickListener {
            FunctionUtility().loadFragment(
                fragmentManager,
                ProfileInfoFragment(fragmentManager),
                false
            )
        }
        }
    }



    private fun updateUI() {
        emailUserTv.text = sharedPreferences.getString("email", "false")
        nameUserTv.text = sharedPreferences.getString("name", "false")
    }

    private fun logOut() {
        Toast.makeText(requireContext(), "logged out", Toast.LENGTH_LONG).show()
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        val intent = Intent(requireContext(), Login::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


    fun refreshUserInfo() {
        val emailUser: String = sharedPreferences.getString("email", "false").toString()
        FunctionUtility().getUserInfoAndUpdateSharedPreferences(emailUser, queue, sharedPreferences) {
            updateUI()  // Update UI after fetching new data
        }
    }

}

}


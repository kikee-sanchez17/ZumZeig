package fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.zumzeig.Login
import com.example.zumzeig.R
import libraries.FunctionUtility

class UserFragment(private val fragmentManager: FragmentManager) : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var nameUserTv:TextView
    private lateinit var emailUserTv:TextView
    private lateinit var userInfoBtn: Button
    private lateinit var changePasswordBtn:Button
    private lateinit var feesBtn:Button
    private lateinit var legalNotesBtn:Button
    private lateinit var logOutBtn:Button
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

        sharedPreferences = requireContext().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
        Log.d("tete",sharedPreferences.getString("logged","false").toString())

        nameUserTv.text = sharedPreferences.getString("name","false")
        emailUserTv.text = sharedPreferences.getString("email","false")


        if(sharedPreferences.getString("logged","false").equals("false")){
            val intent= Intent(requireActivity(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        logOutBtn.setOnClickListener {
            Toast.makeText(requireContext(), "logged out", Toast.LENGTH_LONG).show()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("logged","")
            editor.putString("name","")
            editor.putString("user_ID","")
            editor.putString("email","")
            editor.putString("last_name", "")
            editor.putString("country", "")
            editor.putString("postal_code", "")
            editor.putString("birthday", "")
            editor.putString("phone_number", "")
            editor.apply()
            val intent= Intent(requireContext(), Login::class.java)
            startActivity(intent)
            (requireContext() as AppCompatActivity).finish()
        }

        userInfoBtn.setOnClickListener{
            FunctionUtility().loadFragment(
                fragmentManager,
                ProfileInfoFragment(fragmentManager),
                false
            )
        }

    }

}
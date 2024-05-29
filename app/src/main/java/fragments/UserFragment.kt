package fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.zumzeig.Login
import com.example.zumzeig.R
import com.example.zumzeig.SignUp

class UserFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
        Log.d("tete",sharedPreferences.getString("logged","false").toString())

        if(sharedPreferences.getString("logged","false").equals("false")){
            val intent= Intent(requireActivity(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}
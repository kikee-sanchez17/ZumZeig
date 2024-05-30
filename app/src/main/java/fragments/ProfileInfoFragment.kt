package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.zumzeig.R

class ProfileInfoFragment(private val fragmentManager: FragmentManager) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editButton: Button = view.findViewById(R.id.editButton)
        val saveButton: Button = view.findViewById(R.id.saveButton)

        val nameEditText: EditText = view.findViewById(R.id.nameEditText)
        val lastNameEditText: EditText = view.findViewById(R.id.lastNameEditText)
        val countryEditText: EditText = view.findViewById(R.id.countryEditText)
        val postalCodeEditText: EditText = view.findViewById(R.id.postalCodeEditText)
        val birthdayEditText: EditText = view.findViewById(R.id.birthdayEditText)
        val phoneNumberEditText: EditText = view.findViewById(R.id.phoneNumberEditText)

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
            nameEditText.visibility = View.GONE
            lastNameEditText.visibility = View.GONE
            countryEditText.visibility = View.GONE
            postalCodeEditText.visibility = View.GONE
            birthdayEditText.visibility = View.GONE
            phoneNumberEditText.visibility = View.GONE
            saveButton.visibility = View.GONE

            editButton.visibility = View.VISIBLE

            // Aquí puedes agregar la lógica para guardar los datos editados
        }
    }
}
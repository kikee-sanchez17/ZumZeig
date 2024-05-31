package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zumzeig.R

// Define a ParticipateFragment class extending Fragment
class ParticipateFragment : Fragment() {
    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout specified in fragment_participate.xml
        // into the container of this fragment
        return inflater.inflate(R.layout.fragment_participate, container, false)
    }
}


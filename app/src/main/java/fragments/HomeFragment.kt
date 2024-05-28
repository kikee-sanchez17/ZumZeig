package fragments

import adapter.EventAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zumzeig.R
import model.Event

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private val events = listOf(
        Event("https://www.enricsanchezmontoya.cat/zumzeig/images/1_image1.png", "Rebeladas", "Andrea Gautier, Tabatta Salinas", "Un documental sobre les integrants de El Colectivo Cine Mujer; la seva feina, història i evolució. Sessió que forma part de La Mostra Internacional de Films de Dones de Barcelona (MIFDB), un festival dissident, feminista i apassionat que es concentra en dos moments de l’any.", "Dimarts 28.5.24", "19:00", "Paral·leles"),
        Event("https://www.enricsanchezmontoya.cat/zumzeig/images/1_image1.png", "Rebeladas", "Andrea Gautier, Tabatta Salinas", "Un documental sobre les integrants de El Colectivo Cine Mujer; la seva feina, història i evolució. Sessió que forma part de La Mostra Internacional de Films de Dones de Barcelona (MIFDB), un festival dissident, feminista i apassionat que es concentra en dos moments de l’any.", "Dimarts 28.5.24", "19:00", "Paral·leles"),
        Event("https://www.enricsanchezmontoya.cat/zumzeig/images/1_image1.png", "Rebeladas", "Andrea Gautier, Tabatta Salinas", "Un documental sobre les integrants de El Colectivo Cine Mujer; la seva feina, història i evolució. Sessió que forma part de La Mostra Internacional de Films de Dones de Barcelona (MIFDB), un festival dissident, feminista i apassionat que es concentra en dos moments de l’any.", "Dimarts 28.5.24", "19:00", "Paral·leles"),
        Event("https://www.enricsanchezmontoya.cat/zumzeig/images/1_image1.png", "Rebeladas", "Andrea Gautier, Tabatta Salinas", "Un documental sobre les integrants de El Colectivo Cine Mujer; la seva feina, història i evolució. Sessió que forma part de La Mostra Internacional de Films de Dones de Barcelona (MIFDB), un festival dissident, feminista i apassionat que es concentra en dos moments de l’any.", "Dimarts 28.5.24", "19:00", "Paral·leles")

    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.RecyclerOne)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        eventAdapter = EventAdapter(events)
        recyclerView.adapter = eventAdapter
    }
}

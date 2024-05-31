package fragments

import adapter.EventAdapter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.zumzeig.R
import com.google.gson.Gson
import model.Event
import org.json.JSONException

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/fragmenthome.php"
    private lateinit var queue: RequestQueue
    var events = mutableListOf<Event>()


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
        queue = Volley.newRequestQueue(requireContext())

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                try {
                    events.clear()
                    // Parsear la respuesta JSON a una lista de objetos Event
                    val gson = Gson()
                    val eventsArray = gson.fromJson(response.toString(), Array<Event>::class.java)

                    // Manejar la lista de eventos
                    for (event in eventsArray) {
                        events.add(event)

                        // Aquí puedes crear objetos Event y hacer lo que necesites con ellos
                    }
                    initRecyclerView()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error", "Fallo al hacer la solicitud: ${error.message}")
            }
        )

        // Añadir la solicitud a la cola de solicitudes
        queue.add(stringRequest)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        eventAdapter = EventAdapter(events)
        recyclerView.adapter = eventAdapter
    }
}

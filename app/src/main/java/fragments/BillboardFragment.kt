package fragments

import adapter.EventAdapter
import adapter.EventBillboardAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import utils.OnEventClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.zumzeig.Login
import com.example.zumzeig.R
import com.google.gson.Gson
import model.Event
import network.MyStringRequest
import org.json.JSONException

class BillboardFragment : Fragment(),OnEventClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventBillboardAdapter: EventBillboardAdapter
    var events = mutableListOf<Event>()
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/fragmenthome.php"
    private var urlsave: String = "https://enricsanchezmontoya.cat/zumzeig/saveevent.php"
    private lateinit  var queue: RequestQueue
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_billboard, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.RecyclerTwo)
        queue=Volley.newRequestQueue(requireContext())
        sharedPreferences = requireContext().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)

        val getAllEvents = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                try {
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
        queue.add(getAllEvents)

    }
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        eventBillboardAdapter = EventBillboardAdapter(requireContext(),events,this,)
        recyclerView.adapter = eventBillboardAdapter
    }

    override fun onEventClick(eventId: Int) {
        if(sharedPreferences.getString("logged","false").equals("false")){
            val intent= Intent(requireActivity(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }else{
        val idUser =sharedPreferences.getString("user_ID","false").toString()
        val params = mapOf(
            "idEvent" to eventId.toString(),
            "idUser" to idUser
        )
        val saveEvent = MyStringRequest(
            Request.Method.POST, urlsave,params,
            Response.Listener { response ->
                Log.d("saveevent",response + eventId+" "+idUser)
                if(response=="Event saved successfully.") {
                    Toast.makeText(requireContext(), "Event saved successfully.", Toast.LENGTH_LONG).show()

                }else if(response=="Event is already saved."){
                    Toast.makeText(requireContext(), response, Toast.LENGTH_LONG).show()

                }else{
                    Toast.makeText(requireContext(), response, Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error", "Fallo al hacer la solicitud: ${error.message}")
            }
        )
            queue.add(saveEvent)
        }


    }

}
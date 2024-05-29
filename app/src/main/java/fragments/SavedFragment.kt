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
import utils.OnEventClickListener

class SavedFragment : Fragment(), OnEventClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventBillboardAdapter: EventBillboardAdapter
    var events = mutableListOf<Event>()
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/eventssaved.php"
    private lateinit var queue: RequestQueue
    private lateinit var imageButton: ImageButton
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
        Log.d("tete",sharedPreferences.getString("logged","false").toString())

        if(sharedPreferences.getString("logged","false").equals("false")){
            val intent= Intent(requireActivity(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }else{

        recyclerView = view.findViewById(R.id.RecyclerTwo)
        queue = Volley.newRequestQueue(requireContext())
        val id =sharedPreferences.getString("user_ID","false").toString()
        val allFields: Map<String, *> = sharedPreferences.all

        // Creamos un StringBuilder para construir el string resultante
        val stringBuilder = StringBuilder()

        // Iteramos sobre todos los campos y los agregamos al StringBuilder
        for ((key, value) in allFields) {
            stringBuilder.append("$key: $value\n")
        }
        Log.d("infouser",stringBuilder.toString())
        val params = mapOf(
            "id" to id,
        )
            val urlWithParams = url + "?" + params.map { "${it.key}=${it.value}" }.joinToString("&")

            val stringRequest = MyStringRequest(
            Request.Method.GET, urlWithParams,params,
            Response.Listener { response ->
                try {
                    // Parsear la respuesta JSON a una lista de objetos Event
                    val gson = Gson()
                    Log.d("adios",response)
                    val eventsArray = gson.fromJson(response.toString(), Array<Event>::class.java)

                    // Manejar la lista de eventos
                    for (event in eventsArray) {
                        events.add(event)
                        Log.d("eventossaved",events.toString())
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
    }
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        eventBillboardAdapter = EventBillboardAdapter(requireContext(),events,this)
        recyclerView.adapter = eventBillboardAdapter
    }

    override fun onEventClick(eventId: Int) {
        TODO("Not yet implemented")
    }
}
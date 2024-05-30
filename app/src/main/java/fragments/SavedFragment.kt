package fragments

import adapter.EventBillboardAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
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
    private var urldelete: String = "https://enricsanchezmontoya.cat/zumzeig/deleteevent.php"
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

        val idUser =sharedPreferences.getString("user_ID","false").toString()
        val params = mapOf(
            "idEvent" to eventId.toString(),
            "idUser" to idUser
        )
        val saveEvent = MyStringRequest(
            Request.Method.POST, urldelete,params,
            Response.Listener { response ->
                Log.d("deleteevent",response + eventId+" "+idUser)
                if(response=="Event deleted successfully.") {
                    Toast.makeText(requireContext(), "Event deleted successfully.", Toast.LENGTH_LONG).show()
                    updateRecyclerViewAfterEventDeleted(eventId)

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
    // Método para actualizar el contenido del RecyclerView después de eliminar el evento
    private fun updateRecyclerViewAfterEventDeleted(eventId: Int) {
        // Busca y elimina el evento de la lista
        val iterator = events.iterator()
        while (iterator.hasNext()) {
            val event = iterator.next()
            if (event.Event_ID == eventId) {
                iterator.remove()
                break
            }
        }
        // Notifica al adaptador que los datos han cambiado
        eventBillboardAdapter.notifyDataSetChanged()
    }
}
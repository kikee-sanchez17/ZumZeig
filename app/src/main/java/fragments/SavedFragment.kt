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
import android.widget.TextView
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
    private lateinit var noEventsTextView: TextView
    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }
    // Initialize the fragment view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
        //Log.d("tete",sharedPreferences.getString("logged","false").toString())
        noEventsTextView = view.findViewById(R.id.NoEventsSavedTV)
        if(sharedPreferences.getString("logged","false").equals("false")){
            val intent= Intent(requireActivity(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            recyclerView = view.findViewById(R.id.RecyclerTwo)
            queue = Volley.newRequestQueue(requireContext())
            val id = sharedPreferences.getString("user_ID", "false").toString()
            val allFields: Map<String, *> = sharedPreferences.all

            // Create a StringBuilder to build the resulting string
            val stringBuilder = StringBuilder()

            // Iterate over all fields and append them to the StringBuilder
            for ((key, value) in allFields) {
                stringBuilder.append("$key: $value\n")
            }
            //Log.d("infouser", stringBuilder.toString())
            val params = mapOf(
                "id" to id,
            )
            val urlWithParams = url + "?" + params.map { "${it.key}=${it.value}" }.joinToString("&")

            val stringRequest = MyStringRequest(
                Request.Method.GET, urlWithParams, params,
                Response.Listener { response ->
                    try {
                        if (response == "No events found.") {
                            noEventsTextView.visibility = View.VISIBLE
                        } else {
                            // Clear the list of events before adding new events
                            events.clear()

                            // Parse the JSON response into a list of Event objects
                            val gson = Gson()
                            val eventsArray = gson.fromJson(response.toString(), Array<Event>::class.java)

                            // Handle the list of events
                            for (event in eventsArray) {
                                events.add(event)
                                //Log.d("eventossaved", events.toString())
                                // Here you can create Event objects and do whatever you need with them
                            }
                            initRecyclerView()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    //Log.e("Error", "Fallo al hacer la solicitud: ${error.message}")
                }
            )

            // Add the request to the request queue
            queue.add(stringRequest)
        }
    }
    // Initialize the RecyclerView
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        eventBillboardAdapter = EventBillboardAdapter(requireContext(),events,this)
        recyclerView.adapter = eventBillboardAdapter
    }

    override fun onEventClick(eventId: Int) {
    }
    // Handle click on the save icon
    override fun onSaveIconClick(eventId: Int) {

        val idUser =sharedPreferences.getString("user_ID","false").toString()
        val params = mapOf(
            "idEvent" to eventId.toString(),
            "idUser" to idUser
        )
        val saveEvent = MyStringRequest(
            Request.Method.POST, urldelete,params,
            Response.Listener { response ->
                //Log.d("deleteevent",response + eventId+" "+idUser)
                if(response=="Event deleted successfully.") {
                    Toast.makeText(requireContext(), "Event deleted successfully.", Toast.LENGTH_LONG).show()
                    updateRecyclerViewAfterEventDeleted(eventId)

                }else{
                    Toast.makeText(requireContext(), response, Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                //Log.e("Error", "Fallo al hacer la solicitud: ${error.message}")
            }
        )
        queue.add(saveEvent)


    }
    // Method to update the RecyclerView content after deleting the event
    private fun updateRecyclerViewAfterEventDeleted(eventId: Int) {
        // Find and remove the event from the list
        val iterator = events.iterator()
        while (iterator.hasNext()) {
            val event = iterator.next()
            if (event.Event_ID == eventId) {
                iterator.remove()
                break
            }
        }
        // Notify the adapter that the data has changed
        eventBillboardAdapter.notifyDataSetChanged()
    }
}

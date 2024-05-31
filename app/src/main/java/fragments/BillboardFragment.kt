package fragments

import adapter.EventBillboardAdapter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.zumzeig.R
import com.google.gson.Gson
import libraries.FunctionUtility
import model.Event
import network.MyStringRequest
import org.json.JSONException
import utils.OnEventClickListener

class BillboardFragment (private val fragmentManager: FragmentManager): Fragment(), OnEventClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventBillboardAdapter: EventBillboardAdapter
    var events = mutableListOf<Event>()
    private var url: String = "https://enricsanchezmontoya.cat/zumzeig/fragmenthome.php"
    private var urlsave: String = "https://enricsanchezmontoya.cat/zumzeig/saveevent.php"
    private lateinit var queue: RequestQueue
    private lateinit var sharedPreferences: SharedPreferences

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_billboard, container, false)
    }

    // Initialize the fragment view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.RecyclerTwo)
        queue = Volley.newRequestQueue(requireContext())
        sharedPreferences = requireContext().getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)

        val getAllEvents = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                try {
                    events.clear()
                    // Parse the JSON response into a list of Event objects
                    val gson = Gson()
                    val eventsArray = gson.fromJson(response.toString(), Array<Event>::class.java)

                    // Handle the list of events
                    for (event in eventsArray) {
                        events.add(event)

                        // Here you can create Event objects and do whatever you need with them
                    }
                    initRecyclerView()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                //Log.e("Error", "Failed to make request: ${error.message}")
            }
        )

        // Add the request to the request queue
        queue.add(getAllEvents)
    }

    // Initialize the RecyclerView
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        eventBillboardAdapter = EventBillboardAdapter(requireContext(), events, this)
        recyclerView.adapter = eventBillboardAdapter
    }

    // Handle click on the save icon
    override fun onSaveIconClick(eventId: Int) {
        val idUser = sharedPreferences.getString("user_ID", "false").toString()
        val params = mapOf(
            "idEvent" to eventId.toString(),
            "idUser" to idUser
        )
        val saveEvent = MyStringRequest(
            Request.Method.POST, urlsave, params,
            Response.Listener { response ->
                //Log.d("saveevent", response + eventId + " " + idUser)
                if (response == "Event saved successfully.") {
                    Toast.makeText(requireContext(), "Event saved successfully.", Toast.LENGTH_LONG).show()
                } else if (response == "Event is already saved.") {
                    Toast.makeText(requireContext(), response, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), response, Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                //Log.e("Error", "Failed to make request: ${error.message}")
            }
        )
        queue.add(saveEvent)
    }

    // Handle click on the event item
    override fun onEventClick(eventId: Int) {
        FunctionUtility().loadFragment(fragmentManager, EventFragment(eventId), true)
    }
}


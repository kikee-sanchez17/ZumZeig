package adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.zumzeig.Login
import com.example.zumzeig.R
import libraries.FunctionUtility
import model.Event
import network.MyStringRequest
import org.json.JSONObject
import utils.OnEventClickListener

// Custom ViewHolder for RecyclerView items
class EventBillboardViewHolder(view: View, private val listener: OnEventClickListener, private val context: Context) : RecyclerView.ViewHolder(view) {

    // References to views within the list item
    val imgEvent: ImageView = view.findViewById(R.id.imgEvent)
    val titleEvent: TextView = view.findViewById(R.id.titleEvent)
    val director: TextView = view.findViewById(R.id.eventDirector)
    val fecha: TextView = view.findViewById(R.id.eventFechayhora)
    private lateinit var queue: RequestQueue
    val imageButton: ImageButton = view.findViewById(R.id.saveBtn)
    private lateinit var sharedPreferences: SharedPreferences
    val eventBoxCL: ConstraintLayout = view.findViewById(R.id.eventBox)

    // Method to render event data on the view
    @RequiresApi(Build.VERSION_CODES.O)
    fun render(eventModel: Event) {
        // Check if the event is saved
        isSaved(eventModel)

        // Load event image using Glide
        Glide.with(itemView.context).load(eventModel.getaImages()).into(imgEvent)

        // Set texts for views
        titleEvent.text = eventModel.title
        director.text = eventModel.director
        fecha.text = "${eventModel.getDateZ()} ${eventModel.getTime()}"

        // Set title text style based on event type
        when(eventModel.eventTypeName) {
            "Paralelas" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleParalelas)
            "Estrenos" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleEstrenos)
            "Infantil" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleInfantil)
            "Experimental" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleExperimental)
            "Festivales y ciclos" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleFestivales)
            "Noticia" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleNoticia)
        }

        // Set listener for save button
        imageButton.setOnClickListener {
            // Check if the user is logged in
            FunctionUtility().checkUserLoggedIn(context, sharedPreferences) {
                // If user is logged in, call onSaveIconClick method of the listener
                listener.onSaveIconClick(eventModel.Event_ID)
                // Change save button icon
                imageButton.setImageResource(R.drawable.saveblacktotal)
            }
        }

        // Set listener for event layout
        eventBoxCL.setOnClickListener {
            // Call onEventClick method of the listener when layout is clicked
            listener.onEventClick(eventModel.Event_ID)
        }
    }

    // Method to check if the event is saved by the user
    fun isSaved(eventModel: Event) {
        // Initialize Volley request queue
        queue = Volley.newRequestQueue(context)

        // Get user's shared preferences
        sharedPreferences = context.getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("user_ID", "false").toString()
        val idEvent = eventModel.Event_ID

        // Parameters for the request
        val params = mapOf(
            "idEvent" to idEvent.toString(),
            "idUser" to idUser
        )

        // URL to check if event is saved
        val urlsave = "https://enricsanchezmontoya.cat/zumzeig/check_event_saved.php"
        val saveEvent = MyStringRequest(
            Request.Method.POST, urlsave, params,
            Response.Listener { response ->
                //Log.d("eventviewholder", response + idEvent + " " + idUser)
                try {
                    val jsonResponse = JSONObject(response)
                    val isSaved = jsonResponse.getBoolean("isSaved")
                    if (isSaved) {
                        // If event is saved, change save button icon
                        imageButton.setImageResource(R.drawable.saveblacktotal)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                //Log.e("Error", "Request failed: ${error.message}")
            }
        )
        queue.add(saveEvent)
    }
}

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

class EventBillboardViewHolder(view: View, private val listener: OnEventClickListener,private val context: Context) : RecyclerView.ViewHolder(view) {
    val imgEvent: ImageView = view.findViewById(R.id.imgEvent)
    val titleEvent: TextView = view.findViewById(R.id.titleEvent)
    val director: TextView = view.findViewById(R.id.eventDirector)
    val fecha: TextView = view.findViewById(R.id.eventFechayhora)
    private lateinit  var queue: RequestQueue
    val imageButton: ImageButton = view.findViewById(R.id.saveBtn)
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    fun render(eventModel: Event) {
        isSaved(eventModel)
        Glide.with(itemView.context).load(eventModel.getaImages()).into(imgEvent)
        titleEvent.text = eventModel.title
        director.text = eventModel.director
        fecha.text = "${eventModel.getDateZ()} ${eventModel.getTime()}"
        imageButton.setOnClickListener {
            FunctionUtility().checkUserLoggedIn(context, sharedPreferences) {
                // Este bloque solo se ejecuta si el usuario está logueado
                listener.onEventClick(eventModel.Event_ID)
                imageButton.setImageResource(R.drawable.icon_save_filled)
            }
        }
    }
    fun isSaved(eventModel: Event) {
        queue= Volley.newRequestQueue(context)
        sharedPreferences=context.getSharedPreferences("UserInfo", AppCompatActivity.MODE_PRIVATE)
        val idUser =sharedPreferences.getString("user_ID","false").toString()
        val idEvent =eventModel.Event_ID
        val params = mapOf(
            "idEvent" to idEvent.toString(),
            "idUser" to idUser
        )
        var urlsave = "https://enricsanchezmontoya.cat/zumzeig/check_event_saved.php"
        val saveEvent = MyStringRequest(
            Request.Method.POST, urlsave,params,
            Response.Listener { response ->
                Log.d("eventviewholder",response + idEvent+" "+idUser)
                try {
                    val jsonResponse = JSONObject(response)
                    val isSaved = jsonResponse.getBoolean("isSaved")
                    if(isSaved){
                        imageButton.setImageResource(R.drawable.icon_save_filled)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error", "Fallo al hacer la solicitud: ${error.message}")
            }
        )
        queue.add(saveEvent)
    }

    }


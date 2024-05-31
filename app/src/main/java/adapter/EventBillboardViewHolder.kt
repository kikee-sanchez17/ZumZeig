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

class EventBillboardViewHolder(view: View, private val listener: OnEventClickListener,private val context: Context) : RecyclerView.ViewHolder(view) {
    val imgEvent: ImageView = view.findViewById(R.id.imgEvent)
    val titleEvent: TextView = view.findViewById(R.id.titleEvent)
    val director: TextView = view.findViewById(R.id.eventDirector)
    val fecha: TextView = view.findViewById(R.id.eventFechayhora)
    private lateinit  var queue: RequestQueue
    val imageButton: ImageButton = view.findViewById(R.id.saveBtn)
    private lateinit var sharedPreferences: SharedPreferences
    val eventBoxCL : ConstraintLayout = view.findViewById(R.id.eventBox)
    @RequiresApi(Build.VERSION_CODES.O)
    fun render(eventModel: Event) {
        isSaved(eventModel)
        Glide.with(itemView.context).load(eventModel.getaImages()).into(imgEvent)
        titleEvent.text = eventModel.title
        director.text = eventModel.director
        fecha.text = "${eventModel.getDateZ()} ${eventModel.getTime()}"

        if(eventModel.eventTypeName=="Paralelas"){
            titleEvent.setTextAppearance(R.style.titleCarteleraStyleParalelas);
        }else if(eventModel.eventTypeName=="Estrenos"){
            titleEvent.setTextAppearance(R.style.titleCarteleraStyleEstrenos);
        }else if(eventModel.eventTypeName=="Infantil"){
            titleEvent.setTextAppearance(R.style.titleCarteleraStyleInfantil);
        }else if(eventModel.eventTypeName=="Experimental"){
            titleEvent.setTextAppearance(R.style.titleCarteleraStyleExperimental);
        }else if(eventModel.eventTypeName=="Festivales y ciclos"){
            titleEvent.setTextAppearance(R.style.titleCarteleraStyleFestivales);
        }else if(eventModel.eventTypeName=="Noticia"){
            titleEvent.setTextAppearance(R.style.titleCarteleraStyleNoticia);
        }

        imageButton.setOnClickListener {
            FunctionUtility().checkUserLoggedIn(context, sharedPreferences) {
                // Este bloque solo se ejecuta si el usuario está logueado
                listener.onSaveIconClick(eventModel.Event_ID)
                imageButton.setImageResource(R.drawable.saveblacktotal)
            }
        }
        eventBoxCL.setOnClickListener {
            listener.onEventClick(eventModel.Event_ID)

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
                        imageButton.setImageResource(R.drawable.saveblacktotal)

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


package adapter

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zumzeig.R
import model.Event

// Class to maintain and handle views of RecyclerView items
class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Initialize views
    val imgEvent: ImageView = view.findViewById(R.id.imgEvent)
    val titleEvent: TextView = view.findViewById(R.id.titleEvent)
    val tipusEvent: TextView = view.findViewById(R.id.tipusEvent)
    val sinopsis: TextView = view.findViewById(R.id.sinopsisText)
    val director: TextView = view.findViewById(R.id.eventDirector)
    val fecha: TextView = view.findViewById(R.id.eventFecha)
    val hora: TextView = view.findViewById(R.id.eventHora)

    // Method to render data of an event into views
    @RequiresApi(Build.VERSION_CODES.O)
    fun render(eventModel: Event) {
        // Load event image using Glide
        Glide.with(itemView.context).load(eventModel.getaImages()).into(imgEvent)
        // Set event title
        titleEvent.text = eventModel.title
        // Set event type
        tipusEvent.text = eventModel.eventTypeName
        // Set event synopsis
        sinopsis.text = eventModel.introduction
        // Set event director
        director.text = eventModel.director
        // Set event date
        fecha.text = eventModel.getDateZ()
        // Set event time
        hora.text = eventModel.getTime()

        // Change title style based on event type
        when(eventModel.eventTypeName) {
            "Parallel" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleParalelas)
            "Premieres" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleEstrenos)
            "Children" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleInfantil)
            "Experimental" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleExperimental)
            "Festivals and Cycles" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleFestivales)
            "News" -> titleEvent.setTextAppearance(R.style.titleCarteleraStyleNoticia)
        }
    }
}

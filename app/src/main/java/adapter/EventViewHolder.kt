package adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zumzeig.R
import model.Event

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imgEvent: ImageView = view.findViewById(R.id.imgEvent)
    val titleEvent: TextView = view.findViewById(R.id.titleEvent)
    val tipusEvent: TextView = view.findViewById(R.id.tipusEvent)
    val sinopsis: TextView = view.findViewById(R.id.sinopsisText)
    val director: TextView = view.findViewById(R.id.eventDirector)
    val fecha: TextView = view.findViewById(R.id.eventFecha)
    val hora: TextView = view.findViewById(R.id.eventHora)

    fun render(eventModel: Event) {
        Glide.with(itemView.context).load(eventModel.urlImage).into(imgEvent)
        titleEvent.text = eventModel.titleEvent
        tipusEvent.text = eventModel.tipoEvento
        sinopsis.text = eventModel.sinopsis
        director.text = eventModel.director
        fecha.text = eventModel.fecha
        hora.text = eventModel.hora
    }
}

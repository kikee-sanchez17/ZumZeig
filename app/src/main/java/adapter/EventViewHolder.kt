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

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imgEvent: ImageView = view.findViewById(R.id.imgEvent)
    val titleEvent: TextView = view.findViewById(R.id.titleEvent)
    val tipusEvent: TextView = view.findViewById(R.id.tipusEvent)
    val sinopsis: TextView = view.findViewById(R.id.sinopsisText)
    val director: TextView = view.findViewById(R.id.eventDirector)
    val fecha: TextView = view.findViewById(R.id.eventFecha)
    val hora: TextView = view.findViewById(R.id.eventHora)

    @RequiresApi(Build.VERSION_CODES.O)
    fun render(eventModel: Event) {
        Glide.with(itemView.context).load(eventModel.getaImages()).into(imgEvent)
        titleEvent.text = eventModel.title
        tipusEvent.text = eventModel.eventTypeName
        sinopsis.text = eventModel.introduction
        director.text = eventModel.director
        fecha.text = eventModel.getDateZ()
        hora.text = eventModel.getTime()
    }
}

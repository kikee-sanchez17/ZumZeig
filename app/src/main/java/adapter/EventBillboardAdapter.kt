package adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.zumzeig.R
import model.Event
import utils.OnEventClickListener

class EventBillboardAdapter(
    private val context: Context, // Agregar contexto al constructor
    private val events: List<Event>,
    private val listener: OnEventClickListener
) : RecyclerView.Adapter<EventBillboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventBillboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event_billboard, parent, false)
        return EventBillboardViewHolder(view, listener,context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: EventBillboardViewHolder, position: Int) {
        holder.render(events[position])
    }

    override fun getItemCount(): Int = events.size
}

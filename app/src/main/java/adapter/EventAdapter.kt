package adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.zumzeig.R
import model.Event

// Definition of the EventAdapter class that extends RecyclerView.Adapter with a ViewHolder of type EventViewHolder
class EventAdapter(val events: List<Event>) : RecyclerView.Adapter<EventViewHolder>() {

    // Method called when RecyclerView needs a new ViewHolder of a specific type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        // Inflate the layout for the list item
        val layoutInflater = LayoutInflater.from(parent.context)
        // Create a new ViewHolder with the inflated layout
        return EventViewHolder(layoutInflater.inflate(R.layout.item_event, parent, false))
    }

    // Method that returns the total number of items in the list
    override fun getItemCount(): Int {
        // Return the size of the events list
        return events.size
    }

    // Method that binds the data of a specific item in the list to a specific view
    @RequiresApi(Build.VERSION_CODES.O) // Requires API level 26 (Android O) or higher
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        // Get the event at the specified position
        val item = events[position]
        // Call the render method of the ViewHolder to display the event data in the view
        holder.render(item)
    }
}

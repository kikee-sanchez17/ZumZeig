package adapter

import android.content.Context // Import Context to handle Android-specific resources
import android.os.Build // Import Build to check Android version
import android.view.LayoutInflater // Import LayoutInflater to inflate layouts
import android.view.ViewGroup // Import ViewGroup as a base for views
import androidx.annotation.RequiresApi // Import RequiresApi for version constraints
import androidx.recyclerview.widget.RecyclerView // Import RecyclerView for lists
import com.example.zumzeig.R // Import resources from the specific package
import model.Event // Import the Event model
import utils.OnEventClickListener // Import the listener to handle clicks on events

// Adapter to display a billboard of events in a RecyclerView
class EventBillboardAdapter(
    private val context: Context, // Context to access Android-specific resources
    private val events: List<Event>, // List of events to display
    private val listener: OnEventClickListener // Listener to handle clicks on events
) : RecyclerView.Adapter<EventBillboardViewHolder>() { // Inherit from RecyclerView.Adapter

    // Create and return a ViewHolder for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventBillboardViewHolder {
        // Inflate the layout for each item in the list
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event_billboard, parent, false)
        // Return a new ViewHolder with the inflated view, the listener, and the context
        return EventBillboardViewHolder(view, listener, context)
    }

    // Bind event data to the ViewHolder
    @RequiresApi(Build.VERSION_CODES.O) // Ensure the method is used in Android Oreo or higher
    override fun onBindViewHolder(holder: EventBillboardViewHolder, position: Int) {
        // Call the render method of the ViewHolder to display event data at the current position
        holder.render(events[position])
    }

    // Return the total number of items in the list
    override fun getItemCount(): Int = events.size
}


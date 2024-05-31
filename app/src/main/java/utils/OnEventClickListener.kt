package utils

// Interface defining the click listener for events
interface OnEventClickListener {
    // Method called when an event is clicked
    fun onEventClick(eventId: Int)

    // Method called when the save icon of an event is clicked
    fun onSaveIconClick(eventId: Int)
}


package model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Event(
    val Event_ID: Int,
    var title: String,
    var director: String,
    var introduction: String,
    var eventTypeName: String,
    var eventDates: List<String>,
    var images: List<String>
) {
    // Getter y Setter para Event_ID
    fun gettEvent_ID(): Int {
        return Event_ID
    }

    // Getter y Setter para title
    fun gettTitle(): String {
        return title
    }

    fun settTitle(value: String) {
        title = value
    }

    // Getter y Setter para director
    fun gettDirector(): String {
        return director
    }

    fun settDirector(value: String) {
        director = value
    }

    // Getter y Setter para introduction
    fun gettIntroduction(): String {
        return introduction
    }

    fun settIntroduction(value: String) {
        introduction = value
    }

    // Getter y Setter para eventTypeName
    fun gettEventTypeName(): String {
        return eventTypeName
    }

    fun settEventTypeName(value: String) {
        eventTypeName = value
    }

    // Getter y Setter para eventDates
    fun gettEventDates(): List<String> {
        return eventDates
    }

    fun settEventDates(value: List<String>) {
        eventDates = value
    }

    // Getter y Setter para images
    fun gettImages(): List<String> {
        return images
    }

    fun settImages(value: List<String>) {
        images = value
    }
    fun getaImages(): String {
        return images.get(0)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateZ(): String {

        // Formatear la fecha
        val formattedDate = getDateTime().format(DateTimeFormatter.ofPattern("EEEE d.M.yy"))

        // Formatear la hora sin segundos
        return formattedDate
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTime(): String {
        val formattedTime = getDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        return formattedTime
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateTime(): LocalDateTime {
        val dateTime  = LocalDateTime.parse(eventDates.get(0), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        return dateTime
    }


}


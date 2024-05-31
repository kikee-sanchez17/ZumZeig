package fragments

import adapter.ImageSliderAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.zumzeig.R
import network.MyStringRequest
import org.json.JSONException
import org.json.JSONObject

class EventFragment(private val eventId: Int) : Fragment() {

    private lateinit var queue: RequestQueue

    private lateinit var viewPager: ViewPager2
    private lateinit var titleTextView: TextView
    private lateinit var directorTextView: TextView
    private lateinit var introductionTextView: TextView
    private lateinit var synopsisTextView: TextView
    private lateinit var trailerTextView: TextView
    private lateinit var originalTitleTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var durationTextView: TextView
    private lateinit var countryTextView: TextView
    private lateinit var languageTextView: TextView
    private lateinit var versionTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var aFilmByTextView: TextView
    private lateinit var festivalAwardsTextView: TextView
    private lateinit var accompaniedSessionsTextView: TextView
    private lateinit var eventTypeNameTextView: TextView
    private lateinit var eventDatesTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), eventId.toString(), Toast.LENGTH_LONG).show()
        queue = Volley.newRequestQueue(requireContext())

        viewPager = view.findViewById(R.id.viewPager)
        titleTextView = view.findViewById(R.id.titleTextView)
        directorTextView = view.findViewById(R.id.directorTextView)
        introductionTextView = view.findViewById(R.id.introductionTextView)
        synopsisTextView = view.findViewById(R.id.synopsisTextView)
        trailerTextView = view.findViewById(R.id.trailerTextView)
        originalTitleTextView = view.findViewById(R.id.originalTitleTextView)
        yearTextView = view.findViewById(R.id.yearTextView)
        durationTextView = view.findViewById(R.id.durationTextView)
        countryTextView = view.findViewById(R.id.countryTextView)
        languageTextView = view.findViewById(R.id.languageTextView)
        versionTextView = view.findViewById(R.id.versionTextView)
        ratingTextView = view.findViewById(R.id.ratingTextView)
        aFilmByTextView = view.findViewById(R.id.aFilmByTextView)
        festivalAwardsTextView = view.findViewById(R.id.festivalAwardsTextView)
        accompaniedSessionsTextView = view.findViewById(R.id.accompaniedSessionsTextView)
        eventDatesTextView = view.findViewById(R.id.eventDatesTextView)

        fetchEventData()
    }

    private fun fetchEventData() {
        val params = mapOf(
            "idEvent" to eventId.toString()
        )
        var url: String = "https://enricsanchezmontoya.cat/zumzeig/geteventinfo.php?email=$eventId"

        val getAllEvents = MyStringRequest(
            Request.Method.POST, url, params,
            Response.Listener { response ->
                try {
                    val jsonArray = JSONObject(response).getJSONArray("data")
                    Log.d("eventinfo",response)
                    if (jsonArray.length() > 0) {
                        val event = jsonArray.getJSONObject(0)
                        bindEventData(event)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error", "Fallo al hacer la solicitud: ${error.message}")
            }
        )

        queue.add(getAllEvents)
    }

    private fun bindEventData(event: JSONObject) {
        titleTextView.text = event.getString("title")
        directorTextView.text = event.getString("director")
        introductionTextView.text = event.getString("introduction")
        synopsisTextView.text = event.getString("synopsis")
        trailerTextView.text = event.getString("trailer")
        originalTitleTextView.text = event.getString("original_title")
        yearTextView.text = event.getString("year")
        durationTextView.text = event.getString("duration")
        countryTextView.text = event.getString("country")
        languageTextView.text = event.getString("language")
        versionTextView.text = event.getString("version")
        ratingTextView.text = event.getString("rating")
        aFilmByTextView.text = event.getString("a_film_by")
        festivalAwardsTextView.text = event.getString("festival_awards")
        accompaniedSessionsTextView.text = event.getString("accompanied_sessions")
        eventDatesTextView.text = event.getString("eventDates")

        checkAndSetVisibility(titleTextView, event.getString("title"),getString(R.string.event_title))
        checkAndSetVisibility(directorTextView, event.getString("director"),getString(R.string.event_director))
        checkAndSetVisibility(introductionTextView, event.getString("introduction"),getString(R.string.event_introduction))
        checkAndSetVisibility(synopsisTextView, event.getString("synopsis"),getString(R.string.event_synopsis))
        checkAndSetVisibility(trailerTextView, event.getString("trailer"),getString(R.string.event_trailer))
        checkAndSetVisibility(originalTitleTextView, event.getString("original_title"),getString(R.string.event_original_title))
        checkAndSetVisibility(yearTextView, event.getString("year"),getString(R.string.event_year))
        checkAndSetVisibility(durationTextView, event.getString("duration"),getString(R.string.event_duration))
        checkAndSetVisibility(countryTextView, event.getString("country"),getString(R.string.event_country))
        checkAndSetVisibility(languageTextView, event.getString("language"),getString(R.string.event_language))
        checkAndSetVisibility(versionTextView, event.getString("version"),getString(R.string.event_version))
        checkAndSetVisibility(ratingTextView, event.getString("rating"),getString(R.string.event_rating))
        checkAndSetVisibility(aFilmByTextView, event.getString("a_film_by"),getString(R.string.event_a_film_by))
        checkAndSetVisibility(festivalAwardsTextView, event.getString("festival_awards"),getString(R.string.event_festival_awards))
        checkAndSetVisibility(accompaniedSessionsTextView, event.getString("accompanied_sessions"),getString(R.string.event_accompanied_sessions))
        checkAndSetVisibility(eventTypeNameTextView, event.getString("eventTypeName"),getString(R.string.event_type_name))
        checkAndSetVisibility(eventDatesTextView, event.getString("eventDates"),getString(R.string.event_dates))

        // Populate viewPager with image URLs
        val images = event.getJSONArray("images")
        val imageUrls = mutableListOf<String>()
        for (i in 0 until images.length()) {
            imageUrls.add(images.getString(i))
        }
        val adapter = ImageSliderAdapter(requireContext(),imageUrls)
        viewPager.adapter = adapter
    }

    private fun checkAndSetVisibility(textView: TextView, value: String,info:String) {
        if (value == "null") {
            textView.visibility = View.GONE
        } else {
            textView.text = info+": "+value
        }
    }
}
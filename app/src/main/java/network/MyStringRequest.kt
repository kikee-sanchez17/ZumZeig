package network

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

// Custom StringRequest class to handle POST requests with parameters
class MyStringRequest(
    method: Int, // HTTP method (e.g., Request.Method.POST)
    url: String, // URL to send the request to
    private val params: Map<String, String>, // Parameters to include in the request body
    listener: Response.Listener<String>, // Listener to handle successful responses
    errorListener: Response.ErrorListener // Listener to handle errors
) : StringRequest(method, url, listener, errorListener) {

    // Override the getParams() method to provide parameters for the request
    override fun getParams(): Map<String, String> {
        return params
    }
}

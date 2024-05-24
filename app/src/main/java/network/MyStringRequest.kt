package network

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class MyStringRequest(
    method: Int,
    url: String,
    private val params: Map<String, String>,
    listener: Response.Listener<String>,
    errorListener: Response.ErrorListener
) : StringRequest(method, url, listener, errorListener) {

    override fun getParams(): Map<String, String> {
        return params
    }
}

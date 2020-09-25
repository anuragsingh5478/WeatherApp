package com.anuragsingh5478.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var currentWeather : TextView
    lateinit var currentWeatherImage : ImageView
    lateinit var editTextLocation: EditText
    lateinit var buttonSearch : Button
    private lateinit var queue :RequestQueue
    lateinit var textViewTemp : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentWeather = findViewById(R.id.weather_result)
        currentWeatherImage = findViewById(R.id.currentWeatherImage)
        editTextLocation = findViewById(R.id.editTextLocation)
        buttonSearch = findViewById(R.id.butto_search)
        textViewTemp = findViewById(R.id.textView_temperature)
        buttonSearch.setOnClickListener {
            if(editTextLocation.text.toString() != null){
                getWeather(editTextLocation.text.toString())
            }
        }

        queue = Volley.newRequestQueue(this)




    }

    fun getWeather(s:String){
        val url = "http://api.weatherapi.com/v1/current.json?key=e26bf3d9cba2466bb26112541202409&q="+s

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,

            { response ->
                currentWeather.text = response.getJSONObject("current").getJSONObject("condition").getString("text").toString()
                textViewTemp.text = response.getJSONObject("current").getString("temp_c") + "\u00B0"
                val imageUrl : String = response.getJSONObject("current").getJSONObject("condition").getString("icon").toString()

                Glide.with(this).load("http:" + imageUrl).into(currentWeatherImage)

            },
            {

                currentWeather.text = "cant update"
            }
        )

        queue.add(jsonObjectRequest)
    }


}
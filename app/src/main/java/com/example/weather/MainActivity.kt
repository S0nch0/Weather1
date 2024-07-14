package com.example.weather

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editCity = findViewById<EditText>(R.id.editCity)
        val city = editCity.text
        val sendRequest = findViewById<Button>(R.id.sendRequest)
        val cityTextView = findViewById<TextView>(R.id.city)
        val temperatureTextView = findViewById<TextView>(R.id.temperature)
        val windTextView = findViewById<TextView>(R.id.windSpeed)

        sendRequest.setOnClickListener {
            ApiClient.retrofit.create(ApiInterface::class.java)
                .getWeather(city.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ response->
                    cityTextView.text = getString(R.string.city) + " " + city.toString()
                    temperatureTextView.text = getString(R.string.temperature) + response.temperature
                    windTextView.text = getString(R.string.windSpeed) + response.wind
                }, { error ->
                    Toast.makeText(this@MainActivity, "${error.message}", Toast.LENGTH_SHORT).show()
                })
        }
    }
}

data class WeatherResponse(val temperature:String, val wind:String)
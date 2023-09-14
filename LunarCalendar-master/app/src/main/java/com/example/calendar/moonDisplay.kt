package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class moonDisplay : AppCompatActivity() {
    lateinit var moonPic : ImageView
    lateinit var heading : TextView
    lateinit var name : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moon_display)
        val bundle:Bundle?=intent.extras
        val dateValue:String?=bundle?.getString("date")
        val phase:String?=bundle?.getString("phaseName")

        moonPic = findViewById(R.id.moonImage)
        heading = findViewById(R.id.headingPage2)
        name = findViewById(R.id.nameOfPhase)
        heading.setText(dateValue.toString())
        name.setText(phase.toString())
        if(phase == "Primer Cuarto Creciente")
            moonPic.setImageResource(R.drawable.first_quarter);
        else if(phase == "Creciente Creciente")
            moonPic.setImageResource(R.drawable.waxing_crescent);
        else if(phase == "Luna Llena")
            moonPic.setImageResource(R.drawable.full_moon);
        else if(phase == "Último Cuarto Creciente")
            moonPic.setImageResource(R.drawable.third_quarter);
        else if(phase == "Creciente Menguante")
            moonPic.setImageResource(R.drawable.waning_crescent);
        else if(phase == "Gibbosa menguante")
            moonPic.setImageResource(R.drawable.waning_gibbous);
        else if(phase == "Gibbosa Creciente")
            moonPic.setImageResource(R.drawable.waxing_gibbous);
    }
}
package com.example.yousave

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RecurringActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recurring)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        val home = findViewById<ImageButton>(R.id.home_button).setOnClickListener{ startActivity(
            Intent(this, MainActivity::class.java)
        ) }
        val settings = findViewById<ImageButton>(R.id.settings_button).setOnClickListener{ startActivity(
            Intent(this, SettingsActivity::class.java)
        ) }
        val history = findViewById<ImageButton>(R.id.history_button).setOnClickListener{ startActivity(Intent(this, HistoryActivity::class.java)) }

    }
}
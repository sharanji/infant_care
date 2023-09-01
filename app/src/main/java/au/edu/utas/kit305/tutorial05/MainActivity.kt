package au.edu.utas.kit305.tutorial05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import au.edu.utas.kit305.tutorial05.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var ui : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.btnFeeding.setOnClickListener {
            val i = Intent(this, FeedingHistoryActivity::class.java)
            startActivity(i)
        }

        ui.btnNappy.setOnClickListener {
            val i = Intent(this, NappyHistoryActivity::class.java)
            startActivity(i)
        }

        ui.btnSleep.setOnClickListener {
            val i = Intent(this, SleepHistroyActivity::class.java)
            startActivity(i)
        }

        ui.btnTemperate.setOnClickListener {
            val i = Intent(this, TemperateHistoryActivity::class.java)
            startActivity(i)
        }
    }
}
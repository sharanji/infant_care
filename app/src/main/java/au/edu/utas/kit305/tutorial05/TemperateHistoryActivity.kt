package au.edu.utas.kit305.tutorial05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import au.edu.utas.kit305.tutorial05.databinding.ActivityFeedingHistoryBinding
import au.edu.utas.kit305.tutorial05.databinding.ActivityNappyHistoryBinding
import au.edu.utas.kit305.tutorial05.databinding.ActivityTemperateHistoryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class TemperateHistoryActivity : AppCompatActivity() {

    private lateinit var ui : ActivityTemperateHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityTemperateHistoryBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.btnAddnewTemparate.setOnClickListener {
            val i = Intent(this, TempreateActivity::class.java)
            startActivity(i)
        }

        ui.btnShareTemparate.setOnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().getReference("TemperatureRecords").orderByChild("date").limitToLast(7)
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val myDataJson = Gson().toJson(dataSnapshot.value)
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, myDataJson.toString())
                    startActivity(Intent.createChooser(shareIntent, "Last Seven Days Temperature Data"))
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database read error
                }
            })
        }

        ui.btnTemparateHome.setOnClickListener{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        ui.btnSummaryTemparate.setOnClickListener {
            val i = Intent(this, TemperateSummaryActivity::class.java)
            startActivity(i)
        }



    }
}
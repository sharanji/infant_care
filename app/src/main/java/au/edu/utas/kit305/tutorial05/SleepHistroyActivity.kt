package au.edu.utas.kit305.tutorial05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import au.edu.utas.kit305.tutorial05.databinding.ActivityFeedingHistoryBinding
import au.edu.utas.kit305.tutorial05.databinding.ActivitySleepHistroyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson


class SleepHistroyActivity : AppCompatActivity() {

    private lateinit var ui : ActivitySleepHistroyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivitySleepHistroyBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.btnAddnew.setOnClickListener {
            val i = Intent(this, SleepingActivity::class.java)
            startActivity(i)
        }

        ui.btnSummary.setOnClickListener {
            val i = Intent(this, SleepingSummaryActivity::class.java)
            startActivity(i)
        }

        ui.btnSleepHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        ui.btnShareSleep.setOnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().getReference("SleepingRecords").orderByChild("date").limitToLast(7)
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val myDataJson = Gson().toJson(dataSnapshot.value)
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, myDataJson.toString())
                    startActivity(Intent.createChooser(shareIntent, "Last Seven Days Sleep Data"))
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database read error
                }
            })
        }
    }
}
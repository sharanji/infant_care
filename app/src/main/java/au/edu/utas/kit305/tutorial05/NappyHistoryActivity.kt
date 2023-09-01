package au.edu.utas.kit305.tutorial05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import au.edu.utas.kit305.tutorial05.databinding.ActivityNappyHistoryBinding
import au.edu.utas.kit305.tutorial05.databinding.ActivitySleepHistroyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class NappyHistoryActivity : AppCompatActivity() {
    private lateinit var ui : ActivityNappyHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityNappyHistoryBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.btnAddnew.setOnClickListener {
            val i = Intent(this, NappyActivity::class.java)
            startActivity(i)
        }

        ui.btnSummary.setOnClickListener {
            val i = Intent(this, NappySummaryActivity::class.java)
            startActivity(i)
        }

        ui.btnNappyHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        ui.btnShareNappy.setOnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().getReference("NappyRecords").orderByChild("date").limitToLast(7)
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val myDataJson = Gson().toJson(dataSnapshot.value)
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, myDataJson.toString())
                    startActivity(Intent.createChooser(shareIntent, "Last Seven Days Nappy Data"))
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database read error
                }
            })
        }
    }
}
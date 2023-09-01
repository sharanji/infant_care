package au.edu.utas.kit305.tutorial05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditFeedingActivity : AppCompatActivity() {

    private lateinit var et_feed_date: EditText
    private lateinit var et_start_time: EditText
    private lateinit var et_end_time: EditText
    private lateinit var et_bt_count: EditText
    private lateinit var et_remarks : EditText
    private lateinit var btnSave: Button

    private lateinit var database: FirebaseDatabase
    private lateinit var feedingRef: DatabaseReference

    private var feedingId: String? = null
    private var feedingside: String? = null
    private var feedingtype: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_feeding)

        database = FirebaseDatabase.getInstance()
        feedingRef = database.getReference("FeedingRecords")

        et_feed_date = findViewById(R.id.et_feed_date)
        et_start_time = findViewById(R.id.et_start_time)
        et_end_time = findViewById(R.id.et_end_time)
        et_bt_count = findViewById(R.id.et_bt_count)
        et_remarks = findViewById(R.id.et_remarks)
        btnSave = findViewById(R.id.btn_save)

        val intent = intent
        feedingId = intent.getStringExtra("id")
        et_feed_date.setText(intent.getStringExtra("date"))
        et_start_time.setText(intent.getStringExtra("starttime"))
        et_end_time.setText(intent.getStringExtra("endttime"))
        et_bt_count.setText(intent.getStringExtra("btcount"))
        et_remarks.setText(intent.getStringExtra("remarks"))
        feedingside = intent.getStringExtra("side")
        feedingtype = intent.getStringExtra("type")


        btnSave.setOnClickListener {

            val id = intent.getStringExtra("id")
            val date = et_feed_date.text.toString().trim()
            val startTime = et_start_time.text.toString().trim()
            val endtTime = et_end_time.text.toString().trim()
            val btcount =et_bt_count.text.toString().trim()
            val remarks = et_remarks.text.toString().trim()
            val side =  intent.getStringExtra("side")
            val type =  intent.getStringExtra("type")
            val Feeding = Feeding(id, date, startTime,endtTime,btcount, remarks,side,type)


            feedingRef.child(id?: "").setValue(Feeding).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Feeding record updated successfully", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, FeedingHistoryActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Failed to update Feeding record", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
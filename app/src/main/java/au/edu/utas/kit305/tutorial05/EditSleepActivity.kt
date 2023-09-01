package au.edu.utas.kit305.tutorial05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditSleepActivity : AppCompatActivity() {

    private lateinit var et_sleep_date: EditText
    private lateinit var et_start_time: EditText
    private lateinit var et_end_time: EditText
    private lateinit var et_remarks : EditText
    private lateinit var btnSave: Button

    private lateinit var database: FirebaseDatabase
    private lateinit var sleepRef: DatabaseReference

    private var sleepId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sleep)

        database = FirebaseDatabase.getInstance()
        sleepRef = database.getReference("SleepingRecords")

        et_sleep_date = findViewById(R.id.et_sleep_date)
        et_start_time = findViewById(R.id.et_start_time)
        et_end_time = findViewById(R.id.et_end_time)
        et_remarks = findViewById(R.id.et_remarks)
        btnSave = findViewById(R.id.btn_save)

        val intent = intent
        sleepId = intent.getStringExtra("id")
        et_sleep_date.setText(intent.getStringExtra("date"))
        et_start_time.setText(intent.getStringExtra("starttime"))
        et_end_time.setText(intent.getStringExtra("endtime"))
        et_remarks.setText(intent.getStringExtra("remarks"))

        btnSave.setOnClickListener {

            val id = intent.getStringExtra("id")
            val date = et_sleep_date.text.toString().trim()
            val startTime = et_start_time.text.toString().trim()
            val endtTime = et_end_time.text.toString().trim()
            val remarks = et_remarks.text.toString().trim()
            val Sleeping = Sleeping(id, date, startTime,endtTime, remarks)


            sleepRef.child(id?: "").setValue(Sleeping).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sleeping record updated successfully", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, SleepHistroyActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Failed to update Sleeping record", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
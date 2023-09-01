package au.edu.utas.kit305.tutorial05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NappyFeedingActivity : AppCompatActivity() {

    private lateinit var et_nappy_date: EditText
    private lateinit var et_start_time: EditText
    private lateinit var et_end_time: EditText
    private lateinit var et_bt_count: EditText
    private lateinit var et_remarks : EditText
    private lateinit var btnSave: Button

    private lateinit var database: FirebaseDatabase
    private lateinit var feedingRef: DatabaseReference

    private var nappyId: String? = null
    private var nappytype: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nappy_feeding)

        database = FirebaseDatabase.getInstance()
        feedingRef = database.getReference("NappyRecords")

        et_nappy_date = findViewById(R.id.et_nappy_date)
        et_start_time = findViewById(R.id.et_start_time)
        et_end_time = findViewById(R.id.et_end_time)
        et_bt_count = findViewById(R.id.et_bt_count)
        et_remarks = findViewById(R.id.et_remarks)
        btnSave = findViewById(R.id.btn_save)

        val intent = intent
        nappyId = intent.getStringExtra("id")
        et_nappy_date.setText(intent.getStringExtra("date"))
        et_start_time.setText(intent.getStringExtra("starttime"))
        et_end_time.setText(intent.getStringExtra("endttime"))
        et_bt_count.setText(intent.getStringExtra("ntCount"))
        et_remarks.setText(intent.getStringExtra("remarks"))
        nappytype = intent.getStringExtra("type")

        btnSave.setOnClickListener {

            val id = intent.getStringExtra("id")
            val date = et_nappy_date.text.toString().trim()
            val startTime = et_start_time.text.toString().trim()
            val endtTime = et_end_time.text.toString().trim()
            val ntCount =et_bt_count.text.toString().trim()
            val remarks = et_remarks.text.toString().trim()
            val type =  intent.getStringExtra("type")
            val Nappy = Nappy(id, date, startTime,endtTime,ntCount, remarks,type)


            feedingRef.child(id?: "").setValue(Nappy).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Nappy record updated successfully", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, NappyHistoryActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Failed to update Nappy record", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
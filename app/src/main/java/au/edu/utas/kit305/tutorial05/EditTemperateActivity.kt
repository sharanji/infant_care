package au.edu.utas.kit305.tutorial05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditTemperateActivity : AppCompatActivity() {
    private lateinit var etTempDate: EditText
    private lateinit var etBtCount: EditText
    private lateinit var etRemarks: EditText
    private lateinit var btnSave: Button


    private lateinit var database: FirebaseDatabase
    private lateinit var temperatureRef: DatabaseReference

    private var temperatureId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_temperate)

        database = FirebaseDatabase.getInstance()
        temperatureRef = database.getReference("TemperatureRecords")

        etTempDate = findViewById(R.id.et_temp_date)
        etBtCount = findViewById(R.id.et_bt_count)
        etRemarks = findViewById(R.id.et_remarks)
        btnSave = findViewById(R.id.btn_save)


        val intent = intent
        temperatureId = intent.getStringExtra("id")
        etTempDate.setText(intent.getStringExtra("date"))
        etBtCount.setText(intent.getStringExtra("btcount"))
        etRemarks.setText(intent.getStringExtra("remarks"))

        btnSave.setOnClickListener {

            val id = intent.getStringExtra("id")
            val date = etTempDate.text.toString().trim()
            val btcount = etBtCount.text.toString().trim()
            val remarks = etRemarks.text.toString().trim()


            val temperature = Temperature(id, date, btcount, remarks)


            temperatureRef.child(id?: "").setValue(temperature).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Temperature record updated successfully", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, TemperateHistoryActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Failed to update temperature record", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
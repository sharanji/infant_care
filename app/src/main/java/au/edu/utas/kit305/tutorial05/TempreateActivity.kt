package au.edu.utas.kit305.tutorial05

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast

import au.edu.utas.kit305.tutorial05.databinding.ActivityTemperateHistoryBinding
import au.edu.utas.kit305.tutorial05.databinding.ActivityTempreateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class TempreateActivity : AppCompatActivity() {

    private lateinit var ui : ActivityTempreateBinding

    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tempreate)

        ui = ActivityTempreateBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.editDateTp.setOnClickListener{

            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    ui.editDateTp.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }

        ui.btnBackTemperate.setOnClickListener {
            val i = Intent(this, TemperateHistoryActivity::class.java)
            startActivity(i)
        }

        ui.btnSaveTp.setOnClickListener {

            val myUuid = UUID.randomUUID()
            val myUuidAsString = myUuid.toString()


            val editDate = ui.editDateTp.text.toString()



            val ntCount = ui.editTempCountTp.text.toString()

            val remarks = ui.editRemarksTp.text.toString()


            if(editDate == "")
            {
                Toast.makeText(this, "Please check the date", Toast.LENGTH_SHORT).show();
            }
            else if(ntCount == "")
            {
                Toast.makeText(this, "Please check the count", Toast.LENGTH_SHORT).show();
            }
            else{
                database = FirebaseDatabase.getInstance().getReference("TemperatureRecords")

                val temperatureRecords = Temperature(
                    id = myUuidAsString,
                    date = editDate,
                    btcount = ntCount,
                    remarks = remarks
                )

                database.child(myUuidAsString).setValue(temperatureRecords).addOnSuccessListener {

                    ui.editDateTp.text.clear()
                    ui.editTempCountTp.text.clear()
                    ui.editRemarksTp.text.clear()

                    Toast.makeText(this, "Successfully record added", Toast.LENGTH_SHORT).show();

                }.addOnFailureListener{
                    Toast.makeText(this, "Failed please check again", Toast.LENGTH_SHORT).show();
                }
            }


        }




    }
}
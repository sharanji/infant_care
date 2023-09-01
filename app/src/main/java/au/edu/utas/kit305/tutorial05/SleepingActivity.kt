package au.edu.utas.kit305.tutorial05

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.RadioButton
import android.widget.Toast
import au.edu.utas.kit305.tutorial05.databinding.ActivityFeedingBinding
import au.edu.utas.kit305.tutorial05.databinding.ActivitySleepingBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class SleepingActivity : AppCompatActivity() {

    private lateinit var ui : ActivitySleepingBinding

    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivitySleepingBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.editDate.setOnClickListener{

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
                    ui.editDate.setText(dat)
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

        ui.btnCamera.setOnClickListener {
            val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select Option")

            builder.setItems(options) { dialog, item ->
                when {
                    options[item] == "Take Photo" -> {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, 1)
                    }
                    options[item] == "Choose from Gallery" -> {
                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(intent, 2)
                    }
                    options[item] == "Cancel" -> {
                        dialog.dismiss()
                    }
                }
            }
            builder.show()
        }

        ui.btnBackSleep.setOnClickListener {
            val i = Intent(this, SleepHistroyActivity::class.java)
            startActivity(i)
        }


        ui.editEndTime.setOnClickListener{
            val c = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                ui.editEndTime.setText(SimpleDateFormat("HH:mm").format(c.time))
            }
            TimePickerDialog(this, timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()

        }

        ui.editStartTime.setOnClickListener{
            val c = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                ui.editStartTime.setText(SimpleDateFormat("HH:mm").format(c.time))
            }
            TimePickerDialog(this, timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()

        }

        ui.btnSave.setOnClickListener {

            val myUuid = UUID.randomUUID()
            val myUuidAsString = myUuid.toString()
            val editDate = ui.editDate.text.toString()
            val startTime = ui.editStartTime.text.toString()
            val endTime = ui.editEndTime.text.toString()
            val remarks = ui.editRemarks.text.toString()


            if(editDate == ""){
                Toast.makeText(this, "Please check the date", Toast.LENGTH_SHORT).show();
            }
            else if(startTime == "" )
            {
                Toast.makeText(this, "Please check the Start time", Toast.LENGTH_SHORT).show();
            }
            else if(endTime == "" )
            {
                Toast.makeText(this, "Please check the end time", Toast.LENGTH_SHORT).show();
            }
            else{


                database = FirebaseDatabase.getInstance().getReference("SleepingRecords")

                val sleepingRecords = Sleeping(
                    id = myUuidAsString,
                    date = editDate,
                    startTime = startTime,
                    endTime = endTime,
                    remarks = remarks
                )

                database.child(myUuidAsString).setValue(sleepingRecords).addOnSuccessListener {


                    ui.editDate.text.clear()
                    ui.editStartTime.text.clear()
                    ui.editEndTime.text.clear()
                    ui.editRemarks.text.clear()

                    Toast.makeText(this, "Successfully record added", Toast.LENGTH_SHORT).show();

                }.addOnFailureListener{
                    Toast.makeText(this, "Failed please check again", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                val image = data?.extras?.get("data") as Bitmap
                ui.imageViewCamera.setImageBitmap(image)
            } else if (requestCode == 2) {
                val selectedImage = data?.data
                ui.imageViewCamera.setImageURI(selectedImage)
            }
        }

    }


}
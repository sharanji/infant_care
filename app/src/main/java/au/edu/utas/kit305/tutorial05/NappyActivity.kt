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
import au.edu.utas.kit305.tutorial05.databinding.ActivityNappyBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class NappyActivity : AppCompatActivity() {

    private lateinit var ui : ActivityNappyBinding

    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityNappyBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.editDateNp.setOnClickListener{

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
                    ui.editDateNp.setText(dat)
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

        ui.editEndTimeNp.setOnClickListener{
            val c = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                ui.editEndTimeNp.setText(SimpleDateFormat("HH:mm").format(c.time))
            }
            TimePickerDialog(this, timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()

        }

        ui.editStartTimeNp.setOnClickListener{
            val c = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                ui.editStartTimeNp.setText(SimpleDateFormat("HH:mm").format(c.time))
            }
            TimePickerDialog(this, timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()

        }


        ui.btnBackNappy.setOnClickListener {
            val i = Intent(this, NappyHistoryActivity::class.java)
            startActivity(i)
        }

        val rdType = ui.radioGroupTypeNp

        ui.btnSaveNp.setOnClickListener {

            val myUuid = UUID.randomUUID()
            val myUuidAsString = myUuid.toString()


            val selectRdType :Int = rdType!!.checkedRadioButtonId
            val rdTypeVal = findViewById<RadioButton>(selectRdType)
            val TypeVal = if(rdTypeVal != null) rdTypeVal.text.toString() else ""
            //Toast.makeText(this, rdTypeVal.text, Toast.LENGTH_SHORT).show();



            val editDate = ui.editDateNp.text.toString()

            val startTime = ui.editStartTimeNp.text.toString()
            val endTime = ui.editEndTimeNp.text.toString()

            val ntCount = ui.editNappyCountNp.text.toString()

            val remarks = ui.editRemarksNp.text.toString()

            if(TypeVal == "")
            {
                Toast.makeText(this, "Please check the type", Toast.LENGTH_SHORT).show();
            }
            else if(editDate == "")
            {
                Toast.makeText(this, "Please check the date", Toast.LENGTH_SHORT).show();
            }
            else if(startTime == "")
            {
                Toast.makeText(this, "Please check the start time", Toast.LENGTH_SHORT).show();
            }
            else if(endTime == "")
            {
                Toast.makeText(this, "Please check the end time", Toast.LENGTH_SHORT).show();
            }
            else if(ntCount == "")
            {
                Toast.makeText(this, "Please check the count", Toast.LENGTH_SHORT).show();
            }
            else{
                database = FirebaseDatabase.getInstance().getReference("NappyRecords")

                val nappyRecords = Nappy(
                    id = myUuidAsString,
                    type = TypeVal,
                    date = editDate,
                    startTime = startTime,
                    endtTime = endTime,
                    ntcount = ntCount,
                    remarks = remarks
                )

                database.child(myUuidAsString).setValue(nappyRecords).addOnSuccessListener {

                    ui.radioGroupTypeNp.clearCheck()
                    ui.editDateNp.text.clear()
                    ui.editStartTimeNp.text.clear()
                    ui.editEndTimeNp.text.clear()
                    ui.editNappyCountNp.text.clear()
                    ui.editRemarksNp.text.clear()

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
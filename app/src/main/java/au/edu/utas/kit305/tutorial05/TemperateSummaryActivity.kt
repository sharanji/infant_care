package au.edu.utas.kit305.tutorial05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class TemperateSummaryActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var temperateRecyclerview : RecyclerView
    private lateinit var temperateArrayList : ArrayList<Temperature>

    private lateinit var temperateTempArrayList : ArrayList<Temperature>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperate_summary)

        temperateRecyclerview = findViewById(R.id.recycleViewTemperate)
        temperateRecyclerview.layoutManager = LinearLayoutManager(this)
        temperateRecyclerview.setHasFixedSize(true)

        temperateArrayList = arrayListOf<Temperature>()
        temperateTempArrayList = arrayListOf<Temperature>()
        getSleepData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item,menu)

        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Log.d("NappySummaryActivity", "onQueryTextChange: $newText")
                temperateArrayList.clear()
                val searchText = newText?.trim()?.toLowerCase(Locale.getDefault()) ?: ""

                if (searchText.isEmpty()) {
                    temperateTempArrayList.addAll(temperateArrayList)
                } else {
                    for (Sleep in temperateArrayList) {
                        if (Sleep.date?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
                            temperateTempArrayList.add(Sleep)
                        }
                    }
                }

                temperateRecyclerview.adapter?.notifyDataSetChanged()

                return true


            }


        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun getSleepData() {
        dbref = FirebaseDatabase.getInstance().getReference("TemperatureRecords")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val temperate = userSnapshot.getValue(Temperature::class.java)
                        temperateArrayList.add(temperate!!)

                    }


                    temperateTempArrayList.addAll(temperateArrayList)

                    temperateRecyclerview.adapter = TemperateAdapter(temperateTempArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    }
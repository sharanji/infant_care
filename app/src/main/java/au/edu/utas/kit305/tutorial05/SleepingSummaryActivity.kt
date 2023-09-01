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

class SleepingSummaryActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var sleepRecyclerview : RecyclerView
    private lateinit var sleepingArrayList : ArrayList<Sleeping>

    private lateinit var sleepingTempArrayList : ArrayList<Sleeping>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleeping_summary)

        sleepRecyclerview = findViewById(R.id.recycleViewSleeping)
        sleepRecyclerview.layoutManager = LinearLayoutManager(this)
        sleepRecyclerview.setHasFixedSize(true)

        sleepingArrayList = arrayListOf<Sleeping>()
        sleepingTempArrayList = arrayListOf<Sleeping>()
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
                sleepingTempArrayList.clear()
                val searchText = newText?.trim()?.toLowerCase(Locale.getDefault()) ?: ""

                if (searchText.isEmpty()) {
                    sleepingTempArrayList.addAll(sleepingArrayList)
                } else {
                    for (Sleep in sleepingArrayList) {
                        if (Sleep.date?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
                            sleepingTempArrayList.add(Sleep)
                        }
                    }
                }

                sleepRecyclerview.adapter?.notifyDataSetChanged()

                return true


            }


        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun getSleepData() {
        dbref = FirebaseDatabase.getInstance().getReference("SleepingRecords")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val sleep= userSnapshot.getValue(Sleeping::class.java)
                        sleepingArrayList.add(sleep!!)

                    }


                    sleepingTempArrayList.addAll(sleepingArrayList)

                    sleepRecyclerview.adapter = SleepingAdapter(sleepingTempArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}
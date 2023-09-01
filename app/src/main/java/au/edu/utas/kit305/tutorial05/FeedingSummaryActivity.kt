package au.edu.utas.kit305.tutorial05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
//import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import androidx.appcompat.widget.SearchView
import java.util.*
import kotlin.collections.ArrayList

class FeedingSummaryActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var feedRecyclerview : RecyclerView
    private lateinit var feedingArrayList : ArrayList<Feeding>

    private lateinit var feedingTempArrayList : ArrayList<Feeding>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeding_summary)

        feedRecyclerview = findViewById(R.id.recycleViewFeeding)
        feedRecyclerview.layoutManager = LinearLayoutManager(this)
        feedRecyclerview.setHasFixedSize(true)

        feedingArrayList = arrayListOf<Feeding>()
        feedingTempArrayList = arrayListOf<Feeding>()
        getFeedingData()



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

                feedingTempArrayList.clear()
                val searchText = newText?.trim()?.toLowerCase(Locale.getDefault()) ?: ""

                if (searchText.isEmpty()) {
                    feedingTempArrayList.addAll(feedingArrayList)
                } else {
                    for (Feeding in feedingArrayList) {
                        if (Feeding.type?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
                            feedingTempArrayList.add(Feeding)
                        }
                    }
                }

                feedRecyclerview.adapter?.notifyDataSetChanged()

                return true


            }


        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun getFeedingData() {
        dbref = FirebaseDatabase.getInstance().getReference("FeedingRecords")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val feed= userSnapshot.getValue(Feeding::class.java)
                        feedingArrayList.add(feed!!)

                    }

                    feedingTempArrayList.addAll(feedingArrayList)

                    feedRecyclerview.adapter = FeedingAdapter(feedingTempArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
    })


    }
}
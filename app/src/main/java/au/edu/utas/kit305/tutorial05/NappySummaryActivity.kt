package au.edu.utas.kit305.tutorial05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class NappySummaryActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var nappyRecyclerview : RecyclerView
    private lateinit var nappyArrayList : ArrayList<Nappy>

    private lateinit var nappyTempArrayList : ArrayList<Nappy>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nappy_summary)

        nappyRecyclerview = findViewById(R.id.recycleViewNappy)
        nappyRecyclerview.layoutManager = LinearLayoutManager(this)
        nappyRecyclerview.setHasFixedSize(true)

        nappyArrayList = arrayListOf<Nappy>()
        nappyTempArrayList = arrayListOf<Nappy>()
        getNappyData()
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
                nappyTempArrayList.clear()
                val searchText = newText?.trim()?.toLowerCase(Locale.getDefault()) ?: ""

                if (searchText.isEmpty()) {
                    nappyTempArrayList.addAll(nappyArrayList)
                } else {
                    for (Nappy in nappyArrayList) {
                        if (Nappy.type?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
                            nappyTempArrayList.add(Nappy)
                        }
                    }
                }

                nappyRecyclerview.adapter?.notifyDataSetChanged()

                return true


            }


        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun getNappyData() {
        dbref = FirebaseDatabase.getInstance().getReference("NappyRecords")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val nappy= userSnapshot.getValue(Nappy::class.java)
                        nappyArrayList.add(nappy!!)

                    }

                    nappyTempArrayList.addAll(nappyArrayList)

                    nappyRecyclerview.adapter = NappyAdapter(nappyTempArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
package au.edu.utas.kit305.tutorial05

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import au.edu.utas.kit305.tutorial05.databinding.ActivityFeedingBinding
import au.edu.utas.kit305.tutorial05.databinding.ActivityMainBinding
import au.edu.utas.kit305.tutorial05.databinding.FeedingRecordsBinding
import com.google.firebase.database.FirebaseDatabase


class FeedingAdapter(private val feedingList : ArrayList<Feeding>) : RecyclerView.Adapter<FeedingAdapter.MyViewHolder>() {



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.feeding_records,
                parent, false
            )
            return MyViewHolder(itemView)

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


            val currentitem = feedingList[position]
            //holder.id.text = currentitem.id
            holder.feedingDate.text = currentitem.date
            holder.startTime.text = currentitem.startTime
            holder.endTime.text = currentitem.endtTime
            holder.btCount.text = currentitem.btcount
            holder.remarks.text = currentitem.remarks
            holder.type.text = currentitem.type
            holder.side.text = currentitem.side

            holder.id.setOnClickListener {
                val database = FirebaseDatabase.getInstance()
                val ref = database.getReference("FeedingRecords")
                val feedingId = currentitem.id
                if (feedingId != null) {
                    ref.child(feedingId).removeValue().addOnSuccessListener {
                        feedingList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, feedingList.size)
                        Toast.makeText(holder.itemView.context, "Feeding Record deleted successfully", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(holder.itemView.context, "Failed to delete item", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            holder.itemView.setOnClickListener {

                val intent = Intent(holder.itemView.context, EditFeedingActivity::class.java)


                intent.putExtra("id", currentitem.id)
                intent.putExtra("date", currentitem.date)
                intent.putExtra("starttime", currentitem.startTime)
                intent.putExtra("endttime", currentitem.endtTime)
                intent.putExtra("btcount", currentitem.btcount)
                intent.putExtra("remarks", currentitem.remarks)
                intent.putExtra("type", currentitem.type)
                intent.putExtra("side", currentitem.side)


                holder.itemView.context.startActivity(intent)
            }

        }

        override fun getItemCount(): Int {

            return feedingList.size
        }


        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val  id : Button  = itemView.findViewById(R.id.btnDelete)
            val feedingDate: TextView = itemView.findViewById(R.id.tvfeedingDate)
            val startTime: TextView = itemView.findViewById(R.id.tvStartTime)
            val endTime: TextView = itemView.findViewById(R.id.tvEndTime)
            val btCount: TextView = itemView.findViewById(R.id.tvbtCount)
            val remarks: TextView = itemView.findViewById(R.id.tvRemarks)
            val type: TextView = itemView.findViewById(R.id.tvfeedingType)
            val side: TextView = itemView.findViewById(R.id.tvfeedingSide)


        }



    }


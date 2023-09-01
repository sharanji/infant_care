package au.edu.utas.kit305.tutorial05

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class SleepingAdapter(private val sleepingList : ArrayList<Sleeping>) : RecyclerView.Adapter<SleepingAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.sleeping_records,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val currentitem = sleepingList[position]
        //holder.id.text = currentitem.id
        holder.sleepingDate.text = currentitem.date
        holder.startTime.text = currentitem.startTime
        holder.endTime.text = currentitem.endTime
        holder.remarks.text = currentitem.remarks


        holder.id.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("SleepingRecords")
            val sleepId = currentitem.id
            if (sleepId != null) {
                ref.child(sleepId).removeValue().addOnSuccessListener {
                    sleepingList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, sleepingList.size)
                    Toast.makeText(holder.itemView.context, "Sleeping Record deleted successfully", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(holder.itemView.context, "Failed to delete item", Toast.LENGTH_SHORT).show()
                }
            }
        }


        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, EditSleepActivity::class.java)


            intent.putExtra("id", currentitem.id)
            intent.putExtra("date", currentitem.date)
            intent.putExtra("starttime", currentitem.startTime)
            intent.putExtra("endtime", currentitem.endTime)
            intent.putExtra("remarks", currentitem.remarks)



            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {

        return sleepingList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id : Button = itemView.findViewById(R.id.btnDeleteSp)
        val sleepingDate: TextView = itemView.findViewById(R.id.tvfeedingDateSp)
        val startTime: TextView = itemView.findViewById(R.id.tvStartTimeSp)
        val endTime: TextView = itemView.findViewById(R.id.tvEndTimeSp)
        val remarks: TextView = itemView.findViewById(R.id.tvRemarksSp)



    }


}
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

class NappyAdapter(private val nappyList : ArrayList<Nappy>) : RecyclerView.Adapter<NappyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NappyAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.nappy_records,
            parent, false
        )
        return NappyAdapter.MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: NappyAdapter.MyViewHolder, position: Int) {


        val currentitem = nappyList[position]
        //holder.id.text = currentitem.id
        holder.nappyDate.text = currentitem.date
        holder.startTime.text = currentitem.startTime
        holder.endTime.text = currentitem.endtTime
        holder.ntCount.text = currentitem.ntcount
        holder.remarks.text = currentitem.remarks
        holder.type.text = currentitem.type


        holder.id.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("NappyRecords")
            val nappyId = currentitem.id
            if (nappyId != null) {
                ref.child(nappyId).removeValue().addOnSuccessListener {
                    nappyList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, nappyList.size)
                    Toast.makeText(holder.itemView.context, "Nappy Record deleted successfully", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(holder.itemView.context, "Failed to delete item", Toast.LENGTH_SHORT).show()
                }
            }
        }


        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, NappyFeedingActivity::class.java)


            intent.putExtra("id", currentitem.id)
            intent.putExtra("date", currentitem.date)
            intent.putExtra("starttime", currentitem.startTime)
            intent.putExtra("endttime", currentitem.endtTime)
            intent.putExtra("remarks", currentitem.remarks)
            intent.putExtra("ntCount", currentitem.ntcount)
            intent.putExtra("type", currentitem.type)


            holder.itemView.context.startActivity(intent)
        }


    }
    override fun getItemCount(): Int {
        return nappyList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  id : Button = itemView.findViewById(R.id.btnDeleteNp)
        val nappyDate: TextView = itemView.findViewById(R.id.tvnappyDateNp)
        val startTime: TextView = itemView.findViewById(R.id.tvStartTimeNp)
        val endTime: TextView = itemView.findViewById(R.id.tvEndTimeNp)
        val ntCount: TextView = itemView.findViewById(R.id.tvCountNp)
        val remarks: TextView = itemView.findViewById(R.id.tvRemarksNp)
        val type: TextView = itemView.findViewById(R.id.tvnappyTyepNp)



    }
}
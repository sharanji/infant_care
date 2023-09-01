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

class TemperateAdapter(private val temperatureList : ArrayList<Temperature>) : RecyclerView.Adapter<TemperateAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.temperature_records,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val currentitem = temperatureList[position]
        //holder.id.text = currentitem.id
        holder.temperatureDate.text = currentitem.date
        holder.btCount.text = currentitem.btcount
        holder.remarks.text = currentitem.remarks

        holder.id.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("TemperatureRecords")
            val temperatureId = currentitem.id
            if (temperatureId != null) {
                ref.child(temperatureId).removeValue().addOnSuccessListener {
                    temperatureList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, temperatureList.size)
                    Toast.makeText(holder.itemView.context, "Feeding Record deleted successfully", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(holder.itemView.context, "Failed to delete item", Toast.LENGTH_SHORT).show()
                }
            }
        }


        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, EditTemperateActivity::class.java)


            intent.putExtra("id", currentitem.id)
            intent.putExtra("date", currentitem.date)
            intent.putExtra("btcount", currentitem.btcount)
            intent.putExtra("remarks", currentitem.remarks)



            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {

        return temperatureList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  id : Button = itemView.findViewById(R.id.btnDeleteTemp)
        val temperatureDate: TextView = itemView.findViewById(R.id.tvtempDateSp)
        val btCount: TextView = itemView.findViewById(R.id.tvtempCount)
        val remarks: TextView = itemView.findViewById(R.id.tvRemarksTemp)



    }



}
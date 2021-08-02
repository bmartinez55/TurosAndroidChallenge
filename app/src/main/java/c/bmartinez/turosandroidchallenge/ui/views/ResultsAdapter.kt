package c.bmartinez.turosandroidchallenge.ui.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import c.bmartinez.turosandroidchallenge.R
import c.bmartinez.turosandroidchallenge.data.model.Data
import c.bmartinez.turosandroidchallenge.utils.YelpConstants
import com.bumptech.glide.Glide

class ResultsAdapter (private val context: Context, private val data: List<Data>): RecyclerView.Adapter<ResultsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ResultsAdapter.ViewHolder, position: Int) {
        val d = data[position]
        holder.bind(d)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(d: Data){
            itemView.findViewById<TextView>(R.id.itemTitle).text = d.name
            Glide.with(context).load(d.image_url).into(itemView.findViewById(R.id.itemImage))
            val address = d.location.address + " " + d.location.city + ", " + d.location.state + " " + d.location.zipCode
            itemView.findViewById<TextView>(R.id.itemAddress).text = address
            val miles = (d.distance / YelpConstants().metersInMile).toString() + " mi"
            itemView.findViewById<TextView>(R.id.itemDistance).text = miles
        }
    }
}
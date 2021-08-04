package c.bmartinez.yelpclone.ui.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.data.model.Pizza
import c.bmartinez.yelpclone.utils.YelpConstants
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class ResultsAdapter (private val context: Context, private val data: List<Pizza>): RecyclerView.Adapter<ResultsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ResultsAdapter.ViewHolder, position: Int) {
        val d = data[position]
        holder.bind(d)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(p: Pizza){
            itemView.findViewById<TextView>(R.id.itemTitle).text = p.name
            Glide.with(context).load(p.image_url).into(itemView.findViewById(R.id.itemImage))
            val address = p.location.address + " " + p.location.city + ", " + p.location.state + " " + p.location.zipCode
            itemView.findViewById<TextView>(R.id.itemAddress).text = address
            val decFormat = DecimalFormat("#.##")
            val miles = (decFormat.format(p.distance / YelpConstants().metersInMile)).toString() + " mi"
            itemView.findViewById<TextView>(R.id.itemDistance).text = miles
        }
    }
}
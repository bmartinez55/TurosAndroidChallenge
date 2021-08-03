package c.bmartinez.yelpclone.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.data.model.Pizza
import c.bmartinez.yelpclone.data.repo.YelpRepository
import c.bmartinez.yelpclone.data.services.RetrofitService
import c.bmartinez.yelpclone.ui.viewmodels.MainViewModel
import c.bmartinez.yelpclone.ui.viewmodels.MyViewModelFactory
import com.google.gson.Gson
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name
    lateinit var viewModel: MainViewModel
    lateinit var adapter: ResultsAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)

        getData()
    }

    private fun getData() {
        val data = mutableListOf<Pizza>()
        adapter = ResultsAdapter(this, data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofitService = RetrofitService.getInstance()
        val yelpRepo = YelpRepository(retrofitService)

        viewModel = ViewModelProvider(this, MyViewModelFactory(yelpRepo)).get(MainViewModel::class.java)
        viewModel.getAllPizzaLocations()
        viewModel.pizzaData.observe(this, {
            if(it.isEmpty()){
                Log.d(TAG, "Search came back empty")
            } else {
                data.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
//        viewModel.beerData.observe(this, {
//            for(m in it){
//                data.add(m)
//            }
//        })
        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            Log.d(MainActivity::class.java.name, it)
            adapter.notifyDataSetChanged()
        })

        //viewModel.getAllBeerLocations()
    }

}
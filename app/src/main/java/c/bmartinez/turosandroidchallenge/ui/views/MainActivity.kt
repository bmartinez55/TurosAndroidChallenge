package c.bmartinez.turosandroidchallenge.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.bmartinez.turosandroidchallenge.R
import c.bmartinez.turosandroidchallenge.data.model.Data
import c.bmartinez.turosandroidchallenge.data.repo.YelpRepository
import c.bmartinez.turosandroidchallenge.data.services.RetrofitService
import c.bmartinez.turosandroidchallenge.ui.viewmodels.MainViewModel
import c.bmartinez.turosandroidchallenge.ui.viewmodels.MyViewModelFactory

class MainActivity : AppCompatActivity() {

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
        val data = mutableListOf<Data>()
        adapter = ResultsAdapter(this, data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val retrofitService = RetrofitService.getInstance()
        val yelpRepo = YelpRepository(retrofitService)
        viewModel = ViewModelProvider(this, MyViewModelFactory(yelpRepo)).get(MainViewModel::class.java)
        viewModel.pizzaData.observe(this, {
            for(x in it){
                data.add(x)
            }
        })
        viewModel.beerData.observe(this, {
            for(m in it){
                data.add(m)
            }
        })
        adapter.notifyDataSetChanged()
        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            Log.d(MainActivity::class.java.name, it)
            adapter.notifyDataSetChanged()
        })
        viewModel.getAllPizzaLocations()
        viewModel.getAllBeerLocations()
    }

}
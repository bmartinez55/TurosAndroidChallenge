package c.bmartinez.yelpclone.ui.views

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.data.model.Results
import c.bmartinez.yelpclone.data.repo.YelpRepository
import c.bmartinez.yelpclone.data.services.RetrofitService
import c.bmartinez.yelpclone.ui.viewmodels.MainViewModel
import c.bmartinez.yelpclone.ui.viewmodels.MyViewModelFactory

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name
    lateinit var viewModel: MainViewModel
    lateinit var adapter: ResultsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var menu: Menu
    lateinit var retrofitService: RetrofitService
    lateinit var yelpRepository: YelpRepository
    var data = mutableListOf<Results>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ResultsAdapter(this, data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        retrofitService = RetrofitService.getInstance()
        yelpRepository = YelpRepository(retrofitService)
        viewModel = ViewModelProvider(this, MyViewModelFactory(yelpRepository)).get(MainViewModel::class.java)

        //getData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu!!
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search)
        val searchView: androidx.appcompat.widget.SearchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(input: String?): Boolean {
                if(!input.isNullOrBlank()){
                    searchData(input)
                    return false
                }
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.search -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchData(searchTerm: String){
        viewModel.getSearchResults(searchTerm)
        viewModel.data.observe(this, {
            if(it.isEmpty()){
                Log.d(TAG, "Search came back empty")
            } else {
                if(data.isNotEmpty()){
                    data.clear()
                }
                data.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    //private fun getData() {


//        viewModel = ViewModelProvider(this, MyViewModelFactory(yelpRepo)).get(MainViewModel::class.java)
//        viewModel.getAllPizzaLocations()
//        viewModel.pizzaData.observe(this, {
//            if(it.isEmpty()){
//                Log.d(TAG, "Search came back empty")
//            } else {
//                data.addAll(it)
//                adapter.notifyDataSetChanged()
//            }
//        })
//        viewModel.beerData.observe(this, {
//            for(m in it){
//                data.add(m)
//            }
//        })
//        viewModel.errorMessage.observe(this, {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//            Log.d(TAG, it)
//            adapter.notifyDataSetChanged()
//        })
//
//        //viewModel.getAllBeerLocations()
//    }

}
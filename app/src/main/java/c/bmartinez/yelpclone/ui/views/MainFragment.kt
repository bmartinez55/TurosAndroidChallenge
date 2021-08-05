package c.bmartinez.yelpclone.ui.views

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.data.model.Results
import c.bmartinez.yelpclone.data.repo.YelpRepository
import c.bmartinez.yelpclone.data.services.RetrofitService
import c.bmartinez.yelpclone.ui.viewmodels.MainViewModel
import c.bmartinez.yelpclone.ui.viewmodels.MyViewModelFactory

class MainFragment: Fragment() {

    private val TAG = MainFragment::class.java.name
    lateinit var viewModel: MainViewModel
    lateinit var adapter: ResultsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var menu: Menu
    lateinit var retrofitService: RetrofitService
    lateinit var yelpRepository: YelpRepository
    var data = mutableListOf<Results>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_frag_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = ResultsAdapter(this.requireContext(), data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        retrofitService = RetrofitService.getInstance()
        yelpRepository = YelpRepository(retrofitService)
        viewModel = ViewModelProvider(this, MyViewModelFactory(yelpRepository)).get(MainViewModel::class.java)

        getPopularLocations()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)

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
    }

    private fun getPopularLocations() {
        viewModel.getPopularLocations()
        viewModel.data.observe(viewLifecycleOwner, {
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
}
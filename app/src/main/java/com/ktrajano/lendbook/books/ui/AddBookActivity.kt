package com.ktrajano.lendbook.books.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ktrajano.lendbook.books.adapters.BooksAdapter
import com.ktrajano.lendbook.databinding.ActivityAddBookBinding
import com.ktrajano.lendbook.models.ApiStatus
import com.ktrajano.lendbook.models.VolumeInfo
import com.ktrajano.lendbook.utils.DashboardTabsProvider
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddBookActivity : DashboardTabsProvider, AppCompatActivity() {

  private lateinit var binding : ActivityAddBookBinding
  private val viewModel by viewModel<AddBookViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAddBookBinding.inflate(layoutInflater)
    setContentView(binding.root)
    observeState()
  }

  override fun setUpTabs() {

  }

  private fun observeState(){
    getSearchResults()
    lifecycleScope.launch {
      viewModel.books.collect{ state ->
        when(state.status){
          ApiStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
          ApiStatus.SUCCESS -> {
            binding.progressBar.visibility = View.GONE
            if(state.data != null){
              binding.bookResults.visibility = View.VISIBLE
              val adapter = BooksAdapter(state.data, ::setupClickListener)
              val linearLayout = LinearLayoutManager(this@AddBookActivity,RecyclerView.VERTICAL,false)
              binding.bookResults.layoutManager = linearLayout
              binding.bookResults.adapter = adapter
            }
          }
          ApiStatus.ERROR -> {
            binding.bookResults.visibility = View.GONE
            binding.noResultsBooks.root.visibility = View.VISIBLE
          }
        }
      }
    }
  }

  private fun setupClickListener() {
    startActivity(Intent(this, BookDetailsActivity::class.java))
  }

  private fun getSearchResults(){
   binding.searchTitleView.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
     override fun onQueryTextSubmit(title: String?): Boolean {
       lifecycleScope.launch {
         viewModel.getBooksByTitle(title!!)
       }
       return true
     }

     override fun onQueryTextChange(p0: String?): Boolean {
       return false
     }
   })
  }
}
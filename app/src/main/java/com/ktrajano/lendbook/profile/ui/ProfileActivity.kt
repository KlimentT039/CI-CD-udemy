package com.ktrajano.lendbook.profile.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.ktrajano.lendbook.books.ui.AddBookActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ktrajano.lendbook.databinding.ActivityProfileBinding
import com.ktrajano.lendbook.databinding.DashboardTabsBinding
import com.ktrajano.lendbook.databinding.NoBooksLayoutBinding
import com.ktrajano.lendbook.utils.DashboardTabsProvider
import com.ktrajano.lendbook.utils.show
import com.ktrajano.lendbook.utils.startTabsActivity
import kotlinx.coroutines.launch

class ProfileActivity : DashboardTabsProvider, AppCompatActivity() {

  private lateinit var binding : ActivityProfileBinding
  private lateinit var noBooksLayoutBinding: NoBooksLayoutBinding
  private lateinit var tabsLayoutBinding: DashboardTabsBinding
  private val viewModel by viewModel<ProfileViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProfileBinding.inflate(layoutInflater)
    noBooksLayoutBinding = binding.noBooksLayout
    tabsLayoutBinding = binding.dashboardTabs
    setupViews()
    setUpTabs()
    setContentView(binding.root)
  }

  override fun setUpTabs() {
    binding.dashboardTabs.profile.setOnClickListener {
      //Do nothing
    }
    tabsLayoutBinding.addBook.setOnClickListener {
      this.startTabsActivity(this,AddBookActivity())
    }

  }

  private fun setupViews(){
    noBooksLayoutBinding.imageView.setOnClickListener {
      this.startTabsActivity(this,AddBookActivity())
    }
    lifecycleScope.launch {
      viewModel.getCurrentUserInfo()
      viewModel.currentUser.collect { currentUser ->
        binding.username.text = currentUser.name
        binding.age.text = currentUser.age.toString()
        binding.scroolView.show(currentUser.books.isNotEmpty())
        binding.noBooksLayout.root.show(currentUser.books.isEmpty())
      }
    }
  }

}
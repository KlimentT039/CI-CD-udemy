package com.ktrajano.lendbook.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ktrajano.lendbook.databinding.ActivityRegisterBinding
import com.ktrajano.lendbook.profile.ui.ProfileActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

  private lateinit var binding: ActivityRegisterBinding

  private val viewModel: AuthenticationViewModel by viewModel()

  private val userData: Observer<AuthenticationEvents> = Observer<AuthenticationEvents> {
    if (it != AuthenticationEvents.UserNotAuthenticated) {
      lifecycleScope.launch {
        startActivity(Intent(this@RegisterActivity, ProfileActivity::class.java))
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegisterBinding.inflate(layoutInflater)
    setupButton()
    setContentView(binding.root)
  }

  private fun setupButton() {
    viewModel.authEvents.observe(this, userData)
    binding.signButton.setOnClickListener {
      viewModel.registerUser(
        binding.username.text.toString(),
        binding.mailLogin.text.toString(),
        binding.passwordConfirm.text.toString(),
        binding.datePicker.text.toString().toInt()
      )
      viewModel.checkIfUserIsAuthenticatedInFirebase()
    }
  }

  private fun dateToAge() {
    //TODO
  }

  private fun validationOfFields() {
    //TODO
  }

}
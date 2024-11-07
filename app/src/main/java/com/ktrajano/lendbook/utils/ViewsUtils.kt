package com.ktrajano.lendbook.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View

fun View.show(show: Boolean){
  if(show)
    this.visibility = View.VISIBLE
  else
    this.visibility = View.GONE
}

fun Activity.startTabsActivity(context: Context,activity : Activity) {
  startActivity(Intent(context,activity::class.java))
}
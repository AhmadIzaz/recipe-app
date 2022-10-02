package com.example.recipeapp.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ProgressBar
import com.example.recipeapp.R
import com.github.ybq.android.spinkit.style.ThreeBounce

class ProgressBarDialog(context: Context) : Dialog(context) {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_loading_indicator)
        setCancelable(false)
        progressBar = findViewById(R.id.progress_bar)
        val threeBounce = ThreeBounce()
        progressBar.indeterminateDrawable = threeBounce
    }


}


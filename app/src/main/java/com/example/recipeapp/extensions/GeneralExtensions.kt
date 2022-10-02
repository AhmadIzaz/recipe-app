package com.example.recipeapp.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


fun <T> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

fun <T> T?.isNull(f: () -> Unit) {
    if (this == null) f()
}

inline fun <reified T> Any.cast(crossinline block: (T) -> Unit) {
    (this as? T).notNull {
        block(it)
    }
}

fun Context.showToast(toastMessage: String) =
    Toast.makeText(
        this,
        toastMessage,
        Toast.LENGTH_SHORT
    ).show()

fun TextView.textColor(context: Context, color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

private fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager?.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}




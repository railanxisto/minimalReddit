package com.sample.reddit.ui.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    fun showSnackbar(message: String) =
        Snackbar.make(this.requireView().rootView, message, Snackbar.LENGTH_LONG).show()

    fun showToast(message: String) =
        Toast.makeText(context,  message, Toast.LENGTH_LONG).show()
}
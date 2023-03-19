package com.example.diceroller

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AboutDialog : DialogFragment() {
    private var message: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let {
            message = it.getString("MESSAGE")
        }
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Hello this is about")
                .setNegativeButton(R.string.Ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        @JvmStatic
        fun newInstance() = AboutDialog()
    }
}
package com.tashila.pleasewait

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

class CustomProgressDialog(private val context: Context) : PleaseWaitDialog(context) {
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Toast.makeText(context, "Dialog dismissed", LENGTH_SHORT).show();
    }
}
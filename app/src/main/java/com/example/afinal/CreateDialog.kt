package com.example.afinal

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.afinal.data.Phone


class DelDialog(context: Context):Dialog(context){

//    private var message: String?= null
//    private var toEditName: String?= null
//    private var toEditphone: String?= null

    private var cancelListener: IOnCancelListener? = null
    private var confirmListener: IOnConfirmListener? = null
//    public var txtName:EditText?=null
//    public var txtPhone:EditText?=null

//    fun setEditMessage(phone: Phone): DelDialog {
//        this.toEditName=phone.name
//        this.toEditphone=phone.phone
//        return this
//    }
//
//    fun setMessage(message: String?): DelDialog {
//        this.message = message
//        return this
//    }
//    fun setFields(phone: Phone){
//
//        Log.d("TAG", "setFields: ${phone.name}≤${txtName}" )
////        findViewById<EditText>(R.id.username).setText(phone.name)
////        findViewById<EditText>(R.id.password).setText(phone.name)
//
//    }

    fun setConfirm(Listener: IOnConfirmListener): DelDialog {
        this.confirmListener = Listener
        return this
    }

    fun setCancel(Listener: IOnCancelListener): DelDialog {
        this.cancelListener = Listener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_dialog)

        val btNegative: Button = findViewById(R.id.dialog_negative)
        val btPositive: Button= findViewById(R.id.dialog_positive)


        btPositive.setOnClickListener(this::clickListener)
        btNegative.setOnClickListener(this::clickListener)
    }

    private fun clickListener(v: View){
        when(v.id){
            R.id.dialog_positive -> {
                confirmListener?.onConfirm(this)

            }
            R.id.dialog_negative -> {
                cancelListener?.onCancel(this)
            }
        }
    }

    interface IOnCancelListener {
        fun onCancel(dialog: DelDialog?)
    }

    interface IOnConfirmListener {
        fun onConfirm(dialog: DelDialog?)
    }
}
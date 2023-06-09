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


class CreateDialog(context: Context):Dialog(context){

    private var message: String?= null
    private var toEditName: String?= null
    private var toEditphone: String?= null

    private var cancelListener: IOnCancelListener? = null
    private var confirmListener: IOnConfirmListener? = null
    public var txtName:EditText?=null
    public var txtPhone:EditText?=null

    fun setEditMessage(phone: Phone): CreateDialog {
        this.toEditName=phone.name
        this.toEditphone=phone.phone
        return this
    }

    fun setMessage(message: String?): CreateDialog {
        this.message = message
        return this
    }
    fun setFields(phone: Phone){

        Log.d("TAG", "setFields: ${phone.name}≤${txtName}" )
//        findViewById<EditText>(R.id.username).setText(phone.name)
//        findViewById<EditText>(R.id.password).setText(phone.name)

    }

    fun setConfirm(Listener: IOnConfirmListener): CreateDialog {
        this.confirmListener = Listener
        return this
    }

    fun setCancel(Listener: IOnCancelListener): CreateDialog {
        this.cancelListener = Listener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_dialog)

        val btNegative: Button = findViewById(R.id.dialog_negative)
        val btPositive: Button= findViewById(R.id.dialog_positive)
        val tvContent: TextView = findViewById(R.id.dialog_yes_no_message)
        txtName=findViewById(R.id.username)
        txtPhone=findViewById(R.id.password)
        Log.d("TAG", "onCreate: ${txtName}")
        tvContent.text= message
        txtName?.setText(toEditName)
        txtPhone?.setText(toEditphone)
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
        fun onCancel(dialog: CreateDialog?)
    }

    interface IOnConfirmListener {
        fun onConfirm(dialog: CreateDialog?)
    }
}
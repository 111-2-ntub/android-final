package com.example.afinal

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.data.Phone


class MyAdapter() :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    private var delListener: MyAdapter.DelListener? = null
    private var editListener: MyAdapter.EditListener? = null

    private var phoneList=emptyList<Phone>()


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView
        val txtPhone: TextView
        val btnDel: ImageButton
        val btnEdit: ImageButton

        init {
            // Define click listener for the ViewHolder's View.
            txtName = view.findViewById(R.id.txtName)
            txtPhone = view.findViewById(R.id.txtPhone)
            btnDel = view.findViewById(R.id.btnDel)
            btnEdit = view.findViewById(R.id.btnEdit)
        }
    }

    fun update(datasets: List<Phone>) {
        phoneList = datasets
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_row, parent, false)
        return ViewHolder(view)
    }

    fun setDel(dListener: MyAdapter.DelListener) {
        this.delListener = dListener
        notifyDataSetChanged()

    }

    fun setEdit(eListener: EditListener){
        this.editListener=eListener
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName.text = phoneList[position].name
        holder.txtPhone.text = phoneList[position].phone
        holder.btnDel.setOnClickListener {
            Log.d("TAG", "onBindViewHolder: before")
            delListener?.onDel(position)
            Log.d("TAG", "onBindViewHolder: after")
        }
        holder.btnEdit.setOnClickListener {
            Log.d("TAG", "onBindViewHolder: before on edit")

            editListener?.onEdit(position)
            Log.d("TAG", "onBindViewHolder: after on edit")
        }


    }

    override fun getItemCount() = phoneList.size

    fun getItem(position: Int): Phone {
        return phoneList.get(position)
    }


    interface DelListener {
        fun onDel(p:Int)
    }

    interface EditListener {
        fun onEdit(p:Int)
    }
}

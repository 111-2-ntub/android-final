package com.example.afinal

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.data.Phone
import java.io.File
import java.io.FileInputStream


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
        val imageview:ImageView

        init {
            // Define click listener for the ViewHolder's View.
            txtName = view.findViewById(R.id.txtName)
            txtPhone = view.findViewById(R.id.txtPhone)
            btnDel = view.findViewById(R.id.btnDel)
            btnEdit = view.findViewById(R.id.btnEdit)
            imageview=view.findViewById(R.id.imageView)

        }

    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.clearAnimation()

        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,R.anim.scale_in_scroll))
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
        Log.d("TAG", "onBindViewHolder:uri  ${phoneList[position].image}")
        if(phoneList[position].image!=""){
            holder.imageview.setImageBitmap(
                phoneList[position].image?.let { getImageFromStorage(it) }
            )

        }
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
    fun getImageFromStorage(path: String): Bitmap? {
        var f = File(path);
        var options = BitmapFactory.Options();
        options.inJustDecodeBounds = false;
if(        f.isFile()){
    return BitmapFactory.decodeStream(FileInputStream(f), null, options)
}
        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, 512, 512);
    return null
    }

    interface DelListener {
        fun onDel(p:Int)
    }

    interface EditListener {
        fun onEdit(p:Int)
    }
}

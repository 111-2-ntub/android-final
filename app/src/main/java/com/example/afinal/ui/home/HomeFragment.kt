package com.example.afinal.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.MyAdapter
import com.example.afinal.R
import com.example.afinal.data.Phone
import com.example.afinal.data.PhoneViewModel
import com.example.afinal.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mPhoneViewModel:PhoneViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)



        val recyclerView= _binding!!.recyclerView
        val adapter= MyAdapter()
        val linearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter=adapter

        mPhoneViewModel=ViewModelProvider(this).get(PhoneViewModel::class.java)
        mPhoneViewModel.getAll.observe(viewLifecycleOwner, androidx.lifecycle.Observer {phone->
            Log.d("TAG", "onCreate: ${phone.size.toString()}")
            adapter.update(phone)
        })





        adapter.setDel(object : MyAdapter.DelListener {
            override fun onDel(p: Int) {
                Log.d("TAG", "onDel: before")
                mPhoneViewModel.delPhone(adapter.getItem(p))
                Log.d("TAG", "onDel: ater")
                Log.d("TAG", "onDel: ${mPhoneViewModel.getAll.value?.size}")

            }
        })

        val root: View = binding.root

        adapter.setEdit(object : MyAdapter.EditListener {
            override fun onEdit(p: Int) {
                val phone=adapter.getItem(p)

                val bundle = bundleOf("user_name" to phone.name,"phone" to phone.phone,"id" to phone.id,"bathday" to phone.bathday.toString())
//                val bundle = bundleOf("phone" to phone)
//                val action = SpecifyAmountFragmentDirections.confirmationAction(amount)

                findNavController().navigate(R.id.action_navigation_home_to_navigation_dashboard,bundle)
            }

        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
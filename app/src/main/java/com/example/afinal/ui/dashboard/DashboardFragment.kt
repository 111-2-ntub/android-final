package com.example.afinal.ui.dashboard

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.afinal.R
import com.example.afinal.data.Phone
import com.example.afinal.data.PhoneViewModel
import com.example.afinal.databinding.FragmentDashboardBinding
import java.time.LocalDate
import java.util.Calendar

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var mPhoneViewModel: PhoneViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mPhoneViewModel = ViewModelProvider(this).get(PhoneViewModel::class.java)
        val user_name = arguments?.getString("user_name")
        val p_id = arguments?.getInt("id")
        val pre_phone = arguments?.getString("phone")
        val bathday = arguments?.getString("bathday")
        if (user_name == null) {


//        _binding!!.editTextDate.text
        } else {

            _binding!!.textView.text = "編輯聯絡人"
            _binding!!.txtName.setText(user_name)
            _binding!!.txtPhone.setText(pre_phone)
            _binding!!.editTextDate.setText(bathday)
        }



        Log.d("TAG", "onConfirm: enter $pre_phone")
        _binding!!.btnCancel.setOnClickListener({
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_home)
        })
        _binding!!.imageButton.setOnClickListener {
            var cal = Calendar.getInstance()
            context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        cal.set(year, month, dayOfMonth)
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )
            }

        }
        Log.d("TAG", "onConfirm: after a${p_id==null},${pre_phone==null}")

        _binding!!.btnCreate.setOnClickListener({
            val name = _binding!!.txtName.text.toString()
            val phone = _binding!!.txtPhone!!.text.toString()
            val bathday = _binding!!.editTextDate.text.toString()
            if (name != null && phone != null && bathday != null) {
                if (pre_phone == null) {
                    Log.d("TAG", "phone==null ${pre_phone==null}")
                    insert(name, phone, bathday)
                } else {
                    Log.d("TAG", "phone!=null ${pre_phone==null}")
                    if (p_id != null) {
                        update(name, phone, bathday, p_id)
                    }

                }
            } else {

            }


        })
        return root
    }

    fun update(name: String, phone: String, bathday: String, p_id: Int) {
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(bathday)) {
            Log.d("TAG", "onConfirm: is empty")
        } else {
            Log.d("TAG", "onConfirm: before update")
            var Inref = this
            Inref.run {
                mPhoneViewModel.update(Phone(p_id, name, phone, bathday.toString()))
            }
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_home)

            Log.d("TAG", "onConfirm: after update")
        }
    }

    fun insert(name: String, phone: String, bathday: String) {
        if (name != null && phone != null && bathday != null) {

            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(bathday)) {
                Log.d("TAG", "onConfirm: is empty")
            } else {
                Log.d("TAG", "onConfirm: before add")
                var Inref = this
                Inref.run {
                    mPhoneViewModel.addPhone(Phone(0, name, phone, bathday.toString()))
                }
                findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_home)

                Log.d("TAG", "onConfirm: after add")
            }
        } else {
            Log.d("TAG", "onConfirm: else${_binding!!.txtName?.text}")

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
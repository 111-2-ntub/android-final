package com.example.afinal.ui.dashboard

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.afinal.MainActivity
import com.example.afinal.R
import com.example.afinal.data.Phone
import com.example.afinal.data.PhoneViewModel
import com.example.afinal.databinding.FragmentDashboardBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar


class DashboardFragment : Fragment() {
    val IMAGE_PICK_CODE=1000
    val PERMISSION_CODE=1001
    val CROPPED_IMAGE_PATH = "cropped_image_path"
    val EXTRA_IMAGE_URI = "cropped_image_path"
    var path="";

    val FIXED_ASPECT_RATIO = "extra_fixed_aspect_ratio"
    val EXTRA_ASPECT_RATIO_X = "extra_aspect_ratio_x"
    val EXTRA_ASPECT_RATIO_Y = "extra_aspect_ratio_y"

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
                        _binding!!.editTextDate.setText("${year}/${month+1}/${dayOfMonth}")
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)

                ).show()
            }

        }
        Log.d("TAG", "onConfirm: after a${p_id==null},${pre_phone==null}")

        _binding!!.btnCreate.setOnClickListener({
            val name_e = _binding!!.txtName.text.toString()
            val phone_e = _binding!!.txtPhone!!.text.toString()
            val bathday_e = _binding!!.editTextDate.text.toString()
            val image =path
            if (name_e != null && phone_e != null && bathday != null) {
                if (pre_phone == null) {
                    Log.d("TAG", "phone==null ${pre_phone==null}")
                    insert(name_e, phone_e, bathday_e)
                } else {
                    Log.d("TAG", "phone!=null ${pre_phone==null}")
                    if (p_id != null) {
                        update(name_e, phone_e, bathday, p_id)
                    }

                }
            } else {

            }


        })
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Handle the result here
//            _binding.imageView2.setImageBitmap(result.data)
        }


        _binding!!.imageView2.setOnClickListener({

            if(ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                Log.d("OMG", "onCreateView:have generated ")

                pickImageFromGallery()

            }
            else{
                Log.d("OMG", "onCreateView:haven't generated ")
                permissionPhoto()
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
                mPhoneViewModel.update(Phone(p_id, name, phone, bathday.toString(),path))
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
                    mPhoneViewModel.addPhone(Phone(0, name, phone, bathday.toString(),path))
                }
                findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_home)

                Log.d("TAG", "onConfirm: after add")
            }
        } else {
            Log.d("TAG", "onConfirm: else${_binding!!.txtName?.text}")

        }
    }

//    val pickMediaActivityResultLauncher =
//        rememberLauncherForActivityResult(
//        contract = GetMediaActivityResultContract()
//    ) { uri: Uri? ->
//        //use the received Uri
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun permissionPhoto() {
        ActivityCompat.requestPermissions(
            MainActivity(),arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ), 0
        )
    }
    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this.requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val croppedImage: Bitmap

            croppedImage= MediaStore.Images.Media.getBitmap(context?.contentResolver,
                data?.data
                )
            try {
                val path_v: String = saveToInternalStorage(this.requireContext(), croppedImage)
               path=path_v

            } catch (e: IOException) {
                e.printStackTrace()
            }
            binding.imageView2.setImageURI(data?.data)
        }
    }
    @Throws(IOException::class)
    private fun saveToInternalStorage(context: Context, bitmapImage: Bitmap): String {
        val cw = ContextWrapper(context)

        // Path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)

        // Create imageDir

        val mypath = File(directory, "image.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            //Bitmap scaledBitmap = getCompressedBitmap(bitmapImage);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 70, fos)
        } catch (e: Exception) {
            Log.d("TAG", "saveToInternalStorage: error")
            e.printStackTrace()
        } finally {
            fos!!.close()
        }
        return directory.absolutePath+"/image.jpg"
    }
}

class GetMediaActivityResultContract : ActivityResultContracts.GetContent() {

    override fun createIntent(context: Context, input: String): Intent {
        return super.createIntent(context, input).apply {
            // Force only images and videos to be selectable
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
        }
    }
}
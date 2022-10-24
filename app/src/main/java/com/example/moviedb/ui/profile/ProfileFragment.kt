package com.example.moviedb.ui.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentProfileBinding
import com.example.moviedb.workers.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    private val REQUEST_CODE_PERMISSION = 201

    private val storagePermissionCode = 1
    private var mActivity: Activity? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            profileViewModel.statusLogin(false)
        }

        binding.btnUpdate.setOnClickListener{ update() }
        binding.ivProfpic.setOnClickListener { changePicture() }

    }

    private fun changePicture() {
        checkingPermissions()
    }

    private fun checkingPermissions() {
        if (isGranted(
                this,
                android.Manifest.permission.CAMERA,
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private fun openGallery() {
//        getIntent().type = "image/*"
//        galleryResult.launch("image/*")
        Toast.makeText(requireContext(), "to be implemented", Toast.LENGTH_SHORT).show()
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent.toString())
    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.ivProfpic.load(result) {
                target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    profileViewModel.uploadImage(Utils.convertBitmapToString(bitmap))
                    profileViewModel.applyBlur(getImageUri(bitmap))
                }
            }
        }

    private fun getImageUri(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            bitmap,
            "Initial Profile Picture",
            null
        )
        return Uri.parse(path)
    }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        binding.ivProfpic.setImageBitmap(bitmap)
    }

    private fun isGranted(
        activity: Fragment,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(requireActivity(), permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setNegativeButton("Ok") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun update() {
        val username = binding.etUsername.text.toString()
        val fullname = binding.etFullname.text.toString()
        val address = binding.etPadress.text.toString()

        profileViewModel.editAccount(username, fullname, address)
        Toast.makeText(requireContext(), "Berhasil mengupdate", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_profileFragment_to_noteFragment)
    }

}
package com.example.drawingapp

import android.graphics.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentHomeScreenBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

/**
 * A simple [Fragment] subclass.
 * Use the [HomeScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeScreenFragment : Fragment()
{
    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel: DrawingViewModel by lazy { (activity as MainActivity).viewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        val viewModel by lazy { (activity as MainActivity).viewModel }
        binding.newCanvasButton.setOnClickListener{
            findNavController().navigate(R.id.newCanvasButton)
            viewModel.bitmapCanvas.value!!.drawColor(Color.WHITE)
            viewModel.setCurrentDrawingName("")
        }

        binding.existingDrawing.setOnClickListener{
            findNavController().navigate(R.id.existingDrawing)
        }

        binding.ViewSharedDrawingsButton.setOnClickListener{
            findNavController().navigate(R.id.action_fragment_home_screen_to_sharedDrawingsFragment)
        }

        binding.logInButton.setOnClickListener{
            findNavController().navigate(R.id.action_fragment_home_screen_to_logInFragment)
        }

        getSharedDrawingData()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getSharedDrawingData() {
        val db = Firebase.firestore
        val collection = db.collection(Firebase.auth.currentUser!!.email!!)
        collection.get().addOnSuccessListener { result ->
            for (doc in result) {
                viewModel.addTitle(doc.data.getValue("Drawing Title").toString())
                Log.e("TEST", "Added '${doc.data.getValue("Drawing Title")}' to Titles")
                viewModel.addAuthor(doc.data.getValue("Author").toString())
                Log.e("TEST", "Added '${doc.data.getValue("Author")}' to Titles")
            }

            for (i in 0 until viewModel.getTitlesSize()) {
                var downloadedBitmap: Bitmap
                val ref = Firebase.storage.reference
                val fileRef = ref.child(
                    "${Firebase.auth.currentUser!!.email}/${viewModel.getTitle(i)}.png"
                )
                fileRef.getBytes(10 * 1024 * 1024)
                    .addOnSuccessListener { bytes ->
                        downloadedBitmap = BitmapFactory.decodeByteArray(
                            bytes, 0, bytes.size
                        )
                        viewModel.addBitmap(downloadedBitmap)
                        Log.e("DOWNLOAD_IMAGE", "Successfully received image")

                    }
                    .addOnFailureListener { e ->
                        Log.e("DOWNLOAD_IMAGE", "Failed to get image $e")
                    }
            }
        }
            .addOnFailureListener { exception ->
                Log.w("Uh oh", "Error getting documents.", exception)
            }
    }
}
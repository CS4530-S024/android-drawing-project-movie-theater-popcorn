package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.fragment.app.DialogFragment
import com.example.drawingapp.databinding.FragmentShareDrawingBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.util.Date

class ShareDrawingFragment : DialogFragment() {
    private lateinit var binding: FragmentShareDrawingBinding
    private val viewModel by lazy{(activity as MainActivity).viewModel}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShareDrawingBinding.inflate(layoutInflater)

        binding.doneButton.setOnClickListener{
            if (viewModel.currentDrawingName.value!! != "") {
                if(Firebase.auth.currentUser != null) {
                    val bitmap = viewModel.bitmap.value!!
                    val baos = ByteArrayOutputStream()
                    //save it into PNG format (in memory, not a file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos)
                    val data = baos.toByteArray() //bytes of the PNG
                    //upload it to firestore object storage
                    val ref = Firebase.storage.reference

                    val fileRef = ref.child(
                        "${binding.emailAddressTB.text}/${viewModel.currentDrawingName.value!!}.png")
                    var uploadTask = fileRef.putBytes(data)
                    uploadTask
                        .addOnFailureListener{
                            Toast.makeText(activity, "Sharing failed", Toast.LENGTH_LONG).show();}
                        .addOnSuccessListener {
                            Toast.makeText(activity, "Sharing successful!", Toast.LENGTH_LONG).show();}

                    val document = mapOf("Author" to Firebase.auth.currentUser!!.email,
                        "Drawing Title" to viewModel.currentDrawingName.value!!
                    )
                    val db = Firebase.firestore
                    db.collection("${binding.emailAddressTB.text}/").document(viewModel.currentDrawingName.value!!)
                        .set(document)
                        .addOnSuccessListener{Log.e("UPLOAD", "DOC UPLOAD SUCCESSFUL!")}
                        .addOnFailureListener{e -> Log.e("UPLOAD", "DOC UPLOAD FAILED!: $e")}

                }
                else{
                    Toast.makeText(activity, "Please log in before sharing", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(activity, "Please name the drawing before sharing", Toast.LENGTH_LONG).show();
            }

        }

        return binding.root
    }

    companion object {
        const val TAG = "ShareDrawingDialog"
    }
}
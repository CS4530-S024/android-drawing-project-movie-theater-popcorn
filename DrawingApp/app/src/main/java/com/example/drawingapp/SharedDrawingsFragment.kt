package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentDrawingCompBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.google.firestore.v1.DocumentChange


class SharedDrawingsFragment : Fragment() {
    val viewModel: DrawingViewModel by lazy {(activity as MainActivity).viewModel}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentDrawingCompBinding.inflate(layoutInflater)
        //ComposeView gives us a `Composable` context to run functions in
        binding.composeView.setContent {
            AllSharedDrawings()
        }

        return binding.root
    }

    @Composable
    fun AllSharedDrawings(){
        var user by remember { mutableStateOf(Firebase.auth.currentUser) }
        Column{
        Box {
                Button(onClick = { findNavController().navigate(
                        R.id.action_sharedDrawingsFragment_to_fragment_home_screen)
                }) { Text("Go Back") }
        }
            if (user == null) { //show the login stuff only if the user hasn't logged in yet
                Column {
                    //UI for inputting username and password
                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    Text("Not logged in")
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") })
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Row {
                        Button(onClick = {
                            Firebase.auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener{ task ->
                                    if (task.isSuccessful) {
                                        user = Firebase.auth.currentUser
                                    } else {
                                        Toast.makeText(context, "login failed, try again", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }) {
                            Text("Log In")
                        }
                        Button(onClick = {
                            Firebase.auth.createUserWithEmailAndPassword(
                                email,
                                password
                            )
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        user = Firebase.auth.currentUser
                                    } else {
                                        Toast.makeText(context, "Create user failed, try again", Toast.LENGTH_LONG).show()
                                        Log.e("Create user error", "${task.exception}")
                                    }
                                }
                        }) {
                            Text("Sign Up")
                        }
                    }
                }

            }
            else {
                Column{
                    val titles = mutableListOf<String>()
                    val authors = mutableListOf<String>()
                    val db = Firebase.firestore
                    val collection = db.collection(Firebase.auth.currentUser!!.email!!)
                    collection.get().addOnSuccessListener {
                        result -> for (doc in result)
                        {
                            titles.add(doc.data.getValue("Drawing Title").toString())
                            Log.e("TEST", "Added '${doc.data.getValue("Drawing Title")}' to Titles")
                            authors.add(doc.data.getValue("Author").toString())
                            Log.e("TEST", "Added '${doc.data.getValue("Author")}' to Titles")
                        }
                    }
                        .addOnFailureListener { exception ->
                            Log.w("Uh oh", "Error getting documents.", exception) }
                    Log.e("TEST", "Titles size: ${titles.size}")
                    Log.e("TEST", "Authors size: ${authors.size}")
                    for (i in 0 until titles.size) {
                        var downloadedBitmap = Bitmap.createBitmap(
                            1000, 1000, Bitmap.Config.ARGB_8888);
                        val ref = Firebase.storage.reference
                        val fileRef = ref.child(
                            "${Firebase.auth.currentUser!!.email}/${titles[i]}")
                        fileRef.getBytes(10 * 1024 * 1024)
                            .addOnSuccessListener { bytes ->
                                downloadedBitmap = BitmapFactory.decodeByteArray(
                                    bytes, 0, bytes.size
                                )
                            }
                            .addOnFailureListener { e ->
                                Log.e("DOWNLOAD_IMAGE", "Failed to get image $e")
                            }
                        ExistingDrawingItem(title = titles[i], author = authors[i], bitmap = downloadedBitmap)
                    }
                }
            }
        }
    }
    @Composable
    fun ExistingDrawingItem(
        title: String,
        author: String,
        bitmap: Bitmap
    )
    {
        Row(Modifier.padding(16.dp)) {
            Card {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = ""
                )
                Text(
                    text = "Title: $title" + "Author: $author"
                )
                Button(onClick = {
                    findNavController().navigate(
                        R.id.action_sharedDrawingsFragment_to_fragment_drawing)
                    context?.filesDir?.absolutePath?.let {
                        viewModel.saveDrawing(title,
                            it, bitmap)
                    }
                    context?.filesDir?.absolutePath?.let { viewModel.loadDrawing(it + title) }
                    viewModel.setCurrentDrawingName(title)
                }) {
                    Text("Download")
                }
            }
        }
    }
}
package com.example.drawingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentDrawingCompBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LogInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentDrawingCompBinding.inflate(layoutInflater)
        //ComposeView gives us a `Composable` context to run functions in
        binding.composeView.setContent {
            Column {
                Box {
                    Button(onClick = {
                        findNavController().navigate(
                            R.id.action_logInFragment_to_fragment_home_screen
                        )
                    }) { Text("Go Back") }
                }

                if (Firebase.auth.currentUser == null) { //show the login stuff only if the user hasn't logged in yet
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
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                context,
                                                "login complete",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "login failed, try again",
                                                Toast.LENGTH_LONG
                                            ).show()
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
                                            Toast.makeText(
                                                context,
                                                "Sign up complete",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Create user failed, try again",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            Log.e("Create user error", "${task.exception}")
                                        }
                                    }
                            }) {
                                Text("Sign Up")
                            }
                        }
                    }

                } else {
                    Button(onClick = {
                        Firebase.auth.signOut()
                    }) {
                        Text("Sign out")
                    }
                }
            }
        }

        return binding.root
    }
}
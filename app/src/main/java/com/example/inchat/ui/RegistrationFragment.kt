package com.example.inchat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.inchat.R
import com.example.inchat.theme.InchatTheme
import com.example.inchat.ui.registration_compose.RegistrationScreen
import com.example.inchat.view_model.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
  private val viewModel:RegistrationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                InchatTheme {
                  RegistrationScreen(viewModel = viewModel, chatScreen = { chatScreen() }, signIn ={signIn()})
                }
            }
        }
    }
     private fun chatScreen(){
         this.findNavController().navigate(R.id.action_signUpFragment_to_chatFragment)

     }

    private fun signIn(){
        this.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)

    }
}
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
import com.example.inchat.ui.signin_compose.SignInScreen
import com.example.inchat.view_model.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private val viewModel:SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.autoLogin { chatScreen() }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                InchatTheme {
                    SignInScreen(viewModel = viewModel,signUp = {signUp()},chatScreen = {chatScreen()})
                }
            }
        }
    }
  private fun signUp(){
      this.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
  }
  private fun chatScreen(){
    this.findNavController().navigate(R.id.action_signInFragment_to_chatFragment)

  }

}
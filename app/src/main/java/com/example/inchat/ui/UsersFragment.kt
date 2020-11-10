package com.example.inchat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.inchat.R
import com.example.inchat.theme.InchatTheme
import com.example.inchat.ui.users_compose.UsersScreen
import com.example.inchat.view_model.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment() {
    private val viewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.subscribeToTopic()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                InchatTheme() {
                    UsersScreen(viewModel = viewModel, userProfile = { navigationToProfile() },requireActivity())
                }
            }
        }
    }

    private fun navigationToProfile() {
        (activity as MainActivity).navController.navigate(R.id.action_chatFragment_to_profileFragment)
    }
}
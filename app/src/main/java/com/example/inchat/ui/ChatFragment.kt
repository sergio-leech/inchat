package com.example.inchat.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.inchat.R
import com.example.inchat.adapters.ChatAdapter
import com.example.inchat.databinding.FragmentChatBinding
import com.example.inchat.theme.InchatTheme
import com.example.inchat.ui.chat_compose.Chat
import com.example.inchat.view_model.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var adapter: ChatAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       // val id = arguments?.getString("userId")
      val binding = FragmentChatBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.chatViewModel = chatViewModel
        binding.imgBack.setOnClickListener {
            navigationBackBtn()
        }

        binding.btnSendMessage.setOnClickListener {
            val message = binding.inputMessage.text.toString()
            if(message.isNotEmpty()){
                chatViewModel.sendMessage(message)
            }
            binding.inputMessage.setText("")

        }

        adapter = ChatAdapter()
        binding.chatRecyclerView.adapter = adapter

        chatViewModel.listMessage.observe(viewLifecycleOwner){ message->
            adapter.submitList(message)

        }


        return binding.root
    }

    private fun navigationBackBtn() {
        activity?.onBackPressed()
    }

}
package com.example.inchat.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.ComposeView
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.inchat.R
import com.example.inchat.firebase.MyLifecycleObserver
import com.example.inchat.model.User
import com.example.inchat.theme.InchatTheme
import com.example.inchat.ui.profile_compose.Profile
import com.example.inchat.ui.profile_compose.ProfileScreen
import com.example.inchat.view_model.ProfileViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await
import java.util.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()
   // lateinit var observer: MyLifecycleObserver
    private val filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 22102020
   // private lateinit var getContent: ActivityResultLauncher<String>
    var bitmap:Bitmap? = null
    private lateinit var getContent: ActivityResultLauncher<String>
    private val firebaseAuth = Firebase.auth
    private val storage = Firebase.storage.reference
    private val databaseReference =
        Firebase.database.getReference("Users").child(firebaseAuth.uid.toString())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // observer = MyLifecycleObserver(requireActivity().activityResultRegistry)
      //  lifecycle.addObserver(observer)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         getContent = requireActivity().activityResultRegistry.register(
            "key",
            this,
            ActivityResultContracts.GetContent()
        ) {uri->

             storage.child("image/" + UUID.randomUUID().toString()).putFile(uri)
                 .addOnSuccessListener {
                     val hashMap: HashMap<String, String> = HashMap()
                     hashMap.apply {
                         put("profileImage",uri.toString())
                     }
                     databaseReference.updateChildren(hashMap as Map<String, Any>)
                 }
             /* bitmap = MediaStore.Images.Media.getBitmap(
                 requireActivity().contentResolver,
                 it
             )*/
             Log.d("TAG","URI $uri")
        }
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                InchatTheme() {
                    ProfileScreen(viewModel = viewModel,
                        arrowBackClick = { navigationToChat() },
                        exitBtn = { navigationToSignIn() },
                        selectImage = { selectImage() })
                }
            }
        }
    }

    private fun navigationToChat() {
        activity?.onBackPressed()
    }

    private fun navigationToSignIn() {
        viewModel.firebaseUtil.exitFromProfile {
            this.findNavController().navigate(R.id.action_profileFragment_to_signInFragment)
        }
    }

   private fun selectImage() {
        getContent.launch("image/*")
    }


}
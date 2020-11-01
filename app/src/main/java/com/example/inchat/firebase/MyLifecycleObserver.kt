package com.example.inchat.firebase

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentResolverCompat
import androidx.core.net.toUri
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class MyLifecycleObserver(private val registry: ActivityResultRegistry) : DefaultLifecycleObserver {
    private lateinit var getContent: ActivityResultLauncher<String>
    private val firebaseAuth = Firebase.auth
    private val storage = Firebase.storage.reference
    private val databaseReference =
        Firebase.database.getReference("Users").child(firebaseAuth.uid.toString())


    var filePath: Uri? = null
    //  var image: Bitmap? = null

    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("key", owner, ActivityResultContracts.GetContent()) { uri ->
            // Handle the returned Uri

            storage.child("image/" + UUID.randomUUID().toString()).putFile(uri)
                .addOnSuccessListener {
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap["profileImage"] = uri.toString()
                    databaseReference.updateChildren(hashMap as Map<String, Any>)
                }
             /*firebaseAuth.currentUser?.updateProfile(userProfileChangeRequest {
                 this.photoUri = uri
                 this.displayName = "Anderdog"
             })*/

            /* try {
                 val source = ImageDecoder.createSource(registry.contentResolver, filePath?:"")
                 image = ImageDecoder.decodeBitmap(source)
             }catch (e: IOException){
                 e.printStackTrace()
             }*/
        }
    }

    fun selectImage() {
        getContent.launch("image/*")
    }

}
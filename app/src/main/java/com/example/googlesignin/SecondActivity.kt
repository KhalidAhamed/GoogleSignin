package com.example.googlesignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googlesignin.databinding.ActivitySecondBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SecondActivity : AppCompatActivity() {
    private lateinit var gso : GoogleSignInOptions
    private lateinit var gsc : GoogleSignInClient
    private lateinit var account : GoogleSignInAccount
    private lateinit var secondBinding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(secondBinding.root)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this,gso)

        account = GoogleSignIn.getLastSignedInAccount(this)!!
        if (account != null){
            val personName = account.displayName
            val personEmail = account.email
            secondBinding.name.text = personName
            secondBinding.email.text = personEmail

        }


        secondBinding.signoutbtn.setOnClickListener {
            signOut()
        }




    }

    private fun signOut() {
        gsc.signOut().addOnCompleteListener {
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
package com.example.googlesignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.googlesignin.databinding.ActivityMainBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.SignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlin.Result
import kotlin.Result as Result1

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var gso : GoogleSignInOptions
    private lateinit var gsc : GoogleSignInClient
    private lateinit var signInIntent : Intent
    private lateinit var account: GoogleSignInAccount
    private lateinit var callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this,gso)


        binding.signinBtn.setOnClickListener {
            signIn()
        }

        binding.crashBtn.setOnClickListener {
            throw RuntimeException("Crash test")
        }

        callbackManager = CallbackManager.Factory.create()

        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // Handle successful login here
            }

            override fun onCancel() {
                // Handle login cancelation here
            }

            override fun onError(error: FacebookException) {
                // Handle login error here
            }
        })




    }
    

    private fun signIn() {
        signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent,1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000){
            val task : Task<GoogleSignInAccount>
            try {
                task  = GoogleSignIn.getSignedInAccountFromIntent(data)
                task.getResult(ApiException::class.java)
                navigateToSecondActivity()
            }catch (e : ApiException){
                Toast.makeText(applicationContext,"Something Went Wrong",Toast.LENGTH_LONG).show()
            }
        }

        account = GoogleSignIn.getLastSignedInAccount(this)!!

        if (account != null){
            navigateToSecondActivity()
        }
    }



    private fun navigateToSecondActivity() {
        finish()
        intent = Intent(this,SecondActivity::class.java)
        startActivity(intent)
    }

}
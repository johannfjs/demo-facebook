package com.jcodee.demofacebook.kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.jcodee.demofacebook.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callbackManager = CallbackManager.Factory.create()
        login_button.setReadPermissions("email")
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                val request = GraphRequest.newMeRequest(
                    loginResult!!.accessToken
                ) { `object`, response ->
                    // Application code
                    Toast.makeText(
                        this@MainActivity,
                        "Data -> id = ${`object`.get("id")} | nombre = ${`object`.get("name")}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,link")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
                // App code
                Toast.makeText(this@MainActivity, "Cancel", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: FacebookException) {
                // App code
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}

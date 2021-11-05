package com.softocorp.priscriptosender_v2.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.softocorp.priscriptosender_v2.R
import com.softocorp.priscriptosender_v2.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var txtSignUp: TextView
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtSignUp = findViewById(R.id.txtSignUp)

        btnLogin.setOnClickListener {
            if (etUsername.length() == 0 && etPassword.length() == 0) {
                if (etUsername.length() == 0) {
                    etUsername.error = "This field can't be blank"
                }
                if (etPassword.length() == 0) {
                    etPassword.error = "This field can't be blank"
                }
            } else {
                mAuth.signInWithEmailAndPassword(etUsername.text.toString(), etPassword.text.toString())
                    .addOnCompleteListener(this) {task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {

                        }
                    }
            }
        }

        txtSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            val pair = Pair<View, String>(btnLogin, "text")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@LoginActivity, pair)
            ActivityCompat.startActivity(this@LoginActivity, intent, options.toBundle())
        }

    }

    override fun onStop() {
        super.onStop()
        ActivityCompat.finishAfterTransition(this@LoginActivity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
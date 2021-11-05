package com.softocorp.priscriptosender_v2.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.util.Pair
import androidx.core.app.ActivityOptionsCompat
import com.google.firebase.auth.FirebaseAuth
import com.softocorp.priscriptosender_v2.R
import com.softocorp.priscriptosender_v2.authentication.RegistrationActivity
import com.softocorp.priscriptosender_v2.dashboard.DashboardActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var imgMainLogo: ImageView
    private lateinit var txtOne: TextView
    private lateinit var txtTwo: TextView
    private val mAuth = FirebaseAuth.getInstance().uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(R.layout.activity_splashscreen)

        imgMainLogo = findViewById(R.id.imgMainLogo)
        txtOne = findViewById(R.id.txtOne)
        txtTwo = findViewById(R.id.txtTwo)

        imgMainLogo.animation = AnimationUtils.loadAnimation(
            this@SplashScreenActivity,
            R.anim.bottom_animation
        )
        txtOne.animation =
            AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.left_animation)
        txtTwo.animation =
            AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.right_animation)

        Handler().postDelayed({
            if (mAuth != null) {
                val intent = Intent(this@SplashScreenActivity, DashboardActivity::class.java)
                val pair = Pair<View, String>(imgMainLogo, "logo")
                val pair1 = Pair<View, String>(txtOne, "left")
                val pair2 = Pair<View, String>(txtTwo, "right")
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@SplashScreenActivity,
                    pair,
                    pair1,
                    pair2
                )
                ActivityCompat.startActivity(this@SplashScreenActivity, intent, options.toBundle())
            } else {
                val intent = Intent(this@SplashScreenActivity, RegistrationActivity::class.java)
                val pair = Pair<View, String>(imgMainLogo, "logo")
                val pair1 = Pair<View, String>(txtOne, "left")
                val pair2 = Pair<View, String>(txtTwo, "right")
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@SplashScreenActivity,
                    pair,
                    pair1,
                    pair2
                )
                ActivityCompat.startActivity(this@SplashScreenActivity, intent, options.toBundle())
            }
            }, 1500)

        }

                override fun onStop() {
            super.onStop()
            ActivityCompat.finishAfterTransition(this@SplashScreenActivity)
        }

    }
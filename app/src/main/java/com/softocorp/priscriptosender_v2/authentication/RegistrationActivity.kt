package com.softocorp.priscriptosender_v2.authentication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.core.util.Pair
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.softocorp.priscriptosender_v2.R
import com.google.firebase.database.FirebaseDatabase
import com.softocorp.priscriptosender_v2.dashboard.DashboardActivity
import com.softocorp.priscriptosender_v2.model.User
import kotlinx.android.synthetic.main.custom_profile_dialog.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var llPersonal: LinearLayout
    private lateinit var llAddress: LinearLayout
    private lateinit var etFullName: TextInputEditText
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etPhoneNumber: TextInputEditText
    private lateinit var btnNext: ImageView
    private lateinit var txtSignInEdOne: TextView
    private lateinit var etAddress1: TextInputEditText
    private lateinit var etStreetName: TextInputEditText
    private lateinit var etAreaName: TextInputEditText
    private lateinit var etCityName: TextInputEditText
    private lateinit var etPinCode: TextInputEditText
    private lateinit var checkBox: CheckBox
    private lateinit var btnPrevious: ImageView
    private lateinit var btnRegister: Button
    private lateinit var txtSignIn: TextView
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        llPersonal = findViewById(R.id.llPersonal)
        llAddress = findViewById(R.id.llAddress)
        etFullName = findViewById(R.id.etFullName)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        btnNext = findViewById(R.id.btnNext)
        etAddress1 = findViewById(R.id.etAddress1)
        etStreetName = findViewById(R.id.etStreetName)
        etAreaName = findViewById(R.id.etAreaName)
        etCityName = findViewById(R.id.etCityName)
        etPinCode = findViewById(R.id.etPinCode)
        checkBox = findViewById(R.id.checkBox)
        btnPrevious = findViewById(R.id.btnPrevious)
        btnRegister = findViewById(R.id.btnRegister)
        txtSignIn = findViewById(R.id.txtSignIn)
        txtSignInEdOne = findViewById(R.id.txtSignInEdOne)

        var flag = 0

        var email = etUsername.text.toString()
        var password = etPassword.text.toString()
        var phone = etPhoneNumber.text.toString()
        var fullName = etFullName.text.toString()

        btnNext.setOnClickListener {
            if (etFullName.length() == 0 && etUsername.length() == 0 && etPassword.length() == 0 && etPhoneNumber.length() == 0) {
                if (etFullName.length() == 0) {
                    etFullName.error = "This field can't be blank"
                }
                if (etUsername.length() == 0) {
                    etUsername.error = "This field can't be blank"
                }
                if (etPassword.length() == 0) {
                    etPassword.error = "This field can't be blank"
                }
                if (etPhoneNumber.length() == 0) {
                    etPhoneNumber.error = "This field can't be blank"
                }
            } else {
                email = etUsername.text.toString()
                password = etPassword.text.toString()
                phone = etPhoneNumber.text.toString()
                fullName = etFullName.text.toString()
                btnNext.animation = AnimationUtils.loadAnimation(this@RegistrationActivity,
                    R.anim.bottom_animation
                )
                if (flag == 0) {
                    llPersonal.animate()
                        .translationX(-llPersonal.width.toFloat())
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(object: AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                llPersonal.visibility = View.GONE
                                llPersonal.clearAnimation()
                                flag = 1
                                llAddress.visibility = View.VISIBLE
                            }
                        })
                } else {
                    llPersonal.animate()
                        .translationX(-llPersonal.width.toFloat())
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(object: AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                llPersonal.visibility = View.GONE
                                llPersonal.clearAnimation()
                                llAddress.alpha = 1.0f
                                llAddress.translationX = (-llPersonal.width/llPersonal.width).toFloat()
                                llAddress.visibility = View.VISIBLE
                                llAddress.clearAnimation()
                            }
                        })
                }
            }
        }

        btnPrevious.setOnClickListener {
            llAddress.animate()
                .translationX(llAddress.width.toFloat())
                .alpha(0.0f)
                .setDuration(300)
                .setListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        llAddress.visibility = View.GONE
                        llAddress.clearAnimation()
                        llPersonal.alpha = 1.0f
                        llPersonal.translationX = (llPersonal.width/-llPersonal.width).toFloat()
                        llPersonal.visibility = View.VISIBLE
                        llPersonal.clearAnimation()
                    }
                })
        }

        btnRegister.setOnClickListener {
            if (etAddress1.length() == 0 && etStreetName.length() == 0 && etCityName.length() == 0 && etAreaName.length() == 0 && etPinCode.length() == 0) {
                if (etAddress1.length() == 0) {
                    etAddress1.error = "This field can't be blank"
                }
                if (etStreetName.length() == 0) {
                    etStreetName.error = "This field can't be blank"
                }
                if (etCityName.length() == 0) {
                    etCityName.error = "This field can't be blank"
                }
                if (etAreaName.length() == 0) {
                    etAreaName.error = "This field can't be blank"
                }
                if (etPinCode.length() == 0) {
                    etPinCode.error = "This field can't be blank"
                }
            } else {
                if (!checkBox.isChecked) {
                    checkBox.error = "This field can't be blank"
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val uid = mAuth.uid
                                val ref = FirebaseDatabase.getInstance()
                                    .getReference("/logintype/users/$uid")
                                val user = User(
                                    fullName,
                                    email,
                                    phone,
                                    etAddress1.text.toString(),
                                    etStreetName.text.toString(),
                                    etAreaName.text.toString(),
                                    etCityName.text.toString(),
                                    etPinCode.text.toString()
                                )

                                ref.setValue(user)
                                    .addOnCompleteListener {
                                        val dialog = Dialog(this@RegistrationActivity)
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                        dialog.setContentView(R.layout.custom_dialog)
                                        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.edt_bg))
                                        dialog.setCancelable(false)
                                        val imgSuccess =
                                            dialog.findViewById<ImageView>(R.id.imgSuccess)
                                        val txtSuccess =
                                            dialog.findViewById<TextView>(R.id.txtSuccess)
                                        val txtStatement =
                                            dialog.findViewById<TextView>(R.id.txtStatement)
                                        val btnTrial =
                                            dialog.findViewById<Button>(R.id.btnFreeTrial)
                                        btnTrial.setOnClickListener {
                                            val intent = Intent(this@RegistrationActivity, DashboardActivity::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                            startActivity(intent)
                                        }
                                        dialog.create()
                                        dialog.show()
                                    }
                                    .addOnFailureListener {

                                    }
                            } else {

                            }
                        }
                }
            }
        }

        txtSignInEdOne.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            val pair = Pair<View, String>(txtSignIn, "text")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@RegistrationActivity, pair)
            ActivityCompat.startActivity(this@RegistrationActivity, intent, options.toBundle())
        }

        txtSignIn.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            val pair = Pair<View, String>(txtSignIn, "text")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@RegistrationActivity, pair)
            ActivityCompat.startActivity(this@RegistrationActivity, intent, options.toBundle())
        }

        checkBox.setOnClickListener {
            checkBox.isChecked = false
            val dialogView = Dialog(this@RegistrationActivity)
            dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogView.setContentView(R.layout.dialog_terms_of_use)
            dialogView.window?.setBackgroundDrawable(getDrawable(R.drawable.edt_bg))
            dialogView.setCancelable(false)
            val imgTerms = dialogView.findViewById<ImageView>(R.id.imgTerms)
            val txtTerms = dialogView.findViewById<TextView>(R.id.txtTerms)
            val txtWholeStatement = dialogView.findViewById<TextView>(R.id.txtWholeStatement)
            val checkBox1 = dialogView.findViewById<CheckBox>(R.id.checkBox)
            val btnStartTrial = dialogView.findViewById<Button>(R.id.btnStartTrial)
            btnStartTrial.text = "Continue"
            btnStartTrial.setOnClickListener {
                //Send to Dashboard Activity
                if (checkBox1.isChecked) {
                    checkBox.text = "I Accept the Terms of Use."
                    checkBox.isChecked = true
                    btnStartTrial.isClickable = true
                    dialogView.dismiss()
                }
                else {
                    checkBox1.error = "Please check this Check Box"
                }
            }
            dialogView.create()
            dialogView.show()
        }

    }

    override fun onStop() {
        super.onStop()
        ActivityCompat.finishAfterTransition(this@RegistrationActivity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
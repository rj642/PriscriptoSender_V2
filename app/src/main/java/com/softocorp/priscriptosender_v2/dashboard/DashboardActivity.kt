package com.softocorp.priscriptosender_v2.dashboard

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.softocorp.priscriptosender_v2.R
import com.softocorp.priscriptosender_v2.authentication.RegistrationActivity
import com.softocorp.priscriptosender_v2.chat.ChatActivity
import com.softocorp.priscriptosender_v2.model.User
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.cream_recycler_view.view.*
import kotlinx.android.synthetic.main.medicine_recycler_view.view.*
import kotlinx.android.synthetic.main.medicine_recycler_view.view.imgIcon
import kotlinx.android.synthetic.main.quick_care_recycler_view.view.*

class DashboardActivity : AppCompatActivity() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this@DashboardActivity, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this@DashboardActivity, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this@DashboardActivity, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this@DashboardActivity, R.anim.to_bottom_anim)}
    private lateinit var toolbar: Toolbar
    private lateinit var fab: ImageView
    private lateinit var offerRecyclerView: RecyclerView
    private lateinit var medicineRecyclerView: RecyclerView
    private lateinit var updateRecyclerView: RecyclerView
    private lateinit var quickRecyclerView: RecyclerView
    private lateinit var creamRecyclerView: RecyclerView
    private val mAuth = FirebaseAuth.getInstance()
    private var clicked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.imgFab)
        offerRecyclerView = findViewById(R.id.offerRecyclerView)
        medicineRecyclerView = findViewById(R.id.medicineRecyclerView)
        updateRecyclerView = findViewById(R.id.updateRecyclerView)
        quickRecyclerView = findViewById(R.id.quickRecyclerView)
        creamRecyclerView = findViewById(R.id.creamRecyclerView)

        imgFab.setOnClickListener {
            onFabButtonClicked()
        }

        imgPicture.setOnClickListener {
            Toast.makeText(this@DashboardActivity, "Picture is selected.", Toast.LENGTH_LONG).show()
        }

        imgChat.setOnClickListener {
            val intent = Intent(this@DashboardActivity, ChatActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val offerAdapter = GroupAdapter<ViewHolder>()
        offerAdapter.add(OfferItems())
        offerAdapter.add(OfferItems())
        offerAdapter.add(OfferItems())
        offerAdapter.add(OfferItems())
        offerAdapter.add(OfferItems())
        offerRecyclerView.adapter = offerAdapter

        val medicineAdapter = GroupAdapter<ViewHolder>()
        medicineAdapter.add(MedicineItems("1SwYjnH9BVuaJnY9byuExhEhPExNX5IbM", "Paracetamol"))
        medicineAdapter.add(MedicineItems("1E5o8hta1ZeLmMN-5K4LaDJ-HhJn4KbFM", "Ibuprofen"))
        medicineAdapter.add(MedicineItems("13vUm_EHPXrRtwwizV9BJdx9Tb9yCSptv", "Rantac"))
        medicineAdapter.add(MedicineItems("15myUOLgY-1OvJuKLjw7sdEOA1MWnxls9", "Seacod"))
        medicineAdapter.add(MedicineItems("14uw-7JhipIlV3UY9Rk9xfg5uIWCEKNbu", "Cetrizine"))
        medicineAdapter.add(MedicineItems("148vo6vvMELYCnohlFUQARvy2SQB-gy5g", "Combiflam"))
        medicineAdapter.add(MedicineItems("12wG5Trs3qwz_NB89k8nYA87eMyfEXclO", "Sumo"))
        medicineAdapter.add(MedicineItems("15iSvOpzJHJ4rGLgOn2hlUNTnkD50fiqT", "Aspirin"))
        medicineAdapter.add(MedicineItems("15CWEKajS7K-5NRZgVm1VLD42GS4gVij7", "Antacid"))
        medicineRecyclerView.adapter = medicineAdapter

        val quickAdapter = GroupAdapter<ViewHolder>()
        quickAdapter.add(QuickCareItems("1pqJqrzpMwAtWjvQJMa-EUnZ8taBj-7E8", "Eye Care"))
        quickAdapter.add(QuickCareItems("1E5o8hta1ZeLmMN-5K4LaDJ-HhJn4KbFM", "Ibuprofen"))
        quickAdapter.add(QuickCareItems("1E5o8hta1ZeLmMN-5K4LaDJ-HhJn4KbFM", "Ibuprofen"))
        quickAdapter.add(QuickCareItems("1E5o8hta1ZeLmMN-5K4LaDJ-HhJn4KbFM", "Ibuprofen"))
        quickAdapter.add(QuickCareItems("1E5o8hta1ZeLmMN-5K4LaDJ-HhJn4KbFM", "Ibuprofen"))
        quickRecyclerView.adapter = quickAdapter

        val updateAdapter = GroupAdapter<ViewHolder>()
        updateAdapter.add(UpdateItems())
        updateAdapter.add(UpdateItems())
        updateAdapter.add(UpdateItems())
        updateAdapter.add(UpdateItems())
        updateAdapter.add(UpdateItems())
        updateRecyclerView.adapter = updateAdapter

        val creamAdapter = GroupAdapter<ViewHolder>()
        creamAdapter.add(CreamItems("16U8nURivh9L7FMogJSwNby-VNAPPMgLx", "Fungicros"))
        creamAdapter.add(CreamItems("14ZQ86mejZFWTGix0DI5HxZryJb2C2cPd", "Volini"))
        creamAdapter.add(CreamItems("18wbZMmmi9F9jTx6AkhNLEc3ruQ82gSVf", "Moov"))
        creamAdapter.add(CreamItems("1550c9sUPZ37L-yTY48SXmo901d0be_FB", "Dermadew"))
        creamAdapter.add(CreamItems("15EMmj8LFMpQE3vyr_1b0mhtGPokvMb13", "Hydrocortisone"))
        creamAdapter.add(CreamItems("15NwaP-vnxp6HWsSXs1cdRy64v9VMkXJ7", "Savlon"))
        creamAdapter.add(CreamItems("165MlcyCtukTN9fHvft3wjjBeooA0PkQa", "Oriscab"))
        creamRecyclerView.adapter = creamAdapter

        setUpToolbar()

    }

    private fun onFabButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            imgPicture.isClickable = false
            imgChat.isClickable = false
        } else {
            imgPicture.isClickable = true
            imgChat.isClickable = true
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            imgPicture.visibility = View.INVISIBLE
            imgChat.visibility = View.INVISIBLE
        } else {
            imgPicture.visibility = View.VISIBLE
            imgChat.visibility = View.VISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            imgPicture.startAnimation(toBottom)
            imgChat.startAnimation(toBottom)
            imgFab.startAnimation(rotateClose)
        } else {
            imgPicture.startAnimation(fromBottom)
            imgChat.startAnimation(fromBottom)
            imgFab.startAnimation(rotateOpen)
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_list_dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.profile) {
            val uid = mAuth.uid
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("/logintype/users/$uid")
            val dialog = Dialog(this@DashboardActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.custom_profile_dialog)
            dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.edt_bg))
            dialog.setCancelable(false)
            var profile = dialog.findViewById<ImageView>(R.id.profile)
            var txtFullName = dialog.findViewById<TextView>(R.id.txtFullName)
            var txtPhoneNumber = dialog.findViewById<TextView>(R.id.txtPhoneNumber)
            var txtEmailAddress = dialog.findViewById<TextView>(R.id.txtEmailAddress)
            var txtAddress = dialog.findViewById<TextView>(R.id.txtAddress)
            val btnEdit = dialog.findViewById<Button>(R.id.btnEdit)
            btnEdit.setOnClickListener {
                dialog.dismiss()
            }
            ref.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    val user = p0.getValue(User::class.java)
                    if (user != null) {
                        txtFullName.text = user.fullName
                        txtPhoneNumber.text = user.phone
                        txtEmailAddress.text = user.email
                        txtAddress.text = user.address + ", " + user.street + ", " + user.area + ", " + user.city + " : " + user.pincode
                    }
                }

            })
            dialog.create()
            dialog.show()
        }

        if (id == R.id.logout) {
            val intent = Intent(this@DashboardActivity, RegistrationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            FirebaseAuth.getInstance().signOut()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}

class OfferItems : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.offer_recycler_view
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

}

class MedicineItems(val imageUrl: String, private val medicineName: String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.medicine_recycler_view
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txtMedicineName.text = medicineName
        val imageLink = "https://drive.google.com/uc?export=download&id=$imageUrl"
        Picasso.get().load(imageLink).into(viewHolder.itemView.imgIcon)
        Picasso.get().isLoggingEnabled = true
    }

}

class QuickCareItems(private val imageLink: String, private val itemName: String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.quick_care_recycler_view
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txtQuickName.text = itemName
        val imageLink = "https://drive.google.com/uc?export=download&id=$imageLink"
        Picasso.get().load(imageLink).into(viewHolder.itemView.imgQuickIcon)
    }

}

class UpdateItems : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.update_recycler_view
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

}

class CreamItems(private val imgUrl: String, private val creamName: String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.cream_recycler_view
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txtCreamName.text = creamName
        val imageLink = "https://drive.google.com/uc?export=download&id=$imgUrl"
        Picasso.get().load(imageLink).into(viewHolder.itemView.imgCreamIcon)
    }

}
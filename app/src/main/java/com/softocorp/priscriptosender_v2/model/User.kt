package com.softocorp.priscriptosender_v2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val fullName: String, val email: String, val phone: String, val address: String, val street: String, val area: String, val city: String, val pincode: String): Parcelable {
    constructor() : this("", "", "", "", "", "", "", "")
}
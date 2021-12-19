package com.github.tomasst365.rgr.data

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

class User() : Parcelable {
    var invalidPasswordCounter: Int = 0

    var id: String? = null
    var login: String? = null
    var password: String? = null
    var isAdmin: Boolean = false

    companion object {
        @SuppressLint("ParcelCreator")
        val CREATOR : Parcelable.Creator<User> = object : Parcelable.Creator<User> {

            override fun createFromParcel(parcel: Parcel): User {
                return User(parcel)
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }

        const val INVALID_PASS_LIMIT = 3
    }

    constructor(parcel: Parcel) : this() {
        invalidPasswordCounter = parcel.readInt()
    }

    constructor(builder: Builder) : this() {
        this.id = builder.id
        this.login = builder.login
        this.password = builder.password
        this.isAdmin = builder.isAdmin
    }

    /**Builder*/
    class Builder {
        var id: String? = ""
        var login: String? = ""
        var password: String? = ""
        var isAdmin: Boolean = false

        fun setLogin(login: String?): Builder {
            this.login = login
            return this
        }

        fun setPassword(password: String?): Builder {
            this.password = password
            return this
        }

        fun setIsAdmin(isAdmin: Boolean): Builder {
            this.isAdmin = isAdmin
            return this
        }

        fun setId(id: String?): Builder {
            this.id = id
            return this
        }

        fun build(): User {
            return User(this)
        }
    }

    /**Parcelable implementation*/
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(invalidPasswordCounter)
    }

    override fun describeContents(): Int {
        return 0
    }

}
package com.example.musicplayer.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "tb_user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val idUser: Int?,
<<<<<<< HEAD

=======
<<<<<<< HEAD
>>>>>>> 167b27c3d563c22aa5c364c3323831283280ab8e
    val email: String?,
//    val name: String?,
    val password: String?
):
//    Serializable,
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
<<<<<<< HEAD

=======
=======
    val email: String,
    val password: String
) : Serializable
>>>>>>> origin/master
>>>>>>> 167b27c3d563c22aa5c364c3323831283280ab8e

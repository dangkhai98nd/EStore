package com.example.estore.model

import android.os.Parcel
import android.os.Parcelable

class Product() : Parcelable {
    var id: String? = null
    var name: String? = null
    var price: Int? = null
    var photoDark: String? = null
    var photoLight: String? = null
    var likeCounter: Int? = null
    var commentCounter: Int? = null
    var type: String? = null //phone? table? watch? headphones?
    var brand: String? = null //samsung? apple? oppo? xiaomi? beats? skullcandy?
    var os: String? = null // android? ios? null?
    var numberSold: Int? = null
    var trending: Boolean? = null
    var listUserLike: MutableList<String>? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        price = parcel.readValue(Int::class.java.classLoader) as? Int
        photoDark = parcel.readString()
        photoLight = parcel.readString()
        likeCounter = parcel.readValue(Int::class.java.classLoader) as? Int
        commentCounter = parcel.readValue(Int::class.java.classLoader) as? Int
        type = parcel.readString()
        brand = parcel.readString()
        os = parcel.readString()
        numberSold = parcel.readValue(Int::class.java.classLoader) as? Int
        trending = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeValue(price)
        parcel.writeString(photoDark)
        parcel.writeString(photoLight)
        parcel.writeValue(likeCounter)
        parcel.writeValue(commentCounter)
        parcel.writeString(type)
        parcel.writeString(brand)
        parcel.writeString(os)
        parcel.writeValue(numberSold)
        parcel.writeValue(trending)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }


}


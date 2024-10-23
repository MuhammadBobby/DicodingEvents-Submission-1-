package com.dicoding.dicodingevent.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class FavoriteEvents (
    @PrimaryKey(autoGenerate = false)

    //id dari event yang dipilih
    @ColumnInfo(name = "id")
    var id: String = "",

    //nama dari event yang dipilih
    @ColumnInfo(name = "name")
    var name: String = "",

    //date dari event yang dipilih
    @ColumnInfo(name = "beginTime")
    var beginTime: String = "",

    //location dari event yang dipilih
    @ColumnInfo(name = "cityName")
    var cityName: String = "",

    //image dari event yang dipilih
    @ColumnInfo(name = "imageLogo")
    var image: String = "",

) : Parcelable
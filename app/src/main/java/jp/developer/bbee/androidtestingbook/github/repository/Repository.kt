package jp.developer.bbee.androidtestingbook.github.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Repository(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    @ColumnInfo(index = true)
    val owner: String,
)

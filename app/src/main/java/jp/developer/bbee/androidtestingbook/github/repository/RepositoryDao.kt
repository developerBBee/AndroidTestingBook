package jp.developer.bbee.androidtestingbook.github.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RepositoryDao {
    @Insert
    fun insertAll(vararg repository: Repository)

    @Query("SELECT * FROM repository WHERE owner = :owner")
    fun findByOwner(owner: String): List<Repository>
}
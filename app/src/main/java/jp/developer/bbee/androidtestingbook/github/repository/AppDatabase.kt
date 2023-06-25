package jp.developer.bbee.androidtestingbook.github.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Repository::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}
package jp.developer.bbee.androidtestingbook.github

import jp.developer.bbee.androidtestingbook.github.repository.AppDatabase
import jp.developer.bbee.androidtestingbook.github.repository.Repository

class RepositoryLocalDataSource(val db: AppDatabase) {
    fun insertAll(vararg repository: Repository) {
        db.repositoryDao().insertAll(*repository)
    }

    fun findByOwner(owner: String): List<Repository> {
        return db.repositoryDao().findByOwner(owner)
    }
}
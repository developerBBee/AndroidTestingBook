package jp.developer.bbee.androidtestingbook.github

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import jp.developer.bbee.androidtestingbook.github.repository.AppDatabase
import jp.developer.bbee.androidtestingbook.github.repository.Repository
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryLocalDataSourceAndroidTest {
    lateinit var repositoryLocalDataSource: RepositoryLocalDataSource

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val db = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java) // メモリ上にデータベースを作成する
            .allowMainThreadQueries()
            .build()
        repositoryLocalDataSource = RepositoryLocalDataSource(db)
    }

    @Test
    fun insert_and_get() {
        val owner = "TEST_OWNER"
        var list = repositoryLocalDataSource.findByOwner(owner)
        Assertions.assertThat(list).isEmpty()

        repositoryLocalDataSource.insertAll(
            Repository(1, "hello", "hello", owner),
            Repository(2, "world", "world", owner)
        )

        list = repositoryLocalDataSource.findByOwner(owner)
        Assertions.assertThat(list).hasSize(2)

        // RobolectricTestRunnerは一時ファイルにデータベースを作成する。
        // 各テストケース終了時に自動クリアされるので後始末の記述は不要。
        // また、他のテストに干渉することもない。
    }

    // 干渉しないことの確認
    @Test
    fun insert_and_get2() {
        val owner = "TEST_OWNER"
        var list = repositoryLocalDataSource.findByOwner(owner)
        Assertions.assertThat(list).isEmpty()

        repositoryLocalDataSource.insertAll(
            Repository(1, "hello", "hello", owner),
            Repository(2, "world", "world", owner)
        )

        list = repositoryLocalDataSource.findByOwner(owner)
        Assertions.assertThat(list).hasSize(2)
    }
}
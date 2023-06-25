package jp.developer.bbee.androidtestingbook.github

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import jp.developer.bbee.androidtestingbook.github.repository.AppDatabase
import jp.developer.bbee.androidtestingbook.github.repository.Repository
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(Enclosed::class)
class EnclosedTest {
    /**
     * 共通のセットアップを継承先に提供する
     */
    abstract class DBTest {
        lateinit var repositoryLocalDataSource: RepositoryLocalDataSource

        /**
         * The @Before methods of superclasses will be run before those of the current class,
         * unless they are overridden in the current class.
         * @see <a href="https://junit.org/junit4/javadoc/4.13/org/junit/Before.html">@Before</a>
         */
        @Before
        fun setUpParent() {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val db = Room
                .databaseBuilder(context, AppDatabase::class.java, "DB")
                .allowMainThreadQueries()
                .build()
            repositoryLocalDataSource = RepositoryLocalDataSource(db)
        }

        /**
         * The @After methods declared in superclasses will be run after those of the current class,
         * unless they are overridden in the current class.
         * @see <a href="https://junit.org/junit4/javadoc/4.13/org/junit/After.html">@After</a>
         */
        @After
        fun tearDownParent() {
            /* TODO */
        }
    }

    /**
     * レコード無しの状態でのテストをまとめたクラス
     */
    @RunWith(RobolectricTestRunner::class)
    class BlankRecord : DBTest() {
        @Test
        fun insertAll_successfully_persist_record() {
            repositoryLocalDataSource.insertAll(
                Repository(1, "hello", "hello", "TEST_OWNER"),
            )
            val findList = repositoryLocalDataSource.findByOwner("TEST_OWNER")
            assertThat(findList).hasSize(1)
        }
    }

    /**
     * レコードが準備された状態でのテストをまとめたクラス
     */
    @RunWith(RobolectricTestRunner::class)
    class RecordPrepared : DBTest() {
        @Before
        fun setUp() {
            // 子クラスの@Beforeは親クラスの@Beforeより後に実行されるので、
            // repositoryLocalDataSourceが初期化されていることが保証される。
            repositoryLocalDataSource.insertAll(
                Repository(1, "hello", "hello", "TEST_OWNER"),
                Repository(2, "world", "world", "TEST_OWNER"),
                Repository(3, "yay!!", "yay!!", "OTHER_OWNER"),
            )
        }

        @Test
        fun findByOwner_givenTestOwner_returnsSizeCount2() {
            val results = repositoryLocalDataSource.findByOwner("TEST_OWNER")
            assertThat(results).hasSize(2)
        }

        @Test
        fun findByOwner_givenOtherOwner_returnsSizeCount1() {
            val results = repositoryLocalDataSource.findByOwner("OTHER_OWNER")
            assertThat(results).hasSize(1)
        }
    }
}
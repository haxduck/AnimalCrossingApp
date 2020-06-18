package com.example.animalcrossingapp

import android.app.Application
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class AnimalCrossingDAOTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private var app = ApplicationProvider.getApplicationContext<Application>()
    private var dao = AnimalCrossingDB.getInstance(appContext)?.animalCrossingDao()!!
    private var db = Room.databaseBuilder(
        appContext.applicationContext,
        AnimalCrossingDB::class.java, "AnimalCrossing.db"
    ).allowMainThreadQueries().createFromAsset("AnimalCrossing.db").build().animalCrossingDao()

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }

    private val <T> LiveData<T>.blockingValue: T?
        get() {
            var value: T? = null
            val latch = CountDownLatch(1)
            observeForever {
                value = it
                latch.countDown()
            }
            if (latch.await(2, TimeUnit.SECONDS)) return value
            else throw Exception("LiveData value was not set within 2 seconds")
        }

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        app = ApplicationProvider.getApplicationContext()
        dao = AnimalCrossingDB.getInstance(appContext)?.animalCrossingDao()!!
    }

    //상세 검색 1건
    @Test
    fun searchDetail() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "虫",
            months = arrayListOf("6月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(6)
        ).blockingValue?.size
        assertEquals(1, test3)
    }

    //상세 검색 flag
    @Test
    fun searchDetail1() {
        val test3 = db.selectLiveDetail(
            flag = "1",
            sort = "虫",
            months = arrayListOf("6月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(6)
        ).blockingValue?.size
        assertEquals(0, test3)
    }

    //상세 검색 sort
    @Test
    fun searchDetail2() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "魚",
            months = arrayListOf("6月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(6)
        ).blockingValue?.size
        assertEquals(0, test3)
    }

    //상세 검색 month
    @Test
    fun searchDetail3() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "虫",
            months = arrayListOf("1月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(6)
        ).blockingValue?.size
        assertEquals(0, test3)
    }

    //상세 검색 price
    @Test
    fun searchDetail4() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "虫",
            months = arrayListOf("6月"),
            minPrice = 0,
            maxPrice = 0,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(6)
        ).blockingValue?.size
        assertEquals(0, test3)
    }

    //상세 검색 place
    @Test
    fun searchDetail5() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "虫",
            months = arrayListOf("6月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("岩"),
            times = arrayListOf(6)
        ).blockingValue?.size
        assertEquals(0, test3)
    }

    //상세 검색 time
    @Test
    fun searchDetail6() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "虫",
            months = arrayListOf("6月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(10)
        ).blockingValue?.size
        assertEquals(0, test3)
    }

    //이름 검색 정확한 자릿수
    @Test
    fun searchName() {
        var flag = false
        val name1 = db.selectLiveSearch("%タナゴ%").blockingValue?.forEach {
            if (it.name == "タナゴ") flag = true
        }
        assertEquals(true, flag)
    }

    //이름 검색 한 자리 추가
    @Test
    fun searchName1() {
        var flag = false
        val name1 = db.selectLiveSearch("%タナゴア%").blockingValue?.forEach {
            if (it.name == "タナゴ") flag = true
        }
        assertEquals(false, flag)
    }

    //이름 검색 두 자리 검색
    @Test
    fun searchName2() {
        var flag = false
        val name1 = db.selectLiveSearch("%タナ%").blockingValue?.forEach {
            if (it.name == "タナゴ") flag = true
        }
        assertEquals(true, flag)
    }

    //이름 검색 한 자리 검색
    @Test
    fun searchName3() {
        var flag = false
        val name1 = db.selectLiveSearch("%タ%").blockingValue?.forEach {
            if (it.name == "タナゴ") flag = true
        }
        assertEquals(true, flag)
    }

    //물고기만
    @Test
    fun getFishes() {
        val list = arrayListOf<Current>()
        val all1 = db.selectLive().blockingValue as ArrayList<Current>
        all1.forEach { if (it.sortation == "魚") list.add(it)}
        assertEquals(80, list.size)
    }

    //벌레만
    @Test
    fun getInsects() {
        val list = arrayListOf<Current>()
        val all1 = db.selectLive().blockingValue as ArrayList<Current>
        all1.forEach { if (it.sortation == "虫") list.add(it)}
        assertEquals(80, list.size)
    }


}


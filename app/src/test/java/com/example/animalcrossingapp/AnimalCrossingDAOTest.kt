package com.example.animalcrossingapp

import android.app.Application
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.example.animalcrossingapp.controller.App
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

    fun getMonthString(months: String): String {
        var marr = months.split(",")
        var str1 = ""
        var flist1: ArrayList<Int> = arrayListOf(marr[0].toInt())
        var slist1: ArrayList<Int> = arrayListOf()
        val hourStr = "時"
        val monthStr = "月"
        val mirai = " ~ 来年"
        var fl = 0
        for (i in 1..marr.size - 1) {
            if (marr[i].toInt() - marr[i - 1].toInt() == 1 && fl == 0) {
                flist1.add(marr[i].toInt())
            } else {
                fl = 1
                slist1.add(marr[i].toInt())
            }
        }

        if (slist1.size == 0 && flist1.size != 1) {
            str1 = flist1[0].toString() + " ~ " + flist1[flist1.size - 1].toString() + monthStr
        } else if (slist1.size != 0) {
            if (flist1[0] == 1 && slist1[slist1.size - 1] == 12 && marr.size < 12) {
                str1 = slist1[0].toString() + mirai + flist1[flist1.size - 1] + monthStr
            } else {
                str1 = flist1[0].toString() + " ~ " + flist1[flist1.size - 1].toString() +
                        monthStr + ", " + slist1[0].toString() + " ~ " + slist1[slist1.size - 1].toString() + monthStr
            }
        } else {
            str1 = flist1[0].toString() + monthStr
        }
        return str1
    }

    fun getTimeString(time: String): String {
        var tarr = time.split(",")
        var str = ""
        var flist: ArrayList<Int> = arrayListOf(tarr[0].toInt())
        var slist: ArrayList<Int> = arrayListOf()
        var f = 0
        for (i in 1..tarr.size - 1) {
            if (tarr[i].toInt() - tarr[i - 1].toInt() == 1 && f == 0) {
                flist.add(tarr[i].toInt())
            } else {
                f = 1
                slist.add(tarr[i].toInt())
            }
        }
        val hourStr = "時"
        if (slist.size == 0 && flist.size != 1) {
            str = flist[0].toString() + " ~ " + flist[flist.size - 1].toString() + hourStr
        } else if (slist.size != 0) {
            str = flist[0].toString() + " ~ " + flist[flist.size - 1].toString() +
                    hourStr + ", " + slist[0].toString() + " ~ " + slist[slist.size - 1].toString() + hourStr
        } else {
            str = flist[0].toString() + hourStr
        }
        return str
    }

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        app = ApplicationProvider.getApplicationContext()
        dao = AnimalCrossingDB.getInstance(appContext)?.animalCrossingDao()!!
    }

    //상세 검색 1건
    @Test
    fun searchDetail1() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "虫",
            months = arrayListOf("6月"),
            minPrice = 100,
            maxPrice = 300,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(6)
        ).size
        assertEquals(1, test3)
    }

    //상세 검색 flag
    @Test
    fun searchDetail2() {
        val test3 = db.selectLiveDetail(
            flag = "1",
            sort = "虫",
            months = arrayListOf("6月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(6)
        ).size
        assertEquals(0, test3)
    }

    //상세 검색 sort
    @Test
    fun searchDetail3() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "魚",
            months = arrayListOf("6月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(6)
        ).size
        assertEquals(0, test3)
    }

    //상세 검색 month
    @Test
    fun searchDetail4() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "虫",
            months = arrayListOf("1月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(6)
        ).size
        assertEquals(0, test3)
    }

    //상세 검색 price
    @Test
    fun searchDetail5() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "虫",
            months = arrayListOf("6月"),
            minPrice = 0,
            maxPrice = 0,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(6)
        ).size
        assertEquals(0, test3)
    }

    //상세 검색 place
    @Test
    fun searchDetail6() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "虫",
            months = arrayListOf("6月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("岩"),
            times = arrayListOf(6)
        ).size
        assertEquals(0, test3)
    }

    //상세 검색 time
    @Test
    fun searchDetail7() {
        val test3 = db.selectLiveDetail(
            flag = "0",
            sort = "虫",
            months = arrayListOf("6月"),
            minPrice = 200,
            maxPrice = 200,
            places = arrayListOf("長靴、缶、タイヤ、腐ったカブ"),
            times = arrayListOf(10)
        ).size
        assertEquals(0, test3)
    }

    //이름 검색 정확한 자릿수
    @Test
    fun searchName1() {
        var flag = false
        db.selectLiveSearch("%テントウムシ%", App.prefs.hemisphere!!).blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(true, flag)
    }

    //이름 검색 일부분
    @Test
    fun searchName2() {
        var flag = false
        db.selectLiveSearch("%テン%", App.prefs.hemisphere!!).blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(true, flag)
    }

    //이름 검색 일부분
    @Test
    fun searchName3() {
        var flag = false
        db.selectLiveSearch("%テト%", App.prefs.hemisphere!!).blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(false, flag)
    }

    //이름 검색 다른 이름
    @Test
    fun searchName4() {
        var flag = false
        db.selectLiveSearch("%タゴ%", App.prefs.hemisphere!!).blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(false, flag)
    }

    //이름 검색 영어 검색
    @Test
    fun searchName5() {
        var flag = false
        db.selectLiveSearch("%ladybug%", App.prefs.hemisphere!!).blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(false, flag)
    }

    //이름 검색 한글 검색
    @Test
    fun searchName6() {
        var flag = false
        db.selectLiveSearch("%무당벌레%", App.prefs.hemisphere!!).blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(false, flag)
    }

    //이름 한자 추가 검색
    @Test
    fun searchName7() {
        var flag = false
        db.selectLiveSearch("%テントウムシタ%", App.prefs.hemisphere!!).blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(false, flag)
    }

    //물고기만
    @Test
    fun getFishes1() {
        var flag = false
        val list = arrayListOf<Current>()
        val all1 = db.selectLive().blockingValue as ArrayList<Current>
        all1.forEach { if (it.sortation == "魚") list.add(it)}
        list.forEach { if (it.sortation == "魚") flag = true }
        assertEquals(true, flag)
    }

    //물고기만
    @Test
    fun getFishes2() {
        var flag = false
        val list = arrayListOf<Current>()
        val all1 = db.selectLive().blockingValue as ArrayList<Current>
        all1.forEach { if (it.sortation == "魚") list.add(it)}
        list.forEach { if (it.sortation == "虫") flag = true }
        assertEquals(false, flag)
    }

    //벌레만
    @Test
    fun getInsects() {
        var flag = false
        val list = arrayListOf<Current>()
        val all1 = db.selectLive().blockingValue as ArrayList<Current>
        all1.forEach { if (it.sortation == "虫") list.add(it)}
        list.forEach { if (it.sortation == "魚") flag = true }
        assertEquals(false, flag)
    }

    //이번달에 잡히는 지 체크
    @Test
    fun checkThisMonth1() {
        val nextMonth = 6
        val animal = db.selectCode("FIS0024", App.prefs.hemisphere!!)
        val marr = animal.month!!.replace("月","").split(",")
        var flag = false
        for (i in (0..marr.size - 1)) {
            if (marr[i] == nextMonth.toString()) {
                flag = true
            }
        }
        assertEquals(true, flag)
    }

    //이번달 지나면 한동안 잡을 수 없는 것 체크
    @Test
    fun checkThisMonth2() {
        val nextMonth = 7
        val animal = db.selectCode("FIS0024", App.prefs.hemisphere!!)
        val marr = animal.month!!.replace("月","").split(",")
        var flag = false
        for (i in (0..marr.size - 1)) {
            if (marr[i] == nextMonth.toString()) {
                flag = true
            }
        }
        assertEquals(false, flag)
    }

    //출현 달 1구간 출력
    @Test
    fun getMonths1() {
        val str1 = this.getMonthString("1,2,3,4,5")
        assertEquals("1月～5月", str1)
    }

    //출현 달 2구간 출력
    @Test
    fun getMonths2() {
        val str1 = this.getMonthString("1,2,3,4,5,8,9,10,11")
        assertEquals("1月～5月、8月～11月", str1)
    }

    //출현 달 1구간 1개월 출력
    @Test
    fun getMonths3() {
        val str1 = this.getMonthString("1")
        assertEquals("1月", str1)
    }

    //출현 시간 1구간 출력
    @Test
    fun getTimes1() {
        val str = this.getTimeString("1,2,3")
        assertEquals("1時～3時", str)
    }

    //출현 시간 2구간 출력
    @Test
    fun getTimes2() {
        val str = this.getTimeString("1,2,3,7,8,9,10")
        assertEquals("1時～3時、7時～10時", str)
    }

    //출현 시간 1구간 1시간 출력
    @Test
    fun getTimes3() {
        val str = this.getTimeString("1")
        assertEquals("1時", str)
    }

    //실시간 출현 동물
    @Test
    fun getRealTime1() {
        var flag = false
        val list = db.selectLiveCurrentAnimal(
            hemisphere = "北半球",
            currentHour = "1",
            currentMonth = "8月").blockingValue!!
        list.forEach { if (it.name == "スッポン") flag = true }
        assertEquals(true, flag)
    }

    //실시간 출현 동물
    @Test
    fun getRealTime2() {
        var flag = false
        val list = db.selectLiveCurrentAnimal(
            hemisphere = "‘南半球",
            currentHour = "1",
            currentMonth = "8月").blockingValue!!
        list.forEach { if (it.name == "スッポン") flag = true }
        assertEquals(false, flag)
    }

    //실시간 출현 동물
    @Test
    fun getRealTime3() {
        var flag = false
        val list = db.selectLiveCurrentAnimal(
            hemisphere = "北半球",
            currentHour = "1",
            currentMonth = "1月").blockingValue!!
        list.forEach { if (it.name == "スッポン") flag = true }
        assertEquals(false, flag)
    }

    //실시간 출현 동물
    @Test
    fun getRealTime4() {
        var flag = false
        val list = db.selectLiveCurrentAnimal(
            hemisphere = "北半球",
            currentHour = "10",
            currentMonth = "8月").blockingValue!!
        list.forEach { if (it.name == "スッポン") flag = true }
        assertEquals(false, flag)
    }

}



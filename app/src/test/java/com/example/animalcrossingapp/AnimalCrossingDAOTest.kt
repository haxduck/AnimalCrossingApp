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
        var hourStr: String
        var monthStr: String
        var nextMonth: String = ""
        var nextDay = ""
        when (App.prefs.language) {
            "ja" -> {
                hourStr = "時"
                monthStr = "月"
            }
            "ko" -> {
                hourStr = "시"
                monthStr = "월"
            }
            else -> {
                hourStr = "時"
                monthStr = "月"
            }
        }
        //월 출력
        var marr = months.replace("月", "").split(",").map{it.toInt()}.toCollection(ArrayList())
        marr.sortWith(compareBy({it}))
        var str1 = ""
        //내년 포함
        if (marr.get(0) == 1 && marr.get(marr.size - 1) == 12 && marr.size < 12) {
            var i = 0
            while (marr.get(i+1) - marr.get(i) == 1) {
                marr.add(marr.get(0))
                marr.remove(marr.get(0))
            }
            marr.add(marr.get(0))
            marr.remove(marr.get(0))
            when(App.prefs.language) {
                "ko" -> nextMonth = "내년 "
                "ja" -> nextMonth = "来年 "
            }
        }
        // 월 출력
        //기간 나누기
        var oneFlag1 = false
        var twoFlag1 = false
        for ( i in 0..marr.size - 1 ) {
            var flag = false
            if ( i == 0 && marr.size > 1 ) {
                if (marr.get(1) - marr.get(0) > 1) {
                    twoFlag1 = true
                } else {
                    str1 += marr.get(0)
                }
            }
            if ( i > 0 && marr.get(i) - marr.get(i - 1) > 1 ) {
                if (oneFlag1 == false) {
                    str1 += " ~ ${marr.get(i-1)}${monthStr}, ${marr.get(i)}"
                    if (i == marr.size - 1) str1 += monthStr
                } else {
                    if (i == marr.size - 1) {
                        str1 += "${monthStr}, ${marr.get(i)}${monthStr}"
                    } else {
//                        str1 += ", ${marr.get(i)}"
                        if (marr[i-1] - marr[i-2] > 1 && marr[i] - marr[i-1] > 1) {
                            str1 += "${monthStr}, ${marr[i]}"
                        } else {
                            str1 += ", ${marr[i]}"
                        }
                    }
                    oneFlag1 = false
                }
                if (i < marr.size - 1 && marr.get(i) - marr.get(i-1) > 1 && marr.get(i+1) - marr.get(i) > 1) {
                    oneFlag1 = true
                }
                if ( i == marr.size -1 && marr.size > 1 ) flag = true
            }
            if ( i == marr.size -1 && marr.size > 1 && flag == false ) {
                str1 += " ~ ${nextMonth}${marr.get(i)}${monthStr}"
            }
            if (marr.size == 1) str1 += "${marr.get(0)}${monthStr}"
        }
        if ( twoFlag1 == true ) str1 = str1.replaceFirst("~", "")
        return str1
    }

    fun getTimeString(time: String): String {
        var hourStr: String
        var monthStr: String
        var nextMonth: String = ""
        var nextDay = ""
        when (App.prefs.language) {
            "ja" -> {
                hourStr = "時"
                monthStr = "月"
            }
            "ko" -> {
                hourStr = "시"
                monthStr = "월"
            }
            else -> {
                hourStr = "時"
                monthStr = "月"
            }
        }
        var tarr = time.split(",").map{it.toInt()}.toCollection(ArrayList())
        tarr.sortWith(compareBy({it}))
        var str = ""
        //내일 포함
        if (tarr.get(0) == 1 && tarr.get(tarr.size - 1) == 24 && tarr.size < 24) {
            var i = 0
            while (tarr.get(i+1) - tarr.get(i) == 1) {
                tarr.add(tarr.get(0))
                tarr.remove(tarr.get(0))
            }
            tarr.add(tarr.get(0))
            tarr.remove(tarr.get(0))
            when(App.prefs.language) {
                "ko" -> nextDay = "내일 "
                "ja" -> nextDay = "翌日 "
            }
        }
        //기간 나누기
        var oneFlag = false
        var twoFlag = false
        for ( i in 0..tarr.size - 1 ) {
            var flag = false
            if ( i == 0 && tarr.size > 1) {
                if (tarr.get(1) - tarr.get(0) > 1) {
                    twoFlag = true
                } else {
                    str += tarr.get(0)
                }
            }
            if ( i > 0 && tarr.get(i) - tarr.get(i - 1) > 1 ) {
                if (oneFlag == false) {
                    str += " ~ ${tarr.get(i-1)}${hourStr}, ${tarr.get(i)}"
                    if (i == tarr.size - 1) str += hourStr
                } else {
                    if (i == tarr.size - 1) {
                        str += "${hourStr}, ${tarr.get(i)}${hourStr}"
                    } else {
//                        str += ", ${tarr.get(i)}${hourStr}"
                        if (tarr[i-1] - tarr[i-2] > 1 && tarr[i] - tarr[i-1] > 1) {
                            str += "${hourStr}, ${tarr.get(i)}"
                        } else {
                            str += ", ${tarr.get(i)}"
                        }
                    }
                    oneFlag = false
                }
                if (i < tarr.size - 1 && tarr.get(i) - tarr.get(i-1) > 1 && tarr.get(i+1) - tarr.get(i) > 1) {
                    oneFlag = true
                }
                if ( i == tarr.size -1 && tarr.size > 1 ) flag = true
            }
            if ( i == tarr.size -1 && tarr.size > 1 && flag == false ) {
                str += " ~ ${nextDay}${tarr.get(i)}${hourStr}"
            }
            if (tarr.size == 1) str += "${tarr.get(0)}${hourStr}"
        }
        if (twoFlag == true) str = str.replaceFirst("~", "")
        if (tarr.size == 24) {
            str = str.replaceFirst("1", "0")
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
        db.selectLiveSearch("%テントウムシ%", "北半球").blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(true, flag)
    }

    //이름 검색 일부분
    @Test
    fun searchName2() {
        var flag = false
        db.selectLiveSearch("%テン%", "北半球").blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(true, flag)
    }

    //이름 검색 일부분
    @Test
    fun searchName3() {
        var flag = false
        db.selectLiveSearch("%テト%", "北半球").blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(false, flag)
    }

    //이름 검색 다른 이름
    @Test
    fun searchName4() {
        var flag = false
        db.selectLiveSearch("%タゴ%", "北半球").blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(false, flag)
    }

    //이름 검색 영어 검색
    @Test
    fun searchName5() {
        var flag = false
        db.selectLiveSearch("%ladybug%", "北半球").blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(false, flag)
    }

    //이름 검색 한글 검색
    @Test
    fun searchName6() {
        var flag = false
        db.selectLiveSearch("%무당벌레%", "北半球").blockingValue?.forEach {
            if (it.name == "テントウムシ") flag = true
        }
        assertEquals(false, flag)
    }

    //이름 한자 추가 검색
    @Test
    fun searchName7() {
        var flag = false
        db.selectLiveSearch("%テントウムシタ%", "北半球").blockingValue?.forEach {
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
        val animal = db.selectCode("FIS0024", "北半球")
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
        val animal = db.selectCode("FIS0024", "北半球")
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



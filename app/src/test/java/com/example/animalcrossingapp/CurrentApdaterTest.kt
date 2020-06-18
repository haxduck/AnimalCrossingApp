package com.example.animalcrossingapp

import android.app.Application
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
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
class CurrentApdaterTest {

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


    @Test
    fun arrangeMonth() {
       /* CurrentAdapter().onBindViewHolder(CurrentAdapter.CurrentViewHolder())*/
    }

    @Test
    fun arrangeTime() {

    }




}


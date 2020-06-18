package com.example.animalcrossingapp

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.animalcrossingapp.database.AnimalCrossingDAO
import com.example.animalcrossingapp.database.AnimalCrossingDB
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AnimalCrossingDAOTest1 {
    private lateinit var animalDao: AnimalCrossingDAO
    private lateinit var db: AnimalCrossingDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(
            context.applicationContext,
            AnimalCrossingDB::class.java, "AnimalCrossing.db"
        ).allowMainThreadQueries().createFromAsset("AnimalCrossing.db").build()
        animalDao = db.animalCrossingDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        var test = animalDao.selectLive().blockingValue?.size
        assertEquals(160, test)
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
}


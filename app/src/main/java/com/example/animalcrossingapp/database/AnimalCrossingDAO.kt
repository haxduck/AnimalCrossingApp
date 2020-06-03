package com.example.animalcrossingapp.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface AnimalCrossingDAO {
    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.price AS price,
            h.name_japan AS habitat,
            i.catch_flag AS flag,
            group_concat(DISTINCT m.month) AS month, 
            group_concat(DISTINCT t.time) AS time
        FROM 
            Information AS i
            LEFT JOIN Month_Information mi ON mi.information_code = i.information_code
            LEFT JOIN Capture c ON i.capture_code = c.capture_code
            LEFT JOIN Habitat h ON h.habitat_code = i.habitat_code
            LEFT JOIN Month m ON m.month_code = mi.month_code
            LEFT JOIN Time_Information ti ON ti.information_code = i.information_code
            LEFT JOIN Time t ON t.time_code = ti.time_code
        GROUP BY i.information_code
        """)
    fun selectAll() : List<Current>


    @Query("""
            select *
            FROM Information 
            inner join Habitat 
            ON Information.habitat_code = Habitat.habitat_code
            """)
    @Transaction
    fun selectAllJ() : List<JoinTest>

    @Query(
        """
            SELECT
                i.information_code,
                i.name_japan, 
                c.picture, 
                h.name_japan, 
                i.price, 
                i.catch_flag, 
                i.size, 
                i.sortation,
                group_concat(DISTINCT m.month) AS months, 
                group_concat(DISTINCT t.time) AS times
            FROM 
                Information AS i
                LEFT JOIN Month_Information mi ON mi.information_code = i.information_code
                LEFT JOIN Capture c ON i.capture_code = c.capture_code
                LEFT JOIN Habitat h ON h.habitat_code = i.habitat_code
                LEFT JOIN Month m ON m.month_code = mi.month_code
                LEFT JOIN Time_Information ti ON ti.information_code = i.information_code
                LEFT JOIN Time t ON t.time_code = ti.time_code
            WHERE m.location = '北半球'
            GROUP BY i.information_code
            """
                )
    fun selectJoin(): List<MyObject>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.price AS price,
            h.name_japan AS habitat,
            i.catch_flag AS flag,
            group_concat(DISTINCT m.month) AS month, 
            group_concat(DISTINCT t.time) AS time
        FROM 
            Information AS i
            LEFT JOIN Month_Information mi ON mi.information_code = i.information_code
            LEFT JOIN Capture c ON i.capture_code = c.capture_code
            LEFT JOIN Habitat h ON h.habitat_code = i.habitat_code
            LEFT JOIN Month m ON m.month_code = mi.month_code
            LEFT JOIN Time_Information ti ON ti.information_code = i.information_code
            LEFT JOIN Time t ON t.time_code = ti.time_code
        WHERE 
            m.location = :hemisphere
            AND t.time = :currentHour
            AND m.month = :currentMonth

        GROUP BY i.information_code        
        """)
    fun selectCurrentAnimal(hemisphere: String, currentHour: String, currentMonth: String): List<Current>

    //tabLayout Insect 가져오는 쿼리
    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.price AS price,
            h.name_japan AS habitat,
            i.catch_flag AS flag,
            group_concat(DISTINCT m.month) AS month, 
            group_concat(DISTINCT t.time) AS time
        FROM 
            Information AS i
            LEFT JOIN Month_Information mi ON mi.information_code = i.information_code
            LEFT JOIN Capture c ON i.capture_code = c.capture_code
            LEFT JOIN Habitat h ON h.habitat_code = i.habitat_code
            LEFT JOIN Month m ON m.month_code = mi.month_code
            LEFT JOIN Time_Information ti ON ti.information_code = i.information_code
            LEFT JOIN Time t ON t.time_code = ti.time_code
        WHERE 
            m.location = :hemisphere
            AND t.time = :currentHour
            AND m.month = :currentMonth
            AND i.sortation = :currentSortation
            AND i.catch_flag = '0'
        GROUP BY i.information_code        
        """)
    fun selectCurrentTablayoutAnimal(hemisphere: String, currentHour: String, currentMonth: String, currentSortation: String): List<Current>




    @Query("UPDATE Information SET catch_flag = :flag WHERE information_code = UPPER(:info)")
    fun updateCatchFlag(info: String, flag: String)

    @Query("SELECT catch_flag FROM Information WHERE catch_flag ='1' AND sortation = '魚'")
    fun viewCatchFish(): List<String>

    @Query("SELECT catch_flag FROM Information WHERE catch_flag ='1' AND sortation = '虫'")
    fun viewCatchBug(): List<String>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.price AS price,
            h.name_japan AS habitat,
            i.catch_flag AS flag,
            group_concat(DISTINCT m.month) AS month, 
            group_concat(DISTINCT t.time) AS time
        FROM 
            Information AS i
            LEFT JOIN Month_Information mi ON mi.information_code = i.information_code
            LEFT JOIN Capture c ON i.capture_code = c.capture_code
            LEFT JOIN Habitat h ON h.habitat_code = i.habitat_code
            LEFT JOIN Month m ON m.month_code = mi.month_code
            LEFT JOIN Time_Information ti ON ti.information_code = i.information_code
            LEFT JOIN Time t ON t.time_code = ti.time_code
        WHERE 
            m.location = '北半球'
        GROUP BY i.information_code
        ORDER BY i.catch_flag DESC
        """)
    fun selectArrange(): List<Current>



}
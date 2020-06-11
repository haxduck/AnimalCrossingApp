package com.example.animalcrossingapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface AnimalCrossingDAO {
    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
        GROUP BY i.information_code
        """)
    fun selectAll(hemisphere: String) : LiveData<List<Current>>


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
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
            AND i.catch_flag = '0'
        GROUP BY i.information_code        
        """)
    fun selectCurrentAnimal(hemisphere: String, currentHour: String, currentMonth: String): List<Current>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
             i.price AS price,
            h.name_japan AS habitat,
            i.catch_flag AS flag,
            group_concat(DISTINCT Month.month) AS month,
            group_concat(DISTINCT t.time) AS time
        FROM
            Information AS i
            LEFT JOIN Month_Information mi ON mi.information_code = i.information_code
            LEFT JOIN Capture c ON i.capture_code = c.capture_code
            LEFT JOIN Habitat h ON h.habitat_code = i.habitat_code
            LEFT JOIN Month ON Month.month_code = mi.month_code
            LEFT JOIN Time_Information ti ON ti.information_code = i.information_code
            LEFT JOIN Time t ON t.time_code = ti.time_code
        WHERE
            Month.location = :hemi
            AND i.sortation = :arrSort
            AND h.name_japan = :loc
            AND i.price >= :min
            AND i.price <= :max
        GROUP BY i.information_code
        """)

    fun select(hemi: String?, arrSort: String, min: Int, max: Int, loc: ArrayList<String>): List<Current>

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
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
        GROUP BY i.information_code
        ORDER BY i.catch_flag DESC
        """)
    fun selectArrange(hemisphere: String): List<Current>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
            i.name_japan LIKE :name
        GROUP BY i.information_code
        """)
    fun selectSearch(name: String): List<Current>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
            i.sortation = '魚'
        GROUP BY i.information_code
        """)
    fun selectFishes(): List<Current>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
            i.sortation = '虫'
        GROUP BY i.information_code
        """)
    fun selectBugs(): List<Current>
    //tabLayout Insect 가져오는 쿼리
    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
            i.sortation = '虫'

        GROUP BY i.information_code        
        """)
    fun selectTablayoutAllInsect(): List<Current>


    //tabLayout fish  가져오는 쿼리
    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
            i.sortation = '魚'

        GROUP BY i.information_code        
        """)
    fun selectTablayoutAllFish(): List<Current>

    //tabLayout animal  가져오는 쿼리
    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
      fun selectTablayoutAllAnimalList(): List<Current>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
    fun selectLive() : LiveData<List<Current>>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
            AND i.catch_flag = '0'
        GROUP BY i.information_code        
        """)
    fun selectLiveCurrentAnimal(hemisphere: String, currentHour: String, currentMonth: String): LiveData<List<Current>>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
        GROUP BY i.information_code
        ORDER BY i.catch_flag DESC
        """)
    fun selectLiveArrange(hemisphere: String): LiveData<List<Current>>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
            i.name_japan LIKE :name
        GROUP BY i.information_code
        """)
    fun selectLiveSearch(name: String): LiveData<List<Current>>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
            i.catch_flag LIKE :flag
            AND i.sortation LIKE :sort
            AND m.month IN (:months)
            AND i.price >= :minPrice AND i.price <= :maxPrice
            AND t.time >= :minTime AND t.time <= :maxTime
            AND h.name_japan IN (:places)
        GROUP BY i.information_code
        """)
    fun selectLiveDetail(
        flag: String,
        sort: String,
        months: ArrayList<String>,
        minTime: String,
        maxTime: String,
        minPrice: Int,
        maxPrice: Int,
        places: ArrayList<String>
    ): LiveData<List<Current>>

    @Query("""
        SELECT
            lower(i.information_code) AS information_code,
            i.name_japan AS name,
            i.name_korea AS namek,
            i.price AS price,
            h.name_japan AS habitat, h.name_korea AS habitatk,
            i.catch_flag AS flag,
            i.sortation AS sortation,
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
            i.information_code = :code
        GROUP BY i.information_code
        """)
    fun selectCode(code: String): Current

}
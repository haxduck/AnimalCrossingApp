package com.example.animalcrossingapp.table

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TestDAO {
    @Query("SELECT * FROM Information")
    fun selectAll() : List<Information>

    @Query("SELECT * FROM Capture")
    fun selectAllP() : List<Capture>

    @Query("SELECT * FROM habitat")
    fun selectAllH() : List<Habitat>

    @Query("SELECT * FROM Month")
    fun selectAllM() : List<Month>

    @Query("SELECT * FROM Month_Information")
    fun selectAll1() : List<Month_Information>

    @Query("SELECT * FROM Time")
    fun selectAll2() : List<Time>

    @Query("SELECT * FROM Time_Information")
    fun selectAll3() : List<Time_Infomation>

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
group_concat(DISTINCT m.month) as months, 
group_concat(DISTINCT t.time) as times

FROM 

Information as i
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
}
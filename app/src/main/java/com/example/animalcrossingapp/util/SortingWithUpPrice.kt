package com.example.animalcrossingapp.util

import com.example.animalcrossingapp.vo.AllVO
import java.util.*

//가격오름차순 정렬
object SortingWithUpPrice {
    @JvmStatic
    fun main(args: Array<String>) {
        val all =
            arrayOf<AllVO>()
        Arrays.sort(
            all
        ) { o1, o2 ->
            val pr1 = (o1 as AllVO).price
            val pr2 = (o2 as AllVO).price
            if (pr1 > pr2) 1 else if (pr1 == pr2) 0 else -1
        }
        for (result in all) {
            println(result)
        }
    }
}
package com.example.animalcrossingapp.util

import com.example.animalcrossingapp.vo.AllVO
import java.util.*

object SortingWithName {

    @JvmStatic
    fun main(args: Array<String>) {
        val all = arrayOf<AllVO>()
        //이름 오름차순 정렬
        fun upSort(){
            Arrays.sort(all)
        }
        //이름 내림차순 정렬
        fun downSort(){
            for (i in 0 until all.size / 2) {
                val tmp = all[i]
                all[i] = all[all.size - 1 - i]
                all[args.size - 1 - i] = tmp
            }
        }
    }

}

class AllVO : Comparable<Any> {
    var name: String? = null
    override fun compareTo(o: Any): Int {
        return name!!.compareTo((o as AllVO).name_japan!!)
    }
}
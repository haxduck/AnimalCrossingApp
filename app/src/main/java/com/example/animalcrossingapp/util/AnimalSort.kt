package com.example.animalcrossingapp.util

object AnimalSort {
    private const val number = 11
    private val data: IntArray = TODO()
    private var cnt = 0

    // quick_sort 호출 횟수
    fun quick_sort(data: IntArray, x: Int, y: Int) {
        cnt++
        if (x >= y) { // 원소가 1개인 경우
            return
        }
        var left = x + 1
        var right = y
        var temp: Int
        while (left < right) {
            while (left <= y && data[left] < data[x]) {
                left++
            }
            while (right >= x && data[x] < data[right]) {
                right--
            }
            if (left < right) {
                temp = data[left]
                data[left] = data[right]
                data[right] = temp
            } else {
                temp = data[x]
                data[x] = data[right]
                data[right] = temp
            }
            quick_sort(data, x, right - 1)
            quick_sort(data, right + 1, y)
        }
    }

    fun printData(data: IntArray, number: Int) {
        for (i in 0 until number) {
            print(data[i].toString() + " ")
        }
        println()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        // 1~100 랜덤한 값 출력 data = new int[number];
        for (i in 0 until number) {
            data[i] = (Math.random() * 100).toInt()
        }
        // 정렬 전
        printData(data, number)
        // 정렬 시작
        quick_sort(data, 0, number - 1)
        // 정렬 후
        printData(data, number)
        // 연산 횟수
        println("N : $number / cnt : $cnt")
    }
}
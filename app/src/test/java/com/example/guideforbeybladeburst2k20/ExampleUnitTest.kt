package com.example.guideforbeybladeburst2k20

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    fun String.chunk(del: Int) {
        val splitedList = mutableListOf<String>()
        var charArray = this.toCharArray()
        val endFor = charArray.size / del
        var start = 0
        var endIndex = del
        var shifter = 0
        for (x in 0..endFor) {
            start = del * x + shifter
            endIndex = start + del
            val newCharArray = charArray.copyOfRange(start, endIndex).toMutableList()
            while (!newCharArray[newCharArray.size - 1].equals(" ")) {
                newCharArray.add(charArray[endIndex + 1])
                shifter += 1
            }
            splitedList.add(newCharArray.toString())

        }
    }
}
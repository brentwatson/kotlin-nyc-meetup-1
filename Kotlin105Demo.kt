package com.brentwatson.kotlin.nyc.demo

/**
 * Demo IDE shortcuts to convert for-loops to functional constructs.
 */
object Kotlin105Demo {
    
    private data class Item(val enabled : Boolean = true)
    private data class User(val name : String = "")
    private fun getUsers() = listOf(User(), null, User(), null, null, User())

    fun one() {
        val items = listOf(Item(true), Item(false), Item(), Item(), Item(false))

        val enabledItems = mutableListOf<Item>()
        for (item in items) {
            if(item.enabled) {
                enabledItems.add(item)
            }
        }
        println(enabledItems)

        val result = arrayListOf<User>()
        for (user in getUsers()) {
            if (user != null) {
                result.add(user)
            }
        }
        print(result)
    }

    fun two() {
        val nums = 1..1000
        var total = 0
        for (num in nums) {
            total += num
        }
        print("Total sum : " + total)
    }

    fun three() {
        var min = Integer.MAX_VALUE;
        for (i in listOf(88, 54, 31, 67, 31, 29, 77)) {
            min = Math.min(min, i)
        }
        println("Min value is: $min")
    }


    fun four() {
        val strings = listOf("", "", "")

        var firstUpper : String? = null
        for (s in strings) {
            if(s.toCharArray()[0].isUpperCase()) {
                firstUpper = s
                break
            }
        }
        println(firstUpper)

        var lastUpper : String? = null
        for (s in strings) {
            if(s.toCharArray()[0].isUpperCase()) {
                lastUpper = s
            }
        }
        println(lastUpper)
    }
}


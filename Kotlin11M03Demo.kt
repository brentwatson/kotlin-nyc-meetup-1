import kotlinx.coroutines.async
import kotlinx.coroutines.asyncUI
import kotlinx.coroutines.generate
import java.awt.Point

object Kotlin11M03Demo {

    // How to setup Kotlin 1.1
    // 1.  Configure Kotlin Plugin Updates -> 1.1
    // 2.  build.gradle updates
    // ** Warning: Beta! **

    private data class Demo(val one: String, val two: String)
    val numbers = listOf("1", "23")

    /**
     * You can now use "_" for unused variable names.
     * You can now use "_" in numeric values.
     */
    private fun underscores() {
        numbers.forEachIndexed { _, s ->
            println(s)
        }
        val (a, _) = Demo("eh", "bee") // IDE warning on "a", but not on "_"
        val oneMillionDollars = 1_000_000
    }


    /**
     * New `enumValues` and `enumValueOf` methods in stdlib.
     */
    enum class DemoEnum { ONE, TWO, THREE }

    private fun enumEnhancements() {
        println(enumValues<DemoEnum>().joinToString())
        val enumValueOf = enumValueOf<DemoEnum>("one")
        enumValueOf.ordinal
    }

    /**
     * No imports needed for various common classes.
     */
    private fun defaultImport() {
        val a = ArrayList<String>()
        val b = HashMap<String, String>()
    }

    /**
     * New methods such as `toMutableList()`, `toSortedSet()`, etc.
     */
    private fun newToMethods() {
        println(listOf("one", "two", "three").toMutableList().add("one"))
        println(listOf("one", "one", "three").toMutableSet())
    }

    /**
     * Support for typealias.
     */
    typealias Action<T> = (T) -> Unit
    typealias Example<T> = T.() -> String
    typealias Example2<T, V> = (T) -> V
    typealias MyFilter<T> = (T) -> Boolean
    typealias MyStringFilter = (s: String) -> Boolean

    typealias Length = Double
    typealias Width = Double

    private fun typeAliases() {
        val e: Example2<String, String> = { s -> s.reversed().toUpperCase() }
        e("my string")

        val f: MyStringFilter = { it.length > 2 }
        println(listOf("1", "111", "2", "222").filter(f))

        var x: Width = 5.5
        val y: Length = 4.4
        var z: Double = 5.5
        x = y
        z = y
        println("$x $y $z")
    }

    /**
     * Using typealias type as a method arg...
     */
    @Suppress("unused")
    fun String.logBeforeAction(action: Action<String>) {
        //Log.d()
        action(this)
    }

    /**
     * Method references can now be made on variables.
     */
    fun boundCallableReferences() {
        listOf("one", "two", "three").map(String::length)
        listOf("one", "two", "three").map { it[0]::toUpperCase }
    }

    /**
     * Destructuring can now be done as lambda parameters.
     */
    fun destructuringInLambdas() {
        listOf(Pair("", "")).map { (a, b) -> a + b }
        listOf(Demo("", "")).map { (one, two) -> "${one.capitalize()} $two" }
    }

    /**
     * Coroutines (async/wait & generate/yield)
     * Good talk on this here: https://www.youtube.com/watch?v=4W3ruTWUhpw
     */
    private data class FakeImage(val url: String)

    fun coroutines() {
        // Example problem:

        // val img = loadImage(url)
        // myUI.setImage(img)

        // Even worse:
        // var img = loadImage(url)
        // img = processImage(img)
        // myUI.setImage(img)

        // Current solution:
        // loadImage(url) { img ->
        //   processImage(img) { img ->
        //     myUI.setImage(img)
        //   }
        // }
        //

        // Async / Wait to the rescue!
        asyncUI {
            try {
                val loadImage = await(loadImage("http://test.com/test.png"))
                val processImage = await(processImage(loadImage))
                println(processImage)
            } catch (e: Exception) {

            }
        }

        val future = async<String> {
            (1..5).map {
                await(startLongAsyncOperation(it))
            }.joinToString("\n")
        }
        println(future.get())

        // Generate / Yield
        val sequence = generate<Int> {
            yield(999)
            for (i in 1..100) {
                yield(i)
            }
            yield(-1)
        }
        println(sequence.joinToString())
    }

    private fun loadImage(url: String) = async<FakeImage> {
        Thread.sleep(1000)
        FakeImage(url)
    }

    private fun processImage(img: FakeImage) = async<FakeImage> {
        Thread.sleep(1000)
        img
    }

    private fun startLongAsyncOperation(v: Int) = async<String> {
        Thread.sleep(1000)
        "Result: $v"
    }

    /**
     * Data classes can now extend from other classes.
     */
    data class ExtendedDataClass(val x: Int) : Point()

    @JvmStatic fun main(args: Array<String>) {
        underscores()
        enumEnhancements()
        defaultImport()
        newToMethods()
        typeAliases()
        boundCallableReferences()
        destructuringInLambdas()
        coroutines()
        ExtendedDataClass(1)
    }
}


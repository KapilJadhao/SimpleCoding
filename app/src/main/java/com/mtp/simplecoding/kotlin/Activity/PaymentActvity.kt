package com.mtp.simplecoding.kotlin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.mtp.simplecoding.R
import com.mtp.simplecoding.kotlin.Adapter.AnimalAdapter
import kotlinx.android.synthetic.main.activity_payment_actvity.*

class PaymentActvity : AppCompatActivity() {

    // Initializing an empty ArrayList to be filled with animals
    val animals: ArrayList<String> = ArrayList()


    val a: Int = 10000
    val d: Double = 100.00
    val f: Float = 100.00f
    val l: Long = 1000000004
    val s: Short = 10
    val b: Byte = 1

    var rawString :String  = "I am Raw String!"
    val escapedString : String  = "I am escaped String!\n"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_actvity)

        // Loads animals into the ArrayList
        addAnimals()

        // Creates a vertical Layout Manager
        rv_animal_list.layoutManager = LinearLayoutManager(this)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it
        rv_animal_list.adapter = AnimalAdapter(animals, this)
    }
    // Adds animals to the empty animals ArrayList
    fun callFragment(){
        val bundle:Bundle



    }

    fun addAnimals() {
        animals.add("dog")
        animals.add("cat")
        animals.add("owl")
        animals.add("cheetah")
        animals.add("raccoon")
        animals.add("bird")
        animals.add("snake")
        animals.add("lizard")
        animals.add("hamster")
        animals.add("bear")
        animals.add("lion")
        animals.add("tiger")
        animals.add("horse")
        animals.add("frog")
        animals.add("fish")
        animals.add("shark")
        animals.add("turtle")
        animals.add("elephant")
        animals.add("cow")
        animals.add("beaver")
        animals.add("bison")
        animals.add("porcupine")
        animals.add("rat")
        animals.add("mouse")
        animals.add("goose")
        animals.add("deer")
        animals.add("fox")
        animals.add("moose")
        animals.add("buffalo")
        animals.add("monkey")
        animals.add("penguin")
        animals.add("parrot")

        println("Your Int Value is "+a);
        println("Your Double  Value is "+d);
        println("Your Float Value is "+f);
        println("Your Long Value is "+l);
        println("Your Short Value is "+s);
        println("Your Byte Value is "+b);
        println("Hello!"+escapedString)
        println("Hey!!"+rawString)
    }
}

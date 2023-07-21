package com.abhiiscoding.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var lastNumeric: Boolean= false
    private var lastDot: Boolean= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     tvInput = findViewById(R.id.tvInput)
    }
    fun onBtnClicked(view: View)
    {
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    fun onClr(view: View)
    {
        tvInput?.text=""
    }
    fun onDecimalPoint(view: View)
    {
        if (lastNumeric && !lastDot)
        {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View)
    {
        tvInput?.text?.let {
            if (lastNumeric && !operatorSelected(it.toString())){
                tvInput?.append((view as Button).text)
                lastDot = false
                lastNumeric = false
            }
        }
    }


    fun onSquareRoot(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                val result = Math.sqrt(tvValue.toDouble())

                if (prefix.isNotEmpty()) {
                    tvInput?.text = removeZeroAfterDot((prefix + result).toString())
                } else {
                    tvInput?.text = removeZeroAfterDot(result.toString())
                }

            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }
    }

    fun onEqual(view: View)
    {
        if(lastNumeric)
        {
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try {

                if (tvValue.startsWith("-"))
                {
                    prefix = "-"
                    //when there is "-" sign at the staring and middle of an operation,
                    //we have to eliminate the starting sign to do the operation.
                    //To do that, we apply the substring method, it starts the string from the index given to it
                    tvValue = tvValue.substring(1)//this will start the string from index 1
                }

                if (tvValue.contains("-")){
                    //this splits the input. Eg: if the input is 99-1, then '99' will be stored at the 0 index of the array and
                    // '1' will be stored at the 1 index of the array.
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix+one
                    }

                    //The tvInput is a string type variable so we have to convert the 'one' and 'two' to string
                    // after doing the operations on them as Doubles.
                    tvInput?.text=removeZeroAfterDot((one.toDouble()-two.toDouble()).toString())
                } else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix+one
                    }
                    tvInput?.text=removeZeroAfterDot((one.toDouble()+two.toDouble()).toString())
                } else if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix+one
                    }
                    tvInput?.text=removeZeroAfterDot((one.toDouble()*two.toDouble()).toString())
                }  else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix+one
                    }
                    tvInput?.text=removeZeroAfterDot((one.toDouble()/two.toDouble()).toString())
                } else if (tvValue.contains("√")){

                }
            }catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")){
            value = result.substring(0,result.length-2)
        }
        return value
    }
    private fun operatorSelected(value: String) :Boolean
    {
        return if (value.startsWith("-"))
        {
           false
        }
        else{
            value.contains("/")
                    ||value.contains("*")
                    ||value.contains("+")
                    ||value.contains("-")
        }
    }
}
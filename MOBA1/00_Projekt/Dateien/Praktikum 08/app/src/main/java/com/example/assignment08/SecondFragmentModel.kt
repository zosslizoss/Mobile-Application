package com.example.assignment08

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecondFragmentModel(): ViewModel() {
    var stocks = MutableLiveData<MutableList<Stock>>()

    val fileName: String = "testfile1   "

    init {
        stocks.value = mutableListOf<Stock>()
    }

    fun initStockList(items: MutableList<Stock>, context: Context){
        val settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = settings.edit()
        items.forEach{
            editor.putString(it.symbol,it.value)
            editor.commit()
        }
    }

    fun addStock(symbol: String, value: String, context: Context) {
        stocks.value!!.add(Stock(symbol, value))
        stocks.postValue(stocks.value!!.toMutableList())
        val settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString(symbol, value.toString())
        editor.commit()
    }

    fun loadStock(context:Context) {
        val settings = context?.getSharedPreferences(fileName, Context.MODE_PRIVATE)


        //it is important that you get an editor reference!
        val editor = settings?.edit()
        val allEntries: Map<String, *> = settings?.getAll() as Map<String, *>
        Log.e("where is my key: ", allEntries.toString())
        for ((key, value) in allEntries) {
            Log.e("where is my key: ", key)
            stocks.value!!.add(Stock(key, value.toString()))
            stocks.postValue(stocks.value!!.toMutableList())
        }

        stocks.value = mutableListOf<Stock>()
    }
}
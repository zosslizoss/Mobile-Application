package com.example.herobattler

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CharactersModel : ViewModel() {
    var characters = MutableLiveData<MutableList<Character>>()

    val fileName: String = "characterSave11"
    val seperator: String = ";"

    init {
        characters.value = mutableListOf<Character>()
    }

    //
    fun initCharacterList(items: MutableList<Character>, context: Context){
        val characterSave = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = characterSave.edit()
        items.forEach{
            editor.putString(it.characterName,
                it.characterLevel+seperator+
                        it.characterClass+seperator+
                        it.characterRace+seperator+
                        it.characterHP+seperator +
                        it.characterMaxHP)
            editor.apply()
        }
    }

    fun addCharacter(name: String, level: String, race: String, cclass: String, hp: String, maxHp: String, context: Context) {
        val settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString(name, level+seperator + cclass+seperator + race+seperator + hp+seperator+ maxHp)
        editor.apply()
    }

    fun loadCharacter(context: Context) {
        val settings = context?.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        characters.value = mutableListOf<Character>()

        //it is important that you get an editor reference!
        val editor = settings?.edit()
        val allEntries: Map<String, *> = settings?.getAll() as Map<String, *>
        for ((key, value) in allEntries) {
            characters.value!!.add(Character(
                key,
                parseLevel(value.toString())[0].trim(),
                parseLevel(value.toString())[1].trim(),
                parseLevel(value.toString())[2].trim(),
                parseLevel(value.toString())[3].trim(),
                parseLevel(value.toString())[4].trim()
            ))
        }
        characters.postValue(characters.value!!.toMutableList())

    }

    fun getMutableListOfCharacters(): MutableList<Character> {
        return characters.value!!.toMutableList()
    }

    fun deleteCharacter(name: String, context: Context){
        val settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.remove(name)
        editor.apply()
    }

    fun resetCharacters(context: Context) {
        val settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.clear()
        editor.apply()
    }

    fun parseLevel(value: String): List<String> {
        val separated: List<String> = value.split(seperator)
        return separated
    }
}
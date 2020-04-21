package com.example.colours.view

import android.content.Context
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.colours.di.component.ColoursActivityViewModelComponent
import com.example.colours.di.component.DaggerColoursActivityViewModelComponent
import com.example.colours.di.module.ColoursActivityViewModelModule
import com.example.colours_ao_test.R
import com.example.colours_ao_test.com.ColourEntity
import com.example.colours_ao_test.com.SHARED_PREFERENCES_KEY
import com.example.colours_ao_test.com.SHARED_PREFERENCE_STRING
import com.example.colours_ao_test.view.ColoursAdapter
import com.example.colours_ao_test.view_model.ColoursActivityViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_colours.*
import java.lang.reflect.Type
import javax.inject.Inject

class ColoursActivity : AppCompatActivity() {

    @Inject
    lateinit var colourActivityViewModel: ColoursActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colours)

        injectDependency().inject(this)

        successful(loadData())

        colourActivityViewModel.loadingState.observe(this, Observer {
            when (it) {
                ColoursActivityViewModel.LoadingState.LOADING -> loading()
                is ColoursActivityViewModel.LoadingState.SUCCESS -> successful(it.listOfWords)
                is ColoursActivityViewModel.LoadingState.FAILURE -> failure(it.errorMessage)
            }
        })

        btn_addColour.setOnClickListener {
            colourActivityViewModel.getWords()
        }
    }

    private fun saveData(listOfWords: ArrayList<ColourEntity>) {
        val prefernces = getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val editor = prefernces.edit()
        val gson = Gson()
        val json = gson.toJson(listOfWords)
        editor.putString(SHARED_PREFERENCE_STRING, json)

        editor.apply()
    }

    private fun loadData(): ArrayList<ColourEntity> {
        val prefernces = getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String = prefernces.getString(SHARED_PREFERENCE_STRING, null)
                ?: return arrayListOf()
        val type: Type = object : TypeToken<List<ColourEntity>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun injectDependency(): ColoursActivityViewModelComponent {
        return DaggerColoursActivityViewModelComponent
                .builder()
                .coloursActivityViewModelModule(ColoursActivityViewModelModule(this))
                .build()
    }

    private fun failure(errorMessage: String?) {
        pb_loading.visibility = GONE
        rv_colours.visibility = GONE
        tv_errorMessage.visibility = VISIBLE
        tv_errorMessage.text = errorMessage

    }

    private fun successful(listOfWords: ArrayList<ColourEntity>) {
        if (listOfWords.isEmpty()) colourActivityViewModel.getWords()

        pb_loading.visibility = GONE
        tv_errorMessage.visibility = GONE
        rv_colours.visibility = VISIBLE
        saveData(listOfWords)
        setUpRecyclerView(listOfWords)
    }

    private fun loading() {
        rv_colours.visibility = GONE
        tv_errorMessage.visibility = GONE
        pb_loading.visibility = VISIBLE
    }

    private fun setUpRecyclerView(listOfColourEntities: ArrayList<ColourEntity>) {
        rv_colours.layoutManager = LinearLayoutManager(this)
        rv_colours.adapter = ColoursAdapter(listOfColourEntities)
    }
}

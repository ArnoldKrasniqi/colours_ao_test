package com.example.colours.di.component

import com.example.colours.di.module.ColoursActivityViewModelModule
import com.example.colours.view.ColoursActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ColoursActivityViewModelModule::class])
interface ColoursActivityViewModelComponent {
    fun inject(coloursActivity : ColoursActivity)
}
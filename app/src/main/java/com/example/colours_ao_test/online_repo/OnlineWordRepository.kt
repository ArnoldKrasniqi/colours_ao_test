package com.example.colours_ao_test.online_repo

import javax.inject.Inject

class OnlineWordRepository @Inject constructor(private val onlineWordClient: OnlineWordClient) {

    suspend fun getRandomNames() : List<String> =  onlineWordClient.getNames()



}
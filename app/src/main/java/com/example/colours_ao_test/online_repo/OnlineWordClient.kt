package com.example.colours_ao_test.online_repo

import com.example.colours_ao_test.com.END_POINT
import retrofit2.http.GET

interface OnlineWordClient {

    @GET(END_POINT)
    suspend fun getNames() : List<String>

}


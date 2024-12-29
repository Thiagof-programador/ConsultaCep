package com.example.consultacep.api

import com.example.consultacep.model.Endereco
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EnderecoAPI {
    //https://viacep.com.br/ws/01001000/json/
    // Base URL :  https://viacep.com.br/
    // ROTA OU ENDPOINT
    //GET,POST,PUT,PATCH E DELETE
    @GET("ws/{cep}/json/")

    suspend fun recuperarEndereco(
        @Path("cep") cep: String
    ) : Response<Endereco>
}
package com.example.consultacep.model

data class Endereco(
    val bairro: String,
    val cep: String,
    val complemento: String,
    val ddd: String,
    val estado: String,
    val gia: String,
    val ibge: String,
    val localidade: String,
    val logradouro: String,
    val regiao: String,
    val siafi: String,
    val uf: String,
    val unidade: String
)
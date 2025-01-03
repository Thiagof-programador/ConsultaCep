package com.example.consultacep

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.consultacep.api.EnderecoAPI
import com.example.consultacep.api.RetrofitHelper
import com.example.consultacep.databinding.ActivityMainBinding
import com.example.consultacep.model.Endereco
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnConsultar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                recuperarEndereco()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TextCep)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private suspend fun recuperarEndereco(){

        var retorno: Response<Endereco>? = null
        val cepDigitadoUsuario = binding.editCep.text.toString()

        try {
            val enderecoAPI = retrofit.create(EnderecoAPI::class.java)
            retorno = enderecoAPI.recuperarEndereco(cepDigitadoUsuario)
        }catch (e:Exception){
            e.printStackTrace()
            //Log.i("info_endereco","erro ao recuperar $e")
            // só pode ser execultado na main pois esta usando CoroutineScope(Dispatchers.IO)
            withContext(Dispatchers.Main){
                binding.textView.text = "erro ao recuperar $e"
            }

        }
        if(retorno != null){
            if(retorno.isSuccessful){

                val endereco = retorno.body()
                val rua = endereco?.logradouro
                val cidade = endereco?.localidade
                val uf = endereco?.uf
                val cepapi = endereco?.cep
                //Log.i("info_endereco","Endereco: $rua , Cidade: $cidade $uf ,CEP: $cepapi")
                withContext(Dispatchers.Main){
                    binding.textView.text = "Endereco: $rua , Cidade: $cidade, Uf: $uf, CEP: $cepapi"
                }
            }else{
                //Log.i("info_endereco","Cep não encontrado: $cepDigitadoUsuario")
                withContext(Dispatchers.Main){
                    binding.textView.text = "Cep não encontrado: $cepDigitadoUsuario"
                }
            }
            binding.editCep.setText("")
        }



    }


}
package com.example.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

data class Data(val pokeName: String,val pokeImageURL: String, val pokeType: String, val pokeAbility: String)
class MainActivity : AppCompatActivity() {
    private lateinit var pokeList: MutableList<Data>
    private lateinit var rvPokemon: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPokemon = findViewById(R.id.poke_list)
        pokeList = mutableListOf()

        getPokeImageURL()
    }

    private fun getPokeImageURL() {
        val client = AsyncHttpClient()
        val pokeList = mutableListOf<Data>()
        val generatedIds = mutableSetOf<Int>()
        while (generatedIds.size < 50) {
            val PokemonsID = Random.nextInt(300, 600)
            if (!generatedIds.contains(PokemonsID)) {
                generatedIds.add(PokemonsID)
                client.get("https://pokeapi.co/api/v2/pokemon/$PokemonsID", object : JsonHttpResponseHandler() {
                    override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                        val pokeName = json.jsonObject.getJSONObject("species").getString("name")
                        val pokeImageURL = json.jsonObject.getJSONObject("sprites").getString("front_default")
                        val pokeType = json.jsonObject.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
                        val pokeAbility = json.jsonObject.getJSONArray("abilities").getJSONObject(0).getJSONObject("ability").getString("name")
                        val datas = Data(pokeName,pokeImageURL, pokeType, pokeAbility)
                        pokeList.add(datas)
                        if (pokeList.size == 50) {
                            val adapter = PokeAdapter(pokeList)
                            rvPokemon.adapter = adapter
                            rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity)
                        }
                    }
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        throwable: Throwable?
                    ) {
                        Log.d("Pokemon Error", errorResponse)
                    }
                })
            }
        }
    }
}

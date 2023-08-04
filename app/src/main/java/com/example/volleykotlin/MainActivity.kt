package com.example.volleykotlin

import android.app.ProgressDialog
import android.location.Geocoder.GeocodeListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.volleykotlin.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        LoadData()

        binding.changeBTN.setOnClickListener {
            LoadData()
        }
    }

    private fun LoadData() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait..")
        progressDialog.show()


        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->

                val obj = JSONObject(response)

                val title = obj.getString("title")
                val author = obj.getString("author")
                val memeLink = obj.getString("url")

                binding.titleTV.text = title
                binding.authorTV.text = author


                Glide.with(this).load(memeLink).into(binding.imgView)

                progressDialog.hide()

            },
            { error ->
                Toast.makeText(this, "error occured", Toast.LENGTH_LONG).show()
                progressDialog.hide()
            })

        queue.add(stringRequest)

    }

}



package com.mstart004.exchangeapp

import adapter.RussianAdapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.mstart004.exchangeapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.language_dialog.view.*
import model.Exchange
import java.text.SimpleDateFormat
import java.util.*

class RussianActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: RussianAdapter
    private lateinit var requestQueue: RequestQueue
    private var url = "http://cbu.uz/uzc/arkhiv-kursov-valyut/json/"

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.txtBar.setText(R.string.valyutalar_russian)

        getRate()
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val currentDate = sdf.format(Date())
        binding.txtDate.text = currentDate


        binding.btnLanguage.setOnClickListener {

            val customDialog = AlertDialog.Builder(this)
            val dialog = customDialog.create()

            dialog.setTitle("Руский")
            val dialogView = layoutInflater.inflate(R.layout.language_dialog, null, false)

            dialog.setView(dialogView)

            dialogView.btn_russian.setOnClickListener {
                onResume()
                dialog.dismiss()

            }

            dialogView.btn_english.setOnClickListener {
                val intent = Intent(this, EnglishActivity2::class.java)
                startActivity(intent)
            }

            dialogView.btn_uzbekLanguage.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            dialog.show()
        }


    }

    private fun getRate() {
        Toast.makeText(this, "Russian Activity", Toast.LENGTH_SHORT).show()
        requestQueue = Volley.newRequestQueue(this)
        VolleyLog.DEBUG = true
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val type = object : TypeToken<List<Exchange>>() {}.type
                val list = Gson().fromJson<List<Exchange>>(response.toString(), type)

                userAdapter = RussianAdapter(list)
                binding.recycle.adapter = userAdapter

            }) { }
        requestQueue.add(jsonArrayRequest)
    }
}
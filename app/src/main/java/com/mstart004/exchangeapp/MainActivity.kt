package com.mstart004.exchangeapp

import adapter.UserAdapter
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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
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

        getRate()
        binding.txtBar.setText(R.string.valyutalar_kursi)
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val currentDate = sdf.format(Date())
        binding.txtDate.text = currentDate

        binding.btnLanguage.setOnClickListener {

            val customDialog = AlertDialog.Builder(this)
            val dialog = customDialog.create()

            dialog.setTitle("Tilni o'zgartirish")
            val dialogView = layoutInflater.inflate(R.layout.language_dialog, null, false)

            dialog.setView(dialogView)

            dialogView.btn_russian.setOnClickListener {
                val intent = Intent(this, RussianActivity2::class.java)
                startActivity(intent)
            }

            dialogView.btn_english.setOnClickListener {
                val intent = Intent(this, EnglishActivity2::class.java)
                startActivity(intent)
            }

            dialogView.btn_uzbekLanguage.setOnClickListener {
                onResume()
                dialog.dismiss()
            }
            dialog.show()
        }


    }

    private fun getRate() {
        Toast.makeText(this, "Uzbek Activity", Toast.LENGTH_SHORT).show()
        requestQueue = Volley.newRequestQueue(this)
        VolleyLog.DEBUG = true

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val type = object : TypeToken<List<Exchange>>() {}.type
                val list = Gson().fromJson<List<Exchange>>(response.toString(), type)

                userAdapter =
                    UserAdapter(list)
                binding.recycle.adapter = userAdapter

            }) { }
        requestQueue.add(jsonArrayRequest)

    }
}

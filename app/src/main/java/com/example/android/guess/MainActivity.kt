package com.example.android.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val logTag = "MainActivity"
    private val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("mainActivity", "Number: ${secretNumber.secret}")
    }

    fun check(view: View) {
        val n = ed_number.text.toString().toInt()
        Log.d("mainActivity", "Number: $n")
        val diff = secretNumber.validate(n)
        var message = "Yes, you get it"
        when {
            diff < 0 -> {
                message = "Bigger"
            }
            diff > 0 -> {
                message = "Smaller"
            }
        }
        //        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle("Message")
            .setMessage(message)
            .setPositiveButton(R.string.ok, null)
            .show()
    }
}

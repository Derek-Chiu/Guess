package com.example.android.guess

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {

    private val TAG = MaterialActivity::class.java.simpleName
    private val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.replay_game))
                .setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    secretNumber.reset()
                    counter.text = secretNumber.count.toString()
                    ed_number.setText("")
                }
                .setNeutralButton(getString(R.string.cancel), null)
                .show()
        }
        counter.text = secretNumber.count.toString()
    }

    fun check(view: View) {
        val n = ed_number.text.toString().toInt()
        Log.d(TAG, "Number: $n")
        val diff = secretNumber.validate(n)
        var message = getString(R.string.yes_you_get_it)
        when {
            diff < 0 -> {
                message = getString(R.string.bigger)
            }
            diff > 0 -> {
                message = getString(R.string.smaller)
            }
            diff == 0 && secretNumber.count <= 3  ->{
                message = getString(R.string.excellent, secretNumber.secret)
            }
        }

        counter.text = secretNumber.count.toString()
        //        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.message))
            .setMessage(message)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

}

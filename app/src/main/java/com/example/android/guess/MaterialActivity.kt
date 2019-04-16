package com.example.android.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {

    private val REQUEST_RECORD: Int = 100
    private val TAG = MaterialActivity::class.java.simpleName
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)

        fab_replay.setOnClickListener { replay() }
        counter.text = secretNumber.count.toString()
        val count = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)
        val nick = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("REC_NICK", null)

        Log.d(TAG, count.toString())
        Log.d(TAG, nick)
    }

    private fun replay() {
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

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
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
            .setPositiveButton(R.string.ok) { dialog, which ->
                if (diff == 0) {
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("counter", secretNumber.count)
//                    startActivity(intent)
                    startActivityForResult(intent, REQUEST_RECORD)
                }
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD) {
            if (resultCode == Activity.RESULT_OK) {
                val nick = data?.getStringArrayExtra("NICK")
                Log.d(TAG, "onActivityResult $nick")
                replay()
            }
        }
    }

}

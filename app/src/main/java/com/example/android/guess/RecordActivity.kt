package com.example.android.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        val count = intent.getIntExtra("counter", -1)
        txCounter.text = count.toString()
        // OnClickListener
        save.setOnClickListener {v: View? ->
            val nick = nick_name.text.toString()
            getSharedPreferences("guess", Context.MODE_PRIVATE)
                .edit()
                .putInt("REC_COUNTER", count)
                .putString("REC_NICK", nick)
                .apply()

            val intent = Intent()
            intent.putExtra("NICK", nick)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}

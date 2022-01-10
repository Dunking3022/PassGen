package com.example.passgen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mainButton: Button = findViewById(R.id.button)
        val mainPass: TextView = findViewById(R.id.textView2)
        val savedPass: TextView = findViewById(R.id.textView3)
        val mainLen:EditText = findViewById(R.id.leninput)
        val btnSave:Button = findViewById(R.id.butSave)
        val btnCopy:Button = findViewById(R.id.butCopy)
        var sharedPref = getSharedPreferences("SavedPass", Context.MODE_PRIVATE )
        val editor = sharedPref.edit()
        var mainLenValue = "YOUR PASSWORD"
        var genPass = "YOUR PASSWORD"
        val savedPassGet = sharedPref.getString("savedPass","Your Password")
        savedPass.text = savedPassGet.toString()
        var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


        mainButton.setOnClickListener {
            var mainLenValue = mainLen.text.toString().toInt()
            genPass = getRandomString(mainLenValue)
            if(!(mainLenValue in 1..24)){
                Toast.makeText(this, "Enter A Number Between 1 and 24",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Password Generated!", Toast.LENGTH_SHORT).show()
                mainPass.text = genPass
            }
        }

        btnSave.setOnClickListener {
            savedPass.text = genPass
            editor.apply {
                putString("savedPass",genPass)
                apply()
            }
        }

        btnCopy.setOnClickListener {
            Toast.makeText(this, "Text Copied To Clipboard!", Toast.LENGTH_SHORT).show()
            val clip = ClipData.newPlainText("Copied Text",savedPassGet.toString())
            clipboard?.setPrimaryClip(clip)

        }
    }
}
fun getRandomString(length: Int) : String {
    val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}
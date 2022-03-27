package com.example.naviassignment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.naviassignment.R

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        val repoName = findViewById<EditText>(R.id.enter_repoName)
        val userName = findViewById<EditText>(R.id.enter_userName)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            if(repoName.text.toString().isNullOrEmpty() && userName.text.toString().isNullOrEmpty()){
                Toast.makeText(this,"Please enter username and name of repository both",Toast.LENGTH_SHORT).show()
            }
            else if(repoName.text.isNullOrEmpty()){
                Toast.makeText(this,"Please enter name of repository",Toast.LENGTH_SHORT).show()
            }
            else if(userName.text.isNullOrEmpty()){
                Toast.makeText(this,"Please enter username",Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, MainActivity::class.java);
                intent.putExtra("repoName",repoName.text.toString());
                intent.putExtra("userName",userName.text.toString());
                startActivity(intent)
            }
        }
    }
}
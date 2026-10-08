package com.example.jomongpro
import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
class MainActivity:Activity(){override fun onCreate(b:Bundle?){super.onCreate(b);window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);setContentView(GameView(this))}}

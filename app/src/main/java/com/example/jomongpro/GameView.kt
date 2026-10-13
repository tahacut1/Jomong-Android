package com.example.jomongpro

import android.content.Context
import android.graphics.*
import android.view.*
import kotlin.math.*

class GameView(context: Context) : View(context) {
    private val p=Paint(Paint.ANTI_ALIAS_FLAG)
    private val title=Paint(Paint.ANTI_ALIAS_FLAG).apply{textSize=42f;typeface=Typeface.DEFAULT_BOLD}
    private val txt=Paint(Paint.ANTI_ALIAS_FLAG).apply{textSize=28f}
    private var px=0f; private var bossHp=600; private var hp=100; private var gold=250
    private var phase=0; private var dialogue=0; private var showDialogue=true; private var attackTimer=0f; private var bossHitTimer=0f
    private val story=arrayOf("راوی: شب فرا رسیده و قلعه دشمن در برابر آریون است.","آریون: اینجا پایان راه نیست؛ آغاز نبرد من است.","فرمانده: آریون! اگر می‌خواهی شهر را پس بگیری، از من عبور کن!","راوی: فرمانده وارد میدان می‌شود. آماده نبرد شو!")
    override fun onDraw(c:Canvas){
        c.drawColor(Color.rgb(34,48,78)); p.color=Color.rgb(245,220,145);c.drawCircle(width*.82f,height*.18f,48f,p)
        p.color=Color.rgb(44,75,48);c.drawRect(0f,height*.40f,width.toFloat(),height.toFloat(),p)
        p.color=Color.rgb(78,80,88);c.drawRect(width*.28f,height*.20f,width*.72f,height*.53f,p);c.drawRect(width*.17f,height*.12f,width*.28f,height*.53f,p);c.drawRect(width*.72f,height*.12f,width*.83f,height*.53f,p)
        p.color=Color.rgb(25,25,30);c.drawRect(width*.45f,height*.36f,width*.55f,height*.53f,p)
        val road=Path();road.moveTo(width*.46f,height*.40f);road.lineTo(width*.54f,height*.40f);road.lineTo(width*.86f,height.toFloat());road.lineTo(width*.14f,height.toFloat());road.close();p.color=Color.rgb(125,105,78);c.drawPath(road,p)
        for(i in 0..5){val x=if(i%2==0)45f+i*30 else width-60f-i*25;val y=height*.50f+(i%3)*55;p.color=Color.rgb(80,48,30);c.drawRect(x-8,y,x+8,y+75,p);p.color=Color.rgb(32,110,52);c.drawCircle(x,y,42f,p)}
        val x=width*.35f+px;val y=height*.67f;p.color=Color.rgb(28,55,145);c.drawCircle(x,y-55,27f,p);c.drawRoundRect(x-30,y-30,x+30,y+65,15f,15f,p);p.color=Color.rgb(205,205,215);p.strokeWidth=9f;val swing=if(attackTimer>0)70f else 25f;c.drawLine(x+25,y-5,x+25+swing,y-65,p)
        val bx=width*.66f;val by=height*.57f;val sc=if(bossHitTimer>0)1.08f else 1f;p.color=Color.rgb(120,24,28);c.drawCircle(bx,by-70,45f*sc,p);c.drawRoundRect(bx-48*sc,by-25,bx+48*sc,by+105,18f,18f,p);p.color=Color.rgb(70,70,78);c.drawRect(bx-60,by-5,bx+60,by+18,p)
        p.color=Color.argb(215,0,0,0);c.drawRect(0f,0f,width.toFloat(),145f,p);title.color=Color.WHITE;c.drawText("جومونگ — فرمانده قلعه",25f,48f,title);txt.color=Color.WHITE
        val m=if(phase==0)"مأموریت: به فرمانده نزدیک شو" else if(phase==1)"مأموریت: فرمانده را شکست بده" else "پیروزی! قلعه فتح شد";c.drawText(m,25f,88f,txt);c.drawText("آریون: $hp    طلا: $gold",25f,126f,txt)
        p.color=Color.DKGRAY;c.drawRect(width-430f,25f,width-25f,62f,p);p.color=Color.RED;c.drawRect(width-430f,25f,width-430f+405f*(bossHp/600f),62f,p);txt.color=Color.WHITE;c.drawText("فرمانده $bossHp / 600",width-420f,98f,txt)
        p.color=Color.argb(190,20,20,20);c.drawCircle(105f,height-115f,75f,p);c.drawRoundRect(width-245f,height-190f,width-35f,height-80f,28f,28f,p);txt.color=Color.WHITE;c.drawText("⚔ حمله",width-205f,height-120f,txt);c.drawText("◀  حرکت  ▶",55f,height-108f,txt)
        if(showDialogue){p.color=Color.argb(238,15,18,24);c.drawRoundRect(35f,height-265f,width-35f,height-35f,25f,25f,p);txt.color=Color.WHITE;c.drawText(story[dialogue],60f,height-190f,txt);p.color=Color.rgb(45,105,190);c.drawRoundRect(width-185f,height-100f,width-55f,height-50f,15f,15f,p);txt.color=Color.WHITE;c.drawText("ادامه",width-158f,height-65f,txt)}
        attackTimer=max(0f,attackTimer-.016f);bossHitTimer=max(0f,bossHitTimer-.016f);invalidate()
    }
    override fun onTouchEvent(e:MotionEvent):Boolean{if(e.action!=MotionEvent.ACTION_DOWN)return true;val x=e.x;val y=e.y;if(showDialogue){if(y>height-290f){if(dialogue<story.lastIndex)dialogue++ else{showDialogue=false;phase=1}};return true};if(x>width-270f&&y>height-215f){bossHp=max(0,bossHp-60);attackTimer=.22f;bossHitTimer=.18f;if(bossHp==0){phase=2;gold+=500}}else if(x<width/2){if(x<width*.28f)px-=18f else px+=18f;px=px.coerceIn(-width*.20f,width*.20f)};return true}
}

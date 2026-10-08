package com.example.jomongpro

import android.content.Context
import android.graphics.*
import android.view.*
import kotlin.math.*

class GameView(context: Context) : View(context) {
    private val p=Paint(Paint.ANTI_ALIAS_FLAG)
    private val title=Paint(Paint.ANTI_ALIAS_FLAG).apply{textSize=48f;typeface=Typeface.DEFAULT_BOLD}
    private val text=Paint(Paint.ANTI_ALIAS_FLAG).apply{textSize=30f}
    private var bossHp=500; private var playerHp=100; private var gold=100
    private var dialogue=true; private var di=0; private var mission=0
    private var playerX=0f
    private val lines=arrayOf(
        "راوی: پس از فتح شهر، آریون به قلعه دشمن نزدیک می‌شود...",
        "آریون: این بار باید فرمانده دشمن را شکست بدهم.",
        "نگهبان: ایست! هیچ‌کس بدون اجازه وارد قلعه نمی‌شود!",
        "آریون: پس راه دیگری برای ورود پیدا می‌کنم.",
        "راوی: دروازه شکسته می‌شود. فرمانده دشمن وارد میدان می‌شود!"
    )
    override fun onDraw(c:Canvas){
        c.drawColor(Color.rgb(125,165,105))
        p.color=Color.rgb(175,145,95); c.drawRect(0f,height*.48f,width.toFloat(),height.toFloat(),p)
        p.color=Color.rgb(90,90,98); c.drawRect(width*.32f,height*.08f,width*.68f,height*.48f,p)
        c.drawRect(width*.22f,height*.16f,width*.30f,height*.48f,p); c.drawRect(width*.70f,height*.16f,width*.78f,height*.48f,p)
        p.color=Color.rgb(45,45,50); c.drawRect(width*.46f,height*.30f,width*.54f,height*.48f,p)
        p.color=Color.rgb(125,80,50); c.drawRect(35f,height*.25f,180f,height*.45f,p); c.drawRect(width-180f,height*.25f,width-35f,height*.45f,p)
        p.color=Color.rgb(45,120,55)
        for(x in floatArrayOf(70f,220f,width-220f,width-70f)){c.drawCircle(x,height*.42f,48f,p);c.drawRect(x-12,height*.42f,x+12,height*.55f,p)}
        val py=height*.67f
        p.color=Color.rgb(35,70,170); c.drawCircle(width*.28f+playerX,py,30f,p); c.drawRect(width*.25f+playerX,py+28,width*.31f+playerX,py+105,p)
        p.strokeWidth=10f;p.color=Color.LTGRAY;c.drawLine(width*.31f+playerX,py+50,width*.39f+playerX,py+10,p)
        p.color=Color.rgb(150,35,35);c.drawCircle(width*.72f,py,42f,p);c.drawRect(width*.675f,py+40,width*.765f,py+145,p)
        p.color=Color.DKGRAY;c.drawLine(width*.675f,py+70,width*.61f,py+20,p)
        p.color=Color.argb(220,0,0,0);c.drawRect(0f,0f,width.toFloat(),150f,p)
        title.color=Color.WHITE;c.drawText("جومونگ — مرحله ۱۰",28f,55f,title)
        text.color=Color.WHITE
        val m=if(mission==0)"مأموریت: وارد شدن به قلعه" else if(mission==1)"مأموریت: فرمانده قلعه را شکست بده" else "مأموریت کامل شد! +500 طلا"
        c.drawText(m,28f,100f,text);c.drawText("سلامت آریون: $playerHp    طلا: $gold",28f,138f,text)
        p.color=Color.DKGRAY;c.drawRect(width-430f,35f,width-30f,70f,p);p.color=Color.RED;c.drawRect(width-430f,35f,width-430f+400f*(bossHp/500f),70f,p)
        text.color=Color.WHITE;c.drawText("فرمانده: $bossHp/500",width-420f,105f,text)
        p.color=Color.rgb(165,35,35);c.drawRoundRect(width-230f,height-150f,width-30f,height-40f,25f,25f,p);text.color=Color.WHITE;c.drawText("⚔ حمله",width-195f,height-80f,text)
        if(dialogue){p.color=Color.argb(235,20,20,25);c.drawRoundRect(35f,height-245f,width-35f,height-35f,25f,25f,p);text.color=Color.WHITE;c.drawText(lines[di],60f,height-170f,text);p.color=Color.rgb(55,110,190);c.drawRoundRect(width-180f,height-95f,width-60f,height-45f,15f,15f,p);text.color=Color.WHITE;c.drawText("ادامه",width-155f,height-60f,text)}
        invalidate()
    }
    override fun onTouchEvent(e:MotionEvent):Boolean{
        if(e.action!=MotionEvent.ACTION_DOWN)return true
        if(dialogue){if(e.y>height-280f){if(di<lines.lastIndex)di++ else{dialogue=false;mission=1}};return true}
        if(e.x>width-260f&&e.y>height-190f){bossHp=max(0,bossHp-50);if(bossHp==0){mission=2;gold+=500}}
        else{playerX=(e.x-width*.28f).coerceIn(-width*.22f,width*.22f);if(mission==1&&abs(playerX)>width*.18f)playerHp=max(0,playerHp-5)}
        return true
    }
}

package com.example.jomongpro
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class GameView(c:Context):View(c){
 val p=Paint(1); val r=Random(4); var px=.22f; var hp=100; var score=0; var level=1; var cd=0f; var last=System.nanoTime()
 data class E(var x:Float,var y:Float,var hp:Int)
 data class A(var x:Float,var y:Float)
 val es=mutableListOf<E>(); val as_=mutableListOf<A>()
 override fun onDraw(c:Canvas){
  val dt=min(.033f,(System.nanoTime()-last)/1e9).toFloat(); last=System.nanoTime(); cd-=dt
  update(dt); draw(c); postInvalidateOnAnimation()
 }
 fun update(dt:Float){
  if(es.size<3+level/2 && r.nextFloat()<dt*(.5f+level*.05f)) es+=E(1.05f,.66f+r.nextFloat()*.18f,2+level/2)
  as_.forEach{it.x+=.7f*dt}; as_.removeAll{it.x>1.1f}
  es.forEach{it.x-=(.05f+level*.006f)*dt;if(it.x<px+.03f&&kotlin.math.abs(it.y-.72f)<.15f){hp-=8;it.x=1.2f}}
  val hits=mutableListOf<Pair<A,E>>()
  as_.forEach{a->es.forEach{e->if(hypot(a.x-e.x,a.y-e.y)<.055f)hits+=a to e}}
  hits.forEach{(a,e)->as_.remove(a);e.hp--;if(e.hp<=0){es.remove(e);score+=100}}
  if(score>=level*800)level++
  if(hp<=0){hp=100;score=max(0,score-300);level=1;es.clear()}
 }
 fun draw(c:Canvas){
  val w=width.toFloat();val h=height.toFloat()
  p.shader=LinearGradient(0f,0f,0f,h,Color.rgb(12,25,52),Color.rgb(100,70,45),Shader.TileMode.CLAMP);c.drawRect(0f,0f,w,h,p);p.shader=null
  p.color=Color.rgb(238,216,160);c.drawCircle(w*.82f,h*.16f,34f,p)
  p.color=Color.rgb(35,55,63);val m=Path();m.moveTo(0f,h*.62f);m.lineTo(w*.2f,h*.38f);m.lineTo(w*.34f,h*.58f);m.lineTo(w*.53f,h*.32f);m.lineTo(w*.75f,h*.57f);m.lineTo(w*.9f,h*.36f);m.lineTo(w,h*.57f);m.lineTo(w,h);m.lineTo(0f,h);m.close();c.drawPath(m,p)
  p.color=Color.rgb(42,69,49);c.drawRect(0f,h*.7f,w,h,p)
  p.color=Color.rgb(120,90,60);c.drawRect(w*.38f,h*.7f,w*.58f,h,p)
  drawHero(c,w,h); es.forEach{drawEnemy(c,it,w,h)}
  p.color=Color.rgb(235,210,150);p.strokeWidth=4f;as_.forEach{c.drawLine(it.x*w,it.y*h,(it.x-.03f)*w,(it.y-.006f)*h,p)}
  p.color=Color.argb(180,0,0,0);c.drawRoundRect(20f,20f,290f,86f,18f,18f,p);p.color=Color.WHITE;p.textSize=25f;c.drawText("جومونگ",35f,51f,p);p.textSize=17f;c.drawText("مرحله $level   امتیاز $score",35f,75f,p)
  p.color=Color.DKGRAY;c.drawRoundRect(w-235,22f,w-35,42f,10f,10f,p);p.color=Color.rgb(180,45,40);c.drawRoundRect(w-233,24f,w-233+196*hp/100f,40f,8f,8f,p)
  p.color=Color.argb(110,255,255,255);c.drawCircle(75f,h-75f,52f,p);c.drawCircle(200f,h-75f,52f,p);c.drawCircle(w-100f,h-80f,58f,p)
  p.color=Color.DKGRAY;p.textSize=24f;c.drawText("←",57f,h-67f,p);c.drawText("→",183f,h-67f,p);p.color=Color.WHITE;p.textSize=20f;c.drawText("تیر",w-120f,h-73f,p)
 }
 fun drawHero(c:Canvas,w:Float,h:Float){val x=px*w;y@run{p.color=Color.rgb(48,58,70);c.drawOval(x-24,h*.72f-75,x+24,h*.72f+8,p);p.color=Color.rgb(205,157,117);c.drawCircle(x,h*.72f-93,20f,p);p.color=Color.rgb(28,28,35);c.drawArc(x-24,h*.72f-112,x+24,h*.72f-72,180f,180f,true,p)}}
 fun drawEnemy(c:Canvas,e:E,w:Float,h:Float){val x=e.x*w;val y=e.y*h;p.color=Color.rgb(73,42,38);c.drawOval(x-20,y-58,x+20,y+8,p);p.color=Color.rgb(170,115,85);c.drawCircle(x,y-72,17f,p);p.color=Color.DKGRAY;c.drawRect(x-17,y-91,x+17,y-75,p)}
 override fun onTouchEvent(e:MotionEvent):Boolean{if(e.action!=MotionEvent.ACTION_DOWN&&e.action!=MotionEvent.ACTION_MOVE)return true;val x=e.x/width;val y=e.y/height;if(y>.75f){if(x<.18f)px=max(.08f,px-.025f) else if(x<.32f)px=min(.55f,px+.025f) else if(x>.78f&&cd<=0){as_+=A(px+.04f,.66f);cd=.28f}};return true}
}
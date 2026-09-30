package com.example.jomongpro

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class GameView(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var heroX = 300f
    private var heroY = 500f

    private var enemyX = 700f
    private var enemyY = 500f

    private var heroHealth = 100
    private var enemyHealth = 100

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()

        // Background
        canvas.drawColor(Color.rgb(70, 120, 70))

        // Ground
        paint.color = Color.rgb(110, 85, 55)
        canvas.drawRect(0f, h * 0.55f, w, h, paint)

        // Title
        paint.color = Color.WHITE
        paint.textSize = 42f
        canvas.drawText("جومونگ - آریون", 30f, 60f, paint)

        // Hero health
        paint.color = Color.DKGRAY
        canvas.drawRect(30f, 80f, 330f, 115f, paint)

        paint.color = Color.rgb(40, 200, 70)
        canvas.drawRect(
            30f,
            80f,
            30f + 300f * min(heroHealth, 100) / 100f,
            115f,
            paint
        )

        // Enemy health
        paint.color = Color.DKGRAY
        canvas.drawRect(w - 330f, 80f, w - 30f, 115f, paint)

        paint.color = Color.RED
        canvas.drawRect(
            w - 330f,
            80f,
            w - 330f + 300f * min(enemyHealth, 100) / 100f,
            115f,
            paint
        )

        // Hero
        paint.color = Color.rgb(40, 90, 180)
        canvas.drawCircle(heroX, heroY, 45f, paint)

        paint.color = Color.WHITE
        paint.textSize = 28f
        canvas.drawText("آریون", heroX - 35f, heroY - 60f, paint)

        // Sword
        paint.color = Color.LTGRAY
        canvas.drawRect(
            heroX + 35f,
            heroY - 8f,
            heroX + 95f,
            heroY + 8f,
            paint
        )

        // Enemy
        paint.color = Color.rgb(170, 40, 40)
        canvas.drawCircle(enemyX, enemyY, 45f, paint)

        paint.color = Color.WHITE
        paint.textSize = 28f
        canvas.drawText("دشمن", enemyX - 35f, enemyY - 60f, paint)

        // Attack button
        paint.color = Color.rgb(120, 40, 40)
        canvas.drawCircle(w - 110f, h - 120f, 75f, paint)

        paint.color = Color.WHITE
        paint.textSize = 30f
        canvas.drawText("حمله", w - 145f, h - 110f, paint)

        // Movement buttons
        paint.color = Color.rgb(50, 50, 50)

        canvas.drawCircle(100f, h - 110f, 55f, paint)
        canvas.drawCircle(220f, h - 110f, 55f, paint)

        paint.color = Color.WHITE
        paint.textSize = 40f
        canvas.drawText("←", 78f, h - 96f, paint)
        canvas.drawText("→", 198f, h - 96f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_DOWN &&
            event.action != MotionEvent.ACTION_MOVE) {
            return true
        }

        val x = event.x
        val y = event.y

        val w = width.toFloat()
        val h = height.toFloat()

        // Move left
        if (x < 160f && y > h - 200f) {
            heroX -= 15f
        }

        // Move right
        if (x in 160f..300f && y > h - 200f) {
            heroX += 15f
        }

        // Attack
        if (x > w - 220f && y > h - 220f) {
            val distance = kotlin.math.abs(heroX - enemyX)

            if (distance < 180f) {
                enemyHealth -= 20

                if (enemyHealth <= 0) {
                    enemyHealth = 100
                    enemyX = w - 150f
                }
            }
        }

        heroX = heroX.coerceIn(60f, w - 300f)

        invalidate()
        return true
    }
}

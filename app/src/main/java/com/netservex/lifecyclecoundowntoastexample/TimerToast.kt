package com.netservex.lifecyclecoundowntoastexample

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.State.*

/****
 * Author: Ahmed Yehya
 * Email: ahmedyehya1992@gmail.com
 * Created on: 12/10/21
 *****/
class TimerToast(val application: Application,lifecycleOwner: LifecycleOwner):LifecycleEventObserver {
    private var started = false
    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }
    private val countdownTimer = object : CountDownTimer(60000,3000) {
        override fun onTick(millisUntilFinished: Long) {
            if(lifecycleOwner.lifecycle.currentState.isAtLeast(STARTED))
                Toast.makeText(application, "${millisUntilFinished/1000}", Toast.LENGTH_SHORT).show()
        }

        override fun onFinish() {
            if(lifecycleOwner.lifecycle.currentState.isAtLeast(STARTED))
                Toast.makeText(application, "Finish", Toast.LENGTH_SHORT).show()
        }
    }


    private fun start() {
        countdownTimer.start()
    }
    private fun stop() {
        countdownTimer.cancel()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event.targetState) {
            DESTROYED -> stop()
            STARTED -> {
                if(!started) {
                    started = true
                    start()
                }
            }
        }
    }
}
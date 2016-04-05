package com.melapelapp.commons;

import android.os.CountDownTimer;

/**
 * Created by mcamacho on 4/5/16.
 */
public abstract class SimpleCountDownTimer extends CountDownTimer {

    public SimpleCountDownTimer(long millisInFuture) {
        super(millisInFuture, millisInFuture);
    }

    @Override
    public void onTick(long l) {
        // no op
    }

    @Override
    public abstract void onFinish();
}
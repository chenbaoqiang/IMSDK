package com.feinno.ucommon;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class AnimHandler extends Handler {

    public static final String TAG = "AnimHandler";

    private static class Arg {
        public Arg(Message msg, long m) {
            this.msg = msg;
            mills = m;
        }
        Message msg;
        long mills;
    }

    public AnimHandler(Looper looper) {
        super(looper);
        mTaskQueue = new ConcurrentLinkedQueue<Arg>();
        mLowTaskQueue = new ConcurrentLinkedQueue<Arg>();
        mLowPriorityHandler = new LowPriorityHandler(this);
    }

    private Handler mLowPriorityHandler;
    private volatile boolean mIsPlaying = false;
    private Queue<Arg> mTaskQueue;
    private Queue<Arg> mLowTaskQueue;

    @Override
    public boolean sendMessageAtTime(Message msg, long uptimeMillis){
        if(mIsPlaying) {
            Log.d(TAG, "animation is play, put it into message queue");
            mTaskQueue.add(new Arg(msg, uptimeMillis));
        } else {
            super.sendMessageAtTime(msg, uptimeMillis);
        }

        // check if we need send it right now.
        postIfNeeded();
        return true;
    }

    public boolean sendLowPriority(Message msg, long uptimeMillis) {
        if(mIsPlaying) {
            Log.d(TAG, "animation is play, put it into low priority message queue");
            mLowTaskQueue.add(new Arg(msg, uptimeMillis));
        } else {
            super.sendMessageAtTime(msg, uptimeMillis);
        }

        // check if we need send it right now.
        postIfNeeded();
        return true;
    }

    public void animationPlay(boolean play) {
        Log.d(TAG, "animationPlay: " + play);
        mIsPlaying = play;
        postIfNeeded();
    }

    private void postIfNeeded() {
        while (!mIsPlaying && !mTaskQueue.isEmpty()) {
            Log.d(TAG, "fire delayed task.");
            try {
                Arg a = mTaskQueue.remove();
                super.sendMessageAtTime(a.msg, a.mills);
            } catch (Exception e) {

            }
        }

        while (!mIsPlaying && !mLowTaskQueue.isEmpty()) {
            Log.d(TAG, "fire delayed low priority task.");
            try {
                Arg a = mLowTaskQueue.remove();
                super.sendMessageAtTime(a.msg, a.mills);
            } catch (Exception e) {

            }
        }
    }

    public Handler getLowPriorityHandler() {
        return mLowPriorityHandler;
    }

    public static class LowPriorityHandler extends Handler{

        AnimHandler innerHandler;
        private LowPriorityHandler(AnimHandler animHandler){
            innerHandler = animHandler;
        }

        @Override
        public boolean sendMessageAtTime(Message msg, long uptimeMillis ){
            return innerHandler.sendLowPriority(msg, uptimeMillis);
        }
    }


}

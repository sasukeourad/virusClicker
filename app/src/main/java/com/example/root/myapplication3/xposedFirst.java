package com.example.root.myapplication3;


import android.content.Context;
import android.content.res.XResources;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;


public class xposedFirst implements IXposedHookLoadPackage{

    public static Integer[] list = new Integer[]{0, 3768,10006, 59238,100002, 495220, 1000002, 9999998,9999999,10000000};
    public static int i = 0;
    public static int j = 0;
    public static boolean doit=true;

    public static Timer timer;


    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
      //  XposedBridge.log("Loaded app: " + lpparam.packageName);
        //virusclicker code

       XposedHelpers.findAndHookMethod("com.tm.ctf.clicker.activity.c", lpparam.classLoader, "onTouchEvent", MotionEvent.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                MotionEvent me = (MotionEvent) param.args[0];
                if(me.getAction()==MotionEvent.ACTION_UP) {
                    XposedBridge.log("Before hooking, set g = " + list[i]);
                    XposedHelpers.setIntField(param.thisObject, "g", list[i]);
                    i++;
                }
            }
        });

        XposedHelpers.findAndHookMethod("com.tm.ctf.clicker.a.a", lpparam.classLoader, "c", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                XposedBridge.log("Returning " + list[i]);
                return list[i];
            }
        });
        //automated clicking
       /* if(lpparam.packageName.equals("com.tm.ctf.clicker")) {
            XposedBridge.log("Loaded App: " + lpparam.packageName);
            XposedHelpers.findAndHookMethod("com.tm.ctf.clicker.activity.c", lpparam.classLoader, "onTouchEvent", MotionEvent.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                    if(timer==null) {
                        timer = new Timer();
                    }
                    final Object object = param.thisObject;
                    if(i>10000000) {
                        timer.cancel();
                    }
                    if(doit) {
                        doit = false;
                        for(int j=0;j<1000;j++) {
                            timer.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    // Obtain MotionEvent object
                                    long downTime = SystemClock.uptimeMillis();
                                    long eventTime = SystemClock.uptimeMillis() + 1;
                                    float x = 5.0f;
                                    float y = 5.0f;
                                    int metaState = 0;
                                    MotionEvent motionEvent = MotionEvent.obtain(
                                            downTime,
                                            eventTime,
                                            MotionEvent.ACTION_UP,
                                            x,
                                            y,
                                            metaState
                                    );
                                    XposedHelpers.callMethod(object, "dispatchTouchEvent", motionEvent);
                                    i++;
                                }
                            }, 0, 1);
                        }
                    }
                }
            });
        }*/


    }
}
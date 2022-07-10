package com.wuxianggujun.hookcarsh;

import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookCrashModule implements IXposedHookLoadPackage {

    private static final String TAG = "异常捕获";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("com.wuxianggujun.hookcarsh")) {
            XposedHelpers.findAndHookMethod("com.wuxianggujun.hookcarsh.MainActivity", loadPackageParam.classLoader,
                    "onCreate", Bundle.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);

                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Class c = loadPackageParam.classLoader.loadClass("com.wuxianggujun.hookcarsh.MainActivity");
                            Field field = c.getDeclaredField("textView");
                            field.setAccessible(true);
                            TextView tv = (TextView) field.get(param.thisObject);
                            tv.setText("模块激活成功！");
                        }
                    });
        } else if (loadPackageParam.packageName.equals("com.wuxianggujun.hooktest")) {
            XposedHelpers.findAndHookMethod("com.wuxianggujun.hooktest.MainActivity", loadPackageParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Class c = loadPackageParam.classLoader.loadClass("com.wuxianggujun.hooktest.MainActivity");
                    Field field = c.getDeclaredField("tv");
                    field.setAccessible(true);
                    TextView tv = (TextView) field.get(param.thisObject);
                    tv.setText("王境泽");
                }
            });

            XposedHelpers.findAndHookMethod("java.lang.ThreadGroup", loadPackageParam.classLoader, "uncaughtException",Thread.class,Throwable.class, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    // param.args 方法的 传入参数
                    XposedBridge.log("args1: " + param.args[0]);
                    XposedBridge.log("args2: " + param.args[1]);
                    XposedBridge.log("------------------");
                    XposedBridge.log((Throwable) param.args[1]);
                    XposedBridge.log("------------------");
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    //super.afterHookedMethod(param);
                    // 在 执行 方法之后，获取方法的返回值 param.getResult();
                }
            });

            XposedHelpers.findAndHookMethod("java.lang.Thread", loadPackageParam.classLoader, "getDefaultUncaughtExceptionHandler",new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    // param.args 方法的 传入参数
                    XposedBridge.log("参数args1: " + param.args[0]);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    //super.afterHookedMethod(param);
                    // 在 执行 方法之后，获取方法的返回值 param.getResult()
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (Thread.UncaughtExceptionHandler) param.getResult();
                    XposedBridge.log("UncaughtExceptionHandler : ---"+uncaughtExceptionHandler.toString());
                }
            });

            XposedHelpers.findAndHookMethod("java.lang.Thread", loadPackageParam.classLoader, "setDefaultUncaughtExceptionHandler",Thread.UncaughtExceptionHandler.class,new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    // param.args 方法的 传入参数
                    XposedBridge.log("参数args1: " + param.args[0]);
                    XposedBridge.log("参数args2: " + param.args[1]);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    //super.afterHookedMethod(param);
                    // 在 执行 方法之后，获取方法的返回值 param.getResult()
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (Thread.UncaughtExceptionHandler) param.getResult();
                    XposedBridge.log("pie ---"+uncaughtExceptionHandler.toString());
                    XposedBridge.log("cao-----"+param.getResultOrThrowable());
                }
            });



        }


    }

}

package com.developer.load.testhook;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;


public class Init implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        //  hook 宿主 app，这里以微信举例
        if (loadPackageParam.packageName.equals("com.tencent.mm")) {

            Log.w("xposed", " --- hook --- ");

            try {

                //  获取 Context
                Context mContext = (Context) callMethod(callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread", null), "currentActivityThread", new Object[0]), "getSystemContext", new Object[0]);

                //  创建目标应用 Context
                Context wxContext = mContext.createPackageContext("com.tencent.mm", Context.CONTEXT_IGNORE_SECURITY);

                //  获取 jar , dex , apk 路径 , 该项目默认将代码放在 sdcard 根目录
                String target = Environment.getExternalStorageDirectory().getPath() + File.separator + "key.apk";

                //  创建 DexClassLoader
                DexClassLoader dexLoader = new DexClassLoader(target, wxContext.getCacheDir().getAbsolutePath(), null, ClassLoader.getSystemClassLoader());

                //  加载 hook 类
                Class targetClass = dexLoader.loadClass("com.developer.load.testhook.Hook");
                Constructor targetConstructor = targetClass.getConstructor();
                Object targetObj = targetConstructor.newInstance();
                Method initMethod = targetClass.getMethod("hookInit", ClassLoader.class);
                initMethod.invoke(targetObj, loadPackageParam.classLoader);

                Log.w("xposed", " *** hook *** ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

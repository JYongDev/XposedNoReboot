package com.developer.load.testhook;

import android.util.Log;
import android.view.MenuItem;

import static com.developer.load.testhook.LauncherInvader.KILL;
import static com.developer.load.testhook.LauncherInvader.LOAD;
import static com.developer.load.testhook.LauncherInvader.TEST_HOOK;


public class MenuClick implements MenuItem.OnMenuItemClickListener {

    private String TAG = "xposed";


    public MenuClick() {
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (item.getTitle().toString().equals(LOAD)) {
            Log.w(TAG, "onMenuItemClick: load ");
            // 把新的 dex , jar , apk 文件放入 sdcard 目录，替换旧的文件，再执行退出程序的代码（这里为 Kill 按钮），重启app后就成功加载了新代码

            // 在此可以执行一些替换 DexClassLoader 中加载的 dex , jar , apk 的函数，项目中替换的是 sdcard 中的 dex , jar , apk 文件
        } else if (item.getTitle().equals(TEST_HOOK)) {
            Log.w(TAG, "onMenuItemClick: test hook " + Common.TEST_STR);
            //  测试是否成功替换
        } else if (item.getTitle().equals(KILL)) {
            //  强制退出宿主应用
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return false;
    }

}

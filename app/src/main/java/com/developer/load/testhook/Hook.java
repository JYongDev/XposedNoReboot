package com.developer.load.testhook;

import android.view.Menu;

import de.robv.android.xposed.XposedHelpers;


public class Hook {

    public void hook() {
    }

    public void hookInit(ClassLoader classLoader) {
        hookLauncher(classLoader);
    }

    private void hookLauncher(ClassLoader classLoader) {
        //  hook 微信主页面，添加子项到菜单栏
        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.LauncherUI",
                classLoader,
                "onCreateOptionsMenu",
                Menu.class,
                new LauncherInvader());
    }
}

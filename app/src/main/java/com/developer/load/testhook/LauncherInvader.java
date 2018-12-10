package com.developer.load.testhook;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import de.robv.android.xposed.XC_MethodHook;

public class LauncherInvader extends XC_MethodHook {

    private MenuClick click;
    public final static String LOAD = "load";
    public final static String TEST_HOOK = "testHook";
    public final static String KILL = "kill";
    public final static String TAG = "xposed";

    public LauncherInvader() {
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

        final Activity activity = (Activity) param.thisObject;

        if (null != activity) {
            click = new MenuClick();

            Menu menu = (Menu) param.args[0];

            menu.add(0, 5623, 0, LOAD);
            menu.add(0, 5624, 0, TEST_HOOK);
            menu.add(0, 5634, 0, KILL);

            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                if (item.getTitle().toString().equals(LOAD)) {
                    item.setOnMenuItemClickListener(click);
                }
                if (item.getTitle().toString().equals(TEST_HOOK)) {
                    item.setOnMenuItemClickListener(click);
                }
                if (item.getTitle().toString().equals(KILL)) {
                    item.setOnMenuItemClickListener(click);
                }
            }

        }
    }
}

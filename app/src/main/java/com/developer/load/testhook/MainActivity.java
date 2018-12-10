package com.developer.load.testhook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private String TAG = "xposed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());


        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.w("xposed", "onClick: test_str " + Common.TEST_STR);
            }
        });

        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
    }

    private String load() {
        String str = MainActivity.this.getPackageCodePath();
//        String str = "/data/data/com.developer.load.testhook/key2.dex";
        Log.w(TAG, "load: " + str);
        Log.w(TAG, "load: 1 ");

        if (!new File(str).exists()) {
            return "file not find";
        }

        PathClassLoader classLoader = new PathClassLoader(str, ClassLoader.getSystemClassLoader());
        Log.w(TAG, "load: 2 ");

        try {
            Class clz = Class.forName("com.developer.load.testhook.Common", true, classLoader);
            Log.w(TAG, "load: 3 ");

            Field[] fiels = clz.getFields();
            Log.w(TAG, "load: 4 ");

            if (fiels == null) {
                Log.w(TAG, "load: 5 ");

                Log.w("xposed", "load: fields is null ");
            } else {
                Log.w(TAG, "load: 6 ");

                for (int i = 0; i < fiels.length; i++) {
                    Log.w("xposed", "load: fields[i] key " + fiels[i].getName());
                    Log.w("xposed", "load: fields[i] value " + fiels[i].get(new Object()));
                }
                Log.w(TAG, "load: 7 ");

            }
        } catch (Throwable e) {
            e.printStackTrace();
            Log.w("xposed", "load: e.toString " + e.toString());
        }

        return "test";
    }

    public static String getRoomStatusAmender(String content) {
        String keyWord = "";

        if (TextUtils.isEmpty(content)) {
            return "";
        }

        String regex = "<username><!\\[CDATA\\[(\\w*)\\]\\]><\\/username>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            try {
                String targetStr = matcher.group(0);
                String keyStr = matcher.group(1);

                if (!TextUtils.isEmpty(targetStr) && !TextUtils.isEmpty(keyStr) &&
                        targetStr.contains(keyStr)) {
                    keyWord = keyStr;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return keyWord;
    }

    public String getRoomNameAmender(String content) {
        String keyWord = "";

        if (TextUtils.isEmpty(content)) {
            return "";
        }

        String regex = "<username><!\\[CDATA\\[(\\w*)\\]\\]><\\/username>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            try {
                String targetStr = matcher.group(0);
                String keyStr = matcher.group(1);

                if (!TextUtils.isEmpty(targetStr) && !TextUtils.isEmpty(keyStr) &&
                        targetStr.contains(keyStr)) {
                    keyWord = keyStr;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return keyWord;
    }

    public static String getNewRoomName(String content) {
        String keyWord = "";
        if (TextUtils.isEmpty(content)) {
            return "";
        }

        String regex = "<plain><!\\[CDATA\\[([\\w\\S\\u4e00-\\u9fa5]*)\\]\\]><\\/plain>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                String targetStr = matcher.group(0);
                String keyStr = matcher.group(1);
                if (!TextUtils.isEmpty(targetStr) &&
                        !TextUtils.isEmpty(keyStr) &&
                        targetStr.contains(keyStr)) {
                    keyWord = keyStr;
                }
            }
        }
        return keyWord;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}

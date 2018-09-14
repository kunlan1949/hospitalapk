package com.dommy.Data;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Data {
        public static boolean saveUserInfo(Context context, String username, String password) {
            try {
                File file = new File(context.getFilesDir(), "Data.txt");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write((username + "##" + password).getBytes());
                fos.close();
                return true;
            } catch (Exception e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
                return false;
            }
        }

        //获取保存的数据
        public static Map<String, String> getSaveUserInfo(Context context) {
            File file = new File(context.getFilesDir(), "Data.txt");
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String str = br.readLine();
                String[] infos = str.split("##");
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", infos[0]);
                map.put("password", infos[1]);
                return map;
            } catch (Exception e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
                return null;
            }
        }
}

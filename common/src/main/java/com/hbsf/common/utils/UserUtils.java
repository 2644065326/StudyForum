package com.hbsf.common.utils;

import com.hbsf.common.net.RetrofitClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserUtils {
    private String email;
    private String icon;
    private String id;
    private String password;
    private int type;
    private String username;


    private static volatile UserUtils instance;

    private static UserUtils getInstance() {
        if (instance == null) {
            synchronized (UserUtils.class) {
                if (instance == null) {
                    instance = new UserUtils();
                }
            }
        }
        return instance;
    }

    public static void updataUserInfo(String email, String icon, String id, String password, int type, String username) {
        if (UserUtils.getInstance() == null) {
            return;
        }
        instance.email = email;
        instance.icon = icon;
        instance.id = id;
        instance.password = password;
        instance.type = type;
        instance.username = username;
    }


    public static String getEmail() {
        if (UserUtils.getInstance() == null) {
            return null;
        }
        return instance.email;
    }

    public static void setEmail(String email) {
        if (UserUtils.getInstance() == null) {
            return;
        }
        instance.email = email;
    }

    public static String getIcon() {
        if (UserUtils.getInstance() == null) {
            return null;
        }
        return instance.icon;
    }

    public static void setIcon(String icon) {
        if (UserUtils.getInstance() == null) {
            return;
        }
        instance.icon = icon;
    }

    public static String getId() {
        if (UserUtils.getInstance() == null) {
            return null;
        }
        return instance.id;
    }

    public static void setId(String id) {
        if (UserUtils.getInstance() == null) {
            return;
        }
        instance.id = id;
    }

    public static String getPassword() {
        if (UserUtils.getInstance() == null) {
            return null;
        }
        return instance.password;
    }

    public static void setPassword(String password) {
        if (UserUtils.getInstance() == null) {
            return;
        }
        instance.password = password;
    }

    public static int getType() {
        if (UserUtils.getInstance() == null) {
            return -1;
        }
        return instance.type;
    }

    public void setType(int type) {
        if (UserUtils.getInstance() == null) {
            return;
        }
        instance.type = type;
    }

    public String getUsername() {
        if (UserUtils.getInstance() == null) {
            return null;
        }
        return instance.username;
    }

    public void setUsername(String username) {
        if (UserUtils.getInstance() == null) {
            return;
        }
        instance.username = username;
    }
}

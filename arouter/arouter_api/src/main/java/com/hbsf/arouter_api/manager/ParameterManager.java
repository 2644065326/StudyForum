package com.hbsf.arouter_api.manager;

import android.app.Activity;
import android.util.LruCache;

import com.hbsf.arouter_api.ParameterGet;

/**
 * 参数的 加载管理器
 *  第一步：查找 Personal_MainActivity$$Parameter
 *  第二步：使用 Personal_MainActivity$$Parameter  this 给他
 */
public class ParameterManager {

    private static ParameterManager instance;

    // private boolean isCallback;

    public static ParameterManager getInstance() {
        if (instance == null) {
            synchronized (ParameterManager.class) {
                if (instance == null) {
                    instance = new ParameterManager();
                }
            }
        }
        return instance;
    }

    // LRU缓存 key=类名      value=参数加载接口
    private LruCache<String, ParameterGet> cache;

    private ParameterManager() {
        cache = new LruCache<>(100);
    }

    // 为什么还要拼接，此次拼接 是为了寻找他
    static final String FILE_SUFFIX_NAME = "$$Parameter"; // 为了这个效果：Order_MainActivity + $$Parameter

    // 使用者 只需要调用这一个方法，就可以进行参数的接收
    public void loadParameter(Activity activity) { // 必须拿到 Personal_MainActivity
        String className = activity.getClass().getName(); // className == Personal_MainActivity

        ParameterGet parameterLoad = cache.get(className); // 先从缓存里面拿 如果有  如果没有
        if (null == parameterLoad) { // 缓存里面没东东   提高性能
            // 拼接 如：Order_MainActivity + $$Parameter
            // 类加载Order_MainActivity + $$Parameter
            try {
                // 类加载Personal_MainActivity + $$Parameter
                Class<?> aClass = Class.forName(className + FILE_SUFFIX_NAME);
                // 用接口parameterLoad = 接口的实现类Personal_MainActivity
                parameterLoad = (ParameterGet) aClass.newInstance();
                cache.put(className, parameterLoad); // 保存到缓存
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        parameterLoad.getParameter(activity); // 最终的执行  会执行我们生成的类
    }
}

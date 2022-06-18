package com.hbsf.arouter_api.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import androidx.annotation.RequiresApi;

import com.hbsf.arouter_annotation.bean.RouterBean;
import com.hbsf.arouter_api.ARouterGroup;
import com.hbsf.arouter_api.ARouterPath;
import com.hbsf.arouter_api.Call;


/**
 * 整个目标
 * 第一步：查找 ARouter$$Group$$personal ---> ARouter$$Path$$personal
 * 第二步：使用 ARouter$$Group$$personal ---> ARouter$$Path$$personal
 */
public class RouterManager {

    private String group; // 路由的组名 app，order，personal ...
    private String path;  // 路由的路径  例如：/app/MainActivity

    /**
     * 上面定义的两个成员变量意义：
     * 1.拿到ARouter$$Group$$personal  根据组名 拿到 ARouter$$Path$$personal
     * 2.操作路径，通过路径 拿到  Activity类，就可以实现跳转了
     */

    // 单例模式
    private static RouterManager instance;

    public static RouterManager getInstance() {
        if (instance == null) {
            synchronized (RouterManager.class) {
                if (instance == null) {
                    instance = new RouterManager();
                }
            }
        }
        return instance;
    }

    // 提供性能  LRU缓存
    private LruCache<String, ARouterGroup> groupLruCache;
    private LruCache<String, ARouterPath> pathLruCache;

    // 为了拼接，例如:ARouter$$Group$$personal
    private final static String FILE_GROUP_NAME = "ARouter$$Group$$";

    private RouterManager() {
        groupLruCache = new LruCache<>(100);
        pathLruCache = new LruCache<>(100);
    }

    /***
     * @param path 例如：/order/Order_MainActivity
     *      * @return
     */
    public BundleManager build(String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new IllegalArgumentException("不按常理出牌 path乱搞的啊，正确写法：如 /order/Order_MainActivity");
        }

        // 同学可以自己增加
        // ...

        if (path.lastIndexOf("/") == 0) { // 只写了一个 /
            throw new IllegalArgumentException("不按常理出牌 path乱搞的啊，正确写法：如 /order/Order_MainActivity");
        }

        // 截取组名  /order/Order_MainActivity  finalGroup=order
        String finalGroup = path.substring(1, path.indexOf("/", 1)); // finalGroup = order

        if (TextUtils.isEmpty(finalGroup)) {
            throw new IllegalArgumentException("不按常理出牌 path乱搞的啊，正确写法：如 /order/Order_MainActivity");
        }

        // 证明没有问题，没有抛出异常
        this.path =  path;  // 最终的效果：如 /order/Order_MainActivity
        this.group = finalGroup; // 例如：order，personal

        // TODO 走到这里后  grooup 和 path 没有任何问题   app，order，personal      /app/MainActivity

        return new BundleManager(); // Builder设计模式 之前是写里面的， 现在写外面吧
    }

    // 真正的导航
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Object navigation(Context context, BundleManager bundleManager) {
        // 例如：寻找 ARouter$$Group$$personal  寻址   ARouter$$Group$$order   ARouter$$Group$$app
        String groupClassName = "com.hbsf." + group + "." + "arouter.group." +FILE_GROUP_NAME + group;
        Log.e("navigation", "navigation: groupClassName=" + groupClassName);

        try {
            // TODO 第一步 读取路由组Group类文件
            ARouterGroup loadGroup = groupLruCache.get(group);
            if (null == loadGroup) { // 缓存里面没有东东
                // 加载APT路由组Group类文件 例如：ARouter$$Group$$order
                Class<?> aClass = Class.forName(groupClassName);
                // 初始化类文件
                loadGroup = (ARouterGroup) aClass.newInstance();

                // 保存到缓存
                groupLruCache.put(group, loadGroup);
            }

            if (loadGroup.getGroupMap().isEmpty()) {
                throw new RuntimeException("路由表Group报废了..."); // Group这个类 加载失败
            }

            // TODO 第二步 读取路由Path类文件
            ARouterPath loadPath = pathLruCache.get(path);
            if (null == loadPath) { // 缓存里面没有东东 Path
                // 1.invoke loadGroup
                // 2.Map<String, Class<? extends ARouterLoadPath>>
                Class<? extends ARouterPath> clazz = loadGroup.getGroupMap().get(group);

                // 3.从map里面获取 ARouter$$Path$$personal.class
                loadPath = clazz.newInstance();

                // 保存到缓存
                pathLruCache.put(path, loadPath);
            }

            // TODO 第三步 跳转
            if (loadPath != null) { // 健壮
                if (loadPath.getPathMap().isEmpty()) { // pathMap.get("key") == null
                    throw new RuntimeException("路由表Path报废了...");
                }

                // 最后才执行操作
                RouterBean routerBean = loadPath.getPathMap().get(path);

                if (routerBean != null) {
                    switch (routerBean.getTypeEnum()) {
                        case ACTIVITY:
                            Intent intent = new Intent(context, routerBean.getMyClass()); // 例如：getClazz == Order_MainActivity.class
                            intent.putExtras(bundleManager.getBundle()); // 携带参数
                            context.startActivity(intent);
                            break;
                        case CALL:
                            Class<?> clazz = routerBean.getMyClass();
                            Call call = (Call) clazz.newInstance();
                            bundleManager.setCall(call);
                            return  bundleManager.getCall();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

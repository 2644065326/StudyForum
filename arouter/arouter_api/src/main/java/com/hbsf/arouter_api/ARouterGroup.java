package com.hbsf.arouter_api;

import java.util.Map;

/**
 * 进行分级，提高查找效率
 * 先找组，再找具体的类
 * key groupName
 * value  ARouterPath
 */
public interface ARouterGroup {


    Map<String, Class<? extends ARouterPath>> getGroupMap();

}

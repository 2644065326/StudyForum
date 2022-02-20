package com.hbsf.arouter_api;


import com.hbsf.arouter_annotation.bean.RouterBean;

import java.util.Map;

/**
 * 进行分级，提高查找效率
 * 先找组，再找具体的类
 * key 具体路径
 * value  类
 */
public interface ARouterPath {


    Map<String, RouterBean> getPathMap();

}

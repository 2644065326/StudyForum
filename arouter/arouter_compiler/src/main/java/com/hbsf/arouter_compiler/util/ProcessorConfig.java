package com.hbsf.arouter_compiler.util;

public interface ProcessorConfig {

    // @ARouter注解 的 包名 + 类名
    String AROUTER_PACKAGE =  "com.hbsf.arouter_annotation.ARouter";

    // 接收参数的TAG标记
    String OPTIONS = "moduleName"; // 同学们：目的是接收 每个module名称
    String APT_PACKAGE = "packageNameForAPT"; // 同学们：目的是接收 包名（APT 存放的包名）

    String PACKAGE_PATH_NAME = ".arouter.path";
    String PACKAGE_GEGROUP_NAME = ".arouter.group";
    String PACKAGE_PARAMETER_NAME = ".arouter.parameter";

    // String全类名
    public static final String STRING_PACKAGE = "java.lang.String";

    // Activity全类名
    public static final String ACTIVITY_PACKAGE = "android.app.Activity";

    // ARouter api 包名
    String AROUTER_API_PACKAGE = "com.hbsf.arouter_api";

    // ARouter api 的 ARouterGroup 高层标准
    String AROUTER_API_GROUP = AROUTER_API_PACKAGE + ".ARouterGroup";

    // ARouter api 的 ARouterPath 高层标准
    String AROUTER_API_PATH = AROUTER_API_PACKAGE + ".ARouterPath";

    // 路由组，中的 Path 里面的 方法名
    String PATH_METHOD_NAME = "getPathMap";

    // 路由组，中的 Group 里面的 方法名
    String GROUP_METHOD_NAME = "getGroupMap";

    // 路由组，中的 Path 里面 的 变量名 1
    String PATH_VAR1 = "pathMap";

    // 路由组，中的 Group 里面 的 变量名 1
    String GROUP_VAR1 = "groupMap";

    // 路由组，PATH 最终要生成的 文件名
    String PATH_FILE_NAME = "ARouter$$Path$$";

    // 路由组，GROUP 最终要生成的 文件名
    String GROUP_FILE_NAME = "ARouter$$Group$$";

    String PARAMETER_PACKAGE = "com.hbsf.arouter_annotation.Parameter";

    String AROUTER_AIP_PARAMETER_GET = AROUTER_API_PACKAGE + ".ParameterGet";

    // ARouter api 的 ParameterGet 方法参数的名字
    String PARAMETER_NAME = "targetParameter";

    // ARouter api 的 ParmeterGet 方法的名字
    String PARAMETER_METHOD_NAME = "getParameter";

    // ARouter aip 的 ParmeterGet 的 生成文件名称 $$Parameter
    String PARAMETER_FILE_NAME = "$$Parameter";

    public static final String STRING = "java.lang.String";
}

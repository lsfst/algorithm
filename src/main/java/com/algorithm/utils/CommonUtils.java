/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190621    lsf     初始版本
 */
package com.algorithm.utils;

import java.util.ResourceBundle;

public class CommonUtils {
    private static ResourceBundle appCfg = null;

    // app config
    public static ResourceBundle getAppCfg() {
        return appCfg == null ? ResourceBundle.getBundle("application") : appCfg;
    }

    public static String getRsAppCfg(String key) {
        String cfgStr = null;
        try {
            cfgStr = getAppCfg().getString(key);
        } catch (Exception ignored) {
        }
        return cfgStr;
    }

}
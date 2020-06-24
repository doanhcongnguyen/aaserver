package com.dasan.aaserver.utils;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {

    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] ips = ip.split(",");
        return "0:0:0:0:0:0:0:1".equals(ips[0]) ? "127.0.0.1" : ips[0];
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}

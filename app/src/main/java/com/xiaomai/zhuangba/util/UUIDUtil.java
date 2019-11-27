package com.xiaomai.zhuangba.util;

import java.net.InetAddress;

public class UUIDUtil {


    /**
     * 可自定义分隔符
     */
    private static String sep = "";
    private static int IP = 0;
    private static String formatedIP = "";
    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
    private static String formatedJVM = "";
    private static short counter = (short) 0;
    private static UUIDUtil uuidUtil;

    public static UUIDUtil getUuidUitl(){
        if (uuidUtil == null){
            uuidUtil = new UUIDUtil();
        }
        return uuidUtil;
    }

    private UUIDUtil() {
        int ipAdd;
        try {
            ipAdd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipAdd = 0;
        }
        IP = ipAdd;
        formatedIP = format(getIP());
        formatedJVM = format(getJVM());
    }

    private String format(int intValue) {
        String formatted = Integer.toHexString(intValue);
        StringBuilder buf = new StringBuilder("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    private String format(short shortValue) {
        String formatted = Integer.toHexString(shortValue);
        StringBuilder buf = new StringBuilder("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    private int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    private int getIP() {
        return IP;
    }

    private int getJVM() {
        return JVM;
    }

    private short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    private int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    private short getCount() {
        synchronized (UUIDUtil.class) {
            if (counter < 0) counter = 0;
            return counter++;
        }
    }

    /**
     * 生成UUID(有序, 无重复概率)
     * @return string
     */
    public String generateOrderlyUUID() {
        return formatedIP + sep
                + formatedJVM + sep
                + format(getHiTime()) + sep
                + format(getLoTime()) + sep
                + format(getCount());
    }

}

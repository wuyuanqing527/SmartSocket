package com.wuyuanqing.smartsocket.constant;

/**
 * Created by wyq on 2015/11/16.
 */
public class SocketConstant {
    public static int rated_current = 10;//额定电流
    public static int rated_voltage = 250;//额定电压
    public static int rated_power = rated_current * rated_voltage;//额定功率
    public static String RATED_CURRENT = rated_current + "A";//
    public static String RATED_VOLTAGE = rated_voltage + "V";//
    public static String RATED_POWER = rated_power + "W";//
}

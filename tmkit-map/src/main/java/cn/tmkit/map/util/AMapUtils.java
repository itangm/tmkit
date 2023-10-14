package cn.tmkit.map.util;

import cn.tmkit.core.lang.ArrayUtil;

/**
 * 地图工具类，来源于高德地图
 *
 * @author ming.tang
 * @version 1.0
 * @date 2023/10/14
 */
public class AMapUtils {

    /**
     * 根据用户的起点和终点经纬度计算两点间距离，此距离为相对较短的距离，单位米。
     *
     * @param start 起点的坐标
     * @param end   终点的坐标
     * @return 两点的距离
     */
    public static double calculateLineDistance(LngLat start, LngLat end) {
        if (ArrayUtil.isAnyNull(start, end)) {
            throw new IllegalArgumentException("坐标不能为空");
        }
        double d1 = 0.01745329251994329D;
        double d2 = start.longitude * d1;
        double d3 = start.latitude * d1;
        double d4 = end.longitude * d1;
        double d5 = end.latitude * d1;

        double d6 = Math.sin(d2);
        double d7 = Math.sin(d3);
        double d8 = Math.cos(d2);
        double d9 = Math.cos(d3);
        double d10 = Math.sin(d4);
        double d11 = Math.sin(d5);
        double d12 = Math.cos(d4);
        double d13 = Math.cos(d5);

        double[] arrayOfDouble1 = new double[3];
        double[] arrayOfDouble2 = new double[3];
        arrayOfDouble1[0] = (d9 * d8);
        arrayOfDouble1[1] = (d9 * d6);
        arrayOfDouble1[2] = d7;
        arrayOfDouble2[0] = (d13 * d12);
        arrayOfDouble2[1] = (d13 * d10);
        arrayOfDouble2[2] = d11;
        double d14 = Math.sqrt((arrayOfDouble1[0] - arrayOfDouble2[0]) * (arrayOfDouble1[0] - arrayOfDouble2[0])
                + (arrayOfDouble1[1] - arrayOfDouble2[1]) * (arrayOfDouble1[1] - arrayOfDouble2[1])
                + (arrayOfDouble1[2] - arrayOfDouble2[2]) * (arrayOfDouble1[2] - arrayOfDouble2[2]));

        return (Math.asin(d14 / 2.0D) * 12742001.579854401D);
    }

}

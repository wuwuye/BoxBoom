package com.example.huangxb.boxboom;

/**
 * Created by huangxb on 2015/9/2.
 **/

public  class MyConstant {
    public static final float rate=100.0f; //物理世界与屏幕环境缩放比列
    public static final float timeStep=1f/60f;
    /**新的JBox2D增加到两个控制迭代，参数均按照官方manual上的参数设置的 */
    public static final int velocityIterations = 10;
    public static final int positionIterations = 8;
    public static float screenW = 0f;
    public static float screenH = 0f;
    public static boolean circleForce = true;
}

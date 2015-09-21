package com.example.huangxb.boxboom;

import org.jbox2d.dynamics.Body;

/**
 * Created by huangxb on 2015/9/18.
 */

public class BodyData {

    public float halfW;
    public float halfH;
    public float radius;
    public myType type;
    public BodyData(float halfH,float halfW){
        this.halfW = halfW;
        this.halfH = halfH;
        type = myType.RectBody;
    }
    public BodyData(float radius){
        this.radius = radius;
        type = myType.CircleBody;
    }
}

package com.example.huangxb.boxboom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Created by huangxb on 2015/9/17.
 */
public class CircleBody {
    private float radius = 50f;
    private float x;//x,y为质点坐标,该值是初始android坐标
    private float y;
    private World world;
    private float rate = 10;
    private boolean isStatic = true;
    private float density = 1f;
    private float friction = 1f;
    private float restitution = 1f;
    private Paint paint;
    private Body body;
    private BodyData bData;
    public CircleBody(World world,boolean isStatic){
        this.world = world;
        rate = MyConstant.rate;
        this.isStatic = isStatic;
    }
    public void setRadius(float radius){
        this.radius = radius;
    }
    public void setXY(float X,float Y){
        x = X;
        y = Y;
    }
/*
* @mDensity 在isStatic为真的情况下,密度固定为0,此时该密度设置无效
*
* */
    public void setOther(float mDensity,float mFriction,float mRestitution){
        density = mDensity;
        friction = mFriction;
        restitution = mRestitution;
    }
    public void createCircleBody(){
        CircleShape circle = new CircleShape();
        circle.m_radius = radius/rate;
        FixtureDef fDef=new FixtureDef();
        if(isStatic)
        {
            /**密度为0时，在物理世界中不受外力影响，为静止的 */
            fDef.density=0;
        }
        else
        {
            /**密度不为0时，在物理世界中会受外力影响 */
            fDef.density=density;
        }
        /**设置摩擦力，范围为 0～1 */
        fDef.friction=friction;
        /**设置物体碰撞的回复力，值越大，物体越有弹性 */
        fDef.restitution=restitution;
        /**添加形状*/
        fDef.shape=circle;
        /**设置BodyDef */
        BodyDef bodyDef=new BodyDef();
        /**此处一定要设置，即使density不为0，
         * 若此处不将body.type设置为BodyType.DYNAMIC,物体亦会静止
         * */
        bodyDef.type=isStatic? BodyType.STATIC:BodyType.DYNAMIC;
        bodyDef.position.set(x / rate, y / rate);
        body = world.createBody(bodyDef);
        body.createFixture(fDef);
        body.setUserData(new BodyData(radius));
    }


}

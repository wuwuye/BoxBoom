package com.example.huangxb.boxboom;

import android.graphics.Paint;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Created by huangxb on 2015/9/18.
 */
public class RectBody {
    private float x;//x,y为矩形中心坐标,即质点坐标,该值是初始坐标
    private float y;
    private float halfH = 50f;
    private float halfW = 50f;
    private World world;
    private boolean isStatic = true;
    private float rate =10;
    private float density = 1f;
    private float friction =1f;
    private float restitution =1f;
    private Paint paint;
    private Body body;
    private BodyData bData;
    public RectBody(World world,boolean isStatic){
        this.world = world;
        this.isStatic = isStatic;
        rate = MyConstant.rate;
    }
    public void setXY(float X,float Y){
        x = X;
        y = Y;
    }

    public void sethalfHW(float hW,float hH){
        halfW = hW;
        halfH = hH;
    }
    public void createRectBody(){
        PolygonShape polygon =new PolygonShape();
        polygon.setAsBox(halfW/rate, halfH/rate);
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
        fDef.shape = polygon;
        BodyDef bodyDef=new BodyDef();
        bodyDef.type=isStatic? BodyType.STATIC:BodyType.DYNAMIC;//new
        bodyDef.position.set(x/rate,y/rate);
        body = world.createBody(bodyDef);
        body.createFixture(fDef);
        body.setUserData(new BodyData(halfH,halfW));
    }

    public void setOther(float mDensity,float mFriction,float mRestitution){
        density = mDensity;
        friction = mFriction;
        restitution = mRestitution;
    }

}

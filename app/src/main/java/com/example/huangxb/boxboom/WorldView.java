package com.example.huangxb.boxboom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;


 /**
  * * Created by huangxb on 2015/9/17.
 */
public class WorldView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    private World world;
    private SurfaceHolder holder;
    private boolean isRun;
    private Paint paint;
    private float rate;
    private BodyData bData;
    public WorldView(Context context)
    {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
        isRun = false;
        rate = MyConstant.rate;
        paint = new Paint();
    }
    public void setWorld(World w){
        world = w;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRun = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
            isRun = false;//销毁线程
    }

    @Override
    public void run() {
        while (isRun){
            if(world != null){
                Canvas c = null;
                try {
                        synchronized (holder){
                            c = holder.lockCanvas();//锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
                            paintWorld(c);
                            //Thread.sleep(50);//睡眠时间为0.05秒
                        }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    if(c != null){
                        holder.unlockCanvasAndPost(c);//结束锁定画图，并提交改变
                    }
                }
            }
        }
    }

    private void paintWorld(Canvas c) {
        c.drawColor(Color.WHITE);
        world.step(MyConstant.timeStep, MyConstant.velocityIterations, MyConstant.positionIterations);
        //绘画各个Body
        Body body =world.getBodyList();
        BodyData bData ;
        for (int i = 0;i < world.getBodyCount();i++){
            bData = (BodyData)body.getUserData();
            switch (bData.type) {
                case RectBody:
                    drawRectBody(c,body);
                    break;
                case CircleBody:
                    if(MyConstant.circleForce){
                        body.applyLinearImpulse(new Vec2(50,0),body.getWorldCenter());
                        MyConstant.circleForce = false;
                    }
                    drawCircleBody(c,body);
                    break;
            }
            body = body.getNext();
        }
    }
    private void drawRectBody(Canvas canvas,Body body){
        bData = (BodyData)body.getUserData();
        paint.setColor(Color.GREEN);
        float z = body.getPosition().x;
        float x=body.getPosition().x*rate;
        float y=body.getPosition().y*rate;
        float angle=body.getAngle();
        canvas.save();
        Matrix m1=new Matrix();
        m1.setRotate((float) Math.toDegrees(angle), x, y);
        canvas.setMatrix(m1);
        canvas.drawRect(x - bData.halfW, y - bData.halfH, x + bData.halfW, y + bData.halfH, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.RED);
        canvas.drawRect(x - bData.halfW, y - bData.halfH, x + bData.halfW, y + bData.halfH, paint);
         paint.reset();
        canvas.restore();
    }
    private void drawCircleBody(Canvas canvas,Body body){
        canvas.save();
        bData = (BodyData)body.getUserData();
        paint.setColor(Color.GREEN);
        float x=body.getPosition().x*rate;
        float y=body.getPosition().y*rate;
        canvas.drawCircle(x, y, bData.radius, paint);
        //描边
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, bData.radius, paint);
        paint.reset();
        canvas.restore();
    }

}

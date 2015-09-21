package com.example.huangxb.boxboom;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;


public class MainActivity extends Activity {
    private World world;
    private float screenW;
    private float screenH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏

        DisplayMetrics metric = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metric);
        if(metric.widthPixels<metric.heightPixels){
            MyConstant.screenW = metric.widthPixels;
            MyConstant.screenH = metric.heightPixels;
        }
        else {
            MyConstant.screenW = metric.heightPixels;
            MyConstant.screenH = metric.widthPixels;
        }
        screenW = MyConstant.screenW;
        screenH = MyConstant.screenH;
        Vec2 gravity = new Vec2(0f,10f);
        world = new World(gravity);
        world.setAllowSleep(true);
        createBody();
        WorldView wv = new WorldView(this);
        wv.setWorld(world);
        this.setContentView(wv);
    }

    private void createBody() {
        CircleBody circle = new CircleBody(world,false);
        circle.setRadius(50);
        circle.setXY(screenW / 2, screenH / 2);
        circle.setOther(1f, 0.5f, 1f);
        circle.createCircleBody();

        float boardW = 40;//board宽度
        RectBody leftBoard =new RectBody(world,true);
        leftBoard.setXY(boardW / 2, screenH / 2);
        leftBoard.sethalfHW(boardW / 2, screenH / 2);
        leftBoard.setOther(2f, 0.5f, 1f);
        leftBoard.createRectBody();

        RectBody rightBoard =new RectBody(world,true);
        rightBoard.setXY(screenW-(boardW / 2), screenH / 2);
        rightBoard.sethalfHW(boardW / 2, screenH / 2);
        rightBoard.setOther(2f, 0.5f, 1f);
        rightBoard.createRectBody();

        RectBody toptBoard =new RectBody(world,true);
        toptBoard.setXY(screenW/2, boardW / 2);
        toptBoard.sethalfHW(screenW/2,boardW/2);
        toptBoard.setOther(2f, 0.5f, 1f);
        toptBoard.createRectBody();

        RectBody buttomBoard =new RectBody(world,true);
        buttomBoard.setXY(screenW/2, screenH -(boardW / 2));
        buttomBoard.sethalfHW(screenW/2,boardW/2);
        buttomBoard.setOther(1f, 0.5f, 1f);
        buttomBoard.createRectBody();

    }


}

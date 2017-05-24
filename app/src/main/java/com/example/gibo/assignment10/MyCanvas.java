package com.example.gibo.assignment10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by gi bo on 2017-05-18.
 */

public class MyCanvas extends View {

    Bitmap mbitmap ;
    Canvas mcanvas;
    Paint mpaint = new Paint();
    String operationType = " ";
    Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

    public MyCanvas(Context context) {
        super(context);
        mpaint.setColor(Color.BLACK);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mpaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mbitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mcanvas = new Canvas();

        mcanvas.setBitmap(mbitmap);
        mcanvas.drawColor(Color.YELLOW);

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mbitmap != null)
            canvas.drawBitmap(mbitmap, 0, 0, null);

    }

    int oldX = -1 , oldY =-1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int)event.getX();
        int Y = (int)event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            oldX = X ;
            oldY = Y;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(oldX != -1) {
                if(!operationType.equals("set"))
                    mcanvas.drawLine(oldX, oldY, X, Y, mpaint);
                invalidate();
                oldY = Y;
                oldX = X;
            }
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            if(oldX != -1) {
                if(operationType.equals("set")){
                    mcanvas.drawBitmap(img, X, Y, mpaint);
                }
                else
                    mcanvas.drawLine(oldX, oldY, X, Y, mpaint);
                invalidate();
                oldY = -1;
                oldX = -1;
            }
        }
        return true;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
        invalidate();
    }

    void clear(){
        mbitmap.eraseColor(Color.YELLOW);
        invalidate();
    }

    public void Save(String file_name){
        try {
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(file_name));
            mbitmap.compress(Bitmap.CompressFormat.JPEG , 100 , bout);
            bout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext()," 없어", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Open(String file_name){
        try {
            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file_name));
            mcanvas.scale(0.5f,0.5f);
            mcanvas.drawBitmap(BitmapFactory.decodeStream(bin), mcanvas.getWidth()/2, mcanvas.getHeight()/2, mpaint);
            mcanvas.scale(2.0f,2.0f);
            bin.close();
            invalidate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext()," 없어", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Rotate(){
        mcanvas.rotate(30,getWidth()/2,getHeight()/2);
    }

    public void Move(){
        mcanvas.translate(10,10);
    }
    public void Scale(){
        mcanvas.scale(1.5f,1.5f);
    }
    public void Skew(){
        mcanvas.skew(0.2f,1f);
    }
    public void Blurring(){
        BlurMaskFilter blur = new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER);
        mpaint.setMaskFilter(blur);
    }
    public void Coloring(){

        float[] array ={2f,0,0,0,-25f,
                0,2f,0,0,-25f,
                0,0,2f,0,-25f,
                0,0,0,1f,0};
        ColorMatrix colorMatrix = new ColorMatrix(array);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        mpaint.setColorFilter(filter);
    }
    public void PenWidth(){
        mpaint.setStrokeWidth(5);
    }

    public void PenRed(){
        mpaint.setColor(Color.RED);
    }


    public void PenBlue(){
        mpaint.setColor(Color.BLUE);
    }

    public void Reset1(){
        mpaint.setMaskFilter(null);
    }

    public void Reset2(){
        mpaint.setColorFilter(null);
    }
    public void Reset3(){
        mpaint.setStrokeWidth(3);
    }
}

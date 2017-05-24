package com.example.gibo.assignment10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    CheckBox cb;
    MyCanvas myCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCanvas = (MyCanvas)findViewById(R.id.mycanvas);
        cb = (CheckBox)findViewById(R.id.checkBox);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb.isChecked())
                    myCanvas.setOperationType("set");
                else
                    myCanvas.setOperationType(" ");
            }
        });
        File file = new File(getFilesDir()+" ");
        file.mkdir();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.bm){
            if(item.isChecked()) {
                item.setChecked(false);
                myCanvas.Reset1();
            }
            else {
                item.setChecked(true);
                myCanvas.Blurring();
            }
        }
        if(item.getItemId() == R.id.cm){
            if(!item.isChecked()) {
                item.setChecked(true);
                myCanvas.Coloring();
            }
            else {
                item.setChecked(false);
                myCanvas.Reset2();
            }
        }
        if(item.getItemId() == R.id.wbm){
            if(!item.isChecked()) {
                item.setChecked(true);
                myCanvas.PenWidth();
            }
            else {
                item.setChecked(false);
                myCanvas.Reset3();
            }
        }
        if(item.getItemId() == R.id.prm)
            myCanvas.PenRed();
        if(item.getItemId() == R.id.pbm)
            myCanvas.PenBlue();
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        if(v.getId() == R.id.bterase){
            myCanvas.clear();
        }
        if(v.getId() == R.id.btsave){
            myCanvas.Save(getFilesDir()+"img.jpg");
        }
        if(v.getId() == R.id.btopen){
            myCanvas.clear();
            myCanvas.Open(getFilesDir()+"img.jpg");
        }
        if(v.getId() == R.id.btrotate){
            cb.setChecked(true);
            myCanvas.Rotate();
        }
        if(v.getId() == R.id.btmove){
            cb.setChecked(true);
            myCanvas.Move();
        }
        if(v.getId() == R.id.btscale){
            cb.setChecked(true);
            myCanvas.Scale();
        }
        if(v.getId() == R.id.btskew){
            cb.setChecked(true);
            myCanvas.Skew();
        }
    }


}

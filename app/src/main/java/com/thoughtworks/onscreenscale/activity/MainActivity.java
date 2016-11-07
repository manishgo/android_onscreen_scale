package com.thoughtworks.onscreenscale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.thoughtworks.onscreenscale.R;
import com.thoughtworks.onscreenscale.service.ScaleService;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button launch = (Button)findViewById(R.id.button1);
    launch.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        startService(new Intent(MainActivity.this, ScaleService.class));
      }
    });

    Button stop = (Button)findViewById(R.id.button2);
    stop.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        stopService(new Intent(MainActivity.this, ScaleService.class));
      }
    });

  }
}

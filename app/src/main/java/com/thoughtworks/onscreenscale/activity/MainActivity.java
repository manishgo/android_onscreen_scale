package com.thoughtworks.onscreenscale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thoughtworks.onscreenscale.R;
import com.thoughtworks.onscreenscale.service.ScaleService;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Bundle bundle = getIntent().getExtras();

    if(bundle != null && bundle.getString("LAUNCH").equals("YES")) {
      startService(new Intent(this, ScaleService.class));
    }

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

  @Override
  protected void onResume() {
    Bundle bundle = getIntent().getExtras();

    if(bundle != null && bundle.getString("LAUNCH").equals("YES")) {
      startService(new Intent(this, ScaleService.class));
    }
    super.onResume();
  }
}

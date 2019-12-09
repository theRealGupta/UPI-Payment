package com.example.theforloopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

       Intent intent = getIntent();

       Boolean status = intent.getBooleanExtra("Status",false);


       textView = findViewById(R.id.textView2);

       if(status)
       {
           textView.setText("Payment Successful");
       }
       else
       {
           textView.setText("Payment Failed");

       }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }
}

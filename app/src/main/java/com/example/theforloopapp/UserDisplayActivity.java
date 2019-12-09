package com.example.theforloopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theforloopapp.ModelClass.getPaymentData;
import com.example.theforloopapp.ModelClass.getScanData;
import com.example.theforloopapp.ModelClass.setPaymentData;
import com.example.theforloopapp.ModelClass.setScanData;
import com.example.theforloopapp.Network.ApiClient;
import com.example.theforloopapp.Network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDisplayActivity extends AppCompatActivity {

    TextView textView1,textView2,textView3;
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_display);

        Intent intent = getIntent();


        double amount = intent.getDoubleExtra("amount",0);
        final String address = intent.getStringExtra("address");
        String productName = intent.getStringExtra("productName");

        textView1 = findViewById(R.id.textView4);
        textView2 = findViewById(R.id.textView);
        textView3 = findViewById(R.id.textView3);
        editText = findViewById(R.id.editText);

        textView1.setText(Double.toString(amount));
        textView2.setText(address);
        textView3.setText(productName);

        /*textView1.setEnabled(false);
        textView2.setEnabled(false);
        textView3.setEnabled(false);*/



    }

    @Override
    protected void onResume() {
        super.onResume();
        button = findViewById(R.id.button3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getTextSize()>0) {

                    setPaymentData paymentData = new setPaymentData();
                    paymentData.setAmount(Double.parseDouble(textView1.getText().toString()));
                    paymentData.setVpaAddress(textView2.getText().toString());
                    paymentData.setProductName(textView3.getText().toString());
                    paymentData.setPin(Integer.parseInt(editText.getText().toString()));

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<getPaymentData> getPaymentDataDetails = apiInterface.getPaymentData(paymentData);
                    getPaymentDataDetails.enqueue(new Callback<getPaymentData>() {

                        @Override
                        public void onResponse(Call<getPaymentData> call, Response<getPaymentData> response) {
                            double amount = response.body().getAmount();
                            String address = response.body().getVpaAddress();
                            boolean status = response.body().getStatus();
                            String productName = response.body().getProductName();
                            Log.d("debug Response : ","amount : "+amount+" address: "+address+" productName : "+productName +"status:"+status);

                            Intent intent = new Intent(getApplicationContext(),FinalActivity.class);
                            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("Status",status);


                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<getPaymentData> call, Throwable t) {
                            Log.d("EEEEE","error" ,t);
                            Toast.makeText(getApplicationContext(),"Something went Wrong Please try Again",2);
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Pin",2);
                }

            }
        });

    }
}

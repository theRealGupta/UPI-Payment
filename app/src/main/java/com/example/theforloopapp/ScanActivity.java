package com.example.theforloopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.theforloopapp.ModelClass.getScanData;
import com.example.theforloopapp.ModelClass.setScanData;
import com.example.theforloopapp.Network.ApiClient;
import com.example.theforloopapp.Network.ApiInterface;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;


public class ScanActivity extends AppCompatActivity {
    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;
    public static  boolean  firstTime = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        cameraView = (SurfaceView) findViewById(R.id.cameraView);
        cameraView.setZOrderMediaOverlay(true);
        holder = cameraView.getHolder();
        firstTime = true;
        barcode = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        if(!barcode.isOperational()){
            Toast.makeText(getApplicationContext(), "Sorry, Couldn't setup the detector", Toast.LENGTH_LONG).show();
            this.finish();
        }
        cameraSource = new CameraSource.Builder(this, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920,1024)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        cameraSource.start(cameraView.getHolder());
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes =  detections.getDetectedItems();
                if(barcodes.size() > 0){
                   // Intent intent = new Intent();
                    Barcode barcode = barcodes.valueAt(0);
                   // intent.putExtra("barcode", barcodes.valueAt(0));
                    String scanDetails = barcode.displayValue;
                    Log.d("barcode" , scanDetails);
                    if(firstTime)
                        firstTime = false;
                    else
                         return;

//                    upi://pay?pn=SHUBHAM%20SINGH&am=200&pa=9980998990.wa.yle@icici
                    String [] result = scanDetails.split("&");
                    Double amount = Double.parseDouble(result[1].split("=")[1]);
                    String vpaAddress = result[2].split("=")[1];
                    String nameStr = result [0];
                    Log.d("debug UPIADDRESS",vpaAddress);
                    Log.d("debug amount",amount.toString());

                    setScanData scandata = new setScanData();
                    scandata.setAmount(amount);
                    scandata.setVpaAddress(vpaAddress);

                   // setResult(RESULT_OK, intent);
                   // finish();


                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<getScanData> getScanDataDetails = apiInterface.getScanData(scandata);
                    getScanDataDetails.enqueue(new Callback<getScanData>() {
                        @Override
                        public void onResponse(Call<getScanData> call, Response<getScanData> response) {
                            double amount = response.body().getAmount();
                            String address = response.body().getVpaAddress();
                            String productName = response.body().getProductName();
                            Log.d("debug Response : ","amount : "+amount+" address: "+address+" productName : "+productName);

                            Intent intent = new Intent(getApplicationContext(),UserDisplayActivity.class);
                           // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("amount",amount);
                            intent.putExtra("address",address);
                            intent.putExtra("productName",productName);

                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<getScanData> call, Throwable t) {
                        Log.d("EEEEE","error" ,t);
                            Toast.makeText(getApplicationContext(),"Something went Wrong Please try Again",2);
                        firstTime = true;
                        }
                    });
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firstTime = true;

    }
}

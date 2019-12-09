package com.example.theforloopapp.Network;
 import com.example.theforloopapp.ModelClass.getPaymentData;
 import com.example.theforloopapp.ModelClass.getScanData;
 import com.example.theforloopapp.ModelClass.setPaymentData;
 import com.example.theforloopapp.ModelClass.setScanData;

 import retrofit2.Call;
 import retrofit2.http.Body;
 import retrofit2.http.Field;
        import retrofit2.http.FormUrlEncoded;
        import retrofit2.http.GET;
        import retrofit2.http.Header;
        import retrofit2.http.Headers;
        import retrofit2.http.POST;
        import retrofit2.http.Path;
        import retrofit2.http.Query;
        import retrofit2.http.Streaming;
        import retrofit2.http.Url;

public interface ApiInterface {



    @POST("valProduct")
    @Headers({"Content-Type: application/json"})
    Call<getScanData>getScanData(@Body setScanData scanData);

    @POST("reqPay")
    @Headers({"Content-Type: application/json"})
    Call<getPaymentData>getPaymentData(@Body setPaymentData paymentData);


}


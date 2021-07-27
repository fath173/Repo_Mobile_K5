package com.fathor.pondokkopi.API;

import com.fathor.pondokkopi.Model.Orders.OrdersResponse;
import com.fathor.pondokkopi.Model.Orders.OrdersUpdateTesti;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIRequestOrders {

    @GET("api/orders/{id}")
    Call<OrdersResponse> getOrdersAll(@Path("id") int id,
                                      @Header("Authorization") String auth);

    @GET("api/orders/{id}/detail")
    Call<OrdersResponse> getOrderDetail(@Path("id") int id,
                                        @Header("Authorization") String auth);

    @PUT("api/orders/{id}/cancel")
    Call<OrdersResponse> putOrderCancel(@Path("id") int id,
                                        @Header("Authorization") String auth);

    @PUT("api/orders/{id}/confirm")
    Call<OrdersResponse> putOrderConfirm(@Path("id") int id,
                                         @Header("Authorization") String auth);

    @PUT("api/orders/{id}/up-testi")
    Call<OrdersResponse> putOrderUptesti(@Path("id") int id, @Body OrdersUpdateTesti testi,
                                         @Header("Authorization") String auth);

    @Multipart
    @POST("api/orders/{id}/payment")
    Call<OrdersResponse> postOrderPayment(@Path("id") int id,
                                          @Header("Authorization") String auth,
                                          @Part MultipartBody.Part image);

}

package com.fathor.pondokkopi.API;

import com.fathor.pondokkopi.Model.Cart.Checkout;
import com.fathor.pondokkopi.Model.Cart.CheckoutResponse;
import com.fathor.pondokkopi.Model.Ongkir.DataOngkirRequest;
import com.fathor.pondokkopi.Model.Ongkir.DataOngkirResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIRequestCarts {
    @POST("api/ongkir")
    Call<DataOngkirResponse> ambilOngkir(@Header("Authorization") String auth,
                                          @Body DataOngkirRequest dataOngkirRequest);
    @POST("api/checkout")
    Call<CheckoutResponse> kirimCheckout(@Header("Authorization") String auth,
                                         @Body Checkout checkout);
}

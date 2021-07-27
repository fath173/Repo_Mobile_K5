package com.fathor.pondokkopi.API;

public class APIUtils {

    private APIUtils(){

    }
    public static final String API_URL = "https://wsjti.id/jembercoffecentre/public/";
    public static APIRequestProducts getReqProducts(){
        return RetrofitClient.getClient(API_URL).create(APIRequestProducts.class);
    }

    public static APIRequestUser getReqUser(){
        return RetrofitClient.getClient(API_URL).create(APIRequestUser.class);
    }

    public static APIRequestOrders getReqOrders(){
        return RetrofitClient.getClient(API_URL).create(APIRequestOrders.class);
    }
    public static APIRequestTestimonials getReqTestimonials(){
        return RetrofitClient.getClient(API_URL).create(APIRequestTestimonials.class);
    }
    public static APIRequestCarts getReqCarts(){
        return RetrofitClient.getClient(API_URL).create(APIRequestCarts.class);
    }

}

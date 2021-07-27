package com.fathor.pondokkopi.API;

import com.fathor.pondokkopi.Model.Testimonials.TestimonialsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRequestTestimonials {
    @GET("api/testimonials")
    Call<TestimonialsResponse> getTestimonials();
}

package com.fathor.pondokkopi.Model.Testimonials;

import java.util.List;

public class TestimonialsResponse {
    private String message;
    private List<TestimonialsData> dataTesti;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TestimonialsData> getDataTesti() {
        return dataTesti;
    }

    public void setDataTesti(List<TestimonialsData> dataTesti) {
        this.dataTesti = dataTesti;
    }
}

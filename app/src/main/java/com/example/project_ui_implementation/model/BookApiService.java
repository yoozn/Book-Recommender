package com.example.project_ui_implementation.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookApiService {
    @GET("volumes")
    Call<BookResponse> searchBooks(@Query("q") String query);
}

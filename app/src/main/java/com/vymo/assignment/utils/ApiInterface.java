package com.vymo.assignment.utils;

import com.vymo.assignment.model.GitResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiInterface {

    @GET("{org}/{repo}/issues?state=open")
    Call<GitResponse[]> getOpenIssues(@Path("org") String organization, @Path("repo") String repo );

    @GET("{org}/{repo}/issues?state=closed")
    Call<GitResponse[]> getClosedIssues(@Path("org") String organization, @Path("repo") String repo );

}

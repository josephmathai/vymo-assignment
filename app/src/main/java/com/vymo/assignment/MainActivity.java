package com.vymo.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vymo.assignment.model.GitResponse;
import com.vymo.assignment.utils.ApiInterface;
import com.vymo.assignment.utils.RetrofitConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private MainActivity mContext;

    private static final int THIRTY_MINUTES = 30 * 60 * 1000;

    private HashMap<String, String> requestHash = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;



        final EditText org = findViewById(R.id.editText_org);
        final EditText repo = findViewById(R.id.editText_repo);

        Button submit = findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            private Response<GitResponse[]> closed;
            private Response<GitResponse[]> open;

            @Override
            public void onClick(View v) {

                if (org.getText().toString().isEmpty() && repo.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "Check the credentials", Toast.LENGTH_SHORT).show();
                }

                RetrofitConfig retrofitConfig = new RetrofitConfig(mContext);
                Retrofit retro = retrofitConfig.getRetro();
                ApiInterface loginApi = retro.create(ApiInterface.class);

                // As there is no complicated query, I am using Shared preference
                SharedPreferences prefs = getSharedPreferences(org.getText().toString()+repo.getText().toString(), MODE_PRIVATE);
                if (prefs == null) {


                    Call<GitResponse[]> gitResponseCall = loginApi.getOpenIssues(org.getText().toString(),repo.getText().toString());
                    gitResponseCall.enqueue(new Callback<GitResponse[]>() {
                        @Override
                        public void onResponse(Call<GitResponse[]> call, Response<GitResponse[]> response) {
                            Toast.makeText(mContext, ""+response.message(), Toast.LENGTH_SHORT).show();
                            open = response;

                            Call<GitResponse[]> gitClosedResponseCall = loginApi.getClosedIssues(org.getText().toString(),repo.getText().toString());
                            gitClosedResponseCall.enqueue(new Callback<GitResponse[]>() {
                                @Override
                                public void onResponse(Call<GitResponse[]> call, Response<GitResponse[]> response) {
                                    Toast.makeText(mContext, ""+response.message(), Toast.LENGTH_SHORT).show();
                                    closed = response;

                                    if (open.body() == null|| closed.body() == null) {
                                        return;
                                    }

                                    ArrayList<GitResponse> openArray = new ArrayList<GitResponse>();
                                    openArray.addAll(Arrays.asList(open.body()));

                                    ArrayList<GitResponse> closeArray = new ArrayList<GitResponse>();
                                    closeArray.addAll(Arrays.asList(closed.body()));

                                    Intent intent = new Intent(mContext, PullRequestActivity.class);
                                    intent.putParcelableArrayListExtra("open", openArray );
                                    intent.putParcelableArrayListExtra("closed",  closeArray);
                                    startActivity(intent);

                                    SharedPreferences.Editor editor = getSharedPreferences(org.getText().toString()+repo.getText().toString(), MODE_PRIVATE).edit();
                                    editor.putLong("requestTime", System.currentTimeMillis());
                                    editor.putString("org", org.getText().toString());
                                    editor.putString("repo", repo.getText().toString());
                                    editor.putString("open", new Gson().toJson(open.body(), GitResponse[].class));
                                    editor.putString("closed", new Gson().toJson(closed.body(), GitResponse[].class));
                                    editor.apply();
                                }

                                @Override
                                public void onFailure(Call<GitResponse[]> call, Throwable t) {
                                    Toast.makeText(mContext, ""+t, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<GitResponse[]> call, Throwable t) {
                            Toast.makeText(mContext, ""+t, Toast.LENGTH_SHORT).show();
                        }
                    });




                } else {

                    long thirtyAgo = System.currentTimeMillis() - THIRTY_MINUTES;
                    if (prefs.getLong("requestTime",0) < thirtyAgo) {
                        Toast.makeText(mContext, "searchTimestamp is older than 30 minutes", Toast.LENGTH_SHORT).show();

                        Call<GitResponse[]> gitResponseCall = loginApi.getOpenIssues(org.getText().toString(),repo.getText().toString());
                        gitResponseCall.enqueue(new Callback<GitResponse[]>() {
                            @Override
                            public void onResponse(Call<GitResponse[]> call, Response<GitResponse[]> response) {
                                Toast.makeText(mContext, ""+response.message(), Toast.LENGTH_SHORT).show();
                                open = response;

                                Call<GitResponse[]> gitClosedResponseCall = loginApi.getClosedIssues(org.getText().toString(),repo.getText().toString());
                                gitClosedResponseCall.enqueue(new Callback<GitResponse[]>() {
                                    @Override
                                    public void onResponse(Call<GitResponse[]> call, Response<GitResponse[]> response) {
                                        Toast.makeText(mContext, ""+response.message(), Toast.LENGTH_SHORT).show();
                                        closed = response;

                                        if (open.body() == null|| closed.body() == null) {
                                            return;
                                        }

                                        ArrayList<GitResponse> openArray = new ArrayList<GitResponse>();
                                        openArray.addAll(Arrays.asList(open.body()));

                                        ArrayList<GitResponse> closeArray = new ArrayList<GitResponse>();
                                        closeArray.addAll(Arrays.asList(closed.body()));

                                        Intent intent = new Intent(mContext, PullRequestActivity.class);
                                        intent.putParcelableArrayListExtra("open", openArray );
                                        intent.putParcelableArrayListExtra("closed",  closeArray);
                                        startActivity(intent);

                                        SharedPreferences.Editor editor = getSharedPreferences(org.getText().toString()+repo.getText().toString(), MODE_PRIVATE).edit();
                                        editor.putLong("requestTime", System.currentTimeMillis());
                                        editor.putString("org", org.getText().toString());
                                        editor.putString("repo", repo.getText().toString());
                                        editor.putString("open", new Gson().toJson(open.body(), GitResponse[].class));
                                        editor.putString("closed", new Gson().toJson(closed.body(), GitResponse[].class));
                                        editor.apply();
                                    }

                                    @Override
                                    public void onFailure(Call<GitResponse[]> call, Throwable t) {
                                        Toast.makeText(mContext, ""+t, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<GitResponse[]> call, Throwable t) {
                                Toast.makeText(mContext, ""+t, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        ArrayList<GitResponse> openArray = new ArrayList<GitResponse>();
                        openArray.addAll(Arrays.asList(new Gson().fromJson(prefs.getString("open", null), GitResponse[].class )));

                        ArrayList<GitResponse> closeArray = new ArrayList<GitResponse>();
                        closeArray.addAll(Arrays.asList(new Gson().fromJson(prefs.getString("closed", null), GitResponse[].class )));
                        Intent intent = new Intent(mContext, PullRequestActivity.class);
                        intent.putParcelableArrayListExtra("open", openArray );
                        intent.putParcelableArrayListExtra("closed",  closeArray);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
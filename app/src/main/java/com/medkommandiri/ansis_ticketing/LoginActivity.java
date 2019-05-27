package com.medkommandiri.ansis_ticketing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import apihelper.BaseApiService;
import apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    BaseApiService mApiService;
    EditText log_et_email, log_et_password;
    CheckBox log_checbox;
    TextView log_tv_register;
    Button btnLogin;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();

        bindingToID();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = log_et_email.getText().toString().trim();
                password = log_et_password.getText().toString().trim();

                if (email.isEmpty()) {
                    log_et_email.setError("Email Tidak Boleh Kosong");
                    log_et_email.requestFocus();
                } else if (password.isEmpty()) {
                    log_et_password.setError("Password Tidak Boleh Kosong");
                    log_et_password.requestFocus();
                } else {
                    login(email, password);
                }

            }
        });

    }

    private void bindingToID() {
        btnLogin = findViewById(R.id.btn_login);
        log_tv_register = findViewById(R.id.log_tv_register);
        log_et_email = findViewById(R.id.log_et_email);
        log_et_password = findViewById(R.id.log_et_password);
        log_checbox = findViewById(R.id.log_checkbox);
    }

    public void login(String email, String password) {
        Log.d("tag", email);
        mApiService.login(email, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                try {
                    result = response.body().string();
                    Log.d("result", result);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    boolean isSuccess = jsonObject.getBoolean("isSuccess");


//                    Log.d("login",""+isSuccess);
                    if (isSuccess == true) {
                        String idUser = jsonObject.getString("user_id");
                        String realname = jsonObject.getString("realname");
                        String id_role = jsonObject.getString("id_role");
                        cacheIt(idUser, realname, id_role);
//                            Log.d("cache",jsonObject.getString("id_role"));
                        if (id_role == "2") {
                            Intent guestActivity = new Intent(getApplicationContext(), DashboardKaryawanActivity.class);
                            startActivity(guestActivity);
                        } else {
                            Intent guestActivity = new Intent(getApplicationContext(), DashboardKaryawanActivity.class);
                            startActivity(guestActivity);
                        }


                    } else if (isSuccess == false) {
                        String info = jsonObject.getString("info");
                        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
//                            Snackbar snackbar = Snackbar.make(null,info,Snackbar.LENGTH_SHORT);
//                            snackbar.show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("FAILED", "failed");
            }
        });
    }

    public void cacheIt(String idUser, String realname, String idRole) {
        Log.d("cache", "ada disini cache");
        SharedPreferences sharedPreferences = getSharedPreferences("xnticket", MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("idUser", idUser);
        ed.putString("realname", realname);
        ed.putString("idRole", idRole);
        ed.commit();
    }
}

package com.medkommandiri.ansis_ticketing;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import apihelper.BaseApiService;
import apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTicketActivity extends AppCompatActivity {
    String id_ticket,id_user;
    TextView dt_realname, dt_title, dt_description, dt_date, dt_time, dt_idTicket;
    ImageView dt_ivStatus;
    BaseApiService apiService;
    RecyclerView rv_detail;
    AppPreferences appPreferences = new AppPreferences();
    AdapterDetailTicket adapterDetailTicket;
    EditText dt_edt_reply;
    Button dt_btn_reply;
    ArrayList<PojoDetailTicket> pojos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ticket);

        apiService = UtilsApi.getApiService();
        Bundle bundle = getIntent().getExtras();
        id_ticket = bundle.getString("idTicket", null);
        id_user = appPreferences.idUser(getApplicationContext());
        bindingID();

        dt_idTicket.setText(id_ticket);

        readDetailTicket(id_ticket);
        adapterDetailTicket = new AdapterDetailTicket(pojos, this);
        rv_detail.setLayoutManager(new LinearLayoutManager(this));
        rv_detail.setAdapter(adapterDetailTicket);

        dt_btn_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = dt_edt_reply.getText().toString();
                if (reply.isEmpty()){
                    dt_edt_reply.requestFocus();
                    dt_edt_reply.setError("Input sometext please");
                } else {
                    inputCommment(reply);
                }

            }
        });


    }

    private void inputCommment(final String reply) {
        apiService.inputComment(id_ticket,reply,id_user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result;

                try {
                    result = response.body().string();

                    JSONObject object = new JSONObject(result);
                    boolean isSuccess = object.getBoolean("isSucces");
                    if (isSuccess == true ){
                        Toast.makeText(DetailTicketActivity.this, ""+object.getString("info"), Toast.LENGTH_SHORT).show();
                        adapterDetailTicket.clearData();
                        readDetailTicket(id_ticket);
                        dt_edt_reply.setText("");
                    } else {
                        Toast.makeText(DetailTicketActivity.this, ""+object.getString("info"), Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void readDetailTicket(String id_ticket) {
        apiService.detailTicket(id_ticket).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();

                    JSONObject object = new JSONObject(result);
                    boolean isSuccess = object.getBoolean("isSucces");
                    if (isSuccess == true) {
                        JSONObject object_ticket = object.getJSONObject("detail_ticket");

                        dt_realname.setText(object_ticket.getString("realname"));
                        dt_title.setText(object_ticket.getString("title"));
                        dt_description.setText(object_ticket.getString("description"));
                        String datetime = object_ticket.getString("timeCreated");
                        String[] parts = datetime.split(" ");
                        String date = parts[0];
                        String time = parts[1];
                        dt_date.setText(date);
                        dt_time.setText(time);
                        if (object_ticket.getString("status").contains("open")) {
                            dt_ivStatus.setBackground(getResources().getDrawable(R.drawable.open));
                        }

                        JSONArray detail_comment = object.getJSONArray("detail_comment");
                        for (int i = 0; i < detail_comment.length(); i++) {
                            JSONObject object_comment = detail_comment.getJSONObject(i);
                            String realname = object_comment.getString("realname");
                            String timecreated = object_comment.getString("timecreatead");
                            String description = object_comment.getString("description");
                            pojos.add(new PojoDetailTicket(description, realname, timecreated));
                            adapterDetailTicket.notifyDataSetChanged();

                        }

                        rv_detail.setAdapter(adapterDetailTicket);


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void bindingID() {
        dt_realname = findViewById(R.id.dt_realname);
        dt_title = findViewById(R.id.dt_title);
        dt_description = findViewById(R.id.dt_desc);
        dt_date = findViewById(R.id.dt_date);
        dt_time = findViewById(R.id.dt_time);
        dt_idTicket = findViewById(R.id.dt_idticket);
        dt_ivStatus = findViewById(R.id.dt_ivStatus);
        rv_detail = findViewById(R.id.dt_rv_comment);
        dt_edt_reply = findViewById(R.id.dt_edt_reply);
        dt_btn_reply = findViewById(R.id.dt_btn_reply);
    }
}

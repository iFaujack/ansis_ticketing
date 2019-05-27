package com.medkommandiri.ansis_ticketing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import apihelper.BaseApiService;
import apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardKaryawanActivity extends AppCompatActivity implements View.OnClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    BaseApiService mApiService;
    RecyclerView rvTicket;
    RecyclerView.LayoutManager layoutManager;
    AdapterTicket adapterTicket;
    ArrayList<PojoTicket> pojos = new ArrayList<>();
    AppPreferences appPreferences = new AppPreferences();
    Button add_ticket;
    CurvedBottomNavigationView bottomMenu;

    String idUser, realname, idRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_karyawan);

        add_ticket = findViewById(R.id.add_ticket);
        mApiService = UtilsApi.getApiService();
        rvTicket = findViewById(R.id.rv_ticket);
        rvTicket.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterTicket = new AdapterTicket(pojos, getApplicationContext());
        rvTicket.setAdapter(adapterTicket);

            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvTicket);



        bottomMenu = findViewById(R.id.bottomMenu);


        idUser = appPreferences.idUser(getApplicationContext());
        idRole = appPreferences.idRole(getApplicationContext());
        Log.d("cache",idRole+idUser);
//        readDashboard(idUser, idRole);

        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent createTask = null;
                switch (menuItem.getItemId()){
                    case R.id.menu_open:
                        addTicket();
                        break;
                }

                return false;
            }
        });


    }

    private void readDashboard(String idUser, String idRole) {
        mApiService.getDashboard(idUser, idRole).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;

                try {
                    result = response.body().string();
                    Log.d("berhasil baca", result);

                    JSONObject object = null;
                    try {
                        object = new JSONObject(result);
                        boolean isSuccess = object.getBoolean("isSucces");
                        Log.d("test",""+isSuccess);
                        if (isSuccess == true) {
                            JSONArray jsonArray = object.getJSONArray("info");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject ticket = jsonArray.getJSONObject(i);
                                String idTicket = ticket.getString("id_ticket");
                                String title = ticket.getString("title");
                                String description = ticket.getString("description");
                                String status = ticket.getString("status");
                                String assign_to = ticket.getString("assign_to");
                                String createdBy = ticket.getString("createdBy");
                                String timecreated = ticket.getString("timeCreated");
                                String realname = ticket.getString("realname");

                                String[] parts = timecreated.split(" ");

                                String date = parts[0];
                                String time = parts[1];

                                pojos.add(new PojoTicket(realname,title,description,date,time,status,idTicket));
                                adapterTicket.notifyDataSetChanged();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    public void addTicket(){
        Intent createTicket = new Intent(this, CreateTicketActivity.class);
        startActivity(createTicket);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_ticket:
                addTicket();
                break;
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AdapterTicket.ViewHolder){
            final  PojoTicket pojoTicket = pojos.get(viewHolder.getAdapterPosition());
            String id_ticket = pojoTicket.getId_ticket();
            Log.d("tag",id_ticket);

            mApiService.closeTicket("close",id_ticket).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String result = response.body().string();
                        Log.d("close",result);
                        JSONObject object = null;
                         try {
                             object = new JSONObject(result);
                             boolean isSuccess = object.getBoolean("isSuccess");
                             String info = object.getString("info");

                             Toast.makeText(DashboardKaryawanActivity.this, ""+info, Toast.LENGTH_SHORT).show();
                         } catch (JSONException e){
                             e.printStackTrace();
                         }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    adapterTicket.clearData();
                    readDashboard(idUser,idRole);
                    rvTicket.setAdapter(adapterTicket);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterTicket.clearData();
        readDashboard(idUser,idRole);
    }
}

package com.medkommandiri.ansis_ticketing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.StringPrepParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import apihelper.BaseApiService;
import apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTicketActivity extends AppCompatActivity  implements View.OnClickListener {
    AppPreferences appPreferences = new AppPreferences();
    EditText ct_et_from, ct_et_title, ct_et_description;
    ImageButton ct_ib_delete , ct_ib_sent;
    Spinner ct_sp_assignto;
    String idUser , idRole,realname,assignto;
    ArrayList<String> role = new ArrayList<>();
    BaseApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);

        intiID();
        mApiService = UtilsApi.getApiService();
//        role.add("");
        role.add("IT Support");


        setSpinner();

        idUser = appPreferences.idUser(getApplicationContext());
        idRole = appPreferences.idRole(getApplicationContext());
        realname = appPreferences.realname(getApplicationContext());

        ct_et_from.setText(realname);






    }

    private void setSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateTicketActivity.this,android.R.layout.simple_spinner_item,role);
        adapter.notifyDataSetChanged();
        ct_sp_assignto.setAdapter(adapter);

        ct_sp_assignto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(getResources().getColor(R.color.primary_text));
                Toast.makeText(CreateTicketActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                assignto = String.valueOf(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("sp","do nothing");
            }
        });
    }

    private void intiID() {
        ct_et_from = findViewById(R.id.ct_edt_from);
        ct_et_description = findViewById(R.id.ct_edt_message);
        ct_et_title = findViewById(R.id.ct_edt_title);
        ct_ib_delete = findViewById(R.id.ct_ib_delete);
        ct_ib_sent = findViewById(R.id.ct_ib_sent);
        ct_sp_assignto = findViewById(R.id.ct_sp_assignto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ct_ib_delete:
                ct_et_title.setText("");
                ct_et_description.setText("");
                break;
            case R.id.ct_ib_sent:
                String title = ct_et_title.getText().toString();
                String description = ct_et_description.getText().toString();
                if (title.isEmpty()){
                    ct_et_title.setError("Input Title First");
                    ct_et_title.requestFocus();
                }

                else if (description.isEmpty()){
                    ct_et_description.setError("Input Message First");
                    ct_et_description.requestFocus();
                } else {
                    createTicket(title,description,assignto,idUser);
                }



        }
    }

    private void createTicket(String title, String Description, String assign_to, String createdBy) {
        mApiService.createTicket(title,Description,assign_to,createdBy).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                try {
                    result = response.body().string();
                    Log.d("abcasd",result);

                    JSONObject object = new JSONObject(result);
                    boolean isSuccess = object.getBoolean("isSucces");
                    if (isSuccess == true){
                        String info = object.getString("info");
                        Toast.makeText(CreateTicketActivity.this, ""+info, Toast.LENGTH_SHORT).show();
                        Intent backToDashboard = new Intent(getApplicationContext(), DashboardKaryawanActivity.class);
                        startActivity(backToDashboard);
                    } else {
                        String info = object.getString("info");
                        Toast.makeText(CreateTicketActivity.this, ""+info, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e){
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
}

package apihelper;

import android.provider.CallLog;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> login(
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("getDashboard.php")
    Call<ResponseBody> getDashboard(
            @Field("id_user") String id_user,
            @Field("id_role") String id_role
    );

    @FormUrlEncoded
    @POST("createTicket.php")
    Call<ResponseBody> createTicket(
            @Field("title") String title,
            @Field("description") String description,
            @Field("assign_to") String assignTo,
            @Field("createdBy") String createdBy
    );

    @FormUrlEncoded
    @POST("updateTicket.php")
    Call<ResponseBody> closeTicket(
            @Field("status") String status,
            @Field("id_ticket") String id_ticket
    );

    @FormUrlEncoded
    @POST("detailTicket.php")
    Call<ResponseBody> detailTicket(
            @Field("id_ticket") String id_ticket
    );

    @FormUrlEncoded
    @POST("inputComment.php")
    Call<ResponseBody> inputComment(
            @Field("id_ticket") String id_ticket,
            @Field("description") String description,
            @Field("id_user") String id_user
    );
}

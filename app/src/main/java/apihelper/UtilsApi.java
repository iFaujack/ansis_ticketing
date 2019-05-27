package apihelper;

public class UtilsApi {
    public static final String BASE_URL = "http://192.168.1.100/php-backend/";


    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }

}

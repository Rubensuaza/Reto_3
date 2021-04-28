package co.com.personacliente2021.service.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import co.com.personacliente2021.LoginActivity;
import co.com.personacliente2021.R;
import co.com.personacliente2021.util.GlobalState;
import co.com.personacliente2021.util.RetrofitFactory;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginServiceImpl extends RetrofitFactory {

    String credentials= "cliente-app"+":"+"abcde*";

    final String basic= "Basic"+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

    public LoginServiceImpl(Context context) {
        super(context);
    }

    public void login(String username, String password){
        Retrofit retrofit=getRetrofitInstance();
        LoginClient loginClient=retrofit.create(LoginClient.class);
        Call<ResponseBody> response=loginClient.login(basic,username,password,"password");
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody=response.body();
                if(response != null){
                    Gson gson=new Gson();
                    try{
                        LoginResponse loginResponse=gson.fromJson(responseBody.toString(),(Type) LoginResponse.class);
                        setToken(loginResponse);
                        ((LoginActivity) getContext()).redirect();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    private void setToken(LoginResponse loginResponse) {
        GlobalState globalState = (GlobalState) getContext().getApplicationContext();
        globalState.setAccessToken(loginResponse.getAcces_token());
    }
}

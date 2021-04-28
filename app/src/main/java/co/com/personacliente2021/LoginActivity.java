package co.com.personacliente2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.personacliente2021.service.login.LoginServiceImpl;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.txtUserName)
    EditText userName;
    @BindView(R.id.txtPassword)
    EditText password;

    LoginServiceImpl loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginService=new LoginServiceImpl(this);
    }

    public void login(View view) {
        loginService.login(userName.getText().toString(),password.getText().toString());
    }
    public void redirect() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
package hr.foi.air.crvenkappica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import hr.foi.air.crvenkappica.web.RequestResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText etUsername,etPassword;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.tvRegister);
        btnLogin.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                String userName = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String hash = "";
                String type = "";
                WebRequest request = new WebRequest();
                WebParams params = new WebParams();
                params.params = "?UserName=" + userName + "&Password=" + password;
                params.service = "/prijava_app.php/";
                request.execute(params);
                String resp = RequestResponse.StaticResponse.getFinalResponse();
                Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
                break;
            case R.id.tvRegister:
                Intent intent = new Intent(Login.this,Registracija.class);
                startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}

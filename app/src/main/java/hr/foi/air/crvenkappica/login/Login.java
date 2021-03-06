package hr.foi.air.crvenkappica.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.registration.Registration;
import hr.foi.air.crvenkappica.fragments.NavigationFragment;
import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;
import hr.foi.air.crvenkappica.web.WebSite;

/**
 * Aktivnost za prijavu u aplikaciju
 */
public class Login extends Activity implements LoginView{

    private Button btnLogin;
    private EditText etUsername,etPassword;
    private TextView register;
    private ProgressDialog progressdialog;
    private String userNameStatus;
    private LoginPresenter presenter;
    /**
     * Pokreće se pri kreiranju aktivnosti, postavlja se layout.
     * Ukoliko je korisnik već prijavljen, otvara se nova aktivnost.
     * Dva listenera "osluškuju" korisnikove akcije.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        presenter = new LoginPresenter(this,new LoginService());
        btnLogin = (Button) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.tvRegister);

        /**
         * Listener za gumb "Login". Prikazuje se poruka za prijavu u aplikaciju i poziva web servis.
         */
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                progressdialog = new ProgressDialog(Login.this);
                progressdialog.setTitle("Dohvat");
                progressdialog.setMessage("Učitavam");
                progressdialog.setIndeterminate(false);
                progressdialog.setCancelable(false);
                progressdialog.show();

                presenter.onLoginClick();
                String userName = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                userNameStatus = userName;

                if (userName.isEmpty())
                    userName = "empty";

                if (password.isEmpty())
                    password = "empty";

                String hash = "";
                String type = "";

                WebParams paramsLogin = new WebParams();
                paramsLogin.adresa = WebSite.WebAdress.getAdresa();
                paramsLogin.params = "?UserName=" + userName + "&Password=" + password;
                paramsLogin.service = "prijava_app.php";
                paramsLogin.listener = response;

                new WebRequest().execute(paramsLogin);
            }
        });

        /**
         * Ukoliko se korisnik želi registrirati, otvara mu se aktivnost koja mu to omogućava.
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });
    }

    /**
    "Listener" koji čeka odgovor web servisa i na temelju outputa korisniku se ispisuje
    poruka za neuspjeh ili ga se preusmjera dalje u aplikaciju.
     */
    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            Intent intent = new Intent(Login.this,NavigationFragment.class);

            if(output == null || output.isEmpty()) {
                progressdialog.hide();
                Toast.makeText(getApplicationContext(), "Error with internet connection", Toast.LENGTH_LONG).show();
            }
            else {

                String status = new String();
                String user_id = new String();

                try {
                    JSONObject jsonObject = new JSONObject(output);
                    status = jsonObject.getString("Status");
                    user_id = jsonObject.getString("Id");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }

                if (status.equals("login_uspjeh")) {
                    progressdialog.hide();
                    LoginStatus.LoginInfo.setLoginName(userNameStatus);
                    LoginStatus.LoginInfo.setLoginState(true);
                    LoginStatus.LoginInfo.setLoginID(user_id);

                    startActivity(intent);
                    finish();
                }
                if (status.equals("login_neuspjeh")) {
                    progressdialog.hide();
                    Toast.makeText(getApplicationContext(), "Prijava neuspješna", Toast.LENGTH_LONG).show();
                }
                if (status.equals("nepostojeci_korisnik")) {
                    progressdialog.hide();
                    Toast.makeText(getApplicationContext(), "Korisnik ne postoji", Toast.LENGTH_LONG).show();
                }
            }
        }
    };


    @Override
    public String getUserName() {
        return etUsername.getText().toString();
    }

    @Override
    public void showUserNameError(int resId) {
        etUsername.setError(getString(resId));
    }
}




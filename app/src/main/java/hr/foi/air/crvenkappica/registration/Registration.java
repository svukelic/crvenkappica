package hr.foi.air.crvenkappica.registration;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import hr.foi.air.crvenkappica.JSONParser;
import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;
import hr.foi.air.crvenkappica.web.WebSite;

//Aktivnost za registraciju
public class Registration extends Activity implements View.OnClickListener {
    private EditText DOB_EditText, User, Pass, Email, Name, Lastname;
    private DatePickerDialog DOB_Picker;
    private SimpleDateFormat dateFormatter;
    private ProgressDialog dialog;
    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            if (output.equals("uspjeh")) {
                dialog.hide();
                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                finish();
            }
            if (output.equals("greska prilikom upisa")) {
                dialog.hide();
                Toast.makeText(getApplicationContext(), "Error during registration", Toast.LENGTH_LONG).show();
            }
            if(output == null || output.isEmpty()) Toast.makeText(getApplicationContext(), "Problem with internet connection", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    /**
     * Pokreće se pri kreiranju aktivnosti, postavlja se layout.
     * Postavljamo listener na button za registraciju
     * Ako je sve u redu sa poljima, pozivamo webservis reg_app.php
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        findViewsById();
        setDateTimeField();
        Button b1 = (Button) findViewById(R.id.button_reg);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationData data = new RegistrationData();
                data.setName(Name.getText().toString());
                data.setLastname(Lastname.getText().toString());
                data.setUsername(User.getText().toString());
                data.setPassword(Pass.getText().toString());
                data.setEmail(Email.getText().toString());
                data.setDOB(DOB_EditText.getText().toString());
                if(data.getName().isEmpty() || data.getLastname().isEmpty() || data.getUsername().isEmpty() || data.getPassword().isEmpty() || data.getEmail().isEmpty() || data.getDOB().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Sva polja moraju biti popunjena.", Toast.LENGTH_LONG).show();
                } else {
                    JSONParser j = new JSONParser(data);
                    dialog = new ProgressDialog(Registration.this);
                    dialog.setTitle(R.string.title_activity_activity__registration);
                    dialog.setMessage("Registracija u tijeku"); //treba provjeriti da se iz strings.xml ucitava
                    dialog.setIndeterminate(false);
                    dialog.setCancelable(false);
                    dialog.show();
                    WebParams webParamsReg = new WebParams();
                    webParamsReg.adresa = WebSite.WebAdress.getAdresa();
                    webParamsReg.service = "reg_app.php";
                    webParamsReg.params = j.getString();
                    webParamsReg.listener = response;
                    new WebRequest().execute(webParamsReg);
                }
            }
        });
        //Prikazuje Date picker ako je isti fokusiran
        DOB_EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    DOB_Picker.show();
            }
        });
    }
    //pronalazi view identificiran pomoću id-a
    private void findViewsById() {
        DOB_EditText = (EditText) findViewById(R.id.dob_editText);
        DOB_EditText.setInputType(InputType.TYPE_NULL);
        Name = (EditText) findViewById(R.id.name_editText);
        Lastname = (EditText) findViewById(R.id.surname_editText);
        User = (EditText) findViewById(R.id.username_editText);
        Pass = (EditText) findViewById(R.id.pass_editText);
        Email = (EditText) findViewById(R.id.email_editText);
        //DOB_EditText.requestFocus();
    }
    //postavlja datetime polje
    private void setDateTimeField() {
        DOB_EditText.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        DOB_Picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DOB_EditText.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    //klik na polje za datum, prikazuje se date picker
    public void onClick(View v) {
        if (v == DOB_EditText) {
            DOB_Picker.show();
        }
    }

}

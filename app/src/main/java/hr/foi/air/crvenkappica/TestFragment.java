package hr.foi.air.crvenkappica;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

public class TestFragment extends Fragment {

    private TextView tvUsername;
    private TextView tvIme;
    private TextView tvPrezime;
    private TextView tvDob;
    private ProgressDialog progressdialog;
    private String userName;
    private static final int PICK_IMAGE_ID = 234;
    private Button b;
    private ImageView i;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_navigacija,container,false);
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        tvUsername.setText("Username: " + LoginStatus.LoginInfo.getLoginName());
        tvIme = (TextView) view.findViewById(R.id.tvIme);
        tvPrezime = (TextView) view.findViewById(R.id.tvPrezime);
        tvDob = (TextView) view.findViewById(R.id.tvDOB);

        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setTitle(R.string.title_activity_login);
        progressdialog.setMessage("Loading profile");
        progressdialog.setIndeterminate(false);
        progressdialog.setCancelable(false);
        progressdialog.show();

        userName = LoginStatus.LoginInfo.getLoginName();
        if (!userName.isEmpty()) {
            String hash = "";
            String type = "";
            WebParams paramsProfil = new WebParams();
            paramsProfil.params = "?UserName=" + userName;
            paramsProfil.service = "profil_dohvat.php";
            paramsProfil.listener = response;
            new WebRequest().execute(paramsProfil);
        }
        b = (Button) view.findViewById(R.id.btnAlbum);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickImage(view);
            }
        });
        return view;
    }

    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            //System.out.println(output);
            progressdialog.hide();
            Toast.makeText(getActivity(), output, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(output);
                tvIme.setText("Ime: " + jsonObject.getString("Ime"));
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    };
    public void onPickImage(View view){
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
        startActivityForResult(chooseImageIntent,PICK_IMAGE_ID);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case PICK_IMAGE_ID:
                Bitmap bitmab = ImagePicker.getImageFromResult(getActivity(),resultCode,data);
                i = (ImageView) getView().findViewById(R.id.imageView);
                i.setImageBitmap(bitmab);
                break;
            default:
                super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
package kmitl.natcha58070069.com.libreria;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class AddDetail extends AppCompatActivity {

    private LibreriaDB libreriaDB;
    private LibreriaInfo libreriaInfo;
    private String status;
    private EditText editName, editComment;
    private Button save, delete;
    private int PLACE_PICKER_REQUEST = 1;
    private TextView tvLocation, tvLatLng;
    private Toolbar toolbarWidget;
    

    //TextWatcher: if empty text save button is Visibility.Gone
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkEmptyText();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void checkEmptyText() {
        save = (Button) findViewById(R.id.saveBtn);

        String name = editName.getText().toString();
        String comment = editComment.getText().toString();
        if (!name.equals("") && !comment.equals("")){
            save.setVisibility(View.VISIBLE);
//            Toast.makeText(this, "Please enter fully information", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);

        //Database
        libreriaDB = Room.databaseBuilder(this, LibreriaDB.class, "LIB_INFO")
                .fallbackToDestructiveMigration()
                .build();

        //toolbar
        toolbarWidget = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarWidget);

        //Button
        save = (Button) findViewById(R.id.saveBtn);
        save.setVisibility(View.GONE); //hide before fully info

        delete = (Button) findViewById(R.id.deleteBtn);
        delete.setVisibility(View.GONE); //hide before click item

        //Edit Text
        editName = (EditText) findViewById(R.id.editName);
        editComment = (EditText) findViewById(R.id.editComment);

        //Edit Text Listener
        editName.addTextChangedListener(textWatcher);
        editComment.addTextChangedListener(textWatcher);

        //Text View for receive info from Place Picker and can Click -> placeClick
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvLatLng = (TextView) findViewById(R.id.tvLatLng);

        //getIntent when click item
        libreriaInfo = getIntent().getParcelableExtra("LibreriaInfo");

        //create info or update info
        if(libreriaInfo == null){
            libreriaInfo = new LibreriaInfo();
            status = "add";
        } else {
            status = "update";
            editName.setText(libreriaInfo.getName());
            editComment.setText(libreriaInfo.getComment());
            tvLocation.setText(libreriaInfo.getLocation());
            tvLatLng.setText(libreriaInfo.getLatlng());
            delete.setVisibility(View.VISIBLE); //show when click item
        }
        //run once to disable if empty
        checkEmptyText();
    }

    public void placeClick(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(this, data);
                String address = String.format("Place : %s", place.getAddress());
                tvLocation.setText(address);
                String latlngAdd = String.format("%s", place.getLatLng());
                tvLatLng.setText(latlngAdd);
            }
        }
    }

    public void onSaveBtn(View view) {
        if (libreriaInfo == null){
            libreriaInfo = new LibreriaInfo();
        }

        String name = editName.getText().toString();
        String comment = editComment.getText().toString();
        String location = tvLocation.getText().toString();
        String latlng = tvLatLng.getText().toString();

        if (name.equals("") || comment.equals("")){
            Toast.makeText(this, "Please enter fully information", Toast.LENGTH_LONG).show();
            status = "";
        } else {
            libreriaInfo.setName(name);
            libreriaInfo.setComment(comment);
            libreriaInfo.setLocation(location);
            libreriaInfo.setLatlng(latlng);
        }

        //insert or update
        if (status == "add"){
            new AsyncTask<Void, Void, LibreriaInfo>() {
                @Override
                protected LibreriaInfo doInBackground(Void... voids) {
                    libreriaDB.getLibreriaDAO().insert(libreriaInfo);
                    return null;
                }
            }.execute();
        } else if (status == "update") {
            new AsyncTask<Void, Void, LibreriaInfo>() {
                @Override
                protected LibreriaInfo doInBackground(Void... voids) {
                    libreriaDB.getLibreriaDAO().update(libreriaInfo);
                    return null;
                }
            }.execute();
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>" + libreriaInfo.getName());

        //Intent
        Intent intent3 = new Intent(this, ShowDetail.class);
        intent3.putExtra("LibreriaInfo", libreriaInfo);
        startActivityForResult(intent3, 999);
//        setResult(RESULT_OK, intent3);
        finish();
    }

    public void onDeleteBtn(View view) {
        new AsyncTask<Void, Void, LibreriaInfo>() {
            @Override
            protected LibreriaInfo doInBackground(Void... voids) {
                libreriaDB.getLibreriaDAO().delete(libreriaInfo);
                return null;
            }
        }.execute();
        Intent intent6 = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, intent6); //not shure
        finish();
    }
}

package kmitl.natcha58070069.com.libreria.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
//import android.text.Editable;
//import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.wafflecopter.charcounttextview.CharCountTextView;

import kmitl.natcha58070069.com.libreria.R;
import kmitl.natcha58070069.com.libreria.database.LibreriaDB;
import kmitl.natcha58070069.com.libreria.model.LibreriaInfo;

public class AddDetail extends AppCompatActivity implements CharCountTextView.CharCountChangedListener {

    private LibreriaDB libreriaDB;
    private LibreriaInfo libreriaInfo;
    /*status -> Can intent to next page?
    add: when click add, dont have info in Room Database
    update: when click item, have info in Room DB if user edit sth must update to DB*/
    private String status;
    /* stay -> for check error
    stay = 1: have error of input ex: comment too long, Can't intent to next page
    stay = 2: don't have input error */
    private int stay;
    private EditText editName, editComment;
    private TextView adTextSave, adTextDelete, tvLocation, tvLatLng;
    private ImageView adSave, adDelete, adBack;
    private LinearLayout adLaySave, adLayDelete;
    private int PLACE_PICKER_REQUEST = 1;
    private Toolbar toolbarWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);
        //Database
        libreriaDB = Room.databaseBuilder(this, LibreriaDB.class, "LIB_INFO")
                .fallbackToDestructiveMigration()
                .build();
        //toolbar
        toolbarWidget = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarWidget);
        //TextView can click
        adTextSave = findViewById(R.id.adTexSave);
        adTextDelete = findViewById(R.id.adTextDelete);
        //Image can click
        adSave = findViewById(R.id.adSave);
        adDelete = findViewById(R.id.adDelete);
        adBack = findViewById(R.id.adBack);
        //LinearLayout can click
        adLaySave = findViewById(R.id.laySave);
        adLayDelete = findViewById(R.id.layDelete);
        //Edit Text
        editName = findViewById(R.id.editName);
        editComment = findViewById(R.id.editComment);
        //Text View for receive info from Place Picker
        tvLocation = findViewById(R.id.tvLocation);
        tvLatLng = findViewById(R.id.tvLatLng);
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
            adBack.setVisibility(View.GONE);
        }

        //Word counter -> Comment
        CharCountTextView charCountTextView = findViewById(R.id.tvTextCounter);
        EditText editComment = findViewById(R.id.editComment);
        charCountTextView.setEditText(editComment);
        charCountTextView.setCharCountChangedListener(this);

        //Word counter -> Comment
        CharCountTextView countTextView = findViewById(R.id.tvNameCounter);
        EditText editName = findViewById(R.id.editName);
        countTextView.setEditText(editName);
        countTextView.setCharCountChangedListener(this);
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

        try{
            if (name.equals("") || comment.equals("")){
                Toast.makeText(this, "Please enter fully information", Toast.LENGTH_LONG).show();
                stay = 1;
                System.out.println(">>>>>>>>>>>>>>>>>>>>> 1");
            } else if (comment.length() > 280){
                Toast.makeText(this, "Comment up to 280 characters, Please try again", Toast.LENGTH_LONG).show();
                stay = 1;
                System.out.println(">>>>>>>>>>>>>>>>>>>>> 2");
            } else if (name.length() > 30){
                Toast.makeText(this, "Name up to 30 characters, Please try again", Toast.LENGTH_LONG).show();
                stay = 1;
                System.out.println(">>>>>>>>>>>>>>>>>>>>> 3");
            } else if (location.equals("Place of Libreria") || latlng.equals("Latitude/Longitude")){
                Toast.makeText(this, "Please add location of Libreria", Toast.LENGTH_LONG).show();
                System.out.println(">>>>>>>>>>>>>>>>>>>>> 4");
                stay = 1;
            }
            else {
                libreriaInfo.setName(name);
                libreriaInfo.setComment(comment);
                libreriaInfo.setLocation(location);
                libreriaInfo.setLatlng(latlng);
                stay = 2;
            }
        }catch (StringIndexOutOfBoundsException e){}

        //insert or update info to DB
        if (status == "add" && stay != 1){
            new AsyncTask<Void, Void, LibreriaInfo>() {
                @Override
                protected LibreriaInfo doInBackground(Void... voids) {
                    libreriaDB.getLibreriaDAO().insert(libreriaInfo);
                    return null;
                }
            }.execute();
        } else if (status == "update" && stay != 1) {
            new AsyncTask<Void, Void, LibreriaInfo>() {
                @Override
                protected LibreriaInfo doInBackground(Void... voids) {
                    libreriaDB.getLibreriaDAO().update(libreriaInfo);
                    return null;
                }
            }.execute();
        }

        if (stay == 2){
            Intent intent3 = new Intent(this, ShowDetail.class);
            intent3.putExtra("LibreriaInfo", libreriaInfo);
            startActivityForResult(intent3, 999);
            finish();
        }
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

    public void onAddLocatBtn(View view) {
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

    public void onBackToMain(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCountChanged(int i, boolean b) {

    }
}

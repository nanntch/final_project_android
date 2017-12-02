package kmitl.natcha58070069.com.libreria.activity;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private ImageView adSave, adDelete;
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

        //TextView && ImageView && LinearLayout --> can click
        adTextSave = findViewById(R.id.adTexSave);
        adTextDelete = findViewById(R.id.adTextDelete);
        adSave = findViewById(R.id.adSave);
        adDelete = findViewById(R.id.adDelete);
        adLaySave = findViewById(R.id.laySave);
        adLayDelete = findViewById(R.id.layDelete);

        //Edit Text receive info
        editName = findViewById(R.id.editName);
        editComment = findViewById(R.id.editComment);

        //Text View for receive info from Place Picker
        tvLocation = findViewById(R.id.tvLocation);
        tvLatLng = findViewById(R.id.tvLatLng);

        //getIntent for receive info when click item
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

    /*onActivityReault
    * get data of selected place and show with Text view
    * - address
    * - latitude & longitude*/
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

    /*Button Save in Add detail Activity ->
    * When user click save btn
    * - receive name, comment, location, lat/lng from user
    * - check empty: If empty editText must warnning and stay this page untill editText not empty
    * - ckeck length: If length of name more than 50 characters must warning
    *                   If length of comment more than 280 char must warning
    * - ckeck add location: If user dont add location must warning and stay this page
    * And then, If information is not available in the database must insert to libreriaDB (Room DB)
    * else update to libreriaDB
    * if success must go to Show Detail (next page) */
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
            } else if (comment.length() > 280){
                Toast.makeText(this, "Comment up to 280 characters, Please try again", Toast.LENGTH_LONG).show();
                stay = 1;
            } else if (name.length() > 50){
                Toast.makeText(this, "Name up to 50 characters, Please try again", Toast.LENGTH_LONG).show();
                stay = 1;
            } else if (location.equals("Place of Libreria") || latlng.equals("Latitude/Longitude")){
                Toast.makeText(this, "Please add location of Libreria", Toast.LENGTH_LONG).show();
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

        /*insert or update info to DB
        * if status == add, insert info to DB
        * else if status == update, update info replace to DB*/
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
        startActivityForResult(intent6, 999);
        finish();
    }

    /*Add Location Button ->
    * Open Place picker (map) for select location of libreria*/
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

    //Constructor for count
    @Override
    public void onCountChanged(int i, boolean b) {

    }

    public void hideKeyboard(View view) {
        //hide keyboard when click space
        InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*Fix Back button of android
    * Link to Main Page if Click back button*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 999);
        finish();
    }
}

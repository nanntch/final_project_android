package kmitl.natcha58070069.com.libreria.activity;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;

import kmitl.natcha58070069.com.libreria.R;
import kmitl.natcha58070069.com.libreria.model.ScreenCapture;
import kmitl.natcha58070069.com.libreria.database.LibreriaDB;
import kmitl.natcha58070069.com.libreria.model.LibreriaInfo;

public class ShowDetail extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView shareFb, editDetail;
    private TextView name, comment, locat, share, edit; //receive value
    private LinearLayout layShare, layEdit;
    //DB
    private LibreriaDB libreriaDB;
    private LibreriaInfo libreriaInfo;

    //Toolbar
    private Toolbar toolbarWidget;

    //lat, lng String for receive substring
    private String lat, lng;
    private double latitude, longitude;

    //for share facebook
    final private int REQUEST_CODE_EXTERNAL_STORAGE = 1;

    //Map
    private GoogleMap mGoogleMap;
    public static final int REQUEST_LOCATION_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        //Database
        libreriaDB = Room.databaseBuilder(this, LibreriaDB.class, "LIB_INFO")
                .fallbackToDestructiveMigration()
                .build();

        //toolbar
        toolbarWidget = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarWidget);

        //can clik
        shareFb = findViewById(R.id.shShare);
        share =findViewById(R.id.shTextShare);
        editDetail = findViewById(R.id.shEdit);
        edit = findViewById(R.id.shTextEdit);
        layShare = findViewById(R.id.layShare);
        layEdit = findViewById(R.id.layEdit);

        //wait for receive value
        name = findViewById(R.id.shTextName);
        comment = findViewById(R.id.shTextComment);
        locat = findViewById(R.id.shTextLocat);

        //getIntent when click item
        libreriaInfo = getIntent().getParcelableExtra("LibreriaInfo");

        //get data from model
        getData();

        //Map
        initMap();
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    /*get data from libreriaInfo for set to TextView and Set latitude & longitude*/
    private void getData() {
        name.setText(libreriaInfo.getName());
        comment.setText(libreriaInfo.getComment());
        locat.setText(libreriaInfo.getLocation());

        //set latlng for mark location
        String strLatLng = libreriaInfo.getLatlng().substring(9);
        String listLatLng[] = strLatLng.split(",");
        lat = listLatLng[0].substring(1);
        lng = listLatLng[1].substring(0,listLatLng[1].length()-1);
        latitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lng);
    }

    public void onEditBtn(View view) {
        Intent intent1 = new Intent(this, AddDetail.class);
        intent1.putExtra("LibreriaInfo", libreriaInfo);
        startActivityForResult(intent1, 999);
        finish();
    }

    //--------------- Capture & Share -------------------//
    private boolean askForPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            return false;
        }
        return true;
    }

    /*When click share button
    * just show information of libreria and capture for share (without button)*/
    public void onShareBtn(View view) {
        if (askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_EXTERNAL_STORAGE)) {
            shareFb.setVisibility(View.GONE);
            editDetail.setVisibility(View.GONE);
            share.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            layShare.setVisibility(View.GONE);
            layEdit.setVisibility(View.GONE);
            //Screen shot
            Bitmap bm = ScreenCapture.takeScreenShotOfRootView(view.getRootView());
            Uri uri = getImageUri(this, bm);
            useShare(uri);
        }
    }

    /*for share with EXTRA STREAM other application on user device*/
    private void useShare(Uri uri) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share Libreria via"));

        shareFb.setVisibility(View.VISIBLE);
        editDetail.setVisibility(View.VISIBLE);
        share.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);
        layShare.setVisibility(View.VISIBLE);
        layEdit.setVisibility(View.VISIBLE);
    }

    //create file for save picture (Screenshot)
    private Uri getImageUri(ShowDetail showDetail, Bitmap bm) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //compress bitmap
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String imgPath = MediaStore.Images.Media.insertImage(showDetail.getContentResolver(), bm, "LibreriaDetail", null);
        return Uri.parse(imgPath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted, please press button again", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //----------------------- Map -------------------------//
    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        goToLocationZoom(latitude, longitude, 15);

        //Mark location of selected
        MarkerOptions mo = new MarkerOptions();
        LatLng latLng = new LatLng(latitude, longitude);
        mo.position(latLng);
        mo.title(libreriaInfo.getName());
        mGoogleMap.addMarker(mo);

        //screen shot map and set Image is Bitmap
        final ImageView mapPreview = findViewById(R.id.preViewMap);
        mapPreview.setImageBitmap(null);
        mapPreview.setLayoutParams(new LinearLayout.LayoutParams(0,0));

        mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mGoogleMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        mapPreview.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                        mapPreview.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }

    /*Fix Back button
    * Link to Main Page if click back button*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 999);
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 999);
        finish();
    }
}

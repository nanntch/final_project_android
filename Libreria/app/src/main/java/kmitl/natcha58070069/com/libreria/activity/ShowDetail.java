package kmitl.natcha58070069.com.libreria.activity;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.ByteArrayOutputStream;

import kmitl.natcha58070069.com.libreria.R;
import kmitl.natcha58070069.com.libreria.model.ScreenCapture;
import kmitl.natcha58070069.com.libreria.database.LibreriaDB;
import kmitl.natcha58070069.com.libreria.model.LibreriaInfo;

public class ShowDetail extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView shareFb, backTomain, editDetail;
    private TextView name, comment, locat, latlng; //receive value
    private TextView back, edit; //with button

    private LibreriaDB libreriaDB;
    private LibreriaInfo libreriaInfo;
    private Toolbar toolbarWidget;

    private String lat, lng;
    private double latitude, longitude;

    //for share facebook
    final private int REQUEST_CODE_EXTERNAL_STORAGE = 1;

    //Map
    GoogleMap mGoogleMap;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
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
        backTomain = findViewById(R.id.shBackToMain);
        editDetail = findViewById(R.id.shEdit);
        back = findViewById(R.id.shTextBackToMain);
        edit = findViewById(R.id.shTextEdit);

        //wait for receive value
        name = findViewById(R.id.shTextName);
        comment = findViewById(R.id.shTextComment);
        locat = findViewById(R.id.shTextLocat);
        latlng = findViewById(R.id.shTextLatLng);

        //getIntent when click item
        libreriaInfo = getIntent().getParcelableExtra("LibreriaInfo");

        getData();

        //Map
        initMap();
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    private void getData() {
        name.setText(libreriaInfo.getName());
        comment.setText(libreriaInfo.getComment());
        locat.setText(libreriaInfo.getLocation());
        latlng.setText(libreriaInfo.getLatlng());

        String strLatLng = libreriaInfo.getLatlng().substring(9);
        String listLatLng[] = strLatLng.split(",");
        lat = listLatLng[0].substring(1);
        lng = listLatLng[1].substring(0,listLatLng[1].length()-1);
        latitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lng);

    }

    public void onEditBtn(View view) {
        Intent intent = new Intent(this, AddDetail.class);
        intent.putExtra("LibreriaInfo", libreriaInfo);
        startActivityForResult(intent, 999);
        finish();
    }

    public void onBackBtn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 999);
//        setResult(RESULT_OK, intent);
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

    public void onShareBtn(View view) {
        if (askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_EXTERNAL_STORAGE)) {
            Bitmap bm = ScreenCapture.takeScreenShotOfRootView(view.getRootView());
            Uri uri = getImageUri(this, bm);
            useShare(uri);
        }
    }

    private void useShare(Uri uri) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share Libreria via"));
    }

    private Uri getImageUri(ShowDetail showDetail, Bitmap bm) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
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
}

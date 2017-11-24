package kmitl.natcha58070069.com.libreria.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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

import java.io.ByteArrayOutputStream;

import kmitl.natcha58070069.com.libreria.R;
import kmitl.natcha58070069.com.libreria.model.ScreenCapture;
import kmitl.natcha58070069.com.libreria.database.LibreriaDB;
import kmitl.natcha58070069.com.libreria.model.LibreriaInfo;

public class ShowDetail extends AppCompatActivity {

    private ImageView shareFb, backTomain, editDetail;
    private TextView name, comment, locat, latlng; //receive value
    private TextView back, edit; //with button

    private LibreriaDB libreriaDB;
    private LibreriaInfo libreriaInfo;
    private Toolbar toolbarWidget;

    //for share facebook
    final private int REQUEST_CODE_EXTERNAL_STORAGE = 1;

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
    }

    private void getData() {
        name.setText(libreriaInfo.getName());
        comment.setText(libreriaInfo.getComment());
        locat.setText(libreriaInfo.getLocation());
        latlng.setText(libreriaInfo.getLatlng());
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

    private boolean askForPermission(String permission, int requestCode){
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            return false;
        }
        return true;
    }

    public void onShareBtn(View view) {
        if (askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_EXTERNAL_STORAGE)){
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
        switch (requestCode){
            case REQUEST_CODE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted, please press button again", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

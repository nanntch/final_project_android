package kmitl.natcha58070069.com.libreria;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowDetail extends AppCompatActivity {

    private ImageView shareFb, backTomain, editDetail;
    private TextView name, comment, locat, latlng; //receive value
    private TextView back, edit; //with button

    private LibreriaDB libreriaDB;
    private LibreriaInfo libreriaInfo;
    private Toolbar toolbarWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        //Database
        libreriaDB = Room.databaseBuilder(this, LibreriaDB.class, "LIB_INFO")
                .fallbackToDestructiveMigration()
                .build();

        //toolbar
        toolbarWidget = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarWidget);

        //can clik
        shareFb = (ImageView) findViewById(R.id.shShare);
        backTomain = (ImageView) findViewById(R.id.shBackToMain);
        editDetail = (ImageView) findViewById(R.id.shEdit);
        back = (TextView) findViewById(R.id.shTextBackToMain);
        edit = (TextView) findViewById(R.id.shTextEdit);

        //wait for receive value
        name = (TextView) findViewById(R.id.shTextName);
        comment = (TextView) findViewById(R.id.shTextComment);
        locat = (TextView) findViewById(R.id.shTextLocat);
        latlng = (TextView) findViewById(R.id.shTextLatLng);

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

    public void onShareBtn(View view) {
    }

    public void onBackBtn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 999);
//        setResult(RESULT_OK, intent);
        finish();
    }
}
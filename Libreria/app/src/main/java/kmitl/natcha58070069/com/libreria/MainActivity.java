package kmitl.natcha58070069.com.libreria;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.MyAdapterListener, View.OnClickListener{

    private LibreriaDB libreriaDB;
    private MyAdapter adapter;
    private RecyclerView list;
    private Button goToProfile, addLib, findLocat;
    private int login = 10;
    private Toolbar toolbarWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Shared Preferences;
        if user close app but login, Main page (Main Activity) is default page to open: login = 10
        if user close app but logout, Front page is default page to open: login = -1*/
        SharedPreferences sp = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        int login = sp.getInt("login", -1);
        //&& login == -1
        if (savedInstanceState == null && login == -1){
            Intent intent = new Intent(this, FrontCover.class);
            startActivityForResult(intent, 999);
        } else if (AccessToken.getCurrentAccessToken() == null){
            Intent intent = new Intent(this, FrontCover.class);
            startActivityForResult(intent, 999);
        }

        //toolbar
        toolbarWidget = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarWidget);

        //Database
        libreriaDB = Room.databaseBuilder(this, LibreriaDB.class, "LIB_INFO")
                .fallbackToDestructiveMigration()
                .build();

        //set Adapter
        adapter = new MyAdapter(this);
        adapter.setContext(this);
        adapter.setListener(this);

        //Button
        goToProfile = (Button) findViewById(R.id.goToProfile);
        goToProfile.setOnClickListener(this);
        addLib = (Button) findViewById(R.id.addBtn);
        addLib.setOnClickListener(this);
        findLocat = (Button) findViewById(R.id.findBtn);
        findLocat.setOnClickListener(this);

        //set RecyclerView
        list = (RecyclerView) findViewById(R.id.lib_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        //for show list
        loadData();
    }

    private void loadData(){
        //set List of Libreria
        new AsyncTask<Void, Void, List<LibreriaInfo>>() {
            @Override
            protected List<LibreriaInfo> doInBackground(Void... voids) {
                List<LibreriaInfo> result = libreriaDB.getLibreriaDAO().findAll();
                return result;
            }

            @Override
            protected void onPostExecute(List<LibreriaInfo> libreriaInfos) {
                super.onPostExecute(libreriaInfos);
                adapter.setData(libreriaInfos);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    //When click item (for edit or update)
    @Override
    public void onClickInfoItem(LibreriaInfo libreriaInfo) {
        Intent intent5 = new Intent(this, AddDetail.class);
        intent5.putExtra("LibreriaInfo", libreriaInfo);
        startActivityForResult(intent5, 999);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goToProfile:
                Intent intent = new Intent(MainActivity.this, FrontCover.class);
                startActivityForResult(intent, 999);
                break;
            case R.id.addBtn:
                Intent intent2 = new Intent(MainActivity.this, AddDetail.class);
                startActivityForResult(intent2, 999);
                break;
            case R.id.findBtn:
                Intent intent4 = new Intent(MainActivity.this, FindLocation.class);
                startActivityForResult(intent4, 999);
                break;
        }
    }

    //for request code == 999
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 999){
            loadData();
        }
    }
}

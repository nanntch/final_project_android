package kmitl.natcha58070069.com.libreria.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;

import java.util.List;

import kmitl.natcha58070069.com.libreria.R;
import kmitl.natcha58070069.com.libreria.adapter.MyAdapter;
import kmitl.natcha58070069.com.libreria.database.LibreriaDB;
import kmitl.natcha58070069.com.libreria.fragment.MapsActivity;
import kmitl.natcha58070069.com.libreria.model.LibreriaInfo;

public class MainActivity extends AppCompatActivity implements MyAdapter.MyAdapterListener{

    private LibreriaDB libreriaDB;
    private MyAdapter adapter;
    private RecyclerView list;

    //Can Click
    private ImageView maAdd, maFind, maLogout;
    private TextView add, find, logout;

    private Toolbar toolbarWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*AccessToken
        if user close app but login, Main page (Main Activity) is default page to open
        if user close app but logout, Front page is default page to open*/
        if (AccessToken.getCurrentAccessToken() == null){
            Intent intent = new Intent(this, FrontCover.class);
            startActivityForResult(intent, 999);
            finish();
            //finish because want to block back button to this page, if log in not yet can't back to this page
        }
        //toolbar
        toolbarWidget = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarWidget);

        //Database
        libreriaDB = Room.databaseBuilder(this, LibreriaDB.class, "LIB_INFO")
                .fallbackToDestructiveMigration()
                .build();

        //set Adapter
        adapter = new MyAdapter(this);
        adapter.setContext(this);
        adapter.setListener(this);

        //ImageView && TextView can click
        maAdd = findViewById(R.id.maAdd);
        maFind = findViewById(R.id.maFind);
        maLogout = findViewById(R.id.maLogout);
        add = findViewById(R.id.maTexAdd);
        find = findViewById(R.id.maTextFind);
        logout = findViewById(R.id.maTextLogout);

        //set RecyclerView
        list = findViewById(R.id.lib_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        //for show list(Recycler)
        loadData();
    }

    /*Load data ->
    get information in Database and put it to adapter for show in Recycler
    findAll -> every id in Database*/
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
        Intent intent5 = new Intent(this, ShowDetail.class);
        intent5.putExtra("LibreriaInfo", libreriaInfo);
        startActivityForResult(intent5, 999);
    }

    //for request code == 999
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 999){
            loadData();
        }
    }

    //Method of Button (Add, Find, Logout)
    public void onAddBtn(View view) {
        Intent intent2 = new Intent(MainActivity.this, AddDetail.class);
        startActivityForResult(intent2, 999);
        finish();
    }

    public void onFindBtn(View view) {
        Intent intent4 = new Intent(MainActivity.this, MapsActivity.class);
        startActivityForResult(intent4, 999);
    }

    public void onLogoutBtn(View view) {
        Intent intent = new Intent(MainActivity.this, FrontCover.class);
        startActivityForResult(intent, 999);
//        finish();
        //don't finish because some user change to back to menu page don't want to log out can press back button
    }
}

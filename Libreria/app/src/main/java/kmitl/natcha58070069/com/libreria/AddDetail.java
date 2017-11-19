package kmitl.natcha58070069.com.libreria;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDetail extends AppCompatActivity implements View.OnClickListener{

    private LibreriaDB libreriaDB;
    private LibreriaInfo libreriaInfo;
    private String status;
    private EditText editName, editComment;
    private Button save, delete;
    
    //if empty text save button is Visibility.Gone
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

        if (name.equals("") || comment.equals("")){
//            Toast.makeText(this, "Please enter fully information", Toast.LENGTH_LONG).show();
        } else {
            save.setVisibility(View.VISIBLE);
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

        //Button
        save = (Button) findViewById(R.id.saveBtn);
        save.setOnClickListener(this);
        save.setVisibility(View.GONE);

        delete = (Button) findViewById(R.id.deleteBtn);
        delete.setOnClickListener(this);
        delete.setVisibility(View.GONE); //hide before click item

        //Edit Text
        editName = (EditText) findViewById(R.id.editName);
        editComment = (EditText) findViewById(R.id.editComment);

        //Edit Text Listener
        editName.addTextChangedListener(textWatcher);
        editComment.addTextChangedListener(textWatcher);

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
            delete.setVisibility(View.VISIBLE); //show when click item
        }
        //run once to disable if empty
        checkEmptyText();
    }

    @Override
    public void onClick(View v) {
        //save button, delete button
        switch (v.getId()){
            case R.id.saveBtn:
                onSaveBtn();
                Intent intent3 = new Intent();
                setResult(RESULT_OK, intent3);
                finish();
                break;
            case R.id.deleteBtn:
                onDeleteBtn();
                Intent intent6 = new Intent();
                setResult(RESULT_OK, intent6);
                finish();
                break;
        }
    }

    private void onDeleteBtn() {
        new AsyncTask<Void, Void, LibreriaInfo>() {
            @Override
            protected LibreriaInfo doInBackground(Void... voids) {
                libreriaDB.getLibreriaDAO().delete(libreriaInfo);
                return null;
            }
        }.execute();
    }

    private void onSaveBtn() {
        if (libreriaInfo == null){
            libreriaInfo = new LibreriaInfo();
        }

        String name = editName.getText().toString();
        String comment = editComment.getText().toString();

        if (name.equals("") || comment.equals("")){
            Toast.makeText(this, "Please enter fully information", Toast.LENGTH_LONG).show();
            status = "";
        } else {
            libreriaInfo.setName(name);
            libreriaInfo.setComment(comment);
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
    }
}

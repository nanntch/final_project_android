package kmitl.natcha58070069.com.libreria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ทุกอย่างที่หน้าเมนมี

        Button go = findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FrontCover.class);
                startActivity(intent);
                finish();
            }
        });

        Button addLib = findViewById(R.id.addBtn);
        addLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, AddDetail.class);
//                intent2.putExtra("bla", bla);
                startActivity(intent2);
            }
        });

        Button findLocat = findViewById(R.id.findBtn);
        findLocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this, FindLocation.class);
                startActivity(intent4);
            }
        });
    }


}

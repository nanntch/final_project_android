package kmitl.natcha58070069.com.libreria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FrontCover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_cover);

        Button nextPage = findViewById(R.id.nextPageBtn);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println(">>> Why dont you go to Next Page?");
                Intent intent = new Intent(FrontCover.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button lala = findViewById(R.id.lala);
        lala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Please do sth plsssssssssssssssss");
            }
        });
    }
}

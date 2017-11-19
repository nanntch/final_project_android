package kmitl.natcha58070069.com.libreria;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FrontCover extends AppCompatActivity {

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View view = inflater.inflate(R.layout.activity_front_cover, container, false);
//        return view;
//    }

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
                setResult(999, intent);
//                startActivity(intent);
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

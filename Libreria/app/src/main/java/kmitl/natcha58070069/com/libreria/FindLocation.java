package kmitl.natcha58070069.com.libreria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_locat);

        //map api this page

        Button goToPage = (Button) findViewById(R.id.goToBtn);
        goToPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindLocation.this, FrontCover.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

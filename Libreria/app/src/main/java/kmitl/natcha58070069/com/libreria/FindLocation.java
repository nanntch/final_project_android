package kmitl.natcha58070069.com.libreria;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class FindLocation extends AppCompatActivity {

    private int PLACE_PICKER_REQUEST = 1;
    private TextView tvPlace;
    private Button backToMainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_locat);

        //for recieve info from Place Picker and can Click -> itemClick
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        //for back to Main page
        backToMainBtn = (Button) findViewById(R.id.backToMainBtn);
    }

    //Onclick Button
    public void goPlacePicker(View view) {
        //This is the place to call Place Picker function
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this),PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void itemClick(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST){
           if (resultCode == RESULT_OK){
               Place place = PlacePicker.getPlace(this, data);
               String address = String.format("Place %s", place.getAddress());
               tvPlace.setText(address);

           }
        }
    }

    public void backToMain(View view) {
        Intent intent = new Intent(FindLocation.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

package itp341.mai.johnathan.koazie;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResultsActivity extends AppCompatActivity {


    // Constants
    public static final String EXTRA_RESULT_NAME = "itp341.mai.johnathan.koazie.resultname";
    public static final String EXTRA_RESULT_LAT = "itp341.mai.johnathan.koazie.resultlat";
    public static final String EXTRA_RESULT_LONG = "itp341.mai.johnathan.koazie.resultlong";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);

        // Getting information from the search
        Intent i = getIntent();
        String location = i.getStringExtra(EXTRA_RESULT_NAME);
        double latitude = i.getDoubleExtra(EXTRA_RESULT_LAT, 0);
        double longitude = i.getDoubleExtra(EXTRA_RESULT_LONG, 0);

        if (f == null) {
            f = ResultsFragment.newInstance(location, latitude, longitude);
        }

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, f);
        fragmentTransaction.commit();
    }

}

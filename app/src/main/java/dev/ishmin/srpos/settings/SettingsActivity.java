package dev.ishmin.srpos.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

import dev.ishmin.srpos.activities.LoginActivity;
import dev.ishmin.srpos.activities.MainActivity;
import dev.ishmin.srpos.R;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    RelativeLayout MainLayout;
    RelativeLayout stockAlert;
    LinearLayout accountInfo;
    LinearLayout aboutApp;
    TextView accountNo;
    Button button;
    Spinner spinner;
    String[] strings = {"20 (default)","0", "10", "30", "40", "50",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MainLayout = findViewById(R.id.settingsMainFrag);
        stockAlert = findViewById(R.id.set1);
        accountInfo = findViewById(R.id.set2);
        aboutApp = findViewById(R.id.set3);
        button = findViewById(R.id.signOutBtn);
        accountNo = findViewById(R.id.ownerTxt);
        accountNo.setText(MainActivity.sharedPreferences.getString("usernumber", ""));
        spinner = findViewById(R.id.stockSpinner);
        //ArrayList for Stock Alert Spinner
        ArrayList<String> list =new ArrayList<>();
        for(int i = 0; i < strings.length; i++){
            list.add(strings[i]);
        }
        //ArrayAdapter to show list in Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        accountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog1();
            }
        });

        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog2();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_signout();
            }
        });
    }

    public void openDialog1() {
        AccountInfoDialog dialog1 = new AccountInfoDialog();
        dialog1.show(getSupportFragmentManager(), "");
    }

    public void openDialog2() {
        AboutAppDialog dialog2 = new AboutAppDialog();
        dialog2.show(getSupportFragmentManager(), "");
    }

    public void user_signout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Spinner methods
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    String txt = parent.getItemAtPosition(position).toString();
        StyleableToast.makeText(parent.getContext(), "Stock Alert set to" + " " + txt, R.style.toastDesign).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    //Do nothing
    }
}

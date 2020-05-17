package dev.ishmin.srpos.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

import dev.ishmin.srpos.R;
import dev.ishmin.srpos.fragments.settings_frag2;

import static dev.ishmin.srpos.fragments.dashboard.DashboardFragment.cardAlert;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static FrameLayout frameLayout;
    public static RelativeLayout layoutMid;
    public static RelativeLayout MainLayout;
    RelativeLayout stockAlert;
    LinearLayout accountInfo;
    LinearLayout aboutApp;
    TextView accountNo;
    Button button;
    Spinner spinner;
   // String[] strings = {"20 (default)","0", "10", "30", "40", "50",};
   String[] strings= new String[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frameLayout = findViewById(R.id.frag_container);
        layoutMid = findViewById(R.id.layMid);
        MainLayout = findViewById(R.id.settingsMainFrag);
        stockAlert = findViewById(R.id.set1);
        accountInfo = findViewById(R.id.set2);
        aboutApp = findViewById(R.id.set3);
        button = findViewById(R.id.signOutBtn);
        accountNo = findViewById(R.id.ownerTxt);
        accountNo.setText(MainActivity.sharedPreferences.getString("usernumber", ""));
        spinner = findViewById(R.id.stockSpinner);
       String present= Integer.toString(MainActivity.sharedPreferences.getInt("stockalert",0 ));
        //ArrayList for StockActivity Alert Spinner
        Log.i("present no.",present);
        ArrayList<String> list =new ArrayList<>();
        list.add(present);
        int add=0;
        for(int i = 0; i < strings.length; i++){

            if(Integer.parseInt(present)!=add)
            {
                list.add(Integer.toString(add));
            }
            add = add + 10;
        }
        //ArrayAdapter to show list in Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        accountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutMid.setVisibility(View.GONE);
                //MainLayout.setPadding(0,0,0,0);
                frameLayout.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frag_container, new settings_frag2());
                transaction.commit();
                //openDialog1();
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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Account Information").setMessage("Phone number: " + MainActivity.sharedPreferences.getString("usernumber", ""))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //closes the dialog box
                    }
                });
        AlertDialog alert = builder1.create();
        alert.show();
    }

    public void openDialog2() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("App Info").setMessage("Smart Retail POS" + " " + "v1.0")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //closes the dialog box
                    }
                });
        AlertDialog alert = builder2.create();
        alert.show();
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

        int x=Integer.parseInt(txt);
        MainActivity.sharedPreferences.edit().putInt("stockalert",x).apply();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    //Do nothing
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{

            Cursor c = MainActivity.SRPOS.rawQuery("SELECT stock FROM Productsnew WHERE adminno="+Long.parseLong(MainActivity.sharedPreferences.getString("usernumber","")), null) ;
            int stock = c.getColumnIndex("stock");
            c.moveToFirst();

            while (!c.isAfterLast())
            {
                int check=c.getInt(stock);

                if(check<MainActivity.sharedPreferences.getInt("stockalert",20))
                {
                    cardAlert.setVisibility(View.VISIBLE);
                    break;
                }

                else if(c.isLast())
                    cardAlert.setVisibility(View.GONE);

                c.moveToNext();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

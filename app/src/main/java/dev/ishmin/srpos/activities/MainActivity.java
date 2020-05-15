package dev.ishmin.srpos.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import dev.ishmin.srpos.R;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration AppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;

    public static SQLiteDatabase SRPOS;
    TextView txtview;
    static public int  flag;
    public static SharedPreferences sharedPreferences;
  //  public static SharedPreferences sharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SRPOS=this.openOrCreateDatabase("SRPOS",MODE_PRIVATE,null);
        sharedPreferences=this.getSharedPreferences("dev.ishmin.srpos", Context.MODE_PRIVATE);
      //  sharedPreferences1=this.getSharedPreferences("dev.ishmin.srpos", Context.MODE_PRIVATE);


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration = new AppBarConfiguration.Builder(R.id.dashboardFragment, R.id.billingFragment, R.id.productsFragment, R.id.salesFragment, R.id.purchaseFragment)
                .setDrawerLayout(drawer)
                .build();
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, AppBarConfiguration);
        txtview = navigationView.getHeaderView(0).findViewById(R.id.phNum);
       // VerifyOtpActivity x =new VerifyOtpActivity();
     //  flag=VerifyOtpActivity.flag;
        if (flag==1)
{

    sharedPreferences.edit().putString("usernumber",VerifyOtpActivity.sharedPreferences.getString("usernumber","")).apply();
   sharedPreferences.edit().putInt("stockalert",20).apply();
   Log.i("WHERE","INSIDE IF");
}
        txtview.setText(sharedPreferences.getString("usernumber",""));

        Log.i("present no. ",sharedPreferences.getString("usernumber",""));
        Log.i("present value.",Integer.toString(sharedPreferences.getInt("stockalert",0)));
//        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        if(savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DashboardFragment()).commit();
//            navigationView.setCheckedItem(R.id.dashboardFragment);
//        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, AppBarConfiguration);
    }
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else{
        super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void user_signout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.action_settings:
                openSettings();
                break;
            case R.id.action_signOut:
                user_signout();
                break;
        }
        return true;
    }
    public void openSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        if((item.getItemId() == R.id.reportsFragment)){
//            StyleableToast.makeText(this,"Coming soon!", R.style.toastDesign).show();
//        }
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}

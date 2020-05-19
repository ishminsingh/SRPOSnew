package dev.ishmin.srpos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.ishmin.srpos.R;
import dev.ishmin.srpos.fragments.billing.CustomAdapter;
import dev.ishmin.srpos.fragments.sales.CustomAdapterSales;

public class Trial extends AppCompatActivity {

    CustomAdapterSales mCustomAdapter;
    ListView listView;
    public static List<String> sales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);

        sales = new ArrayList<>();
        listView = findViewById(R.id.salesList);
        mCustomAdapter = new CustomAdapterSales(this, sales);
        listView.setAdapter(mCustomAdapter);


        try {
            MainActivity x = new MainActivity();
            Cursor c = MainActivity.SRPOS.rawQuery("SELECT * FROM Salesnew WHERE date BETWEEN ' 2020-05-01 'AND '2020-05-16 'AND adminno=" + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")), null);
            int no = c.getColumnIndex("customerno");
            int amount = c.getColumnIndex("billamount");
            int discount = c.getColumnIndex("discount");
            int date = c.getColumnIndex("date");
            int status = c.getColumnIndex("status");

            c.moveToFirst();

            while (!c.isAfterLast()) {
                //Log.i("name", c.getString(name));
                //Log.i("sku", c.getString(sku));
                //String newitem=c.getString(name)+"     "+c.getString(brand)+"     "+c.getInt(stock);
//                Log.i("no", Integer.toString(c.getInt(no)));
                Log.i("billamount", (Float.toString(c.getFloat(amount))));
                Log.i("discount", Float.toString(c.getFloat(discount)));
                Log.i("status", c.getString(status));
                String str = Long.toString(c.getLong(no)) + "," + Float.toString(c.getFloat(amount)) + "," + Float.toString(c.getFloat(discount))
                        + "," + c.getString(date) + "," + c.getString(status);
                sales.add(str);
                c.moveToNext();
            }
            mCustomAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package dev.ishmin.srpos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev.ishmin.srpos.R;

public class StockActivity extends AppCompatActivity {
    static String res = null;
    static String name;
    static String mrp;
    Button read;
    List<String> productlist;
    ListView products;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        final int check=MainActivity.sharedPreferences.getInt("stockalert",20);

        productlist = new ArrayList<String>();
        products = findViewById(R.id.productlist);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productlist);
        products.setAdapter(arrayAdapter);
        final EditText search = findViewById(R.id.search);


        try {
            Cursor c = MainActivity.SRPOS.rawQuery("SELECT name,brand,stock FROM Productsnew WHERE adminno="+Long.parseLong(MainActivity.sharedPreferences.getString("usernumber","")+" AND stock<"+check), null) ;
            int name = c.getColumnIndex("name");

            int brand = c.getColumnIndex("brand");
            int stock = c.getColumnIndex("stock");

            c.moveToFirst();

            while (!c.isAfterLast())
            {
                Log.i("name", c.getString(name));
                Log.i("brand", c.getString(brand));
                String newitem=c.getString(name)+"     "+c.getString(brand)+"     "+Integer.toString(c.getInt(stock));
                productlist.add(newitem);
                arrayAdapter.notifyDataSetChanged();
                c.moveToNext();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }



        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {

                    productlist.clear();
                    String see=search.getText().toString();
                    if(!see.equals(""))
                    {
                        Cursor c = MainActivity.SRPOS.rawQuery("SELECT * FROM Productsnew WHERE name LIKE'"+search.getText().toString()+"%' AND adminno="+Long.parseLong(MainActivity.sharedPreferences.getString("usernumber","")+" AND stock<"+check), null);
                        int name = c.getColumnIndex("name");

                        int brand = c.getColumnIndex("brand");
                        int stock = c.getColumnIndex("stock");
                        c.moveToFirst();

                        while (!c.isAfterLast()) {

                            String newitem=c.getString(name)+"     "+c.getString(brand)+"     "+c.getInt(stock);
                            productlist.add(newitem);
                            arrayAdapter.notifyDataSetChanged();
                            c.moveToNext();

                        }
                    }
                    else
                    {
                        productlist.clear();
                        Cursor c = MainActivity.SRPOS.rawQuery("SELECT * FROM Productsnew WHERE adminno= "+Long.parseLong(MainActivity.sharedPreferences.getString("usernumber","")+" AND stock<"+check), null);
                        int name = c.getColumnIndex("name");

                        int brand = c.getColumnIndex("brand");
                        int stock = c.getColumnIndex("stock");
                        c.moveToFirst();

                        while (!c.isAfterLast()) {

                            String newitem=c.getString(name)+"     "+c.getString(brand)+"     "+c.getInt(stock);
                            productlist.add(newitem);
                            arrayAdapter.notifyDataSetChanged();
                            c.moveToNext();
                        }
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}

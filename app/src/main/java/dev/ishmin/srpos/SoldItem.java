package dev.ishmin.srpos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SoldItem extends AppCompatActivity {
    TableLayout stk ;

     public void init(String to,String from)
     {
         TableRow tbrow = new TableRow(this);
         TextView tv0 = new TextView(this);
         tv0.setText(" Name ");
         tv0.setTextColor(Color.WHITE);
         tbrow.addView(tv0);
         TextView tv1 = new TextView(this);
         tv1.setText(" Mrp ");
         tv1.setTextColor(Color.WHITE);
         tbrow.addView(tv1);
         TextView tv2 = new TextView(this);
         tv2.setText(" Quantity ");
         tv2.setTextColor(Color.WHITE);
         tbrow.addView(tv2);
         TextView tv3 = new TextView(this);
         tv3.setText(" Unit ");
         tv3.setTextColor(Color.WHITE);
         TextView tv4 = new TextView(this);
         tv4.setText(" Date ");
         tv4.setTextColor(Color.WHITE);
         tbrow.addView(tv4);
         stk.addView(tbrow);

         try


         {
             Cursor c = MainActivity.SRPOS.rawQuery("SELECT * FROM Solditems ", null);
             int name = c.getColumnIndex("name");
             int mrp = c.getColumnIndex("mrp");
             int quantity = c.getColumnIndex("quantity");
             int unit = c.getColumnIndex("unit");
             int date = c.getColumnIndex("date");

             c.moveToFirst();

             while (!c.isAfterLast()) {
                 //Log.i("name", c.getString(name));
                 //Log.i("sku", c.getString(sku));
                 //String newitem=c.getString(name)+"     "+c.getString(brand)+"     "+c.getInt(stock);
                 TableRow tbrow0 = new TableRow(this);
                 TextView tv01 = new TextView(this);
                 tv01.setText(c.getString(name));
                 tv01.setTextColor(Color.WHITE);
                 tbrow0.addView(tv01);
                 TextView tv11 = new TextView(this);
                 tv11.setText(Float.toString(c.getFloat(mrp)));
                 tv11.setTextColor(Color.WHITE);
                 tbrow0.addView(tv11);
                 TextView tv21 = new TextView(this);
                 tv21.setText(Integer.toString(c.getInt(quantity)));
                 tv21.setTextColor(Color.WHITE);
                 tbrow0.addView(tv21);
                 TextView tv31 = new TextView(this);
                 tv31.setText(c.getString(unit));
                 tv31.setTextColor(Color.WHITE);
                 TextView tv41 = new TextView(this);
                 tv41.setText(c.getString(date));
                 tv41.setTextColor(Color.WHITE);
                 tbrow0.addView(tv41);
                 stk.addView(tbrow0);

             }
         }
         catch (Exception e)
         {
             e.printStackTrace();

         }

     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold_item);

        stk = (TableLayout) findViewById(R.id.tlGridTable);
        String datefrom="";
        String dateto="";



    }
}

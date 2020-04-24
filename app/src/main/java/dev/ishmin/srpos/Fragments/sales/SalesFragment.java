package dev.ishmin.srpos.Fragments.sales;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import dev.ishmin.srpos.MainActivity;
import dev.ishmin.srpos.R;

public class SalesFragment extends Fragment {
    TableLayout stk ;

    public void init()
    {
       try{
        TableRow tbrow = new TableRow(getActivity());
        TextView tv0 = new TextView(getActivity());
        tv0.setText(" Customer No.");
        tv0.setTextColor(Color.BLACK);
        tv0.setPadding(2, 8, 5, 0);
           tv0.setGravity(Gravity.CENTER);
        tbrow.addView(tv0);

        TextView tv1 = new TextView(getActivity());
        tv1.setText(" Bill Amount ");
        tv1.setTextColor(Color.BLACK);
        tv1.setPadding(2, 8, 5, 0);
           tv1.setGravity(Gravity.CENTER);
        tbrow.addView(tv1);

        TextView tv2 = new TextView(getActivity());
        tv2.setText(" Discount ");
           tv2.setPadding(2, 8, 5, 0);
        tv2.setTextColor(Color.BLACK);
           tv2.setGravity(Gravity.CENTER);
        tbrow.addView(tv2);

        TextView tv3 = new TextView(getActivity());
        tv3.setText(" Date ");
        tv3.setTextColor(Color.BLACK);
           tv3.setPadding(2, 6, 5, 0);
           tv3.setGravity(Gravity.CENTER);
           tbrow.addView(tv3);

        TextView tv4 = new TextView(getActivity());
        tv4.setText(" Status ");
        tv4.setTextColor(Color.BLACK);
           tv4.setPadding(2, 6, 5, 0);
           tv4.setGravity(Gravity.CENTER);
        tbrow.addView(tv4);

        stk.addView(tbrow);}
       catch (Exception e)
       {
           e.printStackTrace();
       }

        try


        {
            Cursor c = MainActivity.SRPOS.rawQuery("SELECT * FROM Sales ", null);
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
              Log.i("no",Integer.toString(c.getInt(no)));
               Log.i("billamount", (Float.toString(c.getFloat(amount))));
               Log.i("discount",Float.toString(c.getFloat(discount)));
               Log.i("status",c.getString(status));

                TableRow tbrow0 = new TableRow(getActivity());
                TextView tv01 = new TextView(getActivity());
                tv01.setText(Long.toString(c.getLong(no)));
                tv01.setGravity(Gravity.CENTER);
                tv01.setTextColor(Color.BLACK);
                tv01.setPadding(2, 5, 5, 0);
                tbrow0.addView(tv01);
                TextView tv11 = new TextView(getActivity());
                tv11.setText(Float.toString(c.getFloat(amount)));
                tv11.setGravity(Gravity.CENTER);
                tv11.setPadding(2, 8, 5, 0);
                tv11.setTextColor(Color.BLACK);
                tbrow0.addView(tv11);
                TextView tv21 = new TextView(getActivity());
                tv21.setText(Float.toString(c.getFloat(discount)));
                tv21.setGravity(Gravity.CENTER);
                tv21.setPadding(2, 8, 5, 0);
                tv21.setTextColor(Color.BLACK);
                tbrow0.addView(tv21);

                TextView tv31 = new TextView(getActivity());
                tv31.setGravity(Gravity.CENTER);
                tv31.setPadding(2, 6, 5, 0);
                tv31.setText(c.getString(date));
                tv31.setTextColor(Color.BLACK);
                tbrow0.addView(tv31);

                TextView tv41 = new TextView(getActivity());
                tv41.setText(c.getString(status));
                tv41.setGravity(Gravity.CENTER);
                tv41.setPadding(2, 6, 5, 0);
                tv41.setTextColor(Color.BLACK);
                tbrow0.addView(tv41);

                stk.addView(tbrow0);
                c.moveToNext();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sales, container, false);
        //return inflater.inflate(R.layout.fragment_sales, container, false);
      // v.requestWindowFeature(Window.FEATURE_NO_TITLE);
        stk  = (TableLayout) v.findViewById(R.id.table_main);
        String datefrom="";
        String dateto="";
        init();
        return v;
    }
}

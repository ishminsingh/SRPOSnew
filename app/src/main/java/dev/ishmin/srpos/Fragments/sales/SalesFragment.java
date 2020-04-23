package dev.ishmin.srpos.Fragments.sales;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TableRow tbrow = new TableRow(getActivity());
        TextView tv0 = new TextView(getActivity());
        tv0.setText(" Name ");
        tv0.setTextColor(Color.WHITE);
        tbrow.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(" Mrp ");
        tv1.setTextColor(Color.WHITE);
        tbrow.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(" Quantity ");
        tv2.setTextColor(Color.WHITE);
        tbrow.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText(" Unit ");
        tv3.setTextColor(Color.WHITE);
        TextView tv4 = new TextView(getActivity());
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
                TableRow tbrow0 = new TableRow(getActivity());
                TextView tv01 = new TextView(getActivity());
                tv01.setText(c.getString(name));
                tv01.setGravity(Gravity.CENTER);
                tv01.setTextColor(Color.WHITE);
                tbrow0.addView(tv01);
                TextView tv11 = new TextView(getActivity());
                tv11.setText(Float.toString(c.getFloat(mrp)));
                tv11.setGravity(Gravity.CENTER);
                tv11.setTextColor(Color.WHITE);
                tbrow0.addView(tv11);
                TextView tv21 = new TextView(getActivity());
                tv21.setText(Integer.toString(c.getInt(quantity)));
                tv21.setGravity(Gravity.CENTER);
                tv21.setTextColor(Color.WHITE);
                tbrow0.addView(tv21);
                TextView tv31 = new TextView(getActivity());
                tv31.setGravity(Gravity.CENTER);
                tv31.setText(c.getString(unit));
                tv31.setTextColor(Color.WHITE);
                TextView tv41 = new TextView(getActivity());
                tv41.setText(c.getString(date));
                tv41.setGravity(Gravity.CENTER);
                tv41.setTextColor(Color.WHITE);
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
        stk  = (TableLayout) v.findViewById(R.id.tlGridTable);
        String datefrom="";
        String dateto="";
        init();
        return v;
    }
}

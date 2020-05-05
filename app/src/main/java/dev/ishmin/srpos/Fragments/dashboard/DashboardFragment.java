package dev.ishmin.srpos.Fragments.dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;


import dev.ishmin.srpos.MainActivity;
//import dev.ishmin.srpos.Payment;
//import dev.ishmin.srpos.PieChart;
import dev.ishmin.srpos.R;
//import dev.ishmin.srpos.TryOutFile;

public class DashboardFragment extends Fragment {

    private CardView cardAlert;
    private CardView cardSales;
    private CardView cardPayments;
    private CardView cardChart;

    private float[] yData=new float[2];
    private String[] xData=  {"Paid Percentage", "Unpaid Percentage"};
    int[] legendColors = new int[] {Color.MAGENTA, Color.YELLOW};
    PieChart pieChart;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        cardAlert = v.findViewById(R.id.cardViewAlert);

        cardPayments = v.findViewById(R.id.cardViewPayments);
        pieChart = v.findViewById(R.id.PieChart);
        pieChart.getDescription().setText("Payments");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(20f);
        pieChart.setTransparentCircleAlpha(0);
        //pieChart.setCenterText("Payments");
        //pieChart.setCenterTextSize(10);
        pieChart.animateY(1000);

        int paid =0;
        int unpaid=0;

       try {
MainActivity x1=new MainActivity();
           Cursor c1 = MainActivity.SRPOS.rawQuery("SELECT status FROM Salesnew WHERE adminno="+Long.parseLong(MainActivity.sharedPreferences.getString("usernumber","")), null);

           int stock = c1.getColumnIndex("status");

           c1.moveToFirst();

           while (!c1.isAfterLast()) {
               String x;
               x = c1.getString(stock);

               if (x.equals("Paid"))
                   paid++;

               else
                   unpaid++;

               c1.moveToNext();

           }
           Log.i("paid",Integer.toString(paid));
           Log.i("Unpaid",Integer.toString(unpaid));
           int total=(paid+unpaid);
           Log.i("Total",Integer.toString(total));
           float paidpercent=(paid*100)/total   ;
           Log.i("perc",Float.toString(paidpercent));
           yData[0]=(paidpercent);
           yData[1]=(100-paidpercent);

       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
        cardSales = v.findViewById(R.id.cardViewSales);

       addDataSet();



        return v;
    }

    private void addDataSet() {

        ArrayList<PieEntry> yEntry = new ArrayList<>();
        ArrayList<String> xEntry = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){

           yEntry.add(new PieEntry(yData[i], i));

        }
        for(int i = 1; i < xData.length; i++){

            xEntry.add(xData[i]);
        }

        //create pie dataSet
        PieDataSet pieDataSet = new PieDataSet(yEntry, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);

        //add colors to dataSet
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        pieDataSet.setColors(colors);

        //add Legend to chart
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(10f);
        legend.setTextSize(10f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        LegendEntry[] entries = new LegendEntry[2];
        for(int i = 0; i<entries.length; i++)
        {
            LegendEntry entry = new LegendEntry();
            entry.formColor = legendColors[i];
            entry.label = String.valueOf(xData[i]);
            entries[i] = entry;
        }
        legend.setCustom(entries);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

}

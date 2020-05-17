package dev.ishmin.srpos.fragments.dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dev.ishmin.srpos.activities.MainActivity;
import dev.ishmin.srpos.R;
import dev.ishmin.srpos.activities.StockActivity;
import dev.ishmin.srpos.activities.Trial;

public class DashboardFragment extends Fragment {

    public static CardView cardAlert;
    private CardView cardSales;
    private CardView cardPayments;

    private float[] yData = new float[2];
    private String[] xData = {"Paid %", "Unpaid %"};
    int[] legendColors = new int[]{Color.rgb(192, 255, 140), Color.rgb(255, 247, 140)};

    PieChart pieChart;
    BarChart barChart;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        cardAlert = v.findViewById(R.id.cardViewAlert);
        cardAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StockActivity.class);
                startActivity(intent);
            }
        });
        try{

            Cursor c = MainActivity.SRPOS.rawQuery("SELECT stock FROM Productsnew WHERE adminno="+Long.parseLong(MainActivity.sharedPreferences.getString("usernumber","")), null) ;
            int stock = c.getColumnIndex("stock");
            c.moveToFirst();

            while (!c.isAfterLast())
            {
                int check=c.getInt(stock);

                if(check<MainActivity.sharedPreferences.getInt("stockalert",20))
                {
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

        cardSales = v.findViewById(R.id.cardViewSales);
        barChart = v.findViewById(R.id.barGraph);
        barChart.setDescription("Sales Graph");

        cardPayments = v.findViewById(R.id.cardViewPayments);

        pieChart = v.findViewById(R.id.PieChart);
        pieChart.setDescription("Payments");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(20f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.animateY(1000);

        int paid = 0;
        int unpaid = 0;

        try {

            MainActivity x1 = new MainActivity();
            Cursor c1 = MainActivity.SRPOS.rawQuery("SELECT status FROM Salesnew WHERE adminno=" + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")), null);

            int stock = c1.getColumnIndex("status");

            c1.moveToFirst();

            while (!c1.isAfterLast())

            {
                String x;
                x = c1.getString(stock);
                if (x.equals("Paid"))
                    paid++;
                else
                    unpaid++;
                c1.moveToNext();

            }

            Log.i("paid", Integer.toString(paid));
            Log.i("Unpaid", Integer.toString(unpaid));
            int total = (paid + unpaid);
            Log.i("Total", Integer.toString(total));
            float paidpercent = (paid * 100) / total;
            Log.i("perc", Float.toString(paidpercent));
            yData[0] = (paidpercent);
            yData[1] = (100 - paidpercent);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        addDataSet();

        addDataSet2();

        return v;
    }

    private void addDataSet() {

        ArrayList<Entry> yEntry = new ArrayList<>();
        ArrayList<String> xEntry = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntry.add(new Entry(yData[i], i));
        }
        for (int i = 1; i < xData.length; i++) {
            xEntry.add(xData[i]);
        }


        //create pie dataSet
        PieDataSet pieDataSet = new PieDataSet(yEntry, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(10);

        //add colors to dataSet
        pieDataSet.setColors(legendColors);

        //add Legend to chart
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(10f);
        legend.setTextSize(10f);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        legend.setCustom(legendColors, xData);

        //create pie data object
        PieData pieData = new PieData(xData, pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public void addDataSet2()

    {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        //entry of bar graph data
      /*  ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(40f, 0));
        barEntries.add(new BarEntry(55f, 1));
        barEntries.add(new BarEntry(70f, 2));
        barEntries.add(new BarEntry(35f, 3));
        barEntries.add(new BarEntry(20f, 4));
        barEntries.add(new BarEntry(85f, 5));*/


        //create barDataSet


        //entry of X-axis values (dates)


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String[] days = new String[10];
        days[0] = sdf.format(date);
        Log.i("Entry made", days[0]);

        for(int i = 1; i <= 5; i++)
        {
            cal.add(Calendar.DAY_OF_MONTH, -1);
            date = cal.getTime();
            days[i] = sdf.format(date);

           // Log.i("Entry made", days[i]);
        }
        int run=1;
        for(int i = 0; i <= 5; i++)
        {

         Log.i("ME RUN",Integer.toString(run));
         String str=days[i];
         String[] arrOfStr = str.split("-");
         String  newdate= arrOfStr[2]+"/"+arrOfStr[1];
            dates.add(newdate);
            Log.i("date added",newdate);
            run++;
        }



        for(int i = 0; i <= 5; i++)
        {

            float sales=0;

            try{
               Log.i("Date search",days[i].trim());
                Cursor c = MainActivity.SRPOS.rawQuery("SELECT billamount,discount FROM Salesnew WHERE adminno="+Long.parseLong(MainActivity.sharedPreferences.getString("usernumber",""))+" AND date='"+days[i].trim()+"'", null) ;
                int billamount = c.getColumnIndex("billamount");
                int discount = c.getColumnIndex("discount");

                c.moveToFirst();

                while (!c.isAfterLast())
                {
                    Log.i("billamount",Float.toString(c.getFloat(billamount)));
                    Log.i("discount",Float.toString(c.getFloat(discount)));

                    float bill=c.getFloat(billamount);
                            float disc=c.getFloat(discount);

                            bill=bill-disc;
                    sales=sales+bill;

                    c.moveToNext();
                }
               Log.i("Sales done", Float.toString(sales));
                Log.i("date", dates.get(i));
                barEntries.add(new BarEntry(sales, i ));

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }


        BarDataSet barDataSet = new BarDataSet(barEntries, "Sales");
        barDataSet.setColors(legendColors);
        /* dates.add("2020/04/01");
        dates.add("2020/04/02");
        dates.add("2020/04/03");
        dates.add("2020/04/04");
        dates.add("2020/04/05");
        dates.add("2020/04/06");*/


     try {
         BarData barData = new BarData(dates, barDataSet);
         barChart.setData(barData);
         barChart.setTouchEnabled(true);
         barChart.setDragEnabled(true);
         barChart.setScaleEnabled(true);
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }


    }
}

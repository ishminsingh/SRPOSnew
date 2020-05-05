package dev.ishmin.srpos;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.DateSorter;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TryOutFile extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private BarChart barChart;
    ArrayList<String> dates;
    ArrayList<BarEntry> barEntries;
    Random random;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tryout);
        relativeLayout = findViewById(R.id.laySalesGraph);
        barChart = findViewById(R.id.barGraph);

        createRandomBarGraph("2020/04/01", "2020/05/10"); //x-Axis(dates), create bar graph b/w these 2 dates
    }
    public void createRandomBarGraph(String Date1, String Date2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Date2);

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();
            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);

            dates = new ArrayList<>();
            dates = getlist(mDate1, mDate2); // get the dates and add them to arraylist

            //create barEntries
            barEntries = new ArrayList<>();
            float max = 0f;
            float value = 0f;
            random = new Random(); // y-Axis entries(random b/w 0-100)
            for(int j = 0; j < dates.size(); j++){
                max = 100f;
                value = random.nextFloat()*max;
                barEntries.add(new BarEntry(value, j));
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
        // create BarDataSet
        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setText("Sales Graph");
    }
    public ArrayList<String> getlist(Calendar startDate, Calendar endDate){
        ArrayList<String> list = new ArrayList<>();
        while(startDate.compareTo(endDate) <= 0){
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH,1);
        }
        return list;
    }
    public String getDate(Calendar cld){
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/"
                + cld.get(Calendar.DAY_OF_MONTH);
        try {
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            curDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return curDate;
    }
}

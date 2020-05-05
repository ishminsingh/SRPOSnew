package dev.ishmin.srpos;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TryOutFile extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private LineChart chart;
    private int[] yValues = {50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
    private String[] xValues = {"2020-04-01", "2020-04-02", "2020-04-03", "2020-04-04", "2020-04-05",
            "2020-04-06", "2020-04-07", "2020-04-08", "2020-04-09", "2020-04-10"};

    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("2020-04-01", "2020-04-02", "2020-04-03", "2020-04-04", "2020-04-05",
            "2020-04-06", "2020-04-07", "2020-04-08", "2020-04-09", "2020-04-10"));


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tryout);
        relativeLayout = findViewById(R.id.laySalesGraph);
        chart = findViewById(R.id.lineChart);
        chart.getDescription().setText("Sales");
        addDataSet2();

    }
    private void addDataSet2(){

        final ArrayList<Entry> yData = new ArrayList<>();
        final ArrayList<String> xData = new ArrayList<>();

        for(int i = 0; i < yValues.length; i++){
            yData.add(new Entry(yValues[i], i));
        }
        for(int i = 0; i < xValues.length; i++){
            xData.add(xValues[i]);
        }
        //create line dataSet
        LineDataSet lineDataSet = new LineDataSet(yData, "Sales");
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.TOP);

        //        chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));


        //create line data object
        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextSize(12f);
        lineData.setValueTextColor(Color.BLACK);

        chart.setData(lineData);
        chart.invalidate();
    }
}

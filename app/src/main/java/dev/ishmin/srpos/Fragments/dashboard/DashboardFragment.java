package dev.ishmin.srpos.Fragments.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import dev.ishmin.srpos.R;
import dev.ishmin.srpos.TryOutFile;

public class DashboardFragment extends Fragment {

    private CardView cardAlert;
    private CardView cardSales;
    private CardView cardPayments;


    private int[] yData = {10, 2};
    private String[] xData = {"Paid", "UnPaid"};
    int[] legendColors = new int[] {Color.MAGENTA, Color.YELLOW};
    PieChart pieChart;
    LineChart chart;
    private int[] yValues = {50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
    private String[] xValues = {"2020-04-01", "2020-04-02", "2020-04-03", "2020-04-04", "2020-04-05",
            "2020-04-06", "2020-04-07", "2020-04-08", "2020-04-09", "2020-04-10"};

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
        addDataSet();

        cardSales = v.findViewById(R.id.cardViewSales);
        chart = v.findViewById(R.id.lineChart);
        chart.getDescription().setText("Sales");
        addDataSet2();
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
        for(int i = 0; i<entries.length; i++){
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

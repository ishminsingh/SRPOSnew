import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;

import dev.ishmin.srpos.R;

public class PaymentsPieChart extends AppCompatActivity {

    private int[] yData = {10, 2};
    private String[] xData = {"Paid", "unPaid"};
    PieChart pieChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tryout);
        pieChart = findViewById(R.id.PieChart);

        pieChart.setDescription("Payments (In Rupees ₹)");

    }
}

package lecture.com.cashmanager.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import lecture.com.cashmanager.R;

public class ReportActivity extends AppCompatActivity {

    BarChart barChart;
    PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Inflate chart to layout xml
        barChart = (BarChart) findViewById(R.id.chart_bar);
        pieChart = (PieChart) findViewById(R.id.chart_pie);

        List<BarEntry> entriesBar = new ArrayList<>();
        entriesBar.add(new BarEntry(0f, 30f));
        entriesBar.add(new BarEntry(1f, 80f));
        entriesBar.add(new BarEntry(2f, 60f));
        entriesBar.add(new BarEntry(3f, 50f));
        // gap of 2f
        entriesBar.add(new BarEntry(5f, 70f));
        entriesBar.add(new BarEntry(6f, 60f));

        BarDataSet set = new BarDataSet(entriesBar, "BarDataSet");
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh

        List<PieEntry> entriesPie = new ArrayList<>();

        entriesPie.add(new PieEntry(18.5f, "Green"));
        entriesPie.add(new PieEntry(26.7f, "Yellow"));
        entriesPie.add(new PieEntry(24.0f, "Red"));
        entriesPie.add(new PieEntry(30.8f, "Blue"));

        PieDataSet setPie = new PieDataSet(entriesPie, "Election Results");
        PieData dataPie = new PieData(setPie);
        pieChart.setData(dataPie);
        pieChart.invalidate(); // refresh


    }
}

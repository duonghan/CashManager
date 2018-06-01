package lecture.com.cashmanager.menu;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.entity.CashReportBar;
import lecture.com.cashmanager.entity.CategoryReportPie;
import lecture.com.cashmanager.model.Category;

public class ReportActivity extends AppCompatActivity {

    BarChart barChart;
    PieChart pieChart;
    Spinner spnType;
    Spinner spnTime;
    EditText edtStart;
    EditText edtEnd;

    private final int MODE_EXPENSE = -1;
    private final int MODE_INCOME = 1;
    private final int MODE_BY_TIME = 3;
    private final int MODE_BY_CATEGORY = 4;
    private final int MODE_START = 5;
    private final int MODE_END = 6;

    private Calendar calendar;
    private int year, month, day;
    private int monthStart;
    private int monthEnd;
    private int modeTime;
    private int modeType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Inflate chart to layout xml
        barChart = (BarChart) findViewById(R.id.chart_bar);
        pieChart = (PieChart) findViewById(R.id.chart_pie);
        spnType = (Spinner) findViewById(R.id.spn_type);
        spnTime = (Spinner) findViewById(R.id.spn_time_or_category);
        edtStart = (EditText) findViewById(R.id.spn_start);
        edtEnd = (EditText) findViewById(R.id.spn_end);

        List<String> lstType = new ArrayList<>();
        lstType.add(getString(R.string.txt_Income));
        lstType.add(getString(R.string.txt_Expense));

        List<String> lstTime = new ArrayList<>();
        lstTime.add(getString(R.string.txt_spn_time));
        lstTime.add(getString(R.string.txt_spn_category));

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // Default value
        monthStart = 0;
        monthEnd = month;
        modeTime = MODE_BY_TIME;
        modeType = MODE_INCOME;

        showDate(year, monthStart, monthEnd);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,R.layout.spinner_item,lstType);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnType.setAdapter(adapter);
        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spnType.getSelectedItemPosition() == 0){
                    modeType = MODE_INCOME;
                    getView();
                }else{
                    modeType = MODE_EXPENSE;
                    getView();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter = new ArrayAdapter(this,R.layout.spinner_item,lstTime);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnTime.setAdapter(adapter);
        spnTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spnTime.getSelectedItemPosition() == 0){
                    modeTime = MODE_BY_TIME;
                    getView();
                }else{
                    modeTime = MODE_BY_CATEGORY;
                    getView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(edtStart, MODE_START);
            }
        });

        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(edtEnd, MODE_END);
            }
        });

        getView();
    }

    // Create and show a DatePickerDialog when click button.
    private void showDatePickerDialog(final EditText textView, int type)
    {
        // Create a new OnDateSetListener instance. This listener will be invoked when user click ok button in DatePickerDialog.
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                StringBuffer strBuf = new StringBuffer();
                strBuf.append(month+1);
                strBuf.append("/");
                strBuf.append(year);
                textView.setText(strBuf);
            }
        };

        // Create the new DatePickerDialog instance.
        DatePickerDialog datePickerDialog = new DatePickerDialog(ReportActivity.this, onDateSetListener, year, month, day);

        // Set dialog icon and title.
//        datePickerDialog.setIcon(R.drawable.if_snowman);
        datePickerDialog.setTitle("Please select date.");

        // Popup the dialog.
        datePickerDialog.show();
    }

    private void showDate(int year, int monthStart, int monthEnd) {
        monthStart += 1;
        monthEnd += 1;

        edtStart.setText(monthStart + "/" + year);
        edtEnd.setText(monthEnd + "/" + year);

    }

    public void getBarChatView(){
        List<BarEntry> entriesBar = new ArrayList<>();
        DBHelper db = new DBHelper(this);
        List<CashReportBar> cashReportBars = new ArrayList<>();

        for(int i = monthStart + 1; i <= monthEnd + 1; i++){

            cashReportBars.add(db.getCashReportBar(i, year, modeType));
        }

        for(CashReportBar bar: cashReportBars){
            entriesBar.add(new BarEntry(bar.getMonth(), bar.getAmount()));
        }

        BarDataSet set = new BarDataSet(entriesBar, getString(R.string.txt_balance));
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueTextSize(15f);
        set.setValueFormatter(new LargeValueFormatter());

        BarData data = new BarData(set);
//        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(1);
        xAxis.setTextSize(17f);

        YAxis left = barChart.getAxisLeft();
        left.setDrawLabels(true);
        left.setDrawZeroLine(true);
        left.setZeroLineColor(R.color.red);
        left.setValueFormatter(new LargeValueFormatter());
        left.setTextSize(15f);

        YAxis right = barChart.getAxisRight();
        right.setEnabled(false);

    }

    public void getPieChatView(){
        List<PieEntry> entriesPie = new ArrayList<>();
        DBHelper db = new DBHelper(this);
        List<CategoryReportPie> categoryReportPies = db.getCategoryReportPie(modeType);

        for(CategoryReportPie reportPie: categoryReportPies){
            entriesPie.add( new PieEntry(reportPie.getAmount(), reportPie.getCategoryName()));
        }

        PieDataSet setPie = new PieDataSet(entriesPie, "Pie");
        setPie.setColors(ColorTemplate.JOYFUL_COLORS);
        setPie.setValueTextSize(15f);

        PieData dataPie = new PieData(setPie);
        dataPie.setValueFormatter(new PercentFormatter());
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setData(dataPie);
        pieChart.invalidate(); // refresh
        pieChart.setDrawEntryLabels(false);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleRadius(30f);

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCirc);
    }

    public void getView(){
        if(modeTime == MODE_BY_TIME){
            barChart.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.GONE);
            getBarChatView();
        }
        else{
            barChart.setVisibility(View.GONE);
            pieChart.setVisibility(View.VISIBLE);
            getPieChatView();
        }
    }

}

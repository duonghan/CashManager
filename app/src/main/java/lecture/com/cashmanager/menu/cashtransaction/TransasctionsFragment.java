package lecture.com.cashmanager.menu.cashtransaction;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import lecture.com.cashmanager.adapters.TransactionShowAdapter;
import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.entity.CashInfo;
import lecture.com.cashmanager.entity.CashInfoMonth;
import lecture.com.cashmanager.entity.OverviewInfo;
import lecture.com.cashmanager.model.CashTransaction;
import lecture.com.cashmanager.model.Category;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransasctionsFragment extends Fragment implements View.OnClickListener{

    private HorizontalCalendar horizontalCalendar;
    private int currentMonth;

    private FloatingActionButton fab_income;
    private FloatingActionButton fab_expense;
    private static final int INCOME = 101;
    private static final int EXPENSE = 102;
    private static final int MY_REQUEST_CODE = 1101;

    RecyclerView recyclerView;
    CashInfoMonth cashInfoMonth;
    List<CashInfo> transactionList;
    TransactionShowAdapter adapter;

    private View rootView;
    private View noctView;
    private View ctOverView;
    private TextView inflow;
    private TextView outflow;
    private TextView summary;

    public TransasctionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_transactions, container, false);
        noctView = rootView.findViewById(R.id.no_transaction);
        ctOverView = rootView.findViewById(R.id.transaction_overview);


        inflow = ctOverView.findViewById(R.id.txt_inflow_value);
        outflow = ctOverView.findViewById(R.id.txt_outflow_value);
        summary = ctOverView.findViewById(R.id.txt_summary_value);

        fab_income = rootView.findViewById(R.id.fab_income);
        fab_expense = rootView.findViewById(R.id.fab_expense);

        fab_income.setOnClickListener(this);
        fab_expense.setOnClickListener(this);

        /* start before 12 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -12);

        /* end after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(3)
                .mode(HorizontalCalendar.Mode.MONTHS)
                .configure()
                    .formatMiddleText("MM/yyyy")
                    .sizeMiddleText(17f)
                .showTopText(false)
                .showBottomText(false)
                .textColor(Color.LTGRAY, Color.WHITE)
                .end()
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                currentMonth = date.get(Calendar.MONTH) + 1;
                setViewByMonth(currentMonth);
            }

        });

        currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        setViewByMonth(currentMonth);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.fab_income:
                Intent addIncome = new Intent(getActivity(), AddTransactionActivity.class);
                addIncome.putExtra("type",1);
                startActivityForResult(addIncome, MY_REQUEST_CODE);
                break;

            case R.id.fab_expense:
                Intent addExpense = new Intent(getActivity(), AddTransactionActivity.class);
                addExpense.putExtra("type",-1);
                startActivityForResult(addExpense, MY_REQUEST_CODE);
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE ) {
            boolean needRefresh = data.getBooleanExtra("needRefresh",true);
            // Refresh ListView
            if(needRefresh) {
//                this.transactionList.clear();
//                DBHelper db = new DBHelper(getContext());
//                List<CashInfo> list=  db.getCTMonthInfo(currentMonth);
//                this.transactionList.addAll(list);
//                // Thông báo dữ liệu thay đổi (Để refresh ListView).
//                this.adapter.notifyDataSetChanged();
            }
        }
    }

    public void setViewByMonth(int month){

        recyclerView = rootView.findViewById(R.id.listTransactions);
        DBHelper db = new DBHelper(getActivity());
        cashInfoMonth = db.getCTMonthInfo(currentMonth);
//        transactionList = db.getCTMonthInfo(currentMonth);
        if(cashInfoMonth.getDay().size() > 0){
            ctOverView.setVisibility(rootView.VISIBLE);
            recyclerView.setVisibility(rootView.VISIBLE);
            noctView.setVisibility(rootView.GONE);

            adapter = new TransactionShowAdapter(getActivity(), cashInfoMonth);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            OverviewInfo overviewInfo = db.getOverviewInfoByMonth(currentMonth);
            inflow.setText(String.valueOf(overviewInfo.getInflow()));
            outflow.setText(String.valueOf(overviewInfo.getOutflow()));
            summary.setText(String.valueOf(overviewInfo.getSummary()));

            ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
            animationAdapter.setDuration(500);
            recyclerView.setAdapter(animationAdapter);
        }else{
            ctOverView.setVisibility(rootView.GONE);
            recyclerView.setVisibility(rootView.GONE);
            noctView.setVisibility(rootView.VISIBLE);
        }
    }
}

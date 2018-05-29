package lecture.com.cashmanager.menu.cashtransaction;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import lecture.com.cashmanager.model.CashTransaction;
import lecture.com.cashmanager.model.Category;


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
    List<CashInfo> transactionList;
    TransactionShowAdapter adapter;


    public TransasctionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);

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
//                Toast.makeText(getContext(), DateFormat.format("MM/yyyy", date) + " is selected!", Toast.LENGTH_SHORT).show();
                currentMonth = date.get(Calendar.MONTH) + 1;
                Toast.makeText(getContext(), currentMonth + "", Toast.LENGTH_LONG).show();
            }

        });

        recyclerView = rootView.findViewById(R.id.listTransactions);
        transactionList = initData(50);

        adapter = new TransactionShowAdapter(getActivity(), transactionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        animationAdapter.setDuration(500);
        recyclerView.setAdapter(animationAdapter);

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

    private List<CashInfo> initData(int n){
        List<CashInfo> list = new ArrayList<>();
        for (int i = 0; i< n; i++){
            list.add(new CashInfo(25000, "category", "description", "15/05/2018"));
        }

        return list;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE ) {
            boolean needRefresh = data.getBooleanExtra("needRefresh",true);
            // Refresh ListView
            if(needRefresh) {
                this.transactionList.clear();
                DBHelper db = new DBHelper(getContext());
                List<CashInfo> list=  db.getCTMonthInfo(currentMonth);
                this.transactionList.addAll(list);
                // Thông báo dữ liệu thay đổi (Để refresh ListView).
                this.adapter.notifyDataSetChanged();
            }
        }
    }
}

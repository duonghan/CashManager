package lecture.com.cashmanager.menu.cashtransaction;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import lecture.com.cashmanager.adapters.TransactionShowAdapter;
import lecture.com.cashmanager.menu.cashtransaction.AddTransactionActivity;
import lecture.com.cashmanager.R;
import lecture.com.cashmanager.model.CashTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransasctionsFragment extends Fragment implements View.OnClickListener{

    private HorizontalCalendar horizontalCalendar;

    private FloatingActionButton fab_income;
    private FloatingActionButton fab_expense;
    private final int INCOME = 111;
    private final int EXPENSE = 111;

    RecyclerView recyclerView;
    List<CashTransaction> transactionList;
    TransactionShowAdapter adapter;


    public TransasctionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_transasctions, container, false);

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
                Toast.makeText(getContext(), DateFormat.format("MM/yyyy", date) + " is selected!", Toast.LENGTH_SHORT).show();
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
                startActivityForResult(addIncome, INCOME);
                break;

            case R.id.fab_expense:
                Intent addExpense = new Intent(getActivity(), AddTransactionActivity.class);
                startActivityForResult(addExpense, EXPENSE);
                break;

            default:
                break;
        }
    }

    private List<CashTransaction> initData(int n){
        List<CashTransaction> list = new ArrayList<>();
        for (int i = 0; i< n; i++){
            list.add(new CashTransaction(i,i,i,i,"Description", "05/05/2018", "15/05/2018"));
        }

        return list;
    }
}

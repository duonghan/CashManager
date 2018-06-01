package lecture.com.cashmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.entity.CashInfo;
import lecture.com.cashmanager.entity.CashInfoMonth;
import lecture.com.cashmanager.menu.cashtransaction.ShowCTActivity;
import lecture.com.cashmanager.menu.category.ShowCategoryActivity;

public class TransactionShowAdapter extends RecyclerView.Adapter<TransactionShowAdapter.ViewHolder> {

    private Context context;
    private ContextCompat contextCompat;
    private CashInfoMonth cashInfoMonth;
    DBHelper db;

    public TransactionShowAdapter() {
    }

    public TransactionShowAdapter(Context context, CashInfoMonth cashInfoMonth) {
        this.context = context;
        this.cashInfoMonth = cashInfoMonth;
        this.db = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_show_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        CashInfo transaction = transactionInfoList.get(position);
        int dayValue=0;

        String day = cashInfoMonth.getDay().get(position);
        final List<CashInfo> arrCashTransaction = db.getCTDayInfo(day);

        for(CashInfo cashInfo: arrCashTransaction){
            if(cashInfo.getType() == 1)
                dayValue += cashInfo.getValue();
            else
                dayValue -= cashInfo.getValue();
        }

        holder.txtDate.setText(day);
        holder.txtDateValue.setText(String.valueOf(dayValue));

        TransactionDateAdapter dateAdapter = new TransactionDateAdapter(context, R.layout.transaction_row_item, arrCashTransaction);
        holder.lvCashItem.setAdapter(dateAdapter);

        holder.lvCashItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CashInfo cashInfo = (CashInfo) parent.getItemAtPosition(position);
                CashInfo cashInfo = arrCashTransaction.get(position);
                Intent showCT = new Intent(context, ShowCTActivity.class);
                showCT.putExtra("ctID", cashInfo.getId());
                context.startActivity(showCT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cashInfoMonth.getDay().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtDate;
        private TextView txtDateValue;
        private ListView lvCashItem;

        private ViewHolder(View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txt_date);
            txtDateValue = itemView.findViewById(R.id.txt_date_value);
            lvCashItem = itemView.findViewById(R.id.lv_cash_item);
        }
    }
}

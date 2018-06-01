package lecture.com.cashmanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.entity.CashInfo;
import lecture.com.cashmanager.entity.CashInfoMonth;
import lecture.com.cashmanager.model.CashTransaction;

public class TransactionShowAdapter extends RecyclerView.Adapter<TransactionShowAdapter.ViewHolder> {

    private Context context;
    private CashInfoMonth cashInfoMonth;

    public TransactionShowAdapter() {
    }

    public TransactionShowAdapter(Context context, CashInfoMonth cashInfoMonth) {
        this.context = context;
        this.cashInfoMonth = cashInfoMonth;
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
        DBHelper db = new DBHelper(context);
        int dayValue=0;

        String day = cashInfoMonth.getDay().get(position);
        List<CashInfo> arrCashTransaction = db.getCTDayInfo(day);

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

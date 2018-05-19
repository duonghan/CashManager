package lecture.com.cashmanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.model.CashTransaction;

public class TransactionShowAdapter extends RecyclerView.Adapter<TransactionShowAdapter.ViewHolder> {

    private Context context;
    private List<CashTransaction> transactionInfoList;

    public TransactionShowAdapter() {
    }

    public TransactionShowAdapter(Context context, List<CashTransaction> transactionInfoList) {
        this.context = context;
        this.transactionInfoList = transactionInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_show_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CashTransaction transaction = transactionInfoList.get(position);

        holder.vName.setText(transaction.getId()+ "");
        holder.vSurname.setText(transaction.getUserid() + "");
        holder.vEmail.setText(transaction.getValue() + "");
        holder.vTitle.setText(transaction.getDescription() + " " + transaction.getType());
    }

    @Override
    public int getItemCount() {
        return transactionInfoList.size();
    }

    

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView vName;
        private TextView vSurname;
        private TextView vEmail;
        private TextView vTitle;

        private ViewHolder(View itemView) {
            super(itemView);

            vName = (TextView) itemView.findViewById(R.id.name_textview);
            vSurname = (TextView) itemView.findViewById(R.id.surname_textview);
            vEmail = (TextView) itemView.findViewById(R.id.email_textview);
            vTitle = (TextView) itemView.findViewById(R.id.title_textview);
        }
    }
}

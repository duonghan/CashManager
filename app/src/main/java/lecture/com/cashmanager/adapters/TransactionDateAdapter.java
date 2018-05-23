package lecture.com.cashmanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.model.CashTransaction;

public class TransactionDateAdapter extends ArrayAdapter<CashTransaction>{
    private Context context;
    private int resource;
    private List<CashTransaction> arrCashTransaction;

    public TransactionDateAdapter(@NonNull Context context, int resource, List<CashTransaction> arrCashTransaction) {
        super(context, resource, arrCashTransaction);

        this.context = context;
        this.resource = resource;
        this.arrCashTransaction = arrCashTransaction;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.transaction_row_item, parent, false);

            holder = new ViewHolder();
            holder.categoryName = convertView.findViewById(R.id.txt_category_name);
            holder.categoryValue = convertView.findViewById(R.id.txt_category_value);
            holder.categoryNote = convertView.findViewById(R.id.txt_category_note);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        CashTransaction cashTransaction = arrCashTransaction.get(position);
//        holder.categoryName.setText(new DBHelper(getContext()).getCategoryName(cashTransaction.getCategoryid()));
//        holder.categoryValue.setText(cashTransaction.getValue());
//        holder.categoryNote.setText(cashTransaction.getDescription());
//
//        if(cashTransaction.getType().equals("Income")){
//            holder.categoryValue.setTextColor(convertView.getResources().getColor(R.color.tb_tiffany_blue));
//        }else{
//            holder.categoryValue.setTextColor(convertView.getResources().getColor(R.color.red));
//        }

        return convertView;
    }

    class ViewHolder {
        TextView categoryName, categoryValue, categoryNote;
    }
}

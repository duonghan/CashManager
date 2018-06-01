package lecture.com.cashmanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.entity.CashInfo;
import lecture.com.cashmanager.model.CashTransaction;

import static android.support.constraint.Constraints.TAG;

public class TransactionDateAdapter extends ArrayAdapter<CashInfo>{
    private Context context;
    private int resource;
    private List<CashInfo> arrCashTransaction;

    public TransactionDateAdapter(@NonNull Context context, int resource, List<CashInfo> arrCashTransaction) {
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

        CashInfo cashInfo = arrCashTransaction.get(position);
        holder.categoryName.setText(cashInfo.getCategory());
        holder.categoryValue.setText(String.valueOf(cashInfo.getValue()));
        holder.categoryNote.setText(cashInfo.getDescription());

        if(cashInfo.getType() == 1){
            holder.categoryValue.setTextColor(convertView.getResources().getColor(R.color.tb_tiffany_blue));
        }else{
            holder.categoryValue.setTextColor(convertView.getResources().getColor(R.color.red));
        }

        return convertView;
    }

    class ViewHolder {
        TextView categoryName, categoryValue, categoryNote;
    }
}

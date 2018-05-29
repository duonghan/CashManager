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
import lecture.com.cashmanager.model.Category;

public class CategoryShowAdapter extends ArrayAdapter<Category> {
    private Context context;
    private int resource;
    private List<Category> arrCategory;

    public CategoryShowAdapter(@NonNull Context context, int resource, List<Category> arrCategory) {
        super(context, resource, arrCategory);
        this.context = context;
        this.resource = resource;
        this.arrCategory = arrCategory;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_custom_category, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.text_view_category_name);
            viewHolder.txtID = (TextView) convertView.findViewById(R.id.text_view_category_id);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Category category = arrCategory.get(position);
        viewHolder.txtName.setText(category.getName());
        viewHolder.txtID.setText(String.valueOf(category.getId()));

        return convertView;
    }

    public class ViewHolder {
        TextView txtName;
        TextView txtID;
    }
}

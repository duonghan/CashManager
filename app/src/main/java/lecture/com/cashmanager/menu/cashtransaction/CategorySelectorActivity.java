package lecture.com.cashmanager.menu.cashtransaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.adapters.CategoryShowAdapter;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.model.Category;

public class CategorySelectorActivity extends AppCompatActivity {

    DBHelper categoryDAO;
    List<Category> listCategory = new ArrayList<>();
    CategoryShowAdapter arrayAdapter;
    ListView listView;
    int type;

    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selector);

        listView = findViewById(R.id.lv_category_selector);
        categoryDAO = new DBHelper(this);
        categoryDAO.createDefaultCategory();

        Intent intent = this.getIntent();
        type = (Integer) intent.getSerializableExtra("categoryType");

        listCategory = categoryDAO.getAllCategoryByType(type);
        this.arrayAdapter = new CategoryShowAdapter(this, R.layout.list_view_custom_category, this.listCategory);

        this.listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category = (Category)parent.getItemAtPosition(position);

                Intent parentActivity = new Intent(getApplicationContext(), AddTransactionActivity.class);
                parentActivity.putExtra("categoryid", category.getId());
                parentActivity.putExtra("type", type);
                startActivity(parentActivity);
                finish();
            }
        });
    }

}

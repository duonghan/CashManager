package lecture.com.cashmanager.menu.category;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.widget.TextView;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.model.Category;

public class ShowCategoryActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtType;

    int categoryid;
    String categoryName;
    int categoryType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_category);

        txtName = (TextView)findViewById(R.id.txt_show_category_name);
        txtType = (TextView)findViewById(R.id.txt_show_category_type);

        Intent intent = getIntent();
        categoryid = (Integer) intent.getSerializableExtra("categoryid");
        DBHelper db = new DBHelper(this);

        //Load locale
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String lang = preferences.getString("lang_list","vi");
        Category category = db.getCategory(categoryid, lang);
        txtName.setText(category.getName());
        txtType.setText(category.getType() == 1 ? R.string.txt_Income : R.string.txt_Expense);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_category, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

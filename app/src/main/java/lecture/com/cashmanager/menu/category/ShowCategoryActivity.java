package lecture.com.cashmanager.menu.category;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.model.Category;

public class ShowCategoryActivity extends AppCompatActivity {

    public final int REQUEST_CODE = 111222;
    public final int INCOME = 1;
    public final int EXPENSE = -1;

    TextView txtName;
    TextView txtType;

    int categoryid;
    Category category;

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
        category = db.getCategory(categoryid, lang);
        txtName.setText(category.getName());
//        txtType.setText(category.getType() == 1 ? R.string.txt_Income : R.string.txt_Expense);
        if(category.getType() == INCOME){
            txtType.setText(R.string.txt_Income );
            txtType.setTextColor(getResources().getColor(R.color.green));
        }
        if(category.getType() == EXPENSE){
            txtType.setText(R.string.txt_Expense );
            txtType.setTextColor(getResources().getColor(R.color.red));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent intent = new Intent(this, ChangeCategoryActivity.class);
                intent.putExtra("category", category);
                intent.putExtra("type", category.getType());
                startActivity(intent);
        }

        if (id == R.id.action_delete) {
//             Ask before delete category
                new AlertDialog.Builder(this)
                        .setTitle(R.string.txt_menu_delete)
                        .setIcon(R.drawable.ic_delete)
                        .setMessage(getString(R.string.txt_ask_delete)+ "\n" + category.getName())
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteCategory(category);
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteCategory(Category selectedCategory) {
        DBHelper db = new DBHelper(this);
        db.deleteCategory(selectedCategory.getId(), true);
        Intent data = getIntent();
        Toast.makeText(this, R.string.txt_delete_success, Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, data);
        finish();
    }

}

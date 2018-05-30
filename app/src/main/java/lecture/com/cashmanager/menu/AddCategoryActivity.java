package lecture.com.cashmanager.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.model.Category;

public class AddCategoryActivity extends AppCompatActivity {

    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;
    private static final int INCOME = 1;
    private static final int EXPENSE = -1;

    private int mode;
    private Category category;
    private int type;
    private boolean needRefresh;

    private EditText txtContent;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        txtContent = findViewById(R.id.add_cate_name);
        btnSave = (Button)findViewById(R.id.btn_add_category_save) ;
        btnCancel = (Button)findViewById(R.id.btn_add_category_cancel) ;

        Intent intent = this.getIntent();
        type = (Integer)intent.getSerializableExtra("type");
        category = (Category)intent.getSerializableExtra("category");

        if(category == null){
            this.mode = MODE_CREATE;
        }else{
            this.mode = MODE_EDIT;
            txtContent.setText(category.getName());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(v.getContext());
                String name = txtContent.getText().toString();

                if(name.equals("")) {   // Validate edit text
                    Toast.makeText(getApplicationContext(),
                            R.string.txt_error_add_category, Toast.LENGTH_LONG).show();
                    return;
                }

                if(mode==MODE_CREATE ) {    // Create new category

                    if(type == INCOME){
                        category = new Category(name, INCOME);
                    }else{
                        category = new Category(name, EXPENSE);
                    }

                    db.addCategory(category);

                    Toast.makeText(getApplicationContext(),
                            R.string.txt_add_category_success, Toast.LENGTH_LONG).show();
                } else  {   // Update category
                    category.setName(name);
                    db.updateCategory(category);

                    Toast.makeText(getApplicationContext(),
                            R.string.txt_update_category_success, Toast.LENGTH_LONG).show();
                }

                needRefresh = true;
                // Back to Category activity
                onBackPressed();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void finish() {

        Intent data = new Intent();
        // Send request to refresh or not list view in Category Activity
        data.putExtra("needRefresh", needRefresh);

        // Activity is completed and return data
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}

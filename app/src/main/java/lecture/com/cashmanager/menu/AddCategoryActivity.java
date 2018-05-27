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

    Category category;
    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private int mode;
    private EditText txtContent;
    private Button btnSave;

    private boolean needRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        txtContent = findViewById(R.id.add_cate_name);
        Intent intent = this.getIntent();
        this.category = (Category)intent.getSerializableExtra("category");

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
                if(name.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter title & content", Toast.LENGTH_LONG).show();
                    return;
                }

                if(mode==MODE_CREATE ) {
                    category = new Category(name, 1);
                    db.addCategory(category);
                } else  {
                    category.setName(name);
                    db.updateCategory(category);
                }

                needRefresh = true;
                // Trở lại MainActivity.
                onBackPressed();
            }
        });
    }

    // Khi Activity này hoàn thành,
    // có thể cần gửi phản hồi gì đó về cho Activity đã gọi nó.
    @Override
    public void finish() {

        // Chuẩn bị dữ liệu Intent.
        Intent data = new Intent();
        // Yêu cầu MainActivity refresh lại ListView hoặc không.
        data.putExtra("needRefresh", needRefresh);

        // Activity đã hoàn thành OK, trả về dữ liệu.
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}

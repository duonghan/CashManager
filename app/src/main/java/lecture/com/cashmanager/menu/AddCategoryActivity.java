package lecture.com.cashmanager.menu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.model.Category;

public class AddCategoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText addCategory;
    RadioGroup rdoType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        addCategory = (EditText)findViewById(R.id.add_cate_name);
        rdoType = (RadioGroup)findViewById(R.id.rdo_type);

        toolbar = (Toolbar)findViewById(R.id.app_bar_add_category);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.title_add_category);
        bar.setDisplayHomeAsUpEnabled(true);

//        addItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_category, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Category [] lstCategory;
        DBHelper categoryDAO = new DBHelper(this);

        String [] lstName = {"Award", "Gifts", "InterestMoney", "Salary", "Selling",
                            "Others", };
        String [] lstExpense = {"Bills_and_Utilities", "Entertainment", "Food_and_Beverage",
                            "Business", "Gifts_and_Donations", "Health_and_Fitness", "Holidays",
                            "Family", "Pets", "Shopping", "Educations", "Friends_and_Lovers",
                            "Insurances", "Investment", "Transportation", "Travel", "Withdrawal"};

//        if (item.getItemId() == R.id.action_save){
//            int rdoChecked = rdoType.getCheckedRadioButtonId();
//
//            if(rdoChecked == R.id.rdo_income){
//                category.setType("Income");
//            }else
//                category.setType("Expense");
//
//            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
//        }
        
//        for(int i = 0 ; i< lstName.length; i++){
//            categoryDAO.addCategory(new Category(lstName[i], "Income"));
//        }
//
//        for(int i = 0 ; i< lstExpense.length; i++){
//            categoryDAO.addCategory(new Category(lstExpense[i], "Expense"));
//        }
//
//        List<String> listCategory =  categoryDAO.getAllStringCategory("Income");
//        listCategory.toArray();

        return super.onOptionsItemSelected(item);
    }

//    public void addItem(){
//        Category [] lstCategory;
//        DBHelper categoryDAO = new DBHelper(this);
//
//        String [] lstName = {"Award", "Gifts", "InterestMoney", "Salary", "Selling",
//                "Others", };
//        String [] lstExpense = {"Bills_and_Utilities", "Entertainment", "Food_and_Beverage",
//                "Business", "Gifts_and_Donations", "Health_and_Fitness", "Holidays",
//                "Family", "Pets", "Shopping", "Educations", "Friends_and_Lovers",
//                "Insurances", "Investment", "Transportation", "Travel", "Withdrawal"};
//
//        if (item.getItemId() == R.id.action_save){
//            int rdoChecked = rdoType.getCheckedRadioButtonId();
//
//            if(rdoChecked == R.id.rdo_income){
//                category.setType("Income");
//            }else
//                category.setType("Expense");
//
//            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
//        }
//
//        for(int i = 0 ; i< lstName.length; i++){
//            categoryDAO.addCategory(new Category(lstName[i], "Income"));
//        }
//
//        for(int i = 0 ; i< lstExpense.length; i++){
//            categoryDAO.addCategory(new Category(lstExpense[i], "Expense"));
//        }
//
//        List<String> listCategory =  categoryDAO.getAllStringCategory("Income");
//        listCategory.toArray();
//    }

}

package lecture.com.cashmanager.menu;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.menu.category.CategoryExpense;
import lecture.com.cashmanager.menu.category.CategoryIncome;

public class DebtActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt);


    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new CategoryIncome(), getString(R.string.txt_Income));
        adapter.addFragment(new CategoryExpense(), getString(R.string.txt_Expense));

        viewPager.setAdapter(adapter);
    }
}

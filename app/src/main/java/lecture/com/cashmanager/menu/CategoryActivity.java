package lecture.com.cashmanager.menu;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.menu.category.CategoryExpense;
import lecture.com.cashmanager.menu.category.CategoryIncome;

public class CategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_tab_income,
            R.drawable.ic_tab_expense,
            R.drawable.ic_tab_debt
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //Setup the ViewPager with the sections adapter
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.category);
        bar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new CategoryIncome(), getString(R.string.txt_Income));
        adapter.addFragment(new CategoryExpense(), getString(R.string.txt_Expense));

        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }
}

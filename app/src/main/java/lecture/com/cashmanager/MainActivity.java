package lecture.com.cashmanager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import lecture.com.cashmanager.menu.AboutFragment;
import lecture.com.cashmanager.menu.CategoryFragment;
import lecture.com.cashmanager.menu.DebtFragment;
import lecture.com.cashmanager.menu.ReportFragment;
import lecture.com.cashmanager.menu.SettingFragment;
import lecture.com.cashmanager.menu.TransasctionsFragment;
import lecture.com.cashmanager.model.Account;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Account account;
    TextView txtName;
    TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        //Set the fragment initially
        TransasctionsFragment mainfragment = new TransasctionsFragment();
        initFragment(mainfragment);

        displayHomePage();

        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("user");

        // Inflate the layout for this fragment
//        txtName = (TextView)findViewById(R.id.nav_header_name);
//        txtName = (TextView)findViewById(R.id.nav_header_email);
//        txtName.setText(account.getName());
//        txtEmail.setText(account.getEmail());

    }

    private void initFragment(Fragment fragment){
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content_main, fragment);
        transaction.commit();
    }

    private void displayHomePage() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_password) {
            return true;
        }

        if (id == R.id.action_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("username", account.getUsername());

            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.open_enter, R.anim.open_exit);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_transactions) {
            TransasctionsFragment mainfragment = new TransasctionsFragment();
            initFragment(mainfragment);
        } else if (id == R.id.nav_debts) {
            DebtFragment mainfragment = new DebtFragment();
            initFragment(mainfragment);
        } else if (id == R.id.nav_report) {
            ReportFragment mainfragment = new ReportFragment();
            initFragment(mainfragment);
        } else if (id == R.id.nav_categories) {
            CategoryFragment mainfragment = new CategoryFragment();
            initFragment(mainfragment);
        } else if (id == R.id.nav_about) {
            AboutFragment mainfragment = new AboutFragment();
            initFragment(mainfragment);
        } else if (id == R.id.nav_setting) {
//            SettingFragment mainfragment = new SettingFragment();
//            initFragment(mainfragment);

//            android.app.Fragment settingFragment = new SettingFragment();
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            if(savedInstanceState == null){
//                fragmentTransaction.add(R.id.)
//            }

            Intent setting = new Intent(this, SettingsActivity.class);
            startActivity(setting);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    //Load Language saved to shared preferences
    public void loadLocale(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String language = preferences.getString("lang_list","vi");
        setLocale(language);
    }

}

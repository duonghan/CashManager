package lecture.com.cashmanager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import lecture.com.cashmanager.authentication.LoginActivity;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.menu.AboutActivity;
import lecture.com.cashmanager.menu.CategoryActivity;
import lecture.com.cashmanager.menu.ReportActivity;
import lecture.com.cashmanager.menu.cashtransaction.TransasctionsFragment;
import lecture.com.cashmanager.model.Account;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TextView txtName;
    private TextView txtAmount;
    private int userid;
    private String username;
    private String name;
    private String email;
    private long amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        //Set the fragment initially
        TransasctionsFragment mainfragment = new TransasctionsFragment();
        initFragment(mainfragment);

        displayHomePage();
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

        // Inflate the layout for this fragment
        View headerView = navigationView.getHeaderView(0);
        txtName = (TextView)headerView.findViewById(R.id.nav_header_name);
        txtAmount = (TextView)headerView.findViewById(R.id.nav_header_amount);
        SharedPreferences preferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        userid = preferences.getInt("id", 1);
        username = preferences.getString("username", "");
        name = preferences.getString("name", "John Cena");
        email = preferences.getString("email", "example@gmail.com");
        amount = preferences.getLong("amount", 0);
        txtName.setText(name);
        txtAmount.setText(String.valueOf(amount));
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
            return showChangePassDialog();
        }

        if (id == R.id.action_logout) {
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.open_enter, R.anim.open_exit);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean showChangePassDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setIcon(R.drawable.ic_change_password);

        final EditText edtCurrentPass = (EditText) dialogView.findViewById(R.id.input_current_password);
        final EditText edtNewPass = (EditText) dialogView.findViewById(R.id.input_new_password);
        final EditText edtReNewPass = (EditText) dialogView.findViewById(R.id.input_re_new_password);
        final boolean[] isValid = {true};

        dialogBuilder.setTitle(R.string.change_pass);
        dialogBuilder.setPositiveButton(R.string.btn_save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String currentPassword = edtCurrentPass.getText().toString();
                String newPassword = edtNewPass.getText().toString();
                String renewPassword = edtReNewPass.getText().toString();

                // Validate
                if (newPassword.isEmpty() || newPassword.length() < 4 || newPassword.length() > 10) {
                    edtNewPass.setError(getString(R.string.txt_error_signup_password));
                    Toast.makeText(getApplicationContext(), R.string.txt_error_signup_password, Toast.LENGTH_LONG).show();
                    isValid[0] = false;
                } else {
                    edtNewPass.setError(null);
                }

                if (renewPassword.isEmpty() || renewPassword.length() < 4 || renewPassword.length() > 10 || !(renewPassword.equals(newPassword))) {
                    edtReNewPass.setError(getString(R.string.txt_error_signup_repass));
                    Toast.makeText(getApplicationContext(), R.string.txt_error_signup_repass, Toast.LENGTH_LONG).show();
                    isValid[0] = false;
                } else {
                    edtReNewPass.setError(null);
                }

                DBHelper db = new DBHelper(getApplicationContext());
                if(!db.checkAccount(username, currentPassword)){
                    edtCurrentPass.setError(getString(R.string.txt_error_login_noti));
                    Toast.makeText(getApplicationContext(), R.string.txt_error_change_pass, Toast.LENGTH_LONG).show();
                    isValid[0] = false;
                }else{

                    //Update password
                    Account account = new Account(userid, username,  newPassword, name, email);
                    db.updateAccount(account);
                    Toast.makeText(getApplicationContext(), R.string.txt_change_pass_success, Toast.LENGTH_LONG).show();
                }

            }
        });
        dialogBuilder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               onBackPressed();
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
        return isValid[0];
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_transactions) {
            TransasctionsFragment mainfragment = new TransasctionsFragment();
            initFragment(mainfragment);
//        } else if (id == R.id.nav_debts) {
//            Intent debt = new Intent(this, DebtActivity.class);
//            startActivity(debt);
        }else if (id == R.id.nav_report) {
            Intent report = new Intent(this, ReportActivity.class);
            startActivity(report);
        } else if (id == R.id.nav_categories) {
            Intent category = new Intent(this, CategoryActivity.class);
            startActivity(category);
        } else if (id == R.id.nav_about) {
            Intent about = new Intent(this, AboutActivity.class);
            startActivity(about);
        } else if (id == R.id.nav_setting) {
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

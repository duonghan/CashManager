package lecture.com.cashmanager.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import lecture.com.cashmanager.MainActivity;
import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.model.Account;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _usernameText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;

    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _usernameText = (EditText) findViewById(R.id.input_email);
        _passwordText   = (EditText) findViewById(R.id.input_password);
        _loginButton    = (Button) findViewById(R.id.btn_login);
        _signupLink     = (TextView)findViewById(R.id.link_signup);

        Intent intent = getIntent();
        if(intent.getStringExtra("username") != null){
            _usernameText.setText(intent.getStringExtra("username"));
            _passwordText.requestFocus();
        }


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage(getString(R.string.authenticating));
//        progressDialog.show();

        final SpotsDialog dialog = new SpotsDialog(this, R.style.AppTheme_Login_Dialog);
        dialog.show();

        final String username = _usernameText.getText().toString().trim();
        String password = _passwordText.getText().toString().trim();

        //authentication logic.
        final DBHelper accountDAO = new DBHelper(this);

        if(!accountDAO.checkAccount(username, password)){
            onLoginFailed();
            dialog.dismiss();
            return;
        }

        account = accountDAO.getAccount(username);

        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    // On complete call either onLoginSuccess or onLoginFailed
                    onLoginSuccess();
//                    onLoginFailed();
                    dialog.dismiss();
                }
            }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                String userName = data.getStringExtra("username");
                _usernameText.setText(userName);
                _passwordText.requestFocus();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(this, MainActivity.class);

        // Save data to share preference
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("username", account.getUsername());
        editor.putString("name", account.getName());
        editor.putString("email", account.getEmail());
        editor.putInt("id", account.getId());
        editor.apply();

        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.close_enter, R.anim.close_exit);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.login_failed), Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username  = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty() || username.length() < 6) {
            _usernameText.setError(getString(R.string.login_username));
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(getString(R.string.login_password));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }



}

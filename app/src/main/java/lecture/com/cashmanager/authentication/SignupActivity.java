package lecture.com.cashmanager.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import lecture.com.cashmanager.R;
import lecture.com.cashmanager.SHA1Hash;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.model.Account;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    String username;

    EditText _nameText;
    EditText _emailText;
    EditText _usernameText;
    EditText _passwordText;
    EditText _reEnterPasswordText;
    Button   _signupButton;
    TextView _loginLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Bind data to the layout activity_signup.xml
        _nameText = (EditText)findViewById(R.id.input_name);
        _emailText = (EditText)findViewById(R.id.input_email);
        _usernameText = (EditText)findViewById(R.id.input_username);
        _passwordText = (EditText)findViewById(R.id.input_password);
        _reEnterPasswordText = (EditText)findViewById(R.id.input_reEnterPassword);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void signup(){
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final SpotsDialog dialog = new SpotsDialog(this, R.style.AppTheme_Signup_Dialog);
        dialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        //Implement signup logic.
        Account acc = new Account(username, SHA1Hash.SHA1(password), name, email);
        DBHelper accountDAO = new DBHelper(this);
        accountDAO.addAccount(acc);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        dialog.dismiss();
                    }
                }, 2000);
    }

    private boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 10) {
            _nameText.setError(getString(R.string.txt_error_signup_name));
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError(getString(R.string.txt_error_signup_email));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (username.isEmpty() || username.length() < 6) {
            _usernameText.setError(getString(R.string.txt_error_signup_username));
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(getString(R.string.txt_error_signup_password));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError(getString(R.string.txt_error_signup_repass));
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    private void onSignupSuccess() {
        _signupButton.setEnabled(true);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("username", username);
        setResult(RESULT_OK, intent);
        startActivity(intent);
        finish();
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.txt_error_signup_noti), Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }
}

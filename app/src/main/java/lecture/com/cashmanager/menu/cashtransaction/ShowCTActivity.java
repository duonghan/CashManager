package lecture.com.cashmanager.menu.cashtransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.entity.CashInfo;
import lecture.com.cashmanager.model.CashTransaction;

public class ShowCTActivity extends AppCompatActivity {

    private int ctID;
    CashInfo cashInfo;
    CashTransaction cashTransaction;

    TextView txtAmount;
    TextView txtCategory;
    TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ct);

        txtAmount = (TextView)findViewById(R.id.txt_show_ct_amount);
        txtCategory = (TextView)findViewById(R.id.txt_show_ct_category);
        txtDate = (TextView)findViewById(R.id.txt_show_ct_date);

        Intent intent = getIntent();
        ctID = (Integer) intent.getSerializableExtra("ctID");
        DBHelper db = new DBHelper(this);
        cashInfo = db.getCashInfo(ctID);
        cashTransaction = db.getCT(ctID);

        txtAmount.setText(String.valueOf(cashInfo.getValue()));
        txtCategory.setText(cashInfo.getCategory());
        txtDate.setText(cashInfo.getDate());

        if(cashInfo.getType() == 1){
            txtAmount.setTextColor(getResources().getColor(R.color.green));
        }else{
            txtAmount.setTextColor(getResources().getColor(R.color.red));
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
            Intent intent = new Intent(this, ChangeTransactionActivity.class);
            intent.putExtra("ctID", cashInfo.getId());
            intent.putExtra("type", cashInfo.getType());
            startActivity(intent);
        }

        if (id == R.id.action_delete) {
//             Ask before delete category
            new AlertDialog.Builder(this)
                    .setTitle(R.string.txt_menu_delete)
                    .setIcon(R.drawable.ic_delete)
                    .setMessage(getString(R.string.txt_ask_delete))
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteCT(cashTransaction);
                        }
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteCT(CashTransaction selectedCT) {
        DBHelper db = new DBHelper(this);
        db.deleteCategory(selectedCT.getId(), true);
        Intent data = getIntent();
        Toast.makeText(this, R.string.txt_delete_success, Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, data);
        finish();
    }
}

package lecture.com.cashmanager.menu;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Project: CashManager
 * Created by DuongHV.
 * Copyright (c) 2018 - HUST.
 */
public class CustomSpinnerDialog extends android.support.v7.widget.AppCompatSpinner {

    public CustomSpinnerDialog(Context context) {
        super(context);
    }

    @Override
    public boolean performClick() {
        // Get current year, month and day.
        Calendar now = Calendar.getInstance();
        int year = now.get(java.util.Calendar.YEAR);
        int month = now.get(java.util.Calendar.MONTH);
        int day = now.get(java.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog dDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Toast.makeText(getContext(), "Something",
                                Toast.LENGTH_SHORT).show();

                    }
                }, year, month, day);
        dDialog.show();
        return false;
    }
}

package topsint.myfriendsfinder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by hp on 08/04/17.
 */

public class DateFormate extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    int y, m, d;
    Calendar calendar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreateDialog(savedInstanceState);
        calendar = Calendar.getInstance();

        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog date = new DatePickerDialog(getActivity(), this, y, m, d);
        date.getDatePicker().setMaxDate(System.currentTimeMillis());
        return date;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        EditText txtDate = (EditText) getActivity().getWindow().getDecorView()
                .getRootView().findViewById(R.id.txt_birthdate);

        year = datePicker.getYear();
        month = datePicker.getMonth();
        day = datePicker.getDayOfMonth();
        txtDate.setText(year +"-"+month + "-"+ day);
        /*txtDate.setText(new StringBuilder().append("").append(day)
                .append("/").append(month + 1).append("/").append(year));*/

    }
}

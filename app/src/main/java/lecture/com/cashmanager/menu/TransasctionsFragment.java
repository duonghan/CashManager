package lecture.com.cashmanager.menu;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import lecture.com.cashmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransasctionsFragment extends Fragment {

    private HorizontalCalendar horizontalCalendar;

    public TransasctionsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_transasctions, container, false);
//
//        /* start before 1 month from now */
//        Calendar startDate = Calendar.getInstance();
//        startDate.add(Calendar.MONTH, -1);
//
//        /* end after 1 month from now */
//        Calendar endDate = Calendar.getInstance();
//        endDate.add(Calendar.MONTH, 1);
//
//        horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
//                .range(startDate, endDate)
//                .datesNumberOnScreen(5)
//                .configure()
//                .formatTopText("MMM")
//                .formatMiddleText("dd")
//                .formatBottomText("EEE")
//                .textSize(14f, 24f, 14f)
//                .showTopText(true)
//                .showBottomText(true)
//                .textColor(Color.LTGRAY, Color.WHITE)
//                .end()
//                .build();
//
//        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
//            @Override
//            public void onDateSelected(Calendar date, int position) {
//                Toast.makeText(getContext(), DateFormat.format("EEE, MMM d, yyyy", date) + " is selected!", Toast.LENGTH_SHORT).show();
//            }
//
//        });
//        return rootView;
//    }

}

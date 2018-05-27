package lecture.com.cashmanager.menu.debt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lecture.com.cashmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DebtReceivableFragment extends Fragment {


    public DebtReceivableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debt_receivable, container, false);
    }

}

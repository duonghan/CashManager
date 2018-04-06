package lecture.com.cashmanager.menu;


import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lecture.com.cashmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private TabItem tabIncome;
    private TabItem tabExpense;
    private TabItem tabDebt;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_category, container, false);
    }

}

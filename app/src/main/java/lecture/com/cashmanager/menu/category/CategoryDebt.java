package lecture.com.cashmanager.menu.category;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lecture.com.cashmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDebt extends Fragment {


    public CategoryDebt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_debt, container, false);
    }

}

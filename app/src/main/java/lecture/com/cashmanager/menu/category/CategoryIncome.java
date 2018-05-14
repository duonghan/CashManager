package lecture.com.cashmanager.menu.category;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.dao.CategoryDAO;
import lecture.com.cashmanager.menu.AddCategoryActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryIncome extends Fragment {

    public final int ADD_INCOME = 119;
    public final int ADD_EXPENSE = 120;

    CategoryDAO categoryDAO;
//    List<String> listIncome = categoryDAO.getAllStringCategory("Income");
    List<String> listIncome = new ArrayList<String>();
    ListView listView;
    FloatingActionButton fab;

    public CategoryIncome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category_income, container, false);

        listView = (ListView) view.findViewById(android.R.id.list);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

//        System.out.println(listIncome);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listIncome);
//        listView.setAdapter(adapter);
//
//        fab.attachToListView(listView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCategory = new Intent(getActivity(), AddCategoryActivity.class);
                startActivityForResult(addCategory, ADD_INCOME);
            }
        });

        return view;
    }

}

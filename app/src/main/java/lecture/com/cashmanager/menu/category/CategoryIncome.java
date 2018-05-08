package lecture.com.cashmanager.menu.category;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.dao.CategoryDAO;
import lecture.com.cashmanager.model.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryIncome extends Fragment {


    List<String> listIncome = new ArrayList<>();
    CategoryDAO categoryDAO;

    public CategoryIncome() {
        // Required empty public constructor
//        listIncome = categoryDAO.getAllStringCategory("Income");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category_income, container, false);

//        ListView listView = (ListView) view.findViewById(android.R.id.list);
//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.attachToListView(listView);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listIncome);
//        listView.setAdapter(adapter);

        return view;
    }

}

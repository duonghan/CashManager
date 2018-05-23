package lecture.com.cashmanager.menu.category;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.menu.AddCategoryActivity;
import lecture.com.cashmanager.model.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryIncome extends Fragment {

    public final int ADD_INCOME = 111;

    DBHelper categoryDAO;
    List<Category> listIncome = new ArrayList<>();
    ArrayAdapter<Category> arrayAdapter;
    ListView listView;
    FloatingActionButton fab;

    public CategoryIncome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category_income, container, false);

        listView = (ListView) view.findViewById(R.id.lv_category_income);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_income_add);

        categoryDAO = new DBHelper(getContext());
        categoryDAO.createDefaultCategory();

        listIncome = categoryDAO.getAllCategoryByType(1);

        this.arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, this.listIncome);

        this.listView.setAdapter(arrayAdapter);
        registerForContextMenu(this.listView);


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

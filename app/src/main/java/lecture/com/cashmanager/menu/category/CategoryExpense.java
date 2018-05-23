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
import lecture.com.cashmanager.adapters.CategoryShowAdapter;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.menu.AddCategoryActivity;
import lecture.com.cashmanager.model.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryExpense extends Fragment {

    public final int ADD_EXPENSE = 222;
    DBHelper categoryDAO;
    List<Category> listExpense = new ArrayList<>();
    CategoryShowAdapter arrayAdapter;
    ListView listView;
    FloatingActionButton fab;

    public CategoryExpense() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_expense, container, false);
        listView = (ListView) view.findViewById(R.id.lv_category_expense);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_expense_add);

        categoryDAO = new DBHelper(getContext());
        categoryDAO.createDefaultCategory();
        listExpense = categoryDAO.getAllCategoryByType(-1);
        this.arrayAdapter = new CategoryShowAdapter(getActivity(), R.layout.list_view_custom_category, this.listExpense);

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
                startActivityForResult(addCategory, ADD_EXPENSE);
            }
        });

        return view;
    }

}

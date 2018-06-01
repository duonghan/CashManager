package lecture.com.cashmanager.menu.category;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.adapters.CategoryShowAdapter;
import lecture.com.cashmanager.db.DBHelper;
import lecture.com.cashmanager.model.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryExpenseFragment extends Fragment {

    public final int ADD_EXPENSE = 222;
    public final int EXPENSE = -1;

    DBHelper categoryDAO;
    List<Category> listExpense = new ArrayList<>();
    CategoryShowAdapter arrayAdapter;
    ListView listView;
    FloatingActionButton fab;

    public CategoryExpenseFragment() {
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

        // Load locale
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String lang = preferences.getString("lang_list","vi");
        listExpense = categoryDAO.getAllCategoryByType(EXPENSE, lang);
        arrayAdapter = new CategoryShowAdapter(getContext(), R.layout.list_view_custom_category, listExpense);

        listView.setAdapter(arrayAdapter);
        registerForContextMenu(listView);

        fab.attachToListView(listView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCategory = new Intent(getActivity(), ChangeCategoryActivity.class);
                addCategory.putExtra("type", EXPENSE);
                startActivityForResult(addCategory, ADD_EXPENSE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getItemAtPosition(position);
                Intent showCategory = new Intent(getActivity(), ShowCategoryActivity.class);
                showCategory.putExtra("categoryid", category.getId());
                startActivityForResult(showCategory, ADD_EXPENSE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_EXPENSE ) {
            boolean needRefresh = data.getBooleanExtra("needRefresh",true);

            // Refresh ListView
            if(needRefresh) {
                listExpense.clear();
                DBHelper db = new DBHelper(getContext());

                // Load locale
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String lang = preferences.getString("lang_list","vi");
                List<Category> list=  db.getAllCategoryByType(EXPENSE, lang);
                listExpense.addAll(list);

                // Notify about data change ( to refresh ListView).
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

}

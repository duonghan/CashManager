package lecture.com.cashmanager.menu.category;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
public class CategoryIncomeFragment extends Fragment {

    public final int ADD_INCOME = 111;
    public final int INCOME = 1;

    DBHelper categoryDAO;
    List<Category> listIncome = new ArrayList<>();
    CategoryShowAdapter arrayAdapter;
    ListView listView;
    FloatingActionButton fab;

    public CategoryIncomeFragment() {
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

        //Load locale
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String lang = preferences.getString("lang_list","vi");
        listIncome = categoryDAO.getAllCategoryByType(INCOME, lang);

        arrayAdapter = new CategoryShowAdapter(getContext(), R.layout.list_view_custom_category, listIncome);

        listView.setAdapter(arrayAdapter);
        registerForContextMenu(listView);

        fab.attachToListView(listView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCategory = new Intent(getActivity(), ChangeCategoryActivity.class);
                addCategory.putExtra("type", INCOME);
                startActivityForResult(addCategory, ADD_INCOME);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getItemAtPosition(position);
                Intent showCategory = new Intent(getActivity(), ShowCategoryActivity.class);
                showCategory.putExtra("categoryid", category.getId());
                startActivityForResult(showCategory, ADD_INCOME);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_INCOME ) {
            boolean needRefresh = data.getBooleanExtra("needRefresh",true);

            // Refresh ListView
            if(needRefresh) {
                listIncome.clear();
                DBHelper db = new DBHelper(getContext());

                // Load locale
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String lang = preferences.getString("lang_list","vi");
                List<Category> list=  db.getAllCategoryByType(INCOME, lang);
                listIncome.addAll(list);

                // Notify about data change ( to refresh ListView).
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
}

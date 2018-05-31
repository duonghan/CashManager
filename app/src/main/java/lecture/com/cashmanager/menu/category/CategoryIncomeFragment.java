package lecture.com.cashmanager.menu.category;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
public class CategoryIncomeFragment extends Fragment {

    private static final int EXPENSE = -1;
    public final int ADD_INCOME = 111;
    public final int ADD_EXPENSE = 222;
    public final int INCOME = 1;
    private static final int MENU_ITEM_VIEW = 110;
    private static final int MENU_ITEM_EDIT = 220;
    private static final int MENU_ITEM_CREATE = 330;
    private static final int MENU_ITEM_DELETE = 440;

    private static final int MY_REQUEST_CODE = 1000;

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
                Intent addCategory = new Intent(getActivity(), AddCategoryActivity.class);
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

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        if(v.getId() == R.id.lv_category_income){
//            menu.setHeaderTitle(getString(R.string.title_context_menu));
//            // groupId, itemId, order, title
//            menu.add(0, MENU_ITEM_VIEW , 0, getString(R.string.txt_menu_view));
//            menu.add(0, MENU_ITEM_EDIT , 1, getString(R.string.txt_menu_edit));
//            menu.add(0, MENU_ITEM_DELETE, 2, getString(R.string.txt_menu_delete));
//        }
//
//        if(v.getId() == R.id.lv_category_expense){
//            menu.setHeaderTitle(getString(R.string.title_context_menu));
//            // groupId, itemId, order, title
//            menu.add(1, MENU_ITEM_VIEW , 0, getString(R.string.txt_menu_view));
//            menu.add(1, MENU_ITEM_EDIT , 1, getString(R.string.txt_menu_edit));
//            menu.add(1, MENU_ITEM_DELETE, 2, getString(R.string.txt_menu_delete));
//        }
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo
//                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//
//        final Category selectedCategory = (Category) listView.getItemAtPosition(info.position);
//
//        if(item.getGroupId() == 0){
//            if(item.getItemId() == MENU_ITEM_VIEW){
//                new AlertDialog.Builder(getContext())
//                        .setTitle(R.string.txt_menu_view)
//                        .setIcon(R.drawable.ic_view)
//                        .setMessage(selectedCategory.getName())
//                        .show();
//            }
//            else if(item.getItemId() == MENU_ITEM_EDIT ){
//                Intent intent = new Intent(getContext(), AddCategoryActivity.class);
//                intent.putExtra("category", selectedCategory);
//                intent.putExtra("type", INCOME);
//                startActivityForResult(intent,ADD_INCOME);
//            }
//            else if(item.getItemId() == MENU_ITEM_DELETE){
//                // Ask before delete category
//                new AlertDialog.Builder(getContext())
//                        .setTitle(R.string.txt_menu_delete)
//                        .setIcon(R.drawable.ic_delete)
//                        .setMessage(getString(R.string.txt_ask_delete)+ "\n" + selectedCategory.getName())
//                        .setCancelable(false)
//                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                deleteCategory(selectedCategory);
//                            }
//                        })
//                        .setNegativeButton(R.string.no, null)
//                        .show();
//            }
//            else {
//                return false;
//            }
//        }
//
//        if(item.getGroupId() == 1){
//            if(item.getItemId() == MENU_ITEM_VIEW){
//                new AlertDialog.Builder(getContext())
//                        .setTitle(R.string.txt_menu_view)
//                        .setIcon(R.drawable.ic_view)
//                        .setMessage(selectedCategory.getName())
//                        .show();
//            }
//            else if(item.getItemId() == MENU_ITEM_EDIT ){
//                Intent intent = new Intent(getContext(), AddCategoryActivity.class);
//                intent.putExtra("category", selectedCategory);
//                intent.putExtra("type", EXPENSE);
//                startActivityForResult(intent,ADD_EXPENSE);
//            }
//            else if(item.getItemId() == MENU_ITEM_DELETE){
//                // Ask before delete category
//                new AlertDialog.Builder(getContext())
//                        .setTitle(R.string.txt_menu_delete)
//                        .setIcon(R.drawable.ic_delete)
//                        .setMessage(getString(R.string.txt_ask_delete)+"\n" + selectedCategory.getName())
//                        .setCancelable(false)
//                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                deleteCategory(selectedCategory);
//                            }
//                        })
//                        .setNegativeButton(R.string.no, null)
//                        .show();
//            }
//            else {
//                return false;
//            }
//        }
//
//
//        return true;
//    }
//
//    private void deleteCategory(Category selectedCategory) {
//        DBHelper db = new DBHelper(getContext());
//        db.deleteCategory(selectedCategory.getId(), true);
//        listIncome.remove(selectedCategory);
//
//        // Refresh ListView.
//        arrayAdapter.notifyDataSetChanged();
//    }

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

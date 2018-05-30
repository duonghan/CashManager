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
public class CategoryIncome extends Fragment {

    public final int ADD_INCOME = 111;
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

        //Load locale
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String lang = preferences.getString("lang_list","vi");
        listIncome = categoryDAO.getAllCategoryByType(INCOME, lang);

        this.arrayAdapter = new CategoryShowAdapter(getActivity(), R.layout.list_view_custom_category, this.listIncome);

        this.listView.setAdapter(arrayAdapter);
        registerForContextMenu(this.listView);


//        System.out.println(listIncome);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listIncome);
//        listView.setAdapter(adapter);
//
        fab.attachToListView(listView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCategory = new Intent(getActivity(), AddCategoryActivity.class);
                startActivityForResult(addCategory, ADD_INCOME);
            }
        });

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(getString(R.string.title_context_menu));

        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_VIEW , 0, getString(R.string.txt_menu_view));
        menu.add(0, MENU_ITEM_EDIT , 1, getString(R.string.txt_menu_edit));
        menu.add(0, MENU_ITEM_DELETE, 2, getString(R.string.txt_menu_delete));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Category selectedCategory = (Category) this.listView.getItemAtPosition(info.position);

        if(item.getItemId() == MENU_ITEM_VIEW){
            Toast.makeText(getContext(),selectedCategory.getName(),Toast.LENGTH_LONG).show();
        }
//        else if(item.getItemId() == MENU_ITEM_CREATE){
//            Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
//
//            // Start AddEditNoteActivity, có phản hồi.
//            this.startActivityForResult(intent, MY_REQUEST_CODE);
//        }
        else if(item.getItemId() == MENU_ITEM_EDIT ){
            Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
            intent.putExtra("category", selectedCategory);

            // Start AddCategoryActivity, có phản hồi.
            this.startActivityForResult(intent,MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_DELETE){
            // Hỏi trước khi xóa.
            new AlertDialog.Builder(getContext())
                    .setMessage(selectedCategory.getName()+". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteCategory(selectedCategory);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }

    private void deleteCategory(Category selectedCategory) {
        DBHelper db = new DBHelper(getContext());
        db.deleteCategory(selectedCategory.getId(), true);
        this.listIncome.remove(selectedCategory);
        // Refresh ListView.
        this.arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE ) {
            boolean needRefresh = data.getBooleanExtra("needRefresh",true);
            // Refresh ListView
            if(needRefresh) {
                this.listIncome.clear();
                DBHelper db = new DBHelper(getContext());

                //Load locale
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String lang = preferences.getString("lang_list","vi");
                List<Category> list=  db.getAllCategoryByType(INCOME, lang);
                this.listIncome.addAll(list);
                // Thông báo dữ liệu thay đổi (Để refresh ListView).
                this.arrayAdapter.notifyDataSetChanged();
            }
        }
    }
}

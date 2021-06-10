package com.example.finalpj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalpj.adapter.FmPagerAdapter;
import com.example.finalpj.adapter.RecordsAdapter;
import com.example.finalpj.entity.Event;
import com.example.finalpj.fragment.FutureFragment;
import com.example.finalpj.fragment.PastFragment;
import com.example.finalpj.utils.DBUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] titles = new String[]{"过去", "未来"};
    private List<Fragment> fragments;
    private PagerAdapter pagerAdapter;
    private Button addEventButton;
    private EditText searchEditText;
    private PastFragment pastFragment;
    private FutureFragment futureFragment;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        List<Event> events = DBUtil.selectAllEvents();
        pastFragment.initEvents(events);
        futureFragment.initEvents(events);
    }

    private void initComponent() {
        context = MainActivity.this;
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        addEventButton = findViewById(R.id.button_add);
        searchEditText = findViewById(R.id.search_edit_text);
        futureFragment = new FutureFragment(context);
        pastFragment = new PastFragment(context);
        fragments = new ArrayList<>();
        fragments.add(pastFragment);
        fragments.add(futureFragment);
        pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        for (int i = 0; i < titles.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setupWithViewPager(viewPager, false);
        addEventButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            startActivity(intent);
        });
        searchEditText.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String text = textView.getText().toString();
                List<Event> events = DBUtil.selectEventFuzzily(text);
                pastFragment.initEvents(events);
                futureFragment.initEvents(events);
                closeSoftKeyBoard(searchEditText, context);
                return true;
            }
            return false;
        });
        for (int i = 0; i < titles.length; i++) {
            tabLayout.getTabAt(i).setText(titles[i]);
        }
    }

    public void flushPage() {
        fragments.clear();
        fragments.add(new PastFragment(context));
        fragments.add(new FutureFragment(context));
        pagerAdapter.notifyDataSetChanged();
        /*pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);*/
        Log.i("flushPage", "触发 flushPage");
    }

    private void closeSoftKeyBoard(EditText searchEditText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }
}

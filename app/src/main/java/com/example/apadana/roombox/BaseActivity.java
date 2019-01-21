package com.example.apadana.roombox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    //    @BindView(R.id.container)
//    ConstraintTableLayout container;
    private TextView mTextMessage;
    public static final String FRAGMENT_VIEWPAGER = "FRAGMENT_VIEWPAGER";
    public static final String FRAGMENT_FIRST = "FRAGMENT_FIRST";
    public static final String FRAGMENT_SECOND = "FRAGMENT_SECOND";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home = new Intent(getBaseContext(),HomeActivity.class);
                    startActivity(home);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_dashboard:
                    replaceFragment(SecondFragment.newInstance(),FRAGMENT_SECOND);
                    return true;
                case R.id.navigation_notifications:
                    replaceFragment(ViewPagerFragment.newInstance(),FRAGMENT_VIEWPAGER);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        ButterKnife.bind(this);

        mTextMessage = (TextView) findViewById(R.id.message);

//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.dynamicContent, FirstFragment.newInstance(), FRAGMENT_FIRST)
//                .commit();
    }

    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.dynamicContent, newFragment, tag)
                .commit();

    }

}

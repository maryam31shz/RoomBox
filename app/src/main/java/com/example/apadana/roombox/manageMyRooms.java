package com.example.apadana.roombox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class manageMyRooms extends BaseActivity {
    private FrameLayout dynamicContent;
    private View wizard;
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        setRooms();

    }

    private void setRooms() {
        String[] courses = {"My Room 1 ","My Room 2" , "*Inset New Rooms*"};
        ArrayAdapter <String> crsAdapter = new ArrayAdapter<String>(this,R.layout.show_myrooms
                , courses);
        listView.setAdapter(crsAdapter);
    }

    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.manage_myrooms, null);
        dynamicContent.addView(wizard);
        listView = (ListView) findViewById(R.id.listView);
    }
}

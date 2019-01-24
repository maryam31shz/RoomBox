package com.example.apadana.roombox;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private FrameLayout dynamicContent;
    private View wizard;
    private CardView myRooms;
    private CardView myapartments;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        myRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startManageMyRoomsActivity();
            }
        });

        myapartments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMangeMyApartmentsActivity();
            }
        });
    }

    private void startMangeMyApartmentsActivity() {
        Intent myApartments = new Intent(getBaseContext(),ManageMyApartments.class);
        startActivity(myApartments);
    }

    private void startManageMyRoomsActivity() {
        Intent myRooms = new Intent(getBaseContext(),manageMyRooms.class);
        startActivity(myRooms);
    }

    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.fragment_first, null);
        dynamicContent.addView(wizard);
        myRooms = (CardView) findViewById(R.id.myRooms);
        myapartments = (CardView) findViewById(R.id.myApartments);
    }
}

package com.example.apadana.roombox;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;

public class ManageSingleApartment extends BaseActivity {
    private Button manageRoomApt;
    private GridView gridView;
    private FrameLayout dynamicContent;
    private View wizard;
    private Button transactinBtn;
    private Button notificationsBtn;
    private Button costsBtn;
    private int aptId;
    private Button setChargeForAllBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        this.setTitle( getThisActivityTitle(getIntent()) );
        setNotifications();
        manageRoomApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startManageRoomAptActivity(v);
            }
        });
        setChargeForAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSetChargeForAllActivity(v);
            }
        });

    }
    public void startSetChargeForAllActivity(View v){
        Intent setChForAll = new Intent(getBaseContext(),setChargeForAllActivity.class);
        startActivity(setChForAll);
        overridePendingTransition(0, 0);
    }
    private void setNotifications() {
        String[] courses = {"Notification1","Notification2"};
        ArrayAdapter<String> crsAdapter = new ArrayAdapter<String>(this,R.layout.show_myrooms
                , courses);
        gridView.setAdapter(crsAdapter);

    }

    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.manage_singleapartment, null);
        dynamicContent.addView(wizard);
        manageRoomApt = (Button) findViewById(R.id.manageRoomApt);
        gridView = (GridView) findViewById(R.id.gridView);
        transactinBtn = (Button) findViewById(R.id.seeTransactionsBtn);
        notificationsBtn = (Button) findViewById(R.id.notificationBtn);
        costsBtn = (Button) findViewById(R.id.costsBtn);
        setChargeForAllBtn = (Button) findViewById(R.id.setChargeForAllBtn);
    }

    public void startManageRoomAptActivity(View v){
        Intent roomApts = new Intent(getBaseContext(),ManageRoomApts.class);
        Bundle Bundle = new Bundle();
        Bundle.putInt("aptId" , aptId);
        roomApts.putExtras(Bundle);

        startActivity(roomApts);
        overridePendingTransition(0, 0);
    }
    public String getThisActivityTitle(Intent apt){
        Bundle aptBundle = apt.getExtras();
        aptId = aptBundle.getInt("aptId");
        Cursor cursor = mDb.rawQuery("Select * " +
                        "from apartment " +
                        "where Id = ?",
                new String[]{Integer.toString(aptId)});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("Name"));


    }
}

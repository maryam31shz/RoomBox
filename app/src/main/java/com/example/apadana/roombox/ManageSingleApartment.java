package com.example.apadana.roombox;

import android.content.Intent;
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

    }

    public void startManageRoomAptActivity(View v){
        Intent roomApts = new Intent(getBaseContext(),ManageRoomApts.class);
        startActivity(roomApts);
        overridePendingTransition(0, 0);
    }
    public String getThisActivityTitle(Intent apt){
        Bundle aptBundle = apt.getExtras();
        String[] array = (String[]) aptBundle.getSerializable("array");
        int pos = aptBundle.getInt("aptPosition");
        return array[pos];


    }
}

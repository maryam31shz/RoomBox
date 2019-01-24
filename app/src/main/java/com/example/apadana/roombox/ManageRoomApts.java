package com.example.apadana.roombox;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class ManageRoomApts extends BaseActivity {
    private FrameLayout dynamicContent;
    private View wizard;
    private String[] rooms;
    private ListView roomView;
    private int[] roomIds;
    private int aptId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        giveAptId();
        setRooms();

        roomView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startManageSingleRoomAdminActivity(view , roomIds[position] );

            }
        });


    }

    private void giveAptId() {
        Bundle aptBundle = getIntent().getExtras();
        aptId = aptBundle.getInt("aptId");
    }

    private void startManageSingleRoomAdminActivity(View view,int id) {

        Intent roomMng = new Intent(view.getContext() , ManageSingleRoomAdmin.class );
        Bundle Bundle = new Bundle();
        Bundle.putInt("roomId" , id);
        Bundle.putInt("aptId" , aptId);
        roomMng.putExtras(Bundle);
        startActivity(roomMng);
        overridePendingTransition(0, 0);
    }

    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.manage_roomapts, null);
        dynamicContent.addView(wizard);
        roomView = (ListView) findViewById(R.id.listView);

    }
    private void setRooms() {
        Cursor cursor = mDb.rawQuery("Select * " +
                        "from room " +
                        "where  ApartmentId = ?",
                new String[]{Integer.toString(aptId)});

        rooms = new String[]{""};

        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();

            rooms = new String[cursor.getCount()];
            roomIds = new int[cursor.getCount()];
            int i = 0;
            do{
                roomIds[i] = cursor.getInt(cursor.getColumnIndex("Id"));
                String rent =
                        (cursor.getInt(cursor.getColumnIndex("sendForRent")) == 1)? "send": "Not send";
                rooms[i++] =  "Room Number : " + cursor.getInt(cursor.getColumnIndex("RoomNumber"))
                        +"\nSend for rent status : " + rent;
            }
            while(cursor.moveToNext());

        }

        ArrayAdapter <String> crsAdapter = new ArrayAdapter<String>(this,R.layout.show_myrooms
                , rooms);
        roomView.setAdapter(crsAdapter);
    }

}

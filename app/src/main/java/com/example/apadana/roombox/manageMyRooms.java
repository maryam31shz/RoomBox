package com.example.apadana.roombox;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class manageMyRooms extends BaseActivity {
    private FrameLayout dynamicContent;
    private View wizard;
    private ListView listView;
    private FloatingActionButton fab;
    private String[] rooms;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        setRooms();
        setFloatingActionBtn();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startManageSingleRoomActivity(view , position , id , rooms);
            }
        });
    }
    private void setRooms() {

        Cursor cursor = mDb.rawQuery("Select RoomNumber,NumberOfBedrooms " +
                        "from user,room,Lease " +
                        "where user.ID=? and user.ID = Lease.Id and room.Id = Lease.Id ",
                new String[]{Integer.toString(user.getId())});
        Toast.makeText(getBaseContext() , Integer.toString(user.getId()) , Toast.LENGTH_LONG).show();
        rooms = new String[]{};
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();

            rooms = new String[cursor.getCount()];
            int i = 0;
            do{

               rooms[i++] =  "Room Number " +
                       cursor.getInt(cursor.getColumnIndex("RoomNumber"))
                           + "\t" + "Number Of Bedrooms = "
                           + cursor.getInt(cursor.getColumnIndex("NumberOfBedrooms"));
            }
            while(cursor.moveToNext());

        }

        ArrayAdapter <String> crsAdapter = new ArrayAdapter<String>(this,R.layout.show_myrooms
                , rooms);
        listView.setAdapter(crsAdapter);
    }

    public void startInsertRoomActivity(View view){
        Intent roomMng = new Intent(view.getContext() , InsertNewRoom.class );
        startActivity(roomMng);
        overridePendingTransition(0, 0);
    }

    private void setFloatingActionBtn() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInsertRoomActivity(view);
            }
        });
    }

    public void startManageSingleRoomActivity(View view , int position , long id , String[] apartments){
        Bundle roomMngBundle = new Bundle();
        Intent roomMng = new Intent(view.getContext() , ManageSingleRoom.class );
        roomMngBundle.putInt("roomPosition" , position);
        roomMngBundle.putLong("roomId" , id);
        roomMngBundle.putSerializable("array" , rooms);
        roomMng.putExtras(roomMngBundle);
        startActivity(roomMng);
        overridePendingTransition(0, 0);
    }

    private void setView() {

        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.manage_myrooms, null);
        dynamicContent.addView(wizard);
        listView = (ListView) findViewById(R.id.listView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }
}

package com.example.apadana.roombox;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

public class InsertNewRoom extends BaseActivity{
    private FrameLayout dynamicContent;
    private View wizard;
    private Button insertCodeRoom;
    private String[] rooms;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setView();
        setRooms();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startSeeSingleRoomActivity(view , position , id , rooms);
            }
        });

    }

    private void startSeeSingleRoomActivity(View view, int position, long id, String[] rooms) {
    }

    private void setRooms() {

        Cursor cursor = mDb.rawQuery("Select * " +
                        "from room,apartment " +
                        "where room.sendForRent = 1  and room.ApartmentId = apartment.Id ",
                null);

        rooms = new String[]{""};
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            rooms = new String[cursor.getCount()];
            int i = 0;
            do {

                rooms[i++] = "Apartment Name : " + cursor.getString(cursor.getColumnIndex("Name"))
                + "\nAdress : " + cursor.getString(cursor.getColumnIndex("Adress"))
                        +"\nDeposit : " + cursor.getInt(cursor.getColumnIndex("Deposit"))
                        +"\nRent Fee :  " + cursor.getInt(cursor.getColumnIndex("RentalPrice"))
                        +"\nRoom Number : " + cursor.getInt(cursor.getColumnIndex("RoomNumber"))
                        +"\nNumber Of Bedrooms : " + cursor.getInt(cursor.getColumnIndex("NumberOfBedrooms"));
            }
            while (cursor.moveToNext());

        }

        ArrayAdapter<String> crsAdapter = new ArrayAdapter<String>(this, R.layout.show_myrooms
                , rooms);
        listView.setAdapter(crsAdapter);
    }
    private void setView() {

        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.insert_newroom_tenant, null);
        dynamicContent.addView(wizard);
        insertCodeRoom = (Button)findViewById(R.id.insertRoomManuallyBtn);
        listView = (ListView) findViewById(R.id.listView);


    }
}

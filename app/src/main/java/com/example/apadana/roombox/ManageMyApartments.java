package com.example.apadana.roombox;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ManageMyApartments extends BaseActivity {
    private FrameLayout dynamicContent;
    private View wizard;
    private ListView listView;
    private String[] apartments;
    private FloatingActionButton fab;
    private int[] aptIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();

        setApartments();
        setFloatingActionBtn();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startManageSingleAptActivity(view , aptIds[position] );

                }
            });
        }


    private void setApartments() {
        Cursor cursor = mDb.rawQuery("Select * " +
                        "from apartment " +
                        "where ManagerId = ?",
                new String[]{Integer.toString(user.getId())});
        //Toast.makeText(getBaseContext() , Integer.toString(user.getId()) , Toast.LENGTH_LONG).show();
        apartments = new String[]{};

        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();

            apartments = new String[cursor.getCount()];
            aptIds = new int[cursor.getCount()];
            int i = 0;
            do{
                aptIds[i] = cursor.getInt(cursor.getColumnIndex("Id"));

                apartments[i++] =  "Apartment Name : " + cursor.getString(cursor.getColumnIndex("Name"))
                        +"\nNumber Of Rooms : " + cursor.getInt(cursor.getColumnIndex("NumberOfRooms"));
            }
            while(cursor.moveToNext());

        }

        ArrayAdapter <String> crsAdapter = new ArrayAdapter<String>(this,R.layout.show_myrooms
                , apartments);
        listView.setAdapter(crsAdapter);
    }

    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.manage_myapartments, null);
        dynamicContent.addView(wizard);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.listView);

    }


    public void startInsertAptActivity(View view){
        Intent aptMng = new Intent(view.getContext() , InsertNewApartment.class );
        startActivity(aptMng);
        overridePendingTransition(0, 0);
    }
    private void setFloatingActionBtn() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInsertAptActivity(view);
            }
        });
    }

    public void startManageSingleAptActivity(View view , int id ){
        Bundle aptMngBundle = new Bundle();
        Intent aptMng = new Intent(view.getContext() , ManageSingleApartment.class );
                    //aptMngBundle.putInt("aptPosition" , position);
                    aptMngBundle.putInt("aptId" , id);
                    //aptMngBundle.putSerializable("array" , apartments);
                    aptMng.putExtras(aptMngBundle);
                    startActivity(aptMng);
                    overridePendingTransition(0, 0);
    }
}

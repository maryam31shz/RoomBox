package com.example.apadana.roombox;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

public class InsertNewApartment extends BaseActivity {
    private FrameLayout dynamicContent;
    private Button saveBtn;
    private View wizard;
    private EditText aptName;
    private EditText numberofRooms;
    private EditText aptAdress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    ContentValues values = new ContentValues();
                    values.put("Name", aptName.getText().toString());
                    values.put("ManagerId", user.getId());
                    values.put("Adress", aptAdress.getText().toString());
                    values.put("NumberOfRooms", Integer.valueOf(numberofRooms.getText().toString()));
                    long aptId = mDb.insertOrThrow(  "apartment",null,values);

                    for(int i = 0 ; i < (Integer.valueOf(numberofRooms.getText().toString())) ; i++){
                        ContentValues roomValues = new ContentValues();
                        Toast.makeText(getBaseContext(),
                                "Can't insert " + (int)aptId , Toast.LENGTH_LONG).show();
                        roomValues.put("ApartmentId", (int)aptId);
                        roomValues.put("RoomNumber", i+1);
                        roomValues.put("NumberOfBedrooms", 0);
                        roomValues.put("sendForRent", 0);
                        mDb.insertOrThrow("room" , null , roomValues);

                    }

                    startManageMyApartmentsActivity();
                }
                catch (SQLException mSQLException){
                    Toast.makeText(getBaseContext(),
                            "Can't insert " + mSQLException , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.insert_newapartment, null);
        dynamicContent.addView(wizard);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        aptAdress = (EditText) findViewById(R.id.adressEdittext);
        aptName = (EditText) findViewById(R.id.apartmenttNameEdittext);
        numberofRooms = (EditText) findViewById(R.id.roomsNumEdittext);

    }
    public void startManageMyApartmentsActivity (){
        Intent myApartments = new Intent(getBaseContext(),ManageMyApartments.class);
        startActivity(myApartments);
        overridePendingTransition(0, 0);
    }
}

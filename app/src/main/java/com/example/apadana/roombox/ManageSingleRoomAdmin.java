package com.example.apadana.roombox;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ManageSingleRoomAdmin extends BaseActivity{
    private FrameLayout dynamicContent;
    private View wizard;
    private TextView roomNumber;
    private TextView tenantId;
    private TextView remainingCharge;
    private EditText setChargeManually;
    private EditText numberOfBedrooms;
    private EditText deposit;
    private EditText rentFee;
    private CheckBox sendForRentChB;
    private Button saveBtn;
    private Button setTenantManuallyBtn;
    private Button terminateRentBtn;
    private Button cancelBtn;
    private int roomId;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        getRoomId();
        setTexts();
        if(tenantId.getText().toString() != "") {
            if (!sendForRentChB.isChecked()) {
                sendForRentChB.setClickable(false);
                setTenantManuallyBtn.setClickable(true);
                terminateRentBtn.setClickable(true);
            }
        }



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        terminateRentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setTenantManuallyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    private void save() {
        int chargeId;
        ContentValues chargeV = new ContentValues();

        if(setChargeManually.getText().toString() != "") {
            chargeV.put("Amount" , setChargeManually.getText().toString());
            chargeV.put("Date",getCurrDate());
            chargeV.put("RoomId",roomId);

        }
        ContentValues roomV = new ContentValues();
        roomV.put("sendForRent" ,
                sendForRentChB.isChecked()?1:0
        );
        roomV.put("NumberOfBedrooms" , numberOfBedrooms.getText().toString());
        roomV.put("Deposit",deposit.getText().toString());
        roomV.put("RentalPrice",rentFee.getText().toString());
        try {
            chargeId = (int)mDb.insert("charge",null,chargeV);
            mDb.update("room",roomV,"Id = ? " , new String[]{Integer.toString(roomId)});
        }
        catch (SQLException mSQLException){
            Toast.makeText(getBaseContext() , "cant Update " + mSQLException , Toast.LENGTH_LONG).show();;

        }
        startManageRoomAptsActivity();

    }

    private void startManageRoomAptsActivity() {
        Intent roomApts = new Intent(getBaseContext(),ManageRoomApts.class);
        Bundle Bundle = getIntent().getExtras();
        roomApts.putExtras(Bundle);
        startActivity(roomApts);
        overridePendingTransition(0, 0);
    }

    private String getCurrDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(c.getTime());

        return date;
    }

    private void setTexts() {
        Cursor cursor = mDb.rawQuery("Select * " +
                        "from Lease " +
                        "where RoomID = ?",
                new String[]{Integer.toString(roomId)});
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            tenantId.setText(
                    Integer.toString(

                            cursor.getInt(cursor.getColumnIndex("TenantId"))
                    )
            );
        }
        cursor = mDb.rawQuery("Select * " +
                        "from room " +
                        "where Id = ?",
                new String[]{Integer.toString(roomId)});
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            roomNumber.setText(
                    Integer.toString(

                            cursor.getInt(cursor.getColumnIndex("RoomNumber"))
                    )
            );
            sendForRentChB.setChecked(
                    (cursor.getInt(cursor.getColumnIndex("sendForRent")) == 1)? true:false

            );

            numberOfBedrooms.setText(
                    Integer.toString(
                            cursor.getInt(cursor.getColumnIndex("NumberOfBedrooms"))
                    )
            );

            rentFee.setText(
                    Integer.toString(
                            cursor.getInt(cursor.getColumnIndex("RentalPrice"))
                    )

            );
            deposit.setText(
                    Integer.toString(
                            cursor.getInt(cursor.getColumnIndex("Deposit"))
                    )

            );

        }
    }

    private void getRoomId() {

        Bundle aptBundle = getIntent().getExtras();
        roomId = aptBundle.getInt("roomId");
    }

    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.manage_singleroomadmin, null);
        dynamicContent.addView(wizard);

        roomNumber = (TextView) findViewById(R.id.roomNumber);
        tenantId = (TextView) findViewById(R.id.tenantId);
        remainingCharge = (TextView) findViewById(R.id.remainingCharge);
        setChargeManually = (EditText) findViewById(R.id.setChargeManually);
        numberOfBedrooms = (EditText) findViewById(R.id.numberOfBedrooms);
        deposit = (EditText) findViewById(R.id.deposit);

        rentFee = (EditText) findViewById(R.id.rentFee);

        sendForRentChB = (CheckBox) findViewById(R.id.goforrentCheckBox);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        setTenantManuallyBtn = (Button) findViewById(R.id.setTenantManuallyBtn);
        terminateRentBtn = (Button) findViewById(R.id.terminateRentBtn);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);



    }
}

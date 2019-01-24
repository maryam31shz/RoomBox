package com.example.apadana.roombox;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class logIn extends AppCompatActivity {
    private EditText email;
    private EditText password ;
    private Button loginBtn;
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    private static int id;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setView();
        setDatabase();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUser(email.getText().toString() , password.getText().toString())){

                    startHomeActivity();
                }
            }
        });

    }

    private void setView() {
        email = (EditText) findViewById(R.id.usernameEditText) ;
        password = (EditText) findViewById(R.id.passEditText) ;
        loginBtn = (Button) findViewById(R.id.loginBtn)  ;
    }

    private void setDatabase() {

        mDBHelper = new com.example.apadana.roombox.DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDBHelper.openDataBase();
            mDb = mDBHelper.getWritableDatabase();
            mDb.enableWriteAheadLogging();
            mDb.execSQL(
                    "CREATE TABLE IF NOT EXISTS `user` (\n" +
                    "\t`ID`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t`Name`\tTEXT NOT NULL,\n" +
                    "\t`Email`\tTEXT NOT NULL UNIQUE,\n" +
                    "\t`Password`\tTEXT NOT NULL,\n" +
                    "\t`BankNumber`\tNUMERIC,\n" +
                    "\t`PhoneNumber`\tNUMERIC NOT NULL,\n" +
                    "\t`car`\tINTEGER\n" +
                    ");\n" +
                    "INSERT INTO `user` (ID,Name,Email,Password,BankNumber,PhoneNumber,car) VALUES (1,'maryam','mshz@yahoo.com','123',12346,9397922112,1),\n" +
                    " (2,'reza','nematolahi','123" +
                    "',840420,9171111111,0);\n" +
                    "CREATE TABLE IF NOT EXISTS `room` (\n" +
                    "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t`ApartmentId`\tINTEGER NOT NULL,\n" +
                    "\t`ChargeId`\tINTEGER,\n" +
                    "\t`RentalPrice`\tNUMERIC,\n" +
                    "\t`ZipCode`\tTEXT,\n" +
                    "\t`Likes`\tNUMERIC,\n" +
                    "\t`NumberOfBedrooms`\tINTEGER NOT NULL,\n" +
                    "\t`RoomNumber`\tNUMERIC NOT NULL,\n" +
                    "\tFOREIGN KEY(`ApartmentId`) REFERENCES `apartment`(`Id`),\n" +
                    "\tFOREIGN KEY(`ChargeId`) REFERENCES `charge`(`Id`)\n" +
                    ");\n"
                    );
            mDb.execSQL("CREATE TABLE IF NOT EXISTS `rent` (" +
                    "`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                    "`LeaseId` INTEGER NOT NULL," +
                    "`PaymentID` INTEGER DEFAULT 0," +
                    "`RentFee` NUMERIC," +
                    "`Date`\tTEXT NOT NULL," +
                    "`LateFee` INTEGER," +
                    "FOREIGN KEY(`PaymentID`) REFERENCES `payment`(`Id`)," +
                    "FOREIGN KEY(`LeaseId`) REFERENCES `Lease`(`Id`)" +
                    ");\n" );
            mDb.execSQL(
                    "CREATE TABLE IF NOT EXISTS `payment` (\n" +
                    "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t`Amount`\tNUMERIC NOT NULL,\n" +
                    "\t`Date`\tTEXT NOT NULL,\n" +
                    "\t`PaymentTitle`\tTEXT\n" +
                    ");\n" );
            mDb.execSQL(
                    "CREATE TABLE IF NOT EXISTS `messages` (\n" +
                    "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t`SenderID`\tINTEGER NOT NULL,\n" +
                    "\t`ReceiverID`\tINTEGER NOT NULL,\n" +
                    "\t`Title`\tTEXT,\n" +
                    "\t`Body`\tTEXT,\n" +
                    "\tFOREIGN KEY(`ReceiverID`) REFERENCES `user`(`ID`),\n" +
                    "\tFOREIGN KEY(`SenderID`) REFERENCES `user`(`ID`)\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS `costs` (\n" +
                    "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t`Title`\tTEXT,\n" +
                    "\t`ManagerId`\tINTEGER NOT NULL,\n" +
                    "\t`Date`\tTEXT,\n" +
                    "\t`Amount`\tNUMERIC NOT NULL,\n" +
                    "\t`ApartmentId`\tINTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(`ApartmentId`) REFERENCES `apartment`(`Id`)\n" +
                    ");\n" );
            mDb.execSQL(
                    "CREATE TABLE IF NOT EXISTS `comments` (\n" +
                    "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t`RoomId`\tINTEGER NOT NULL,\n" +
                    "\t`TenantId`\tINTEGER NOT NULL,\n" +
                    "\t`Title`\tINTEGER NOT NULL,\n" +
                    "\t`Body`\tINTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(`TenantId`) REFERENCES `user`(`ID`),\n" +
                    "\tFOREIGN KEY(`RoomId`) REFERENCES `room`(`Id`)\n" +
                    ");\n" );
            mDb.execSQL(
                    "CREATE TABLE IF NOT EXISTS `charge` (\n" +
                    "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t`Amount`\tINTEGER,\n" +
                    "\t`Date`\tTEXT,\n" +
                    "\t`PaymentID`\tINTEGER NOT NULL,\n" +
                    "\tFOREIGN KEY(`PaymentID`) REFERENCES `payment`(`Id`)\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS `apartment` (\n" +
                    "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t`Name`\tTEXT,\n" +
                    "\t`ManagerId`\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t`Adress`\tTEXT,\n" +
                    "\t`NumberOfRooms`\tNUMERIC,\n" +
                    "\tFOREIGN KEY(`ManagerId`) REFERENCES `user`(`ID`)\n" +
                    ");\n" );
            mDb.execSQL(
                    "CREATE TABLE IF NOT EXISTS `Lease` (\n" +
                    "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t`TenantId`\tINTEGER NOT NULL,\n" +
                    "\t`RoomID`\tINTEGER NOT NULL,\n" +
                    "\t`Deposit`\tREAL,\n" +
                    "\t`StartDate`\tTEXT NOT NULL,\n" +
                    "\t`EndDate`\tTEXT,\n" +
                    "\t`RentFee`\tNUMERIC,\n" +
                    "\tFOREIGN KEY(`RoomID`) REFERENCES `room`(`Id`),\n" +
                    "\tFOREIGN KEY(`TenantId`) REFERENCES `user`(`ID`)\n" +
                    ");\n" +
                    "COMMIT;\n");


        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }
    private void startHomeActivity() {
        Bundle Bundle = new Bundle();
        Bundle.putInt("userid" , id);
        Intent homeIntent = new Intent(logIn.this, HomeActivity.class );
        homeIntent.putExtras(Bundle);
        mDb.releaseReference();
        mDb.close();
        mDBHelper.close();

        startActivity(homeIntent );
    }
    public boolean checkUser(String email , String password) {

        Cursor cursor = mDb.rawQuery("select * from user where Email = ? and Password = ? " ,
                new String[]{email,password});


        if (cursor != null && cursor.moveToFirst()){

            id = cursor.getInt(cursor.getColumnIndex("ID"));

            cursor.close();
            return true;

        }
        else {
            Toast.makeText(getBaseContext(),"Please Enter correct email and password!"
                    , Toast.LENGTH_LONG).show();
            cursor.close();
            return false;
        }


    }


}
//package com.example.apadana.roombox;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class logIn extends AppCompatActivity {
//
//
//    //Button loginBtn;
//    //EditText username;
//    @BindView(R.id.usernameEditText) EditText username;
//    @BindView (R.id.loginBtn) Button loginBtn ;
//    @Override
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_log_in);
//        ButterKnife.bind(this);
//
//        View.OnClickListener on = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startHomeActivity();
//
//            }
//        };
//
//        loginBtn.setOnClickListener(on);
//    }
//
//    private void startHomeActivity() {
//        Intent homeIntent = new Intent(logIn.this, HomeActivity.class );
//        startActivity(homeIntent );
//    }
//
//
//}
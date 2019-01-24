package com.example.apadana.roombox;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;


public class BaseActivity extends AppCompatActivity {
    //    @BindView(R.id.container)
//    ConstraintTableLayout container;
    private TextView mTextMessage;
    public static final String FRAGMENT_VIEWPAGER = "FRAGMENT_VIEWPAGER";
    public static final String FRAGMENT_FIRST = "FRAGMENT_FIRST";
    public static final String FRAGMENT_SECOND = "FRAGMENT_SECOND";
    public static user user;
    public int userid;
    public static BottomNavigationView navigation;
    public static com.example.apadana.roombox.DatabaseHelper mDBHelper;
    public static SQLiteDatabase mDb ;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home = new Intent(getBaseContext(),HomeActivity.class);
                    startActivity(home);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_dashboard:
                    replaceFragment(SecondFragment.newInstance(),FRAGMENT_SECOND);
                    return true;
                case R.id.navigation_notifications:
                    replaceFragment(ViewPagerFragment.newInstance(),FRAGMENT_VIEWPAGER);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        mTextMessage = (TextView) findViewById(R.id.message);


//        navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.dynamicContent, FirstFragment.newInstance(), FRAGMENT_FIRST)
                .commit();
    }



    public void setDatabase() {

        mDBHelper = new com.example.apadana.roombox.DatabaseHelper(getBaseContext());

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDBHelper.openDataBase();
            mDb = mDBHelper.getWritableDatabase();
            mDb.delete("apartment" , "Name = ?" , new String[]{"ghadie"});
            mDb.execSQL(
                            "CREATE TABLE IF NOT EXISTS `user` (\n" +
                            "\t`ID`\tINTEGER NOT NULL DEFAULT 0 PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                            "\t`Name`\tTEXT NOT NULL,\n" +
                            "\t`Email`\tTEXT NOT NULL UNIQUE,\n" +
                            "\t`Password`\tTEXT NOT NULL,\n" +
                            "\t`BankNumber`\tNUMERIC,\n" +
                            "\t`PhoneNumber`\tNUMERIC NOT NULL,\n" +
                            "\t`car`\tINTEGER\n" +
                            ");\n" );
            mDb.execSQL("CREATE TABLE IF NOT EXISTS `charge` (\n" +
                                    "\t`Id`\tINTEGER NOT NULL DEFAULT 0 PRIMARY KEY UNIQUE,\n" +
                                    "\t`Amount`\tINTEGER,\n" +
                                    "\t`Date`\tTEXT,\n" +
                                    "\t`PaymentID`\tINTEGER,\n" +
                                    "\t`RoomId`\tINTEGER,\n" +
                                    "\tFOREIGN KEY(`PaymentID`) REFERENCES `payment`(`Id`),\n" +
                                    "\tFOREIGN KEY(`RoomId`) REFERENCES `room`(`Id`)\n" +
                                    ");" );
            mDb.execSQL(
                            "CREATE TABLE IF NOT EXISTS `apartment` (\n" +
                            "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                            "\t`Name`\tTEXT,\n" +
                            "\t`ManagerId`\tINTEGER NOT NULL,\n" +
                            "\t`Adress`\tTEXT,\n" +
                            "\t`NumberOfRooms`\tNUMERIC,\n" +
                            "\tFOREIGN KEY(`ManagerId`) REFERENCES `user`(`ID`)\n" +
                            ");\n" );
            mDb.execSQL(
                            "CREATE TABLE IF NOT EXISTS`room` (\n" +
                                    "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                                    "\t`ApartmentId`\tINTEGER NOT NULL,\n" +
                                    "\t`ChargeId`\tINTEGER,\n" +
                                    "\t`RentalPrice`\tNUMERIC,\n" +
                                    "\t`ZipCode`\tTEXT,\n" +
                                    "\t`Likes`\tNUMERIC,\n" +
                                    "\t`NumberOfBedrooms`\tINTEGER NOT NULL DEFAULT 0,\n" +
                                    "\t`RoomNumber`\tNUMERIC NOT NULL,\n" +
                                    "\t`sendForRent`\tINTEGER NOT NULL DEFAULT 0,\n" +
                                    "\t`Deposit`\tINTEGER DEFAULT 0,\n" +
                                    "\tFOREIGN KEY(`ApartmentId`) REFERENCES `apartment`(`Id`),\n" +
                                    "\tFOREIGN KEY(`ChargeId`) REFERENCES `charge`(`Id`)\n" +
                                    ");" );
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
                            ");\n" );
            mDb.execSQL(
                            "CREATE TABLE IF NOT EXISTS `rent` (\n" +
                            "\t`Id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                            "\t`LeaseId`\tINTEGER NOT NULL,\n" +
                            "\t`PaymentID`\tINTEGER DEFAULT 0,\n" +
                            "\t`RentFee`\tNUMERIC,\n" +
                            "\t`Date`\tTEXT NOT NULL,\n" +
                            "\t`LateFee`\tINTEGER,\n" +
                            "\tFOREIGN KEY(`PaymentID`) REFERENCES `payment`(`Id`),\n" +
                            "\tFOREIGN KEY(`LeaseId`) REFERENCES `Lease`(`Id`)\n" +
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
                            ");\n" );
            mDb.execSQL(
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
                            "CREATE VIEW IF NOT EXISTS myRooms\n" +
                            "as\n" +
                            "Select room.RoomNumber,room.NumberOfBedrooms,Lease.RentFee\n" +
                            " from user,Lease,room \n" +
                            " where user.ID = Lease.Id and room.Id = Lease.id;\n");



        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.dynamicContent, newFragment, tag)
                .commit();

    }

}

package com.example.apadana.roombox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;

import java.util.List;

public class ManageMyApartments extends BaseActivity {
    private FrameLayout dynamicContent;
    private View wizard;
    private ListView listView;
    private String[] apartments;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();

        setApartments();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(apartments[position] == "*Insert New Apartment*"){
                    startInserAptActivity(view);

                }
                else{
                    startManageSingleAptActivity(view , position , id , apartments);
                }
            }
        });
    }

    private void setApartments() {
       listView = (ListView) findViewById(R.id.listView);

        apartments = new String[]{"My Apartment1", "My apartment2", "*Insert New Apartment*"};
        ArrayAdapter<String> crsAdapter = new ArrayAdapter<String>(this,R.layout.show_myrooms
                , apartments);
        listView.setAdapter(crsAdapter);
    }

    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.manage_myapartments, null);
        dynamicContent.addView(wizard);
    }

    public void startInserAptActivity(View view){
        Intent aptMng = new Intent(view.getContext() , InsertNewApartment.class );
        startActivity(aptMng);
        overridePendingTransition(0, 0);
    }

    public void startManageSingleAptActivity(View view , int position , long id , String[] apartments){
        Bundle aptMngBundle = new Bundle();
        Intent aptMng = new Intent(view.getContext() , ManageSingleApartment.class );
                    aptMngBundle.putInt("aptPosition" , position);
                    aptMngBundle.putLong("aptId" , id);
                    aptMngBundle.putSerializable("array" , apartments);
                    aptMng.putExtras(aptMngBundle);
                    startActivity(aptMng);
                    overridePendingTransition(0, 0);
    }
}

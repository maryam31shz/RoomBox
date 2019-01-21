package com.example.apadana.roombox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;

public class InsertNewApartment extends BaseActivity {
    private FrameLayout dynamicContent;
    private Button saveBtn;
    private View wizard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startManageMyApartmentsActivity();
            }
        });
    }
    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.insert_newapartment, null);
        dynamicContent.addView(wizard);
        saveBtn = (Button) findViewById(R.id.saveBtn);

    }
    public void startManageMyApartmentsActivity (){
        Intent myApartments = new Intent(getBaseContext(),ManageMyApartments.class);
        startActivity(myApartments);
        overridePendingTransition(0, 0);
    }
}

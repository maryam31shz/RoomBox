package com.example.apadana.roombox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class setChargeForAllActivity extends BaseActivity {
    private FrameLayout dynamicContent;
    private View wizard;
    private EditText chargeAmount;
    private EditText deadline;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startManageSingleApartment(v);
            }
        });
    }
    public void startManageSingleApartment(View v){
        Intent mngSingleApt = new Intent(getBaseContext(),ManageSingleApartment.class);
        startActivity(mngSingleApt);
        overridePendingTransition(0, 0);
    }
    private void setView() {
        dynamicContent = (FrameLayout) findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.setchargeforall, null);
        dynamicContent.addView(wizard);

        submit = (Button) findViewById(R.id.submitchargeAmountBtn);
        deadline = (EditText) findViewById(R.id.deadlineEditText);
        chargeAmount = (EditText) findViewById(R.id.chargeAmountEditText);
    }
}

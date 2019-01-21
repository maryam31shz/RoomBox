package com.example.apadana.roombox;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;

public class ManageRoomApts extends BaseActivity {
    private FrameLayout dynamicContent;
    private View wizard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setView() {
        dynamicContent = (FrameLayout)  findViewById(R.id.dynamicContent);
        wizard = getLayoutInflater().inflate(R.layout.manage_roomapts, null);
        dynamicContent.addView(wizard);

    }

}

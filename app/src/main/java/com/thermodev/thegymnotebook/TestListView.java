package com.thermodev.thegymnotebook;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import static android.content.ContentValues.TAG;

/**
 * Created by user on 28-Jul-17.
 */

public class TestListView extends ListView {
    public TestListView(Context context){
        super(context);
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
        Log.d(TAG, "setOnItemClickListener: Clicked");
    }
}

package com.brethauerit.volleyball.helper;

import android.content.Context;
import android.content.Intent;

public class ActivityHelper {

    public static void openActivity(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

}

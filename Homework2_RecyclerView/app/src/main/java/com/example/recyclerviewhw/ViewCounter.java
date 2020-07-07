package com.example.recyclerviewhw;

import android.view.View;
import android.view.ViewGroup;

public class ViewCounter {
    public int countView(View view) {
        int res = 0;
        if (view instanceof ViewGroup) {
            int childNumber = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childNumber; i++) {
                res += countView(((ViewGroup) view).getChildAt(i));
            }
        }
        else {
            res++;
        }
        return res;
    }
}

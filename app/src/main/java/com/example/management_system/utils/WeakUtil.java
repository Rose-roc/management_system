package com.example.management_system.utils;

import android.content.DialogInterface;
import com.example.management_system.utils.listener.WeakOnCancelListener;
import com.example.management_system.utils.listener.WeakOnDismissListener;
import com.example.management_system.utils.listener.WeakOnShowListener;

public class WeakUtil {
    public static WeakOnCancelListener proxy(DialogInterface.OnCancelListener real) {
        return new WeakOnCancelListener(real);
    }

    public static WeakOnDismissListener proxy(DialogInterface.OnDismissListener real) {
        return new WeakOnDismissListener(real);
    }

    public static WeakOnShowListener proxy(DialogInterface.OnShowListener real) {
        return new WeakOnShowListener(real);
    }
}

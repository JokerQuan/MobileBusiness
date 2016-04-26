package com.wq.mobilebusiness.Utils;

import android.content.Context;
import android.widget.Toast;

import com.wq.mobilebusiness.R;


/**
 * Created by 王铨 on 2016/4/4.
 */
public class T {
    public static void showShort(Context context,int resID){
        Toast.makeText(context,resID,Toast.LENGTH_SHORT).show();
    }
    public static void showShort(Context context,String string){
        Toast.makeText(context, string,Toast.LENGTH_SHORT).show();
    }
    public static void showLong(Context context,int resID){
        Toast.makeText(context, resID,Toast.LENGTH_LONG).show();
    }
}

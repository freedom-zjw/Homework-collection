package cn.chonor.final_pro.login_info;

import android.content.Context;

/**
 * Created by Chonor on 2017/12/26.
 */

public class Logined {
    public static boolean CheckedLogined(Context context){
        if(!SaveUser.getUserId(context).equals("")){
            return  true;
        }
        else return false;
    }
}

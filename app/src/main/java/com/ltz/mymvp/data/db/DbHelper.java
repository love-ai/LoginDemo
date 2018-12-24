package com.ltz.mymvp.data.db;

import com.ltz.mymvp.data.local.UserProfileInfo;
import com.ltz.mymvp.data.local.UserProfileInfo_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by xiaowei on 2018/6/4
 */
public class DbHelper {
    public static UserProfileInfo getUserInfo() {
        return SQLite.select().from(UserProfileInfo.class).querySingle();
    }


    public static Boolean saveOrUpdate(BaseModel baseModel) {
        if (baseModel != null) {
            if (baseModel.exists()) {
                return baseModel.update();
            } else {
                return baseModel.save();
            }
        }
        return false;
    }


    public static List<UserProfileInfo> queryUserInfoList() {
        return SQLite.select()
                .from(UserProfileInfo.class)
                .where(UserProfileInfo_Table.name.isNotNull(), UserProfileInfo_Table.id_card_type.greaterThanOrEq(1))
                .orderBy(UserProfileInfo_Table.birthday, true)
                .limit(3)
                .queryList();
    }


    public static void updateUserInfo() {
        SQLite.update(UserProfileInfo.class)
                .set(UserProfileInfo_Table.name.eq(""))
                .where(UserProfileInfo_Table.name.eq("1"))
                .execute();
    }

    public static void deleteUserInfo(){
        UserProfileInfo info = SQLite.select().from(UserProfileInfo.class).querySingle();
        if(info!=null){
            info.delete();
        }
        //or
        SQLite.delete(UserProfileInfo.class)
                .where(UserProfileInfo_Table.name.eq(""))
                .execute();
    }

}

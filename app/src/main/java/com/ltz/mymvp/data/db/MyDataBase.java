package com.ltz.mymvp.data.db;

import com.ltz.mymvp.data.local.UserProfileInfo;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by xiaowei on 2018/6/1
 */
@Database(version = MyDataBase.VERSION, name = MyDataBase.NAME)
public class MyDataBase {
    public static final String NAME = "mydb";
    public static final int VERSION = 1;


    //数据表的更新
//    @Migration(version = 2, priority = 1, database = MyDataBase.class)
//    public static class Migration2 extends AlterTableMigration<UserProfileInfo> {
//
//
//        public Migration2(Class<UserProfileInfo> table) {
//            super(table);
//        }
//
//        @Override
//        public void onPreMigrate() {
//            addColumn(SQLiteType.INTEGER, "amount");
//            addColumn(SQLiteType.TEXT, "user_name_info");
//        }
//    }
}

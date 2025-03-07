package com.example.database;

import static android.app.ProgressDialog.show;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper {
    public static final String database_name = "Employee.db";
    public static final String table_emp = "Emp1";
    public static final String table_dept = "Dept1";
    public static final String emp_id = "emp_id";
    public static final String emp_name = "emp_name";
    public static final String emp_addr = "emp_addr";
    public static final String emp_phone = "emp_phone";
    public static final String emp_dept_no = "dept_no";

    public static final String dept_no = "dept_no";
    public static final String dept_name = "dept_name";
    public static final String dept_addr = "dept_addr";
    Database(Context c){
        super(c,database_name,null,1);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + table_emp + " (" +
                emp_id + " INTEGER PRIMARY KEY, " +
                emp_name + " TEXT, " +
                emp_addr + " TEXT, " +
                emp_phone + " TEXT, "+
                emp_dept_no + " INTEGER, " +
                " FOREIGN KEY("+emp_dept_no+") REFERENCES "+table_dept+"("+dept_no+"))"
        );
        db.execSQL("CREATE TABLE " + table_dept + " (" +
                dept_no + " INTEGER PRIMARY KEY, " +
                dept_name + " TEXT, " +
                dept_addr + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_emp);
        db.execSQL("DROP TABLE IF EXISTS " + table_dept);
        onCreate(db);
    }

    public void onUpdate(SQLiteDatabase db,int old_version,int new_version){

        }
        public void onDownGrade(SQLiteDatabase db,int old_version,int new_version){

        }
        public boolean insertDepartmentData(int no,String name,String addr){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(dept_no,no);
            cv.put(dept_name,name);
            cv.put(dept_addr,addr);

            long isInserted = db.insert(table_dept,null,cv);
            return isInserted!=-1;
        }
        public boolean insertData(int id,String name,String addr,String phone,int dept_no){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(emp_id,id);
            cv.put(emp_name,name);
            cv.put(emp_addr,addr);
            cv.put(emp_phone,phone);
            cv.put(emp_dept_no,dept_no);

            long isInserted = db.insert(table_emp,null,cv);
            return isInserted!=-1;
    }
    public boolean deleteEmployees(String deptName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT dept_no from "+table_dept+" where dept_name =? ",new String[]{deptName});
        if(cursor.moveToNext()){
            int dept_number = cursor.getInt(0);
            db.delete(table_emp,emp_dept_no+"=?",new String[]{String.valueOf(dept_number)});
            cursor.close();
            return true;
        }
        return false;
    }
//    public void dropEmp(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(table_emp,null,null);
//    }
    Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+table_emp,null);
        return res;
    }
    Cursor getDeptData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+table_dept,null);
        return res;
    }
}

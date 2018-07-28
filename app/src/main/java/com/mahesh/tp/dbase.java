package com.mahesh.tp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class dbase extends SQLiteOpenHelper {
    public static final String db_NAME= "TnP";
    public static final String candidateTABLE= "Candidates";
    public static final String marksTABLE= "Marks";
    public dbase(Context context, int db_VERSION) {
        super(context, db_NAME, null, db_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+candidateTABLE+"(id INTEGER PRIMARY KEY AUTOINCREMENT,PRN TEXT UNIQUE,Name TEXT,mobile TEXT)");
        db.execSQL("create table "+marksTABLE+"(PRN TEXT, X DOUBLE, XII DOUBLE DEFAULT 0, FEI DOUBLE DEFAULT 0, FEII DOUBLE DEFAULT 0, SEI DOUBLE DEFAULT 0, SEII DOUBLE DEFAULT 0, TEI DOUBLE DEFAULT 0, TEII DOUBLE DEFAULT 0, BEI DOUBLE DEFAULT 0, BEII DOUBLE DEFAULT 0,CGPA DOUBLE DEFAULT 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void IntoDatabase(ArrayList<Student> studentArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(Student stu: studentArrayList) {
            ContentValues cv = new ContentValues();
            cv.put("PRN", stu.getPRN());
            cv.put("Name", stu.getName());
            cv.put("mobile", stu.getMobileNumber());
            long res = db.insert(candidateTABLE,null,cv);
            if(res!=-1){
                int counter=0;
                double sum=0;
                cv.clear();
                cv.put("PRN",stu.getPRN());
                cv.put("X",stu.getX());
                cv.put("XII",stu.getXII());
                cv.put("FEI",stu.getFEI());
                cv.put("FEII",stu.getFEII());
                cv.put("SEI",stu.getSEI());
                cv.put("SEII",stu.getSEII());
                cv.put("TEI",stu.getTEI());
                cv.put("TEII",stu.getTEII());
                cv.put("BEI",stu.getBEI());
                cv.put("BEII",stu.getBEII());
                if((int)stu.getFEI()>0)      counter++;
                if((int)stu.getFEII()>0)     counter++;
                if((int)stu.getSEI()>0)     counter++;
                if((int)stu.getSEII()>0)     counter++;
                if((int)stu.getTEI()>0)     counter++;
                if((int)stu.getTEII()>0)     counter++;
                if((int)stu.getBEI()>0)     counter++;
                if((int)stu.getBEII()>0)     counter++;
                sum = stu.getTotalSum();
                System.out.print("Sum: "+sum+" counter: "+counter);
                cv.put("CGPA",sum/counter);
                db.insert(marksTABLE,null,cv);
            }
        }
    }
}

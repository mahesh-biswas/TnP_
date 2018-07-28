package com.mahesh.tp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {
    Button browseFile,sendMsg;
    TextView filePath;
    EditText msgField,numberField;
    final int CODE = 4090;
    final String TAG = "myLog: ";
    InputStream iStream = null;
    File file;
    dbase db;
    ArrayList<Student> studentArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
             Component linking Part
         **/
        browseFile = findViewById(R.id.browseFiles);
        sendMsg    = findViewById(R.id.sendMsg);
        msgField   = findViewById(R.id.msgField);
        numberField= findViewById(R.id.numberField);
        filePath = findViewById(R.id.filePath);

        /**
            Database Part
         **/
        db = new dbase(this,1);

        /**
             Button Action Part
         **/
        browseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile(CODE);
            }
        });
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Number = numberField.getText().toString().trim();
                String Message= msgField.getText().toString().trim();
                if(Number.length()==10)
                    sendText(Number,Message);
                else
                    toast("invalid Number!! "+Number.length());
            }
        });
    }

    private void openFile(int  code) {
        if(readStoragePermission()) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, code);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        String fPath = uri.getPath();
        filePath.setText(fPath);
//        String path = Environment.getExternalStorageDirectory()+uri.getPath();
        POIFSFileSystem fs;
        try {
            if (uri.getScheme().equals("content")){
                iStream = getContentResolver().openInputStream(uri);
                fs = new POIFSFileSystem(iStream);
            }else{
                file = new File(uri.getPath());
                FileInputStream fileInputStream = new FileInputStream(file);
                fs = new POIFSFileSystem(fileInputStream);
            }
            HSSFWorkbook workBook = new HSSFWorkbook(fs);
            HSSFSheet mySheet = workBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();
            int row = 0,j;
            String attr[] = new String[0],Attr="";
            HashMap<String,String> map = new HashMap<>();
            while(rowIter.hasNext()){
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                j=0;
                map.clear();
                while(cellIter.hasNext()){
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    if(row==0){
                        Attr +=myCell.toString()+":";
                    }else{
                        map.put(attr[j],myCell.toString());
                    }
                    j++;
                }
                if(row==0){
                    attr = Attr.split(":");
                    println(attr.length+" "+attr[0]+" "+attr[attr.length-1]);
                }else{
                    Student stu = new Student(map.get("Name"),map.get("PRN"),map.get("mobile").substring(1));
                    stu.setX(Double.parseDouble(map.get("X")));
                    stu.setXII(Double.parseDouble(map.get("XII")));
                    stu.setFEI(Double.parseDouble(map.get("FEI")));
                    stu.setFEII(Double.parseDouble(map.get("FEII")));
                    stu.setSEI(Double.parseDouble(map.get("SEI")));
                    stu.setSEII(Double.parseDouble(map.get("SEII")));
                    stu.setTEI(Double.parseDouble(map.get("TEI")));
                    stu.setTEII(Double.parseDouble(map.get("TEII")));
                    stu.setBEI(Double.parseDouble(map.get("BEI")));
                    stu.setBEII(Double.parseDouble(map.get("BEII")));
                    studentArray.add(stu);

                }
                row++;
            }
            println(">> "+Attr+" <<");
//            DisplayArray(studentArray);
            db.IntoDatabase(studentArray);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void DisplayArray(ArrayList<Student> studentArray) {
        for(Student stu : studentArray){
            println("\n<i++>"+stu.getName()+" "+stu.getPRN()+" "+stu.getMobileNumber());
            println("<j++>"+stu.getX()+" "+stu.getXII()+" "+stu.getFEI()+" "+stu.getFEII()+" "+stu.getSEI()+" "+stu.getSEII()+" "+stu.getTEI()+" "+stu.getTEII()+" "+stu.getBEI()+" "+stu.getBEII());
        }
    }

    public boolean smsPermissions(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"SMS Permission is granted");
                return true;
            } else {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
                    return false;
            }
        }else {
                Log.v(TAG,"Storage READ Permission is granted");
                return true;
        }
    }
    public boolean readStoragePermission(){
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG,"Storage READ Permission is granted");
                    return true;
                } else {

                    Log.v(TAG,"Storage READ Permission is revoked");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                    return false;
                }
            }
            else { //permission is automatically granted on sdk<23 upon installation
                Log.v(TAG,"Storage READ Permission is granted");
                return true;
            }
    }
    public void sendText(String number, String msg){
        if(smsPermissions()) {
            SmsManager smsManager = SmsManager.getDefault();
            if (msg.length() < 160)
                smsManager.sendTextMessage(number, null, msg, null, null);
            else {
                ArrayList<String> parts = smsManager.divideMessage(msg);
                smsManager.sendMultipartTextMessage(number, null, parts, null, null);
            }
        }
    }
    public String toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        return "";
    }
    public String println(String s){
        System.out.println(s);
        return "";
    }
}

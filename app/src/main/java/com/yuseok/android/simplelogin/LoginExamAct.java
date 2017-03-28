package com.yuseok.android.simplelogin;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginExamAct extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 100;
    /**
     * Called when the activity is first created.
     */
    private String txtId = null;
    private String txtPwd = null;

    private LoginDBHelper mDBHelper = null;

    private ContentValues cv = null;
    private String MEMBERID = "memid";
    private String PWD = "pwd";
    private SQLiteDatabase db = null;
    private MyAlertDialog alert = null;
    private EditText et = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new LoginDBHelper(this);
         System.out.println("콘솔화면나옴?");
        Log.i("tag insert", "hello");
        alert = new MyAlertDialog(this);
        Button idBtn = (Button) findViewById(R.id.findId);
        Button pwdBtn = (Button) findViewById(R.id.register);
        txtId = (idBtn).getText().toString();
        txtPwd = (pwdBtn).getText().toString();
        idBtn.setOnClickListener(this);
        pwdBtn.setOnClickListener(this);


        Log.i("KTH", "here ok");
    }

    public void onClick(View view) {
        txtId = (et = (EditText) findViewById(R.id.entryId)).getText().toString();
        txtPwd = ((EditText) findViewById(R.id.pwd)).getText().toString();
        Boolean ans = false;
        switch (view.getId()) {
            case R.id.findId:
                ans = isExistMemID(txtId); // txtId가 null 이 아니면 false입력 // varID == null
                et.setText(ans + "");

                Intent intent = new Intent(getApplicationContext(),InappActivity.class);
                intent.putExtra("id",txtId);
                intent.putExtra("Pw",txtPwd);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.register:
                if (!isExistMemID(txtId)) { // varID == null이 아니면
                    Log.i("중복이라면?===", txtId);
                    alert.setMessage("중복된ID가 있음");

                }else {
                    Log.i("중복이아니면?===", txtId);
                    db = mDBHelper.getWritableDatabase();
                    cv = new ContentValues();
                    cv.put(MEMBERID, txtId);
                    cv.put(PWD, txtPwd);
                    db.insert("member", null, cv);

                    alert.setMessage(txtId + "," + txtPwd);
                }
                break;
        }

//        alert.show();

    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
//        super.onActivityResult(requestCode, resultCode, Data);
//        if(requestCode == REQUEST_CODE) {
//            if(resultCode == RESULT_OK) {
//                String id = textId.getExtra().getString("phoneNumber");
//            }
//        }
//    }

    public Boolean isExistMemID(String varID) {
        Boolean ans = false;
        if (varID == null)
            return ans;

            db = mDBHelper.getWritableDatabase();
            String sql = "select memId,pwd from member where memID='" + varID.trim() + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.getCount() == 0) {
                ans = !ans;
            }
        return ans;
     }

    class MyAlertDialog extends AlertDialog.Builder {
        MyAlertDialog(Context ctx) {
            super(ctx);
            this.setNeutralButton("닫기", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    //기본적으로 창이 닫히고, 추가 작업은 없다.
                }
            });
        }

        @Override
        public AlertDialog.Builder setMessage(CharSequence msg) {
            super.setMessage(msg);
            this.show();
            return this;
        }
    }

    public void doLogin(View btn) {

        try {
    		txtId = ((EditText)findViewById(R.id.entryId)).getText().toString();
            txtPwd = ((EditText)findViewById(R.id.pwd)).getText().toString();

            Toast.makeText(this, getResult(txtId,txtPwd), Toast.LENGTH_SHORT);

            switch(btn.getId()){
            	case R.id.findId :
            		db = mDBHelper.getWritableDatabase();
            		String query = "select memId,pwd from member where memId='"+txtId+"'";
                    Log.i("txtID====", query);
            		Cursor cursor = db.rawQuery(query, null);

            		if(cursor.isNull(1)){
            			Toast.makeText(this,"자료없음",Toast.LENGTH_SHORT);
            		}else{
            			Toast.makeText(this,"있음 : "+txtId,Toast.LENGTH_SHORT);
            		}
            		Toast.makeText(this, "체크중",Toast.LENGTH_SHORT);
            		break;
            	case R.id.register :
            		db = mDBHelper.getWritableDatabase();
            		cv = new ContentValues();
            		cv.put(MEMBERID,txtId);
                	cv.put(PWD,txtPwd);

                	db.insert("member",null, cv);
            		break;
            	default :

            }

        	Toast.makeText(this,getResult(txtId,txtPwd), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("error",e.toString());
		}


    }

    public String getResult(String id, String pwd) {
        StringBuffer resultStr = new StringBuffer();
        resultStr.append("id=");
        resultStr.append(id);
        resultStr.append(",pwd=");
        resultStr.append(pwd);
        return resultStr.toString();
    }

}
//class LoginDBHelper extends SQLiteOpenHelper {
//    public LoginDBHelper(Context context) {
//        super(context, "login.db", null, 1);
//    }
//
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table member(_id INTEGER PRIMARY KEY AUTOINCREMENT,memId TEXT,pwd TEXT);");
//    }
//
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists member");
//        onCreate(db);
//    }
//}


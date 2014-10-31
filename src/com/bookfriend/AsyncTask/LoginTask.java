package com.bookfriend.AsyncTask;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.core.e;
import com.bookfriend.Activity.LoginTransitActivity;
import com.bookfriend.http.HttpAgent;
import com.bookfriend.model.Owner;

public class LoginTask extends AsyncTask<String, Integer, Owner> {
    private HttpAgent httpAgent = new HttpAgent();
    private HashMap<String, Object> paras = new HashMap<String, Object>();
    private int code =500;
    private JSONObject msgBody;
    private String userEmail = "";
    private String password = "";
    private LoginTransitActivity loginTransitActivity;


    public  LoginTask(String _userEmail ,String _passWord,LoginTransitActivity _loginActivity){
        userEmail=_userEmail;
        password =_passWord;
        loginTransitActivity = _loginActivity;
    }
    /**
     * 执行任务后调用
     */
    @Override
    protected void onPostExecute(Owner result) {
        super.onPostExecute(result);
        if(code == 200){
            SharedPreferences preferences  = loginTransitActivity.getSharedPreferences("cloud", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username", userEmail);
            editor.putString("password", password);
            editor.putLong("userId", result.getId());
            editor.commit();
            Message messageg = Message.obtain();
            messageg.obj = "success";
            try {
                loginTransitActivity.setNickName(msgBody.getString("userName")) ;
            }catch (Exception e ){
                e.printStackTrace();
            }
            loginTransitActivity.handler.sendMessage(messageg);
        }else{
            Toast.makeText(loginTransitActivity, "用户名或密码错误", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 执行任务之前调用
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        paras.put("email", userEmail);
        paras.put("passwd", password);
    }

    /**
     * 执行任务中调用更新任务进度
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        
        
    }

    /**
     * 执行任务
     */
    @Override
    protected Owner doInBackground(String... arg0) {
        String result = httpAgent.request("api/app/login", paras, "");
        Owner owner = null;
        try{
            JSONObject jsonResult=new JSONObject(result);
            code = jsonResult.getInt("code");
            if(code!=200)return null;
            owner = JSON.parseObject(jsonResult.getJSONObject("msg").toString(), Owner.class);
        }catch (Exception e) {
          Log.e("book", e.getMessage(),e);
        }

        return owner;
    }
}
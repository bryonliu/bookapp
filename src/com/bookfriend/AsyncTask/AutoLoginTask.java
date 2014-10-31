package com.bookfriend.AsyncTask;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import com.bookfriend.Activity.StartActivity;
import com.bookfriend.http.HttpAgent;

public class AutoLoginTask extends AsyncTask<String, Integer, Integer> {
    public HttpAgent httpAgent = new HttpAgent();
    public HashMap<String, Object> paras = new HashMap<String, Object>();
    private StartActivity startActivity;

    public String msgCode = "";
    private String userEmail = "";
    private String password = "";
    private JSONObject msgBody;


    public  AutoLoginTask(String _userEmail ,String _password,StartActivity _startActivity){
        userEmail=_userEmail;
        password =_password;
        startActivity = _startActivity;
    }
    /**
     * 执行任务后调用
     */
    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if(msgCode.equals("200")){
            Message msg = Message.obtain();
            msg.obj = "success";
            try {
                startActivity.setNickName(msgBody.getString("userName"));
            }catch (Exception e ){
               Log.e("book", e.getMessage(), e);
            }
            startActivity.invisiableHandler.sendMessage(msg);
        }else{
            Toast.makeText(startActivity, "请检查网络连接和密码账号的正确性！", Toast.LENGTH_LONG).show();
            Message msg = Message.obtain();
            msg.what=1;
            startActivity.invisiableHandler.sendMessage(msg);
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
     * 执行任务
     */
    @Override
    protected Integer doInBackground(String... arg0) {
        String result = httpAgent.request("api/app/login", paras, "");
        try{
            JSONObject mess=new JSONObject(result);
            msgCode = mess.getString("code");
            msgBody = mess.getJSONObject("msg");
        }catch (Exception e) {
        	 //TODO 登陆异常的时候处理登陆页面的跳转
        	 Log.e("book", e.getMessage(), e);
        }

        return null;
    }
}
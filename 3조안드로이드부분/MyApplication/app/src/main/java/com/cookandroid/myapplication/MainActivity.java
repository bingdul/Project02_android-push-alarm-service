package com.cookandroid.myapplication;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private HttpConnection httpConn = HttpConnection.getInstance();
    String username,password,token;
    EditText et1,et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.txtUsername);
        et2=findViewById(R.id.txtPassword);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=et1.getText().toString();
                password=et2.getText().toString();
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    return;
                                }
                                // Get new FCM registration token
                                token = task.getResult();
                                Log.d("MyFirebaseMsgService", token);
                                sendData(username, password, token);
                            }
                        });
            }
        });
    }

    private void sendData(String username, String password, String token) {
// 네트워크 통신하는 작업은 무조건 작업스레드를 생성해서 호출 해줄 것!!
        new Thread() {
            public void run() {
// 파라미터 2개와 미리정의해논 콜백함수를 매개변수로 전달하여 호출
                httpConn.requestWebServer(username,password,token);
            }
        }.start();;
    }
}
class HttpConnection {

    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();
    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection(){ this.client = new OkHttpClient(); }


    /** 웹 서버로 요청을 한다. */
    public void requestWebServer(String parameter1,String parameter2, String parameter3) {
        try {
            String url1 = "http://192.168.100.8:7070/loginForm";
            String loginPostBody = "username="+parameter1+"&password="+parameter2+"&token="+parameter3;
            Log.d("tag",loginPostBody);

            //Okhttp 객체 생성
            OkHttpClient client = new OkHttpClient();
//            OkHttpClient client= new OkHttpClient().newBuilder()
//                    .addInterceptor(chain -> {
//                        final Request original= chain.request();
//                        final Request authorized= original.newBuilder()
//                                .addHeader("Cookie", "cookie-name=cookie-value")
//                                .build();
//                        return chain.proceed(authorized);
//                    })
//                    .build();
//            //RequestBody 생성
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),
                    loginPostBody);
            // Post 객체 생성
            Request.Builder builder1 = new Request.Builder().url(url1)
                    .post(requestBody1);
            Request request1 = builder1.build();

            //요청 전송
            Response response1 = client.newCall(request1).execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

package com.example.chie.notifitest0429;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.example.chie.notifitest0429.MainActivity.flag;

/**
 * Created by chie on 2017/04/29.
 */

public class MyFirebaseInstanceIdservice extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIdservice";
    private String token;
    private String uid = "NIBvMdUgA0V50JlQdQTwKMiN1c52";

    @Override
    public void onTokenRefresh() {
        //アプリ初起動時にFCMトークンを生成する
        token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "RefreshedToken = " + token);

        //トークンの値をサーバーへ送信
        submit(token);
    }

    private void submit(String token) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d(TAG, "getInstance");

        // 生成されたトークンを、fcmTokensの子として登録
        // キー：トークン値
        // 値：uid
        // DatabaseReference refToken = database.getReference("/fcmTokens");
        // refToken.child(token).setValue(uid);


        // 2017/5/21/追加部分
        // 生成されたトークンを,fcmTokensの子として登録
        // キー：トークン値
        // 値：uid (star318.ss@yawnchat.webapp のものを使用)
        DatabaseReference refToken = database.getReference("/fcmTokens");
        Log.d(TAG, "getReference");
        refToken.child(token).setValue(uid);
        Log.d(TAG, "setValue");

    }

}

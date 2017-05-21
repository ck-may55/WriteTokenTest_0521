package com.example.chie.notifitest0429;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //Firebaseからの通知を受け取ったかどうかを判定する変数flag
    //初期値：０、通知付け取り後：１
    public static int flag = 0;

    //Authentication機能を使うのに必要
    private FirebaseAuth mAuth;

    //ログイン状態を追うためのリスナー
    private FirebaseAuth.AuthStateListener mAuthListener;

    //サインインに必要なメールアドレスとパスワード
    private String mEmail = "star318.ss@yawnchat.webapp";
    private String mPassword = "999999";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            //すでにサインインしているのかを確認
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //サインイン済みであれば、
                    // サインインしたアカウントに対応するUIDがログに表示される
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG,"flag = "+ flag);
                //Uri uri = Uri.parse("https://yawnchat-919a4.firebaseapp.com/");
                Uri uri = Uri.parse("https://yawnchat-919a4.firebaseapp.com/?title=title&emaillocal=star318.ss&password=999999");
                Intent intent = new Intent(Intent.ACTION_VIEW , uri);

                //flagが１であればチャットアプリを開く
                if(flag == 1)
                startActivity(intent);
            }
        });

        //Firebaseにサインイン
        signIn(mEmail, mPassword);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    // Firebaseへのサインインを担う部分
    // 引数１：email, 引数２：password
    // 返り値：なし
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // サインイン成功時
                            Log.d(TAG, "signInWithEmail:success");


                        } else {
                            // サインイン失敗時
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            // トーストの表示
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}

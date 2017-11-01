package topsint.myfriendsfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button Login, Create_Account;
    TextView Forget;
    String AuthToken;
    CallbackManager callbackManager;
     LoginButton loginButton;
    CheckInternetConnection checkInternetConnection;
    String stremail, strpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        callbackManager = CallbackManager.Factory.create();
        loginButton=(LoginButton) findViewById(R.id.btnFbLogin);
        email = (EditText) findViewById(R.id.emil_for_login);
        password = (EditText) findViewById(R.id.pass_for_login);
        Login = (Button) findViewById(R.id.btnLogin);
        Forget = (TextView) findViewById(R.id.txt_forget);
        Create_Account = (Button) findViewById(R.id.txt_create_account);

        loginButton .setReadPermissions("email");
       // loginButton .setReadPermissions("user_birthday");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken()
                        ,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.e("chandresh",object.toString()+"");
                                try {
                                    StoreData.id=object.getString("id");
                                    StoreData.username=object.getString("name");
                                    StoreData.STATIC_EMAIL=object.getString("email");
                                    StoreData.STATIC_GENDER=object.getString("gender");

                                    JSONObject jobject=object.getJSONObject("picture");
                                    JSONObject jsonobject=jobject.getJSONObject("data");

                                    StoreData.Image=jsonobject.getString("url");
                                    Log.e("print","id = "+StoreData.id+" username "+StoreData.username+" Email= "+StoreData.STATIC_EMAIL+" gender= "+StoreData.STATIC_GENDER+" Image= "+StoreData.Image);

                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("username", StoreData.STATIC_Name);
                                    hashMap.put("email", StoreData.STATIC_EMAIL);
                                    hashMap.put("gender", StoreData.STATIC_GENDER);

                                    JSONHelper jsonHelper=new JSONHelper(LoginActivity.this, "http://" + StoreData.STATIC_IP_ADDRESS + ":8080/friends_finder/login", hashMap, new OnAsyncLoader() {
                                        @Override
                                        public void onResult(String result) throws JSONException {

                                        }
                                    });
                                    jsonHelper.execute();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,picture");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
                    SnackBar.makeShort(LoginActivity.this, "Username & paasword can't be empty");
                }
                else {
                    gettextvalue();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("username", stremail);
                    hashMap.put("password", strpassword);


                    JSONHelper jsonHelper = new JSONHelper(LoginActivity.this, "http://" + StoreData.STATIC_IP_ADDRESS + ":8080/friends_finder/login", hashMap, new OnAsyncLoader() {
                        @Override
                        public void onResult(String result) {

                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if (jsonObject.getString("Islogin").equals("Success")) {
                                    Log.e("result" + result, " ");

                                    PrefUtil.putstringPref("username",email.getText().toString(),LoginActivity.this);
                                    PrefUtil.putstringPref("password",password.getText().toString(),LoginActivity.this);
                                    PrefUtil.putbooleanPref(PrefUtil.PRE_LOGINCHECK, true, LoginActivity.this);
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    //Log.e("url",PrefUtil.putbooleanPref(PrefUtil.PRE_LOGINCHECK, true, LoginActivity.this));
                                    finish();
                                } else {
                                    SnackBar.makeLong(LoginActivity.this, "invalid username or password..");
                                }
                                Toast.makeText(LoginActivity.this, "" + result, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    jsonHelper.execute();
                }
            }
        });


        Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, Email.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void gettextvalue() {
        stremail = email.getText().toString();
        strpassword = password.getText().toString();
        StoreData.STATIC_USEREMAIL = stremail;
        StoreData.STATIC_USERPASSWORD = strpassword;
    }
}

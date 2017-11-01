package topsint.myfriendsfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Security;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class Email extends AppCompatActivity {

    EditText editemail;
    Button btnNext;
    Random random;
    int otp_number;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String stremail = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        editemail = (EditText) findViewById(R.id.email_for_registration1);
        btnNext = (Button) findViewById(R.id.btnnext_in_email);



        random= new Random();
        otp_number=random.nextInt(10000);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCheckValidation()) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("email", editemail.getText().toString());
                    JSONHelper jsonHelper = new JSONHelper(Email.this, "http://"+StoreData.STATIC_IP_ADDRESS+":8080/friends_finder/email_is_exist", hashMap, new OnAsyncLoader() {
                        @Override
                        public void onResult(String result) throws JSONException {

                            Log.e("result", result + " test");
                            JSONObject jsonObject = new JSONObject(result);
                            Log.e("jsone"+jsonObject+"json","");
                            if (jsonObject.getString("email").equals("notExist")) {
                                gettextvalue();
                                SendMail sm=new SendMail(Email.this,stremail,"this is one time password to register friendsfinder",String.valueOf(otp_number));
                                sm.execute();
                                Intent i = new Intent(Email.this, OTP_for_registration.class);
                                StoreData.STATIC_EMAIL = stremail;
                                StoreData.STATIC_OTP=  String.valueOf(otp_number);
                                Log.e(StoreData.STATIC_OTP+" otp"," is generated");
                                startActivity(i);
                            } else {
                                final AlertDialog.Builder alert = new AlertDialog.Builder(Email.this);
                                alert.setTitle("Error");
                                alert.setMessage("E-mail adderess already Registered......");
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                    }
                                });
                                alert.show();
                            }
                        }
                    });
                    jsonHelper.execute();
                }
            }
        });
    }

    private boolean isCheckValidation() {
        boolean check = true;
        try {
            gettextvalue();
            if (stremail.isEmpty()) {
                check = false;
                SnackBar.makeShort(Email.this, "Email can't be empty");
            } else if (!stremail.matches(emailPattern)) {
                check = false;
                SnackBar.makeLong(Email.this, "Please enter valid Email address");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    private void gettextvalue() {
        stremail = editemail.getText().toString();
    }
}

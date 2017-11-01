package topsint.myfriendsfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class OTP_for_registration extends AppCompatActivity {

    EditText otp;
    String otp1;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_for_registration);

        otp=(EditText) findViewById(R.id.otp_for_registration);
        next=(Button) findViewById(R.id.btnnext_in_otp);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCheckValidation()){
                    if (otp.getText().toString().equals(StoreData.STATIC_OTP)){
                        gettextvalue();
                        Intent i=new Intent(OTP_for_registration.this,Name_Activity.class);
                        startActivity(i);
                    }
                    else if (!otp.getText().toString().equals(StoreData.STATIC_OTP)){
                        SnackBar.makeLong(OTP_for_registration.this,"Please Enter Valid code");
                    }
                }
            }
        });
    }
    private boolean isCheckValidation() {
        boolean check = true;
        try {
            gettextvalue();
            if (otp1.isEmpty()) {
                check = false;
                SnackBar.makeShort(OTP_for_registration.this, "Please Insert OTP number");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    private void gettextvalue() {
        otp1 = otp.getText().toString();
    }
}



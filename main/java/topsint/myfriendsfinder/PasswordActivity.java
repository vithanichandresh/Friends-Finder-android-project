package topsint.myfriendsfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PasswordActivity extends AppCompatActivity {

    EditText Password;
    Button btnnext;
    String strPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Password=(EditText) findViewById(R.id.password_for_register);
        btnnext=(Button) findViewById(R.id.btnNext_in_Password);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCheckValidation()){
                    gettextvalue();
                    Intent i=new Intent(PasswordActivity.this,setProfileActivity.class);
                    StoreData.STATIC_PASSWORD=strPassword;
                    startActivity(i);
                }
            }
        });
    }
    private boolean isCheckValidation() {
        boolean check = true;
        try {
            gettextvalue();
            if (strPassword.isEmpty()) {
                check = false;
                SnackBar.makeShort(PasswordActivity.this, "PAssword can't be empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    private void gettextvalue() {
        strPassword = Password.getText().toString();
    }
}

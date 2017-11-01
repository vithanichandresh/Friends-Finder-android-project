package topsint.myfriendsfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Name_Activity extends AppCompatActivity {

    EditText username;
    Button btnNext;
    String strName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_);

        username=(EditText) findViewById(R.id.edt_username);
        btnNext=(Button) findViewById(R.id.btnNextUsername);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCheckValidation()){
                    gettextvalue();
                    Intent i=new Intent(Name_Activity.this,Gender.class);
                    StoreData.STATIC_Name=strName;
                    startActivity(i);
                }
            }
        });
    }
    private boolean isCheckValidation() {
        boolean check = true;
        try {
            gettextvalue();
            if (strName.isEmpty()) {
                check = false;
                SnackBar.makeShort(Name_Activity.this, "Username can't be empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    private void gettextvalue() {
        strName = username.getText().toString();
    }
}

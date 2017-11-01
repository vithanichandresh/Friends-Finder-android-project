package topsint.myfriendsfinder;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class birthdayActivity extends AppCompatActivity {

    EditText txtbithdate;
    Button btnNext;
    ImageButton imgbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        txtbithdate=(EditText) findViewById(R.id.txt_birthdate);
        btnNext=(Button) findViewById(R.id.btnNext_in_Birthdate);
        imgbtn=(ImageButton) findViewById(R.id.imageButton);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment=new DateFormate();
                dialogFragment.show(getFragmentManager(),"DatePicker");
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(birthdayActivity.this,PasswordActivity.class);
                StoreData.STATIC_BIRTHDAY=txtbithdate.getText().toString();
                startActivity(i);
            }
        });

    }

}

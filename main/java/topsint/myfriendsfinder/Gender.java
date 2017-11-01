package topsint.myfriendsfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Gender extends AppCompatActivity {

    RadioGroup radioGroupGender;
    RadioButton male,female,radioGenderButton;
    Button btnNext_in_Gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        radioGroupGender=(RadioGroup) findViewById(R.id.radio_gender_group);
     //   male=(RadioButton) findViewById(R.id.Radiobtn_Male);
       // female=(RadioButton) findViewById(R.id.Radiobtn_FeMale);
        btnNext_in_Gender=(Button) findViewById(R.id.btnnext_in_Gender);

        btnNext_in_Gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int SelectedId= radioGroupGender.getCheckedRadioButtonId();
                if (SelectedId != -1){
                    radioGenderButton=(RadioButton) findViewById(SelectedId);
                    Intent i=new Intent(Gender.this,birthdayActivity.class);
                    StoreData.STATIC_GENDER=radioGenderButton.getText().toString();
                    startActivity(i);
                }
            }
        });


    }
}

package topsint.myfriendsfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

public class setProfileActivity extends AppCompatActivity {
    ImageView imgforprofile;
    Button btnFinish;
    boolean flag = false;
    Uri selectedImage;

    Bitmap yourSelectedImage;
    TextView txtchoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);


        txtchoose = (TextView) findViewById(R.id.txtchooseimage);
        imgforprofile = (ImageView) findViewById(R.id.image_for_profile_in_register);
        btnFinish = (Button) findViewById(R.id.btnFinish_In_setProfile);

        if (flag) {
            btnFinish.setEnabled(true);
            txtchoose.setText("");
        } else {
            btnFinish.setEnabled(false);
        }
        imgforprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();
            }
        });


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar calendar = Calendar.getInstance();


                Bitmap bitmapImage = ((BitmapDrawable) imgforprofile.getDrawable()).getBitmap();
                String myBase64Image = encodeToBase64(bitmapImage, Bitmap.CompressFormat.JPEG, 100);

                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("name", "img-" + Calendar.getInstance().getTimeInMillis());
                hashMap.put("username", StoreData.STATIC_Name);
                hashMap.put("email", StoreData.STATIC_EMAIL);
                hashMap.put("birthdate", StoreData.STATIC_BIRTHDAY);
                hashMap.put("password", StoreData.STATIC_PASSWORD);
                hashMap.put("gender", StoreData.STATIC_GENDER);
                hashMap.put("imgPath", myBase64Image);
                hashMap.put("Latitude", StoreData.Latitude);
                hashMap.put("Longitude", StoreData.Longitude);
                Log.e("data", StoreData.STATIC_Name + ", " + StoreData.STATIC_EMAIL + ", " + StoreData.STATIC_BIRTHDAY +"," +StoreData.STATIC_PASSWORD+"," +myBase64Image+", "+StoreData.Latitude+", "+StoreData.Longitude);

                JSONHelper jsonHelper = new JSONHelper(setProfileActivity.this, "http://"+StoreData.STATIC_IP_ADDRESS+":8080/friends_finder/registration",hashMap, new OnAsyncLoader() {
                    @Override
                    public void onResult(String result) {
                        Log.e("result", result + " profile");
                        Toast.makeText(setProfileActivity.this, "data inserted" + result, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(setProfileActivity.this, LoginActivity.class));
                        finish();
                    }
                });
                jsonHelper.execute();
            }

        });


    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallary", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(setProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else if (items[item].equals("Choose from Gallary")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), 0);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("requestCode", requestCode + " test");
        switch (requestCode) {
            case 0:

                if (resultCode == RESULT_OK) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);

                        cursor.close();
                        // Convert file path into bitmap image using below line.
                        yourSelectedImage = BitmapFactory.decodeFile(new File(filePath).getAbsolutePath());
                        imgforprofile.setImageBitmap(yourSelectedImage);
                        Log.e("State", filePath + "_test");
                        flag = true;
                        btnFinish.setEnabled(true);
                    }
                break;
            case 1:
                 if (resultCode == RESULT_OK){
                     if (requestCode == 1){
                             Bitmap imgurl = (Bitmap) data.getExtras().get("data");
                             imgforprofile.setImageBitmap(imgurl);
                         btnFinish.setEnabled(true);
                         }
                     }
                }

        }
    }



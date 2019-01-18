package com.mic.music.mic.Newmic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mic.music.mic.R;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.BaseActivity;
import com.mic.music.mic.utils.ConnectionDetector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private File file;
    private int GALLERY = 1, CAMERA = 2;
    private String userChoosenTask;
    private static final String IMAGE_DIRECTORY = "/musteat";

    Button submitbutton;
    ImageView profile;
    EditText user_name, user_email, user_phone, user_address;
    RadioGroup rgGendar,rgOrganisation;
    ImageView show_calender;
    TextView select_birth;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private String userName,userEmail,userPhone,userOrgnisation,userAddress,userCity,userGender,userDOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        init();
    }
    private void init()
    {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        user_phone = findViewById(R.id.user_phone);
        user_address = findViewById(R.id.user_loaction);
        submitbutton = findViewById(R.id.submit_button);
        profile = findViewById(R.id.user_profile);
        rgGendar = findViewById(R.id.rgGendar);
        rgOrganisation = findViewById(R.id.rgOrganisation);
        show_calender = findViewById(R.id.show_calender);
        select_birth = findViewById(R.id.select_birth);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        show_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDatePickerDialog.show();
            }
        });


        rgGendar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(mContext, rb.getText(), Toast.LENGTH_SHORT).show();
                    userGender = rb.getText().toString();
                }

            }
        });

        rgOrganisation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(mContext, rb.getText(), Toast.LENGTH_SHORT).show();
                    userOrgnisation = rb.getText().toString();
                }

            }
        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AudioVedio.class);
                startActivity(intent);
                }
        });

        setDateTimeField();
    }

    private void setDateTimeField() {
        show_calender.setOnClickListener(this);
        String date = "1-1-2000";
        String parts[] = date.split("-");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                select_birth.setText(dateFormatter.format(newDate.getTime()));
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onClick(View view) {
        fromDatePickerDialog.show();

    }
    /*
     * Capture image
     * */
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result= Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY);
		/*Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, GALLERY);*/
    }

    private void cameraIntent() {
		/*Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(intent, CAMERA);*/
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(mContext, "Image Saved!", Toast.LENGTH_SHORT).show();
                    profile.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profile.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(mContext, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        Log.e("File", wallpaperDirectory.getPath());
        Log.e("wallpaperDirectory", "..." + wallpaperDirectory.exists());
        Log.e("wallpaperDirectory", "===" + wallpaperDirectory.mkdirs());
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            file = new File(wallpaperDirectory, "MustEatProfile.jpg");
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(mContext, new String[]{file.getPath()},
                    new String[]{"image/*"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + file.getAbsolutePath());

            return file.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    public void api() {
        userName = user_name.getText().toString();
        userEmail = user_email.getText().toString();
        userPhone = user_email.getText().toString();
        userAddress = user_address.getText().toString();
        userDOB = select_birth.getText().toString();

        if (file == null) {
            Toast.makeText(mContext, "Please select Image", Toast.LENGTH_LONG).show();
        }else if (!userEmail.matches(emailPattern))
        {
            user_email.setError("Enter Email ID");
        }
        else {
            if (cd.isNetworkAvailable()) {
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("fileToUpload", file.getName(), mFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), userName);
                RequestBody email = RequestBody.create(MediaType.parse("text/plain"), userEmail);
                RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), userPhone);
                RequestBody orgnisation = RequestBody.create(MediaType.parse("text/plain"), userOrgnisation);
                RequestBody address = RequestBody.create(MediaType.parse("text/plain"), userAddress);
                RequestBody city = RequestBody.create(MediaType.parse("text/plain"), userCity);
                RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), userGender);
                RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), userDOB);
                RetrofitService.signup(new Dialog(mContext), retrofitApiClient.signup(name,email,phone,orgnisation,address,city,gender,dob,fileToUpload), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody loginModal = (ResponseBody) result.body();
                        assert loginModal != null;

                            Alerts.show(mContext, loginModal.toString());

                    }

                    @Override
                    public void onResponseFailed(String error) {
                        Alerts.show(mContext, error);
                    }
                });
            } else {
                cd.show(mContext);
            }
        }
    }


}

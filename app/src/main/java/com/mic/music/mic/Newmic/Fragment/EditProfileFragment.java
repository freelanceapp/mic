package com.mic.music.mic.Newmic.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mic.music.mic.Newmic.Activity.HomeActivity;
import com.mic.music.mic.Newmic.Activity.MainActivity;
import com.mic.music.mic.R;
import com.mic.music.mic.constant.Constant;
import com.mic.music.mic.model.User;
import com.mic.music.mic.model.login_responce.LoginModel;
import com.mic.music.mic.model.login_responce.LoginModel1;
import com.mic.music.mic.model.otp_responce.OtpModel;
import com.mic.music.mic.model.token_responce.TokenModel;
import com.mic.music.mic.model.user_responce.UserProfileModel;
import com.mic.music.mic.retrofit_provider.RetrofitService;
import com.mic.music.mic.retrofit_provider.WebResponse;
import com.mic.music.mic.utils.Alerts;
import com.mic.music.mic.utils.AppLocationService;
import com.mic.music.mic.utils.AppPreference;
import com.mic.music.mic.utils.BaseFragment;
import com.mic.music.mic.utils.ConnectionDetector;
import com.mic.music.mic.utils.LocationAddress;

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
import retrofit2.Response;

import static com.mic.music.mic.Newmic.Activity.HomeActivity.user_id;

public class EditProfileFragment extends BaseFragment implements View.OnClickListener , AdapterView.OnItemClickListener{
   private View view;
    EditText user_name, user_email, user_phone, user_address , spinner_city;
    RadioGroup rgGendar,rgOrganisation;
    ImageView show_calender;
    TextView select_birth;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    Button submitbutton, emailVarificationBtn;
    CircleImageView profile;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView tvLocateMe;
    AppLocationService appLocationService;
    private String mobileNumber1;
    private String userName,userEmail,userPhone,userOrgnisation,userAddress,userCity,userGender,userDOB;
    String emailOtp1;
    private File file;
    private int GALLERY = 1, CAMERA = 2;
    private String userChoosenTask;
    private static final String IMAGE_DIRECTORY = "/mic";
    RadioButton rd_school,rd_college,rd_work,rd_other,rd_male,rd_female,rd_other_gender;
    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_edit_profile2, container, false);

        mContext = getActivity();
        activity = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        appLocationService = new AppLocationService(mContext);

        init();
        return view;
    }

    public void init()
    {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        user_name = view.findViewById(R.id.user_name1);
        emailVarificationBtn = view.findViewById(R.id.emailVarificationBtn);
        user_email = view.findViewById(R.id.user_email1);
        user_address = view.findViewById(R.id.user_loaction1);
        submitbutton = view.findViewById(R.id.submit_button1);
        profile = view.findViewById(R.id.user_profile1);
        rgGendar = view.findViewById(R.id.rgGendar1);
        rgOrganisation = view.findViewById(R.id.rgOrganisation1);
        show_calender = view.findViewById(R.id.show_calender);
        select_birth = view.findViewById(R.id.select_birth);
        tvLocateMe = view.findViewById(R.id.tvLocateMe);
        spinner_city = view.findViewById(R.id.spinner_city1);
        rd_school = view.findViewById(R.id.rd_school);
        rd_college = view.findViewById(R.id.rd_college);
        rd_other = view.findViewById(R.id.rd_other);
        rd_work = view.findViewById(R.id.rd_work);


        profileApi();

        setDateTimeField();
        tvLocateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude, mContext,new GeocoderHandler());
                } else {
                    showSettingsAlert();
                }

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        user_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userEmail = user_email.getText().toString();
                if (!userEmail.matches(emailPattern))
                {
                    user_email.setError("Enter Email ID");
                    emailVarificationBtn.setVisibility(View.GONE);

                }else {
                    emailVarificationBtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emailVarificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEmail();
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

        show_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDatePickerDialog.show();
            }
        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTextUpdate();
                api();
            }
        });

    }

    private void getEmail() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getlogin(new Dialog(mContext), retrofitApiClient.getLogin(userEmail), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    LoginModel1 loginModal = (LoginModel1) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
                        showDialog();
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
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


    public void showDialog(){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_email_otp);

        TextView text = (TextView) dialog.findViewById(R.id.emailTv);
        text.setText(userEmail);

        final EditText emailOtp = (EditText) dialog.findViewById(R.id.emailOtpET);

        Button dialogButton = (Button) dialog.findViewById(R.id.emailOTPVarificationBtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailOtp1 = emailOtp.getText().toString();
                if (emailOtp1.length() == 6) {
                    otpVarification1();
                } else {
                    emailOtp.setError("Please Enter OTP");
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void otpVarification1() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getOtp(new Dialog(mContext), retrofitApiClient.getOtp1(userEmail,"111111"), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    OtpModel loginModal = (OtpModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        emailVarificationBtn.setText("varified");
                        user_email.setFocusable(false);
                        emailVarificationBtn.setClickable(false);
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
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


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        fromDatePickerDialog.show();

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

        fromDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                select_birth.setText(dateFormatter.format(newDate.getTime()));
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String city;
            String contry;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    city = bundle.getString("city");
                    contry = bundle.getString("contry");
                    break;
                default:
                    locationAddress = null;
                    city = null;
            }
            user_address.setText(locationAddress);
            spinner_city.setText(city);
        }
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

    private void profileApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getProfile(new Dialog(mContext), retrofitApiClient.getProfile(user_id), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    UserProfileModel loginModal = (UserProfileModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                        Alerts.show(mContext, loginModal.getMessage());
                        user_name.setText(loginModal.getUser().getParticipantName());
                        user_email.setText(loginModal.getUser().getParticipantEmail());
                        select_birth.setText(loginModal.getUser().getParticipantDob());
                        Glide.with(mContext).load(loginModal.getUser().getParticipantImage()).into(profile);
                        user_address.setText(loginModal.getUser().getParticipantAddress());
                       /* if (loginModal.getUser().getParticipantEmailVerificationStatus().equals(null))
                        {
                            user_email.setError("Please varified Email Id");
                            emailVarificationBtn.setVisibility(View.VISIBLE);
                            user_email.setFocusable(false);

                        }else {
                            emailVarificationBtn.setText("varified");
                            user_email.setFocusable(false);
                            emailVarificationBtn.setClickable(false);
                        }*/
                    } else {
                        Alerts.show(mContext, loginModal.getMessage());
                    }
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


    public void api() {
        String strUserId = AppPreference.getStringPreference(mContext, Constant.User_Id);

        if (file == null) {
            Toast.makeText(mContext, "Please select Image", Toast.LENGTH_LONG).show();
        }
        else {
            if (cd.isNetworkAvailable()) {
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                RequestBody id = RequestBody.create(MediaType.parse("text/plain"), strUserId);
                RetrofitService.profileimage(new Dialog(mContext), retrofitApiClient.profileimage(id, fileToUpload), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        TokenModel loginModal = (TokenModel) result.body();
                        assert loginModal != null;
                        Alerts.show(mContext, loginModal.getMessage());

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

    private void getTextUpdate() {
        userName = user_name.getText().toString();
        userEmail = user_email.getText().toString();
        userPhone = user_email.getText().toString();
        userAddress = user_address.getText().toString();
        userDOB = select_birth.getText().toString();

        if (!userEmail.matches(emailPattern))
        {
            user_email.setError("Enter Email ID");
        }
        else {

            if (cd.isNetworkAvailable()) {
                RetrofitService.updateProfile(new Dialog(mContext), retrofitApiClient.updateProfile1(userName,userEmail,userGender,user_id,userDOB,userOrgnisation,userAddress,userCity,"mp","india"), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        TokenModel loginModal = (TokenModel) result.body();
                        assert loginModal != null;
                        if (!loginModal.getError()) {
                            Alerts.show(mContext, loginModal.getMessage());
                        } else {
                            Alerts.show(mContext, loginModal.getMessage());
                        }
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

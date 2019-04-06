package com.online.music.mic.Newmic.Activity;

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
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.online.music.mic.Newmic.About;
import com.online.music.mic.R;
import com.online.music.mic.constant.Constant;
import com.online.music.mic.model.User;
import com.online.music.mic.model.login_responce.LoginModel;
import com.online.music.mic.model.micpagecontents.AppContentMainModal;
import com.online.music.mic.model.otp_responce.OtpModel;
import com.online.music.mic.model.token_responce.TokenModel;
import com.online.music.mic.retrofit_provider.RetrofitService;
import com.online.music.mic.retrofit_provider.WebResponse;
import com.online.music.mic.utils.Alerts;
import com.online.music.mic.utils.AppLocationService;
import com.online.music.mic.utils.AppPreference;
import com.online.music.mic.utils.BaseActivity;
import com.online.music.mic.utils.ConnectionDetector;
import com.online.music.mic.utils.LocationAddress;
import com.online.music.mic.utils.MyStringRandomGen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.widget.Toast.*;

public class MainActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private TextView mNameView, btnRead;

    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyDOx-Hgq_GBt7zstNRcp-Y_6UjqhGSdChA";

    private File file;
    private int GALLERY = 1, CAMERA = 2;
    private String userChoosenTask;
    private static final String IMAGE_DIRECTORY = "/musteat";
    AppLocationService appLocationService;
    String emailOtp1;
    Button submitbutton, emailVarificationBtn;
    CircleImageView profile;
    EditText user_name, user_email, user_phone, user_address,et_city,et_country_ma,et_state_ma;
    RadioGroup rgGendar, rgOrganisation;
    ImageView show_calender;
    TextView select_birth;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView tvLocateMe;
    private String mobileNumber1;
    private String userId, userName, userEmail, userPhone, userOrgnisation, userAddress, userGender, userDOB,usercity,usercountry,userstateadd;

    private CheckBox checkbox_termconditon;
    private boolean check = false;
   private String strchecked = "0";
    MyStringRandomGen myStringRandomGen;

    String strPrivacyPolicyData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appLocationService = new AppLocationService(MainActivity.this);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        String data = AppPreference.getStringPreference(mContext, Constant.User_Data);
        Log.e("Profile ", "..." + data);
        init();
    }

    private void init() {

       // et_home_ma = findViewById(R.id.et_home_add);

        myStringRandomGen = new MyStringRandomGen();
        checkbox_termconditon = findViewById(R.id.checkbox_condition);
        checkbox_termconditon.setOnClickListener(this);
        getPageContent();
        et_city = findViewById(R.id.et_city_add);
        et_country_ma = findViewById(R.id.et_coutnry_add);
        et_state_ma = findViewById(R.id.et_state_add);
        tvLocateMe = findViewById(R.id.tvLocateMe);

        userId = getIntent().getStringExtra("user_id");
        mobileNumber1 = AppPreference.getStringPreference(mContext, Constant.User_Mobile);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        user_name = findViewById(R.id.user_name);
        emailVarificationBtn = findViewById(R.id.emailVarificationBtn);
        user_email = findViewById(R.id.user_email);
        user_phone = findViewById(R.id.user_phone);
       user_address = findViewById(R.id.user_loaction);
        submitbutton = findViewById(R.id.submit_button);
        submitbutton.setOnClickListener(this);
        profile = findViewById(R.id.user_profile);
        rgGendar = findViewById(R.id.rgGendar);
        rgOrganisation = findViewById(R.id.rgOrganisation);
        show_calender = findViewById(R.id.show_calender);
        select_birth = findViewById(R.id.select_birth);
        btnRead = findViewById(R.id.btnRead);
        Log.e("USer ID ", "..." + User.getUser().getUser().getParticipantId());
        user_phone.setText(mobileNumber1);
        user_phone.setFocusable(false);
        setDateTimeField();

        checkbox_termconditon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true)
                {
                     strchecked = "1";
                }else {
                    strchecked = "0";
                }
            }
        });
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strchecked.equals("0")){
                    Toast.makeText(mContext,"Check please Terms and Condition" , Toast.LENGTH_SHORT).show();
                }else{
                    api();
                   // getTextUpdate();
                }
            }
        });
        tvLocateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude, getApplicationContext(), new GeocoderHandler());
                } else {
                    showSettingsAlert();
                }

            }
        });

        emailVarificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
                    //Toast.makeText(mContext, rb.getText(), Toast.LENGTH_SHORT).show();
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
                    // Toast.makeText(mContext, rb.getText(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(mContext, rb.getText(), Toast.LENGTH_SHORT).show();
                    userOrgnisation = rb.getText().toString();
                }

            }
        });

        user_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userEmail = user_email.getText().toString();
                if (!userEmail.matches(emailPattern)) {
                    user_email.setError("Enter Email ID");
                    //emailVarificationBtn.setVisibility(View.GONE);

                } else {
                    // emailVarificationBtn.setVisibility(View.VISIBLE);
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

      /*  AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        autoCompView.setOnItemClickListener(this);
        setDateTimeField();*/

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(MainActivity.this, About.class);
                intent5.putExtra("pagetitile", "Privacy Policy");
                intent5.putExtra("pagecontent", strPrivacyPolicyData);
                startActivity(intent5);
            }
        });
    }

    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        makeText(this, str, LENGTH_SHORT).show();
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            mNameView.setText(Html.fromHtml(place.getAddress() + ""));
        }
    };

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

        fromDatePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                select_birth.setText(dateFormatter.format(newDate.getTime()));
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
     //   fromDatePickerDialog.show();

        switch (view.getId()){
           /* case R.id.checkbox_condition:
                if (((CheckBox)view).isChecked()){
                        check = true;
                    Toast.makeText(this,"Checked",Toast.LENGTH_SHORT).show();
                }
                else {
                    check = false;
                    Toast.makeText(this,"Unchacked",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.submit_button:
                if (((CheckBox)view).isChecked()){
                    check = true;
                    api();
                    getTextUpdate();
                }else{
                    check = false;
                    checkbox_termconditon.isChecked();
                    Toast.makeText(MainActivity.this,"Please check terms and condition",Toast.LENGTH_SHORT).show();
                }
                break;*/
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
                    makeText(mContext, "Image Saved!", LENGTH_SHORT).show();
                    profile.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    makeText(mContext, "Failed!", LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profile.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            makeText(mContext, "Image Saved!", LENGTH_SHORT).show();
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
            file = new File(wallpaperDirectory, myStringRandomGen.generateRandomString()+"_MIC_Profile.jpg");
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(mContext, new String[]{file.getPath()}, new String[]{"image/*"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + file.getAbsolutePath());

            return file.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void api() {
        //String strUserId = AppPreference.getStringPreference(mContext, Constant.User_Id);
        if (file == null) {
            makeText(mContext, "Please select Image", LENGTH_LONG).show();
        } else {
            if (cd.isNetworkAvailable()) {
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                RequestBody id = RequestBody.create(MediaType.parse("text/plain"), userId);
                RetrofitService.profileimage(new Dialog(mContext), retrofitApiClient.profileimage(id, fileToUpload), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        TokenModel loginModal = (TokenModel) result.body();
                        assert loginModal != null;
                        Alerts.show(mContext, loginModal.getMessage());
                        getTextUpdate();
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

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String city;
            String contry = "";
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
            et_city.setText(city);
            et_country_ma.setText(contry);
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        MainActivity.this.startActivity(intent);
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

    private void getEmail() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getEmaillogin(new Dialog(mContext), retrofitApiClient.getLogin1(userEmail), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    LoginModel loginModal = (LoginModel) result.body();
                    assert loginModal != null;
                    if (!loginModal.getError()) {
                       // Alerts.show(mContext, loginModal.getMessage());
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

    private void getTextUpdate() {
        //String strUserId = AppPreference.getStringPreference(mContext, Constant.User_Id);
        userName = user_name.getText().toString();
        userEmail = user_email.getText().toString();
        userPhone = user_phone.getText().toString();
        userAddress = user_address.getText().toString();
        userDOB = select_birth.getText().toString();
        userAddress = user_address.getText().toString();
      //  userhomeadd  = et_home_ma.getText().toString();
        usercity = et_city.getText().toString();
        usercountry = et_country_ma.getText().toString();
        userstateadd = et_state_ma.getText().toString();
       // String strCityStateCountry = ((AutoCompleteTextView) findViewById(R.id.autoCompleteTextView)).getText().toString();
      //  String strSplit[] = strCityStateCountry.split(",");

      /*  String strCity = strSplit[0];
        String strState = strSplit[1];
        String strCountry = strSplit[2];*/

        if (!userEmail.matches(emailPattern)) {
            user_email.setError("Enter Email ID");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.updateProfile(new Dialog(mContext), retrofitApiClient.updateProfile1(
                        userName,userEmail,userGender,userId,userDOB,userOrgnisation,userAddress,usercity,userstateadd,
                        usercountry), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        TokenModel loginModal = (TokenModel) result.body();
                        assert loginModal != null;
                        if (!loginModal.getError()) {
                            Alerts.show(mContext, loginModal.getMessage());
                            AppPreference.setBooleanPreference(mContext, Constant.Is_Login, true);
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
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

    public void showDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
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
            RetrofitService.getOtp(new Dialog(mContext), retrofitApiClient.getOtp1(userEmail, emailOtp1), new WebResponse() {
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

    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:in");
            sb.append("&types=(cities)");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("Main ", "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e("Main ", "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e("Main ", "Cannot process JSON results", e);
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return String.valueOf(resultList.get(index));
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());
                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }


    private void getPageContent() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.appContentPage(new Dialog(mContext), retrofitApiClient.getPageContent(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    AppContentMainModal appContentMainModal = (AppContentMainModal) result.body();
                    if (!appContentMainModal.getError()) {
                        //   Alerts.show(mContext, appContentMainModal.getMessage());

                        strPrivacyPolicyData = appContentMainModal.getPageContent().get(0).getPageContent();
                    } else {
                        Alerts.show(mContext, appContentMainModal.getMessage());
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
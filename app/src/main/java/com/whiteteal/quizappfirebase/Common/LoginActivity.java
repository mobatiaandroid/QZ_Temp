package com.whiteteal.quizappfirebase.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.tv.TvContract;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.whiteteal.quizappfirebase.BackgroundService.BackgroundSoundService;
import com.whiteteal.quizappfirebase.Common.Login.Adapter.StudentAdapter;
import com.whiteteal.quizappfirebase.Common.Login.Model.StudentModels;
import com.whiteteal.quizappfirebase.MainActivity;
import com.whiteteal.quizappfirebase.Manager.AppPreferenceManager;
import com.whiteteal.quizappfirebase.Manager.AppUtilityMethod;
import com.whiteteal.quizappfirebase.R;
import com.whiteteal.quizappfirebase.recyclerviewmanager.RecyclerItemListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ir.alirezabdn.wp7progress.WP10ProgressBar;
import ir.alirezabdn.wp7progress.WP7ProgressBar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_BLOOD_GROUP;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_BOARD_ID;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_CLASS;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_DIV;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_DOB;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_ELECT1;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_ELECT2;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_FATHER;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_GENDER;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_HOUSE;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_ID;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_MENTOR;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_MOTHER;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_NAME;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_PARENT_ID;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_RESPONSE;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_RESPONSECODE;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_STATUSCODE;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_STUDENTS;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_STUDENT_GRNO;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_STUDENT_PHOTO;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_USERCODE;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.RESPONSE_ACCESSTOKEN_EXPIRED;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.RESPONSE_ACCESSTOKEN_MISSING;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.RESPONSE_INTERNALSERVER_ERROR;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.RESPONSE_INVALID_ACCESSTOKEN;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.RESPONSE_SUCCESS;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.STATUSCODE_INVALIDUSER;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.STATUSCODE_INVALIDUSERNAMEORPASWD;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.STATUSCODE_MISSING_PARAMETER;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.STATUSCODE_SUCCESS;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.STATUS_ERROR_OCCURED;
import static com.whiteteal.quizappfirebase.URL_Constants.URLConstant.URL_FORGOT_PASS;
import static com.whiteteal.quizappfirebase.URL_Constants.URLConstant.URL_METHOD_GET_ACCESSTOKEN;
import static com.whiteteal.quizappfirebase.URL_Constants.URLConstant.URL_PARENT_LOGIN;

public class LoginActivity extends AppCompatActivity {
    
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context mContext;
    Activity activity;
    EditText Email,Password;
    TextView ForgotPass,versionTxt;
    String UserId;

    Button LoginBtn;
    public ArrayList<StudentModels> studentModelsArrayList=new ArrayList<>();
    WP7ProgressBar wp10ProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg));
        mContext = this;
        activity = this;
        
        IniUi();
    }

    private void IniUi() {
        Email = findViewById(R.id.login_email);
        Password = findViewById(R.id.login_pass);
        LoginBtn = findViewById(R.id.login_btn);
        ForgotPass = findViewById(R.id.forgot_pass_txt);
        wp10ProgressBar = findViewById(R.id.login_loader);
        versionTxt = findViewById(R.id.TestVersion);

//        Email.setText("9946988195");
//        Email.setText("95259742");
//        Password.setText("123");

//        AppPreferenceManager.setAppVersion(mContext,"Test Build V 1.7");   // Test Build Version 1.7 --> Rules Scrolling, LeaderBoard Status & Result Page status --> Firebase Auth
        versionTxt.setVisibility(View.GONE);




        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                if (Email.getText().toString().equals("")){
                    Email.setError("Please Enter Mobile Number");

                }else if (Password.getText().toString().equals("")){
                    Password.setError("Please Enter Password");
                }else {
                   // CheckUser();
                    if (AppUtilityMethod.isNetworkConnected(mContext)) {
                        wp10ProgressBar.showProgressBar();
//                        StartFirabaseAuthentication();
                        AppPreferenceManager.setPhone(mContext,Email.getText().toString());
                        AppPreferenceManager.setPass(mContext,Password.getText().toString());
                        LoginUsers(Email.getText().toString(), Password.getText().toString());
                    }else {
                        AppUtilityMethod.showSingleButton((Activity)mContext,getString(R.string.please_check_internet));
                    }
                }
            }
        });

        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Email.getText().toString().equals("")){
                    Email.setError("Please Enter Mobile Number");

                }else {
                    // CheckUser();
                    if (AppUtilityMethod.isNetworkConnected(mContext)) {
                        ShowForgotPassAlert(activity, "Do you want to Continue?");
                    }else {
                        AppUtilityMethod.showSingleButton((Activity)mContext,getString(R.string.please_check_internet));
                    }
                }
            }
        });

    }

    private void ShowForgotPassAlert(Activity activity, String s) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_double_btn);

        TextView Msg = dialog.findViewById(R.id.double_msg_txt);
        Msg.setText(s);

        TextView logout = dialog.findViewById(R.id.double_ok_txt);
        logout.setText("Continue");

        TextView cancel = dialog.findViewById(R.id.double_cancel_txt);
        cancel.setText("Cancel");

        ImageView OK = dialog.findViewById(R.id.double_ok_btn);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // RestartApp();
                wp10ProgressBar.showProgressBar();
                ProceedForgotPass(Email.getText().toString());
            }
        });

        ImageView CancelBtn = dialog.findViewById(R.id.double_cancel_btn);
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void ProceedForgotPass(String moblie) {
        okhttp3.OkHttpClient client = new OkHttpClient();
        okhttp3.MediaType MEDIA_TYPE = MediaType.parse("application/json");
        JSONObject postdata = new JSONObject();

        try {
            postdata.put("phone",moblie);
        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(postdata.toString(),MEDIA_TYPE);

        Request request = new Request.Builder()
                .url(URL_FORGOT_PASS)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ResponseValue: E",e.getMessage());
                wp10ProgressBar.hideProgressBar();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String Response = response.body().string();
                Log.d("ResponseValue: Forgot",Response);

                try {
                    JSONObject obj = new JSONObject(Response);
                    String response_code = obj.getString(JTAG_RESPONSECODE);
                    if (response_code.equalsIgnoreCase(RESPONSE_SUCCESS)) {
                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
                        String status_code = secobj.getString(JTAG_STATUSCODE);

                        if (status_code.equalsIgnoreCase(STATUSCODE_SUCCESS)) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, "Password has been sent to your phone number", Toast.LENGTH_SHORT).show();
                                    wp10ProgressBar.hideProgressBar();
                                }
                            });

                        }else if (status_code.equalsIgnoreCase(STATUSCODE_INVALIDUSER)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext,getString(R.string.invalid_user));
                                    wp10ProgressBar.hideProgressBar();
                                }
                            });
                        } else if (status_code.equalsIgnoreCase(STATUSCODE_INVALIDUSERNAMEORPASWD)) {
                            System.out.println("ResponseValue: "+"IVALUDE USERNAME OR PASS");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext,getString(R.string.invalid_usr_pswd));
                                    wp10ProgressBar.hideProgressBar();
                                }
                            });


                        } else if (status_code.equalsIgnoreCase(STATUSCODE_MISSING_PARAMETER)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext,getString(R.string.missing_parameter));
                                    wp10ProgressBar.hideProgressBar();
                                }
                            });



                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext,getString(R.string.common_error));
                                    wp10ProgressBar.hideProgressBar();
                                }
                            });

                        }
                         /*else {
                            AppUtilityMethods.showDialogAlertDismiss((Activity) mContext, getString(R.string.error_heading), getString(R.string.common_error), R.drawable.infoicon,  R.drawable.roundblue);

                        }*/
                    } else if (response_code.equalsIgnoreCase(RESPONSE_INTERNALSERVER_ERROR)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.internal_server_error));
                                wp10ProgressBar.hideProgressBar();
                            }
                        });


                    } else if (response_code.equalsIgnoreCase(RESPONSE_INVALID_ACCESSTOKEN) || response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) || response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED)) {
//                        AppUtilityMethod.getToken(mContext, new AppUtilityMethod.GetTokenSuccess() {
//                            @Override
//                            public void tokenrenewed() {
//                                sendLoginToServer(URL_PARENT_LOGIN);
//                            }
//                        });
                        wp10ProgressBar.hideProgressBar();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtilityMethod.showDialogAlertDismiss((Activity) mContext,mContext.getString(R.string.common_error));
                                wp10ProgressBar.hideProgressBar();
                            }
                        });


                    }
                } catch (Exception ex) {
                    System.out.println("The Exception in edit profile is" + ex.toString());
                }

            }
        });

    }

    private void LoginUsers(String Phone, String Pass) {
        okhttp3.OkHttpClient client = new OkHttpClient();
        okhttp3.MediaType MEDIA_TYPE = MediaType.parse("application/json");
        JSONObject postdata = new JSONObject();

        try {
            postdata.put("phone",Phone);
            postdata.put("passwd",Pass);
            postdata.put("device_id","1");
            postdata.put("device_type","123456789");
        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(postdata.toString(),MEDIA_TYPE);

        Request request = new Request.Builder()
                .url(URL_PARENT_LOGIN)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ResponseValue: E",e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String Response = response.body().string();
                Log.d("ResponseValue: S",Response);

                try {
                    JSONObject obj = new JSONObject(Response);
                    String response_code = obj.getString(JTAG_RESPONSECODE);
                    if (response_code.equalsIgnoreCase(RESPONSE_SUCCESS)) {
                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
                        String status_code = secobj.getString(JTAG_STATUSCODE);
                        if (status_code.equalsIgnoreCase(STATUSCODE_SUCCESS)) {

                            String UserCode = secobj.getString(JTAG_USERCODE);
                            AppPreferenceManager.setUserCode(mContext,UserCode);

                            AppPreferenceManager.setUserId(mContext, secobj.optString(JTAG_PARENT_ID));
                            JSONArray studentListArray = secobj.getJSONArray(JTAG_STUDENTS);
                            AppPreferenceManager.setStudentsResponseFromLoginAPI(mContext, studentListArray.toString());
                            if (studentListArray.length() > 0) {
                                studentModelsArrayList.clear();
                                //AppController.studentArrayList.clear();
                                for (int i = 0; i < studentListArray.length(); i++) {
                                    JSONObject sObject = studentListArray.getJSONObject(i);
                                    AppPreferenceManager.setStudentStar(mContext,sObject.getString("total_stars"));
                                    StudentModels studentModels = new StudentModels();
                                    studentModels.setId(sObject.optString(JTAG_ID));
                                    studentModels.setName(sObject.optString(JTAG_NAME));
                                    studentModels.setDob(sObject.optString(JTAG_DOB));
                                    studentModels.setBlood_group(sObject.optString(JTAG_BLOOD_GROUP));
                                    studentModels.setBoardid(sObject.optString(JTAG_BOARD_ID));
                                    studentModels.setGender(sObject.optString(JTAG_GENDER));
                                    studentModels.setStudent_gr_no(sObject.optString(JTAG_STUDENT_GRNO));
                                    studentModels.setStudent_photo(sObject.optString(JTAG_STUDENT_PHOTO));
                                    studentModels.setHouse(sObject.optString(JTAG_HOUSE));
                                    studentModels.setStudent_division_name(sObject.optString(JTAG_DIV));
                                    studentModels.setClass_name(sObject.optString(JTAG_CLASS));
                                    studentModels.setFather(sObject.optString(JTAG_FATHER));
                                    studentModels.setMother(sObject.optString(JTAG_MOTHER));
                                    studentModels.setMentor(sObject.optString(JTAG_MENTOR));
                                    studentModels.setElect1(sObject.optString(JTAG_ELECT1));
                                    studentModels.setElect2(sObject.optString(JTAG_ELECT2));
                                    studentModels.setStars(sObject.optString("total_stars"));
                                    studentModelsArrayList.add(studentModels);
                                    //AppController.studentArrayList.add(studentModels);
                                }


                                if (studentModelsArrayList.size() == 1) {
                                    if (studentModelsArrayList.get(0).getBoardid().equals("1")) {
                                        String selectedFromList = "ISG";
                                        AppPreferenceManager.setSchoolSelection(mContext, selectedFromList);
                                    } else {
                                        String selectedFromList = "ISG-INT";
                                        AppPreferenceManager.setSchoolSelection(mContext, selectedFromList);
                                    }
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    AppPreferenceManager.setIsGuest(mContext, false);
                                    AppPreferenceManager.setIsUserAlreadyLoggedIn(mContext, true);
                                    intent.putExtra("confirmlogin",true);
                                    startActivity(intent);
                                    finish();
                                    AppPreferenceManager.setStudentId(mContext, studentModelsArrayList.get(0).getId());
                                    AppPreferenceManager.setStudentName(mContext, studentModelsArrayList.get(0).getName());
                                    AppPreferenceManager.setSingleStudent(mContext,"true");
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            wp10ProgressBar.hideProgressBar();
                                            AppPreferenceManager.setSingleStudent(mContext,"false");
                                            showStudentList(studentModelsArrayList);
                                        }
                                    });


                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.no_student_available));
                                        wp10ProgressBar.hideProgressBar();
                                    }
                                });


                            }
                        } else if (status_code.equalsIgnoreCase(STATUSCODE_INVALIDUSERNAMEORPASWD)) {
                            System.out.println("ResponseValue: "+"IVALUDE USERNAME OR PASS");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext,getString(R.string.invalid_usr_pswd));
                                    wp10ProgressBar.hideProgressBar();
                                }
                            });


                        } else if (status_code.equalsIgnoreCase(STATUSCODE_MISSING_PARAMETER)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext,getString(R.string.missing_parameter));
                                    wp10ProgressBar.hideProgressBar();
                                }
                            });



                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext,getString(R.string.common_error));
                                    wp10ProgressBar.hideProgressBar();
                                }
                            });

                        }
                         /*else {
                            AppUtilityMethods.showDialogAlertDismiss((Activity) mContext, getString(R.string.error_heading), getString(R.string.common_error), R.drawable.infoicon,  R.drawable.roundblue);

                        }*/
                    } else if (response_code.equalsIgnoreCase(RESPONSE_INTERNALSERVER_ERROR)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.internal_server_error));
                                wp10ProgressBar.hideProgressBar();
                            }
                        });


                    } else if (response_code.equalsIgnoreCase(RESPONSE_INVALID_ACCESSTOKEN) || response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) || response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED)) {
//                        AppUtilityMethod.getToken(mContext, new AppUtilityMethod.GetTokenSuccess() {
//                            @Override
//                            public void tokenrenewed() {
//                                sendLoginToServer(URL_PARENT_LOGIN);
//                            }
//                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtilityMethod.showDialogAlertDismiss((Activity) mContext,mContext.getString(R.string.common_error));
                                wp10ProgressBar.hideProgressBar();
                            }
                        });


                    }
                } catch (Exception ex) {
                    System.out.println("The Exception in edit profile is" + ex.toString());
                }


            }
        });
    }

    private void showStudentList(final ArrayList<StudentModels> studentModelsArrayList) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_student_list);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView dialogDismiss = (ImageView) dialog.findViewById(R.id.closebtn);
        RecyclerView studentList = (RecyclerView) dialog.findViewById(R.id.recycler_view_social_media);
        Button SelectStu = (Button)dialog.findViewById(R.id.btn_dismiss);
        //if(mSocialMediaArray.get())
        final int sdk = android.os.Build.VERSION.SDK_INT;
       /* if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            dialogDismiss.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.l));

        } else {
            dialogDismiss.setBackground(mContext.getResources().getDrawable(R.drawable.button));

        }*/

//        studentList.addItemDecoration(new DividerItemDecoration(mContext.getResources().getDrawable(line)));
//        studentList.addItemDecoration(new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.line)));
//        studentList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        studentList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        studentList.setLayoutManager(llm);

        StudentAdapter studentAdapter = new StudentAdapter(mContext, studentModelsArrayList);
        studentList.setAdapter(studentAdapter);
        dialogDismiss.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                dialog.dismiss();

            }

        });


        studentList.addOnItemTouchListener(new RecyclerItemListener(mContext, studentList,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
//                        dialog.dismiss();
                        dialogDismiss.setVisibility(View.GONE);
                        for (int i = 0; i<studentModelsArrayList.size();i++){
                            if (studentModelsArrayList.get(i).isClicked()) {
                                studentModelsArrayList.get(i).setClicked(false);
                            }
                        }
                        studentModelsArrayList.get(position).setClicked(true);
                        studentAdapter.notifyDataSetChanged();

                        if(studentModelsArrayList.get(position).getBoardid().equals("1")){
                            String selectedFromList="ISG";
                            AppPreferenceManager.setSchoolSelection(mContext, selectedFromList);
                        }else {
                            String selectedFromList="ISG-INT";
                            AppPreferenceManager.setSchoolSelection(mContext, selectedFromList);
                        }

                        AppPreferenceManager.setIsGuest(mContext,false);
                        AppPreferenceManager.setStudentId(mContext, studentModelsArrayList.get(position).getId());
                        AppPreferenceManager.setStudentName(mContext, studentModelsArrayList.get(position).getName());
                        AppPreferenceManager.setStudentStar(mContext,studentModelsArrayList.get(position).getStars());
                        System.out.println("STARS: "+studentModelsArrayList.get(position).getStars());
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
        SelectStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreferenceManager.setIsUserAlreadyLoggedIn(mContext, true);
                for (int i = 0; i<studentModelsArrayList.size();i++){
                    if (studentModelsArrayList.get(i).isClicked() == false) {
//                        Toast.makeText(mContext, "Please Select Student", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("confirmlogin",true);
                        dialog.dismiss();
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

        dialog.show();
    }

    private void StartFirabaseAuthentication() {
        System.out.println("Fireabse: Called");
        mAuth.signInWithEmailAndPassword("stebin.alex@mobatia.com", "12345678")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            AppPreferenceManager.setFirebaseUID(mContext,user.getUid());
                            System.out.println("Fireabse: "+AppPreferenceManager.getFirebaseUID(mContext));
                            System.out.println("Fireabse: "+user.getUid());
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void ToRegister(View view) {
        startActivity(new Intent(this,RegistrationActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}

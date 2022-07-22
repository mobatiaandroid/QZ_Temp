package com.whiteteal.quizappfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.whiteteal.quizappfirebase.BackgroundService.BackgroundSoundService;
import com.whiteteal.quizappfirebase.Common.Login.Adapter.StudentAdapter;
import com.whiteteal.quizappfirebase.Common.Login.Model.StudentModels;
import com.whiteteal.quizappfirebase.Common.LoginActivity;
import com.whiteteal.quizappfirebase.LeaderBoard.LeaderBoardActivity;
import com.whiteteal.quizappfirebase.Manager.AppPreferenceManager;
import com.whiteteal.quizappfirebase.Manager.AppUtilityMethod;
import com.whiteteal.quizappfirebase.QuizQuestion.Model.QuestionsModel;
import com.whiteteal.quizappfirebase.QuizQuestion.Questions;
import com.whiteteal.quizappfirebase.recyclerviewmanager.RecyclerItemListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ir.alirezabdn.wp7progress.WP7ProgressBar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAGVALUE_TOTAL_STARS;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_ACCESS_TOKEN;
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
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_QUESTIONS;
import static com.whiteteal.quizappfirebase.URL_Constants.JsonTagConstants.JTAG_QUIZ;
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
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.STATUSCODE_INVALIDUSERNAMEORPASWD;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.STATUSCODE_MISSING_PARAMETER;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.STATUSCODE_SUCCESS;
import static com.whiteteal.quizappfirebase.URL_Constants.StausCodes.STATUS_CODE_NO_QUIZ;
import static com.whiteteal.quizappfirebase.URL_Constants.URLConstant.URL_GET_QUIZ;
import static com.whiteteal.quizappfirebase.URL_Constants.URLConstant.URL_METHOD_GET_ACCESSTOKEN;
import static com.whiteteal.quizappfirebase.URL_Constants.URLConstant.URL_PARENT_LOGIN;

public class MainActivity extends AppCompatActivity {

    boolean flag = true;
    Context mContext;
    Activity activity;
    String QuizId, LevelId;
    TextView CountDown, SecofQues, TotalQues, StudentName, Rules, StartsIn,StudentStarCount,Text3,Text4,Text5;
    Button StartQuizBtn;
    ArrayList<QuestionsModel> questionsModels = new ArrayList<>();
    private String EVENT_DATE_TIME;
    WP7ProgressBar loader;
    ImageView Logout, LeaderBoard,DownArrow;
    LinearLayout ChangeStudent;
    ArrayList<StudentModels> studentModelsArrayList = new ArrayList<>();
    CountDownTimer timer;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    VideoView counterVideo;
    private String android_id,DeviceName;
    String Event = "first";
    String VerificationEvent = "normal";
    Dialog dialog;
    String version;
//    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mContext = this;
        activity = this;

        IniUI();

    }

    private void IniUI() {
        CountDown = findViewById(R.id.starts_counter_timer);
        SecofQues = findViewById(R.id.each_ques_sec);
        TotalQues = findViewById(R.id.total_ques_txt);
        StartQuizBtn = findViewById(R.id.start_quiz_button);
        loader = findViewById(R.id.main_loader);
        LeaderBoard = findViewById(R.id.leaderboard_icon);
        Logout = findViewById(R.id.logout_icon);
        ChangeStudent = findViewById(R.id.ChangeStudentLayout);
        StudentName = findViewById(R.id.StudentName);
        Rules = findViewById(R.id.rulesTxt);
        StartsIn = findViewById(R.id.textView5);
        Text3 = findViewById(R.id.textView);
        Text4 = findViewById(R.id.textView3);
        Text5 = findViewById(R.id.textView4);
        DownArrow = findViewById(R.id.DownArrow);
        StudentStarCount = findViewById(R.id.starCount);
        counterVideo = findViewById(R.id.CounterVideo);
        StudentStarCount.setText(AppPreferenceManager.getStudentStar(mContext));

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        AppPreferenceManager.setAppVersion(mContext,"V "+version);   // Live Build Version

        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.setUserId(AppPreferenceManager.getStudentId(mContext));

        if (AppPreferenceManager.getSingleStudent(mContext).equals("true")){
            ChangeStudent.setEnabled(false);
            DownArrow.setVisibility(View.GONE);
        }else {
            ChangeStudent.setEnabled(true);
            DownArrow.setVisibility(View.VISIBLE);
        }

        if (timer != null) {
            timer.cancel();
        }
        Log.d("ResponseValue: Student", AppPreferenceManager.getStudentId(mContext));
        Log.d("ResponseValue: Token", AppPreferenceManager.getAccessToken(mContext));
        Log.d("ResponseValue: Phone", AppPreferenceManager.getPhone(mContext));
        Log.d("ResponseValue: Pass", AppPreferenceManager.getPass(mContext));
//        StudentName.setText(AppPreferenceManager.getStudentName(mContext));
        StudentName.setText(AppPreferenceManager.getStudentName(mContext));
        android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        DeviceName = Build.BRAND;
        Log.d("DEVICEID",android_id);
        Intent svc = new Intent(this, BackgroundSoundService.class);
        startService(svc);

        ChangeStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.showProgressBar();
                FetchData();
//                RemoveVerification();
                Event = "Sec";
                AddUserVerficationDetails("1");
            }
        });

        LeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificationEvent = "logout";

                Intent i = new Intent(mContext,LeaderBoardActivity.class);
                i.putExtra("From","Main");
                startActivity(i);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLogoutAlert(activity, "Do you want to Logout?");
            }
        });

        loader.showProgressBar();
        CheckUserExist();
        GetQuiz();
        CheckForceUpdate();
        RemoveUserAnswers();
//        RemoveUser();

        StartQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Questions.class);
                startActivity(intent);
                /*counterVideo.setVisibility(View.VISIBLE);
                Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.countdown_video);
                counterVideo.setVideoURI(video);
                counterVideo.start();
                counterVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        startActivity(new Intent(mContext, Questions.class));
                    }
                });*/

                VerificationEvent = "logout";
                Map<String, Object> user = new HashMap<>();
                user.put("Name", AppPreferenceManager.getStudentName(mContext));
                user.put("UserId", AppPreferenceManager.getUserId(mContext));
                db.collection("QZ").document("users").collection(AppPreferenceManager.getLevelId(mContext)).document(AppPreferenceManager.getStudentId(mContext))
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                                loader.hideProgressBar();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });

    }

    private void RemoveUserAnswers() {
        DocumentReference Doc_ref = db.collection("QZ").document("Questions");
        Doc_ref.update("0",FieldValue.arrayRemove(AppPreferenceManager.getStudentId(mContext)));
        Doc_ref.update("1",FieldValue.arrayRemove(AppPreferenceManager.getStudentId(mContext)));
        Doc_ref.update("2",FieldValue.arrayRemove(AppPreferenceManager.getStudentId(mContext)));
        Doc_ref.update("3",FieldValue.arrayRemove(AppPreferenceManager.getStudentId(mContext)));
    }

    private void CheckForceUpdate() {
        DocumentReference docRef = db.collection("ForceUpdate").document("Android");
        docRef.get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()){
                   DocumentSnapshot document = task.getResult();
                   System.out.println("ForceUpdate: "+document.getData());
                   String VersionCode = String.valueOf(document.get("VersionCode"));
                   String VersionName = String.valueOf(document.get("VersionName"));
                   int SystemCode = 0;
                   try {
                       PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(getPackageName(), 0);
                       SystemCode = pInfo.versionCode;
                   } catch (PackageManager.NameNotFoundException e) {
                       e.printStackTrace();
                   }

                   if (Integer.parseInt(VersionCode) > SystemCode){
                       System.out.println("ForceUpdate: NOT SAME VERSION");

                       if (timer != null) {
                           timer.cancel();
                       }

                       ForceUpdateAlert();
                   }else {
                       System.out.println("ForceUpdate: SAME VERSION");
                   }

                   System.out.println("ForceUpdate: Code: "+VersionCode+" Name: "+VersionName);
               }
            }
        });
    }


    private void CheckUserExist() {

        DocumentReference docRef = db.collection("USERVERIFICATION").document(AppPreferenceManager.getStudentId(mContext));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("USER_EXISTS", "DocumentSnapshot data: " + document.getData());
                        if (document.get("DeviceID").equals(android_id) && document.get("StudentId").equals(AppPreferenceManager.getStudentId(mContext))){
                            Log.d("USER_EXISTS", "No Problem");
                            AddUserVerficationDetails("0");
                        }else {
                            Log.d("USER_EXISTS", "User Exist");
                            if (timer != null) {
                                timer.cancel();
                            }
                            String Mesg = "User has already logged in and playing Quiz on another"+document.get("DeviceName")+" device";

                            if (document.get("CurrentState").equals("0")){
                                Log.d("USER_EXISTS", "On Active");
                                if (timer != null) {
                                    timer.cancel();
                                }
                                String OPENED = "QZ Application is active in "+document.get("DeviceName")+" device";
//                                ShowUserExistDialog(activity,OPENED);

                                if (timer != null) {
                                    timer.cancel();
                                }
                                Intent svc = new Intent(mContext, BackgroundSoundService.class);
                                stopService(svc);
//                                RemoveVerification();
                                AppPreferenceManager.setIsUserAlreadyLoggedIn(mContext, false);
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, OPENED, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                VerificationEvent = "logout";

                            }else if (document.get("CurrentState").equals("1")){
                                Log.d("USER_EXISTS", "On Background");
                                String DETTT = "User has already logged in another "+document.get("DeviceName")+" device, do you want to switch?";
                                if (timer != null) {
                                    timer.cancel();
                                }
                                ShowSwitchAccountAlert(activity,DETTT);
                            }else if (document.get("CurrentState").equals("2")){
                                Log.d("USER_EXISTS", "On Quiz");
                                if (timer != null) {
                                    timer.cancel();
                                }
                                ShowUserExistDialog(activity,Mesg);
                            }

                        }
                    } else {
                        Log.d("USER_EXISTS", "No such document");
                        AddUserVerficationDetails("0");
                    }
                } else {
                    Log.d("USER_EXISTS", "get failed with ", task.getException());
                }
            }
        });

    }

    private void ShowSwitchAccountAlert(Activity activity, String mesg) {
        if (timer != null) {
            timer.cancel();
        }

        Toast toast = Toast.makeText(mContext, "5", Toast.LENGTH_SHORT);
        toast.show();
        CountDownTimer ACC;
        ACC = new CountDownTimer(5000, 1000) {
            public void onTick(long m) {
                long sec = m/1000+1;
                toast.setText("Logging off in " + sec);
                toast.show();
            }
            public void onFinish() {
                // Finished
                VerificationEvent = "logout";
                if (timer != null) {
                    timer.cancel();
                }
                AppPreferenceManager.setIsUserAlreadyLoggedIn(mContext, false);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }.start();


        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_double_btn);

        TextView Msg = dialog.findViewById(R.id.double_msg_txt);
        Msg.setText(mesg);

        TextView logout = dialog.findViewById(R.id.double_ok_txt);
        logout.setText("Switch");

        TextView cancel = dialog.findViewById(R.id.double_cancel_txt);
        cancel.setText("Cancel");

        ImageView OK = dialog.findViewById(R.id.double_ok_btn);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificationEvent = "normal";
                if (ACC != null){
                    ACC.cancel();
                }
                if (timer != null) {
                    timer.cancel();
                }
                dialog.dismiss();
                CountDown.setText("");
                SecofQues.setText("");
                TotalQues.setText("");
                Rules.setVisibility(View.GONE);
                Text3.setVisibility(View.GONE);
                Text4.setVisibility(View.GONE);
                Text5.setVisibility(View.GONE);
                CountDown.setText("");
                StartsIn.setVisibility(View.GONE);

                studentModelsArrayList.clear();
                questionsModels.clear();
                loader.showProgressBar();
                GetQuiz();
                AddUserVerficationDetails("0");
            }
        });

        ImageView CancelBtn = dialog.findViewById(R.id.double_cancel_btn);
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                }

                if (ACC != null){
                    ACC.cancel();
                }

                dialog.dismiss();
                AppPreferenceManager.setIsUserAlreadyLoggedIn(mContext, false);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                VerificationEvent = "logout";
            }
        });
        dialog.show();
    }

    private void AddUserVerficationDetails(String AppState) {

        /*
         *      0 = Application Active
         *      1 = Application Background
         *      2 = Application in Quiz
         */

        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        Log.d("CurrentData",currentDate);

        Map<String,Object> map = new HashMap<>();
        map.put("CurrentState",AppState);
        map.put("LoginTime",currentDate);
        map.put("DeviceID",android_id);
        map.put("DeviceName",DeviceName);
        map.put("StudentId",AppPreferenceManager.getStudentId(mContext));
        map.put("Version",AppPreferenceManager.getAppVersion(mContext));
        map.put("Name",AppPreferenceManager.getStudentName(mContext));
        db.collection("USERVERIFICATION").document(AppPreferenceManager.getStudentId(mContext))
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       // Toast.makeText(mContext, "Inserted", Toast.LENGTH_SHORT).show();
//                        CheckUserExist();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    private void FetchData() {
        okhttp3.OkHttpClient client = new OkHttpClient();
        okhttp3.MediaType MEDIA_TYPE = MediaType.parse("application/json");
        JSONObject postdata = new JSONObject();

        try {
            postdata.put("phone", AppPreferenceManager.getPhone(mContext));
            postdata.put("passwd", AppPreferenceManager.getPass(mContext));
            postdata.put("device_id", "1");
            postdata.put("device_type", "123456789");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(postdata.toString(), MEDIA_TYPE);

        Request request = new Request.Builder()
                .url(URL_PARENT_LOGIN)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ResponseValue: E", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String Response = response.body().string();
                Log.d("ResponseValue: S", Response);

                try {
                    JSONObject obj = new JSONObject(Response);
                    String response_code = obj.getString(JTAG_RESPONSECODE);
                    if (response_code.equalsIgnoreCase(RESPONSE_SUCCESS)) {
                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
                        String status_code = secobj.getString(JTAG_STATUSCODE);
                        String UserCode = secobj.getString(JTAG_USERCODE);
                        AppPreferenceManager.setUserCode(mContext,UserCode);
                        if (status_code.equalsIgnoreCase(STATUSCODE_SUCCESS)) {

                            AppPreferenceManager.setUserId(mContext, secobj.optString(JTAG_PARENT_ID));
                            JSONArray studentListArray = secobj.getJSONArray(JTAG_STUDENTS);
                            AppPreferenceManager.setStudentsResponseFromLoginAPI(mContext, studentListArray.toString());
                            if (studentListArray.length() > 0) {
                                studentModelsArrayList.clear();
                                //AppController.studentArrayList.clear();
                                for (int i = 0; i < studentListArray.length(); i++) {
                                    JSONObject sObject = studentListArray.getJSONObject(i);

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
                                    intent.putExtra("confirmlogin", true);
                                    startActivity(intent);
                                    finish();
                                    AppPreferenceManager.setStudentId(mContext, studentModelsArrayList.get(0).getId());
                                    AppPreferenceManager.setStudentName(mContext, studentModelsArrayList.get(0).getName());

                                } else {
                                    runOnUiThread(new Runnable() {
                                        @RequiresApi(api = Build.VERSION_CODES.P)
                                        @Override
                                        public void run() {
                                            loader.hideProgressBar();

                                            ShowStudentList(studentModelsArrayList);
                                        }
                                    });


                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.no_student_available));
                                    }
                                });


                            }
                        } else if (status_code.equalsIgnoreCase(STATUSCODE_INVALIDUSERNAMEORPASWD)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.invalid_usr_pswd));
                                }
                            });


                        } else if (status_code.equalsIgnoreCase(STATUSCODE_MISSING_PARAMETER)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.missing_parameter));
                                }
                            });


                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.common_error));
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
                                AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.common_error));
                            }
                        });


                    }
                } catch (Exception ex) {
                    System.out.println("The Exception in edit profile is" + ex.toString());
                }


            }
        });
    }

    private void ShowStudentList(ArrayList<StudentModels> studentModelsArrayList) {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_student_list);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        ImageView dialogDismiss = (ImageView) dialog.findViewById(R.id.closebtn);
        RecyclerView studentList = (RecyclerView) dialog.findViewById(R.id.recycler_view_social_media);
        Button SelectStu = (Button) dialog.findViewById(R.id.btn_dismiss);
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

        StudentAdapter studentAdapter = new StudentAdapter(mContext, this.studentModelsArrayList);
        studentList.setAdapter(studentAdapter);
        dialogDismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                CheckUserExist();
                AddUserVerficationDetails("0");
            }

        });

        studentList.addOnItemTouchListener(new RecyclerItemListener(mContext, studentList,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
//                        dialog.dismiss();
                        dialogDismiss.setVisibility(View.GONE);
                        for (int i = 0; i < studentModelsArrayList.size(); i++) {
                            if (studentModelsArrayList.get(i).isClicked()) {
                                studentModelsArrayList.get(i).setClicked(false);
                            }
                        }
                        studentModelsArrayList.get(position).setClicked(true);
                        studentAdapter.notifyDataSetChanged();


                        if (MainActivity.this.studentModelsArrayList.get(position).getBoardid().equals("1")) {
                            String selectedFromList = "ISG";
                            AppPreferenceManager.setSchoolSelection(mContext, selectedFromList);
                        } else {
                            String selectedFromList = "ISG-INT";
                            AppPreferenceManager.setSchoolSelection(mContext, selectedFromList);
                        }

                        AppPreferenceManager.setIsGuest(mContext, false);
                        AppPreferenceManager.setStudentId(mContext, MainActivity.this.studentModelsArrayList.get(position).getId());
                        AppPreferenceManager.setStudentName(mContext, MainActivity.this.studentModelsArrayList.get(position).getName());
                        AppPreferenceManager.setStudentStar(mContext,MainActivity.this.studentModelsArrayList.get(position).getStars());
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
                if (timer != null) {
                    timer.cancel();
                }

                for (int i = 0; i<studentModelsArrayList.size();i++){
                    if (studentModelsArrayList.get(i).isClicked() == false) {
//                        Toast.makeText(mContext, "Please Select Student", Toast.LENGTH_SHORT).show();
                    }else {
                        StudentName.setText(AppPreferenceManager.getStudentName(mContext));
                        StudentStarCount.setText(AppPreferenceManager.getStudentStar(mContext));
                        dialog.dismiss();
                        if (timer != null) {
                            timer.cancel();
                        }
                        CountDown.setText("");
                        SecofQues.setText("");
                        TotalQues.setText("");
                        Rules.setVisibility(View.GONE);
                        Text3.setVisibility(View.GONE);
                        Text4.setVisibility(View.GONE);
                        Text5.setVisibility(View.GONE);
                        CountDown.setText("");
                        StartsIn.setVisibility(View.GONE);

                        studentModelsArrayList.clear();
                        questionsModels.clear();
                        loader.showProgressBar();
                        GetQuiz();
                    }
                }




            }
        });
        dialog.show();
    }

    private void GetQuiz() {

        if (Event.equals("Sec")){
            CheckUserExist();
        }

        CountDown.setText("");
        SecofQues.setText("");
        TotalQues.setText("");
        StartsIn.setVisibility(View.GONE);
        questionsModels.clear();
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        JSONObject postdata = new JSONObject();

        try {
            postdata.put("studentId", AppPreferenceManager.getStudentId(mContext));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(postdata.toString(), MEDIA_TYPE);

        Request request = new Request.Builder()
                .url(URL_GET_QUIZ)
                .post(body)
                .header("Authorization", "Bearer " + AppPreferenceManager.getAccessToken(mContext))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ResponseValue: E", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String Response = response.body().string();
//                String Response = "{\"responsecode\":\"200\",\"response\":{\"statuscode\":\"202\",\"total_stars\":3}}";
                Log.d("ResponseValue: S", Response);
                AppPreferenceManager.setJsonResponse(mContext, Response);
                try {
                    JSONObject obj = new JSONObject(Response);
                    String response_code = obj.getString(JTAG_RESPONSECODE);
                    if (response_code.equalsIgnoreCase(RESPONSE_SUCCESS)) {
                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);

                        String status_code = secobj.getString(JTAG_STATUSCODE);
                        if (status_code.equalsIgnoreCase(STATUSCODE_SUCCESS)) {
                            final JSONObject thirdobj = secobj.getJSONObject(JTAG_QUIZ);
                            CheckForceUpdate();
                            String STARS = secobj.getString(JTAGVALUE_TOTAL_STARS);
                            System.out.println("ResponseValue: stars INSIDE Success"+STARS);
                            AppPreferenceManager.setStudentStar(mContext,STARS);

                            EVENT_DATE_TIME = thirdobj.optString("starts_in_hour");
                            AppPreferenceManager.setEndTime(mContext, EVENT_DATE_TIME);
                            QuizId = thirdobj.optString("qz_id");
                            LevelId = thirdobj.optString("id");
                            AppPreferenceManager.setQuizId(mContext, QuizId);
                            AppPreferenceManager.setLevelId(mContext, LevelId);


                            final JSONArray jsonArray = thirdobj.getJSONArray(JTAG_QUESTIONS);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject job = jsonArray.getJSONObject(i);
                                ArrayList<String> OP = new ArrayList<>();
                                QuestionsModel model = new QuestionsModel();
                                model.setQuestion(job.optString("question"));
                                model.setAnswer(job.optString("answer_index"));
                                model.setExplanation(job.optString("explanation"));

                                final JSONArray array = job.getJSONArray("options");
                                for (int K = 0; K < array.length(); K++) {
                                    OP.add(String.valueOf(array.get(K)));
                                }
                                model.setOptions(OP);
                                questionsModels.add(model);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < questionsModels.size(); i++) {
                                        Log.d("ResponseValue: SIZE", String.valueOf(questionsModels.size()));
                                        TotalQues.setVisibility(View.VISIBLE);
                                        Rules.setVisibility(View.VISIBLE);
                                        Text3.setVisibility(View.VISIBLE);
                                        Text4.setVisibility(View.VISIBLE);
                                        Text5.setVisibility(View.VISIBLE);
                                        StartsIn.setVisibility(View.VISIBLE);
                                        TotalQues.setText("2. Totally, you have to answer " + questionsModels.size() + " questions");
                                        AppPreferenceManager.setTotalQuestion(mContext, "Totally you have " + questionsModels.size() + " Questions");

                                    }

                                }
                            });

                            runOnUiThread(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.P)
                                @Override
                                public void run() {
                                    loader.hideProgressBar();

                                    SecofQues.setVisibility(View.VISIBLE);
                                    StartsIn.setVisibility(View.VISIBLE);
                                    SecofQues.setText("1. You have " + thirdobj.optString("question_duration") + " seconds to answer each question");
                                    AppPreferenceManager.setTotalTime(mContext, "Each Questions have " + thirdobj.optString("question_duration") + " seconds");
//                                    StartTimer(endTime);
                                    countDownStart(EVENT_DATE_TIME);
                                }
                            });

                            Log.d("ResponseValue: INSIDE", thirdobj.optString("start_time"));

                        } else if (status_code.equalsIgnoreCase(STATUS_CODE_NO_QUIZ)) {
                            runOnUiThread(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.P)
                                @Override
                                public void run() {
                                    loader.hideProgressBar();
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.no_quiz_available));
                                    //countDownStart(AppPreferenceManager.getEndTime(mContext));

                                    String STARS = null;
                                    try {
                                        STARS = secobj.getString(JTAGVALUE_TOTAL_STARS);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("ResponseValue: stars INSIDE NO QUIZ"+STARS);
                                    AppPreferenceManager.setStudentStar(mContext,STARS);

                                    SecofQues.setText("");
                                    TotalQues.setText("");
                                    Rules.setVisibility(View.GONE);
                                    Text3.setVisibility(View.GONE);
                                    Text4.setVisibility(View.GONE);
                                    Text5.setVisibility(View.GONE);
                                    CountDown.setText("");
                                    StartsIn.setVisibility(View.GONE);
                                }
                            });
                        }else if (status_code.equalsIgnoreCase("216")){
                            System.out.println("ResponseValue: Called for Token");
                           AppUtilityMethod.RenewToken(mContext);
                           GetQuiz();
                        }
                        else if (status_code.equalsIgnoreCase(STATUSCODE_INVALIDUSERNAMEORPASWD)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.invalid_usr_pswd));
                                }
                            });


                        } else if (status_code.equalsIgnoreCase(STATUSCODE_MISSING_PARAMETER)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.missing_parameter));
                                }
                            });


                        } else {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.common_error));
//                                }
//                            });
                            AppUtilityMethod.RenewToken(mContext);
                            GetQuiz();
                        }
                         /*else {
                            AppUtilityMethods.showDialogAlertDismiss((Activity) mContext, getString(R.string.error_heading), getString(R.string.common_error), R.drawable.infoicon,  R.drawable.roundblue);

                        }*/
                    } else if (response_code.equalsIgnoreCase(RESPONSE_INTERNALSERVER_ERROR)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, getString(R.string.internal_server_error));
                            }
                        });


                    } else if (response_code.equalsIgnoreCase(RESPONSE_INVALID_ACCESSTOKEN) || response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) || response_code.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED)) {
//                        AppUtilityMethod.getToken(mContext, new AppUtilityMethod.GetTokenSuccess() {
//                            @Override
//                            public void tokenrenewed() {
//                                sendLoginToServer(URL_PARENT_LOGIN);
//                            }
//                        });
                        AppUtilityMethod.RenewToken(mContext);
                        GetQuiz();
                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                AppUtilityMethod.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.common_error));
//                            }
//                        });
                        AppUtilityMethod.RenewToken(mContext);
                        GetQuiz();

                    }
                } catch (Exception ex) {
                    System.out.println("The Exception in edit profile is" + ex.toString());
                }


            }
        });

    }

    private void countDownStart(String event_date_time) {

        String[] tokens = event_date_time.split(":");
        int secondsToMs = Integer.parseInt(tokens[2]) * 1000;
        int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
        long total = secondsToMs + minutesToMs + hoursToMs;

        long SampleTime = 20000;

        System.out.println("ARASHAD TIME: " + total);

        timer = new CountDownTimer(total, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                CountDown.setVisibility(View.VISIBLE);
                CountDown.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                System.out.println("Counter: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));

                if (CountDown.getText().toString().equals("00:00:13")){
                    System.out.println("COUNTER: Reached for Video");
                    counterVideo.setVisibility(View.VISIBLE);
                    Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.countdown_video);
                    counterVideo.setVideoURI(video);
                    counterVideo.start();
                    
//                    counterVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            Toast.makeText(mContext, "Video Completed", Toast.LENGTH_SHORT).show();
//                        }
//                    });

                }
            }

            public void onFinish() {
//                AppUtilityMethod.showDialogAlertDismiss((Activity) mContext,getString(R.string.no_quiz_available));
                CountDown.setVisibility(View.VISIBLE);
                CountDown.setText("00:00:00");
                loader.showProgressBar();
                System.out.println("QuizId: " + QuizId);
                startActivity(new Intent(mContext, Questions.class));
                AddUserVerficationDetails("2");
                Map<String, Object> user = new HashMap<>();
                user.put("Name", AppPreferenceManager.getStudentName(mContext));
                user.put("UserId", AppPreferenceManager.getUserId(mContext));

                db.collection("QZ").document("users").collection(AppPreferenceManager.getLevelId(mContext)).document(AppPreferenceManager.getStudentId(mContext))
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                                loader.hideProgressBar();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        };
        timer.start();

    }

    private void ShowUserExistDialog(Activity activity, String mesg) {
        if (timer != null) {
            timer.cancel();
        }

        Toast toast = Toast.makeText(mContext, "5", Toast.LENGTH_SHORT);
        toast.show();
        CountDownTimer ExistCounter;
        ExistCounter = new CountDownTimer(5000, 1000) {
            public void onTick(long m) {
                long sec = m/1000+1;
//                toast.cancel();
                toast.setText("Logging off in " + sec);
                toast.show();
            }
            public void onFinish() {
                // Finished
                VerificationEvent = "logout";
                if (timer != null) {
                    timer.cancel();
                }
                AppPreferenceManager.setIsUserAlreadyLoggedIn(mContext, false);
                Intent svc = new Intent(mContext, BackgroundSoundService.class);
                stopService(svc);

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }.start();

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_single_btn);
        TextView Msg = dialog.findViewById(R.id.single_msg_txt);
        Msg.setText(mesg);
        ImageView OK = dialog.findViewById(R.id.single_ok_btn);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                finishAffinity();
//                System.exit(0);
                VerificationEvent = "logout";
                if (timer != null) {
                    timer.cancel();
                }

                if (ExistCounter != null){
                    ExistCounter.cancel();
                }

                AppPreferenceManager.setIsUserAlreadyLoggedIn(mContext, false);
                Intent svc = new Intent(mContext, BackgroundSoundService.class);
                stopService(svc);

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        dialog.show();
    }


    private void ShowLogoutAlert(Activity activity, String do_you_want_to_logout) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_double_btn);

        TextView Msg = dialog.findViewById(R.id.double_msg_txt);
        Msg.setText(do_you_want_to_logout);

        TextView logout = dialog.findViewById(R.id.double_ok_txt);
        logout.setText("Logout");

        TextView cancel = dialog.findViewById(R.id.double_cancel_txt);
        cancel.setText("Cancel");

        ImageView OK = dialog.findViewById(R.id.double_ok_btn);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // RestartApp();
                if (timer != null) {
                    timer.cancel();
                }
                VerificationEvent = "logout";
                Intent svc = new Intent(mContext, BackgroundSoundService.class);
                stopService(svc);

                RemoveVerification();
                AppPreferenceManager.setIsUserAlreadyLoggedIn(mContext, false);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
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

    private void RemoveVerification() {
        db.collection("USERVERIFICATION").document(AppPreferenceManager.getStudentId(mContext))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       // Toast.makeText(mContext, "Removed Verfication", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      //  Toast.makeText(mContext, "Error in Removing", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void RemoveUser() {
        DocumentReference productIdRef = db.collection("QZ").document("users")
                .collection(AppPreferenceManager.getLevelId(mContext)).document(AppPreferenceManager.getStudentId(mContext));
        productIdRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(mContext, "Failed with: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("count: onDestroy Called");
        Intent svc = new Intent(mContext, BackgroundSoundService.class);
        stopService(svc);
        loader.hideProgressBar();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onRestart() {
        System.out.println("count: onRestart Called");

        Intent svc = new Intent(mContext, BackgroundSoundService.class);
        startService(svc);
        loader.showProgressBar();
        questionsModels.clear();
        if (timer != null) {
            timer.cancel();
        }
        CountDown.setText("");
        SecofQues.setText("");
        TotalQues.setText("");
        Rules.setVisibility(View.GONE);
        Text3.setVisibility(View.GONE);
        Text4.setVisibility(View.GONE);
        Text5.setVisibility(View.GONE);
        CountDown.setText("");
        StartsIn.setVisibility(View.GONE);
        GetQuiz();
        CheckForceUpdate();
//        AddUserVerficationDetails("0");
        VerificationEvent = "normal";
        CheckUserExist();
        super.onRestart();
    }
    @Override
    protected void onStop() {
        System.out.println("count: onStop Called");
       // loader.hideProgressBar();
//        AddUserVerficationDetails("1");
        if (timer != null) {
            timer.cancel();

        }
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
           // loader.hideProgressBar();
            System.out.println("KEYCODE_HOME");
            System.out.println("count: KEY DOWN");
//            AddUserVerficationDetails("1");
            VerificationEvent = "normal";
            if (timer != null) {
                timer.cancel();
            }
            return true;
        }
        return false;
    }


    @Override
    protected void onUserLeaveHint() {
      //  loader.hideProgressBar();
        System.out.println("count: Hint Called");
        if (timer != null) {
            timer.cancel();
        }
        super.onUserLeaveHint();
    }

    @Override
    public void onPause() {
        if (timer != null) {
            timer.cancel();
        }
        Intent svc1 = new Intent(mContext, BackgroundSoundService.class);
        stopService(svc1);

        if (VerificationEvent.equals("logout")){
            System.out.println("count: Logout Called");
        }else {
            System.out.println("count: Normal Called");

            AddUserVerficationDetails("1");
        }

        if (isApplicationSentToBackground(this)){
            // Do what you want to do on detecting Home Key being Pressed
           // loader.hideProgressBar();
//            AddUserVerficationDetails("1");
            System.out.println("count: onPause Called");
            if (timer != null) {
                timer.cancel();
            }
            Intent svc = new Intent(mContext, BackgroundSoundService.class);
            stopService(svc);
        }
        super.onPause();
    }

    public boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    private void ForceUpdateAlert() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_single_btn);
        TextView Msg =dialog.findViewById(R.id.single_msg_txt);
        TextView BtnText = dialog.findViewById(R.id.single_ok_txt);
        Msg.setText("A new version is available on the store");
        ImageView OK = dialog.findViewById(R.id.single_ok_btn);
        BtnText.setText("Update");

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.isg.quiz"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                // RestartApp();
            }
        });
        dialog.show();
    }


}






package com.whiteteal.quizappfirebase.URL_Constants;

public interface URLConstant {


    /*======================= Dev Url ======================================> */
//    public static String URL_HEAD="http://qz.mobatia.com/api/";

    /*  <==================== NOW LIVE =====================================> */
   /* public static String URL_HEAD="https://qzapp.meitsystems.com/api/";*/

    public static String URL_HEAD ="http://qz.mobatia.com:8081/api/";
    public static String URL_METHOD_GET_ACCESSTOKEN = URL_HEAD+"user/token";
    public static String URL_PARENT_LOGIN = URL_HEAD+"login";
    public static String URL_GET_QUIZ = URL_HEAD+"getQuiz";
    public static String URL_SUBMIT_QUIZ=URL_HEAD+"submitQuiz";
    public static String URL_GET_LEADERBOARD=URL_HEAD+"leaderBoard";
    public static String URL_FORGOT_PASS=URL_HEAD+"forgotPassword";

    public static String URL_PARENT_REGISTRATION=URL_HEAD+"api/parentRegistration";
    public static String URL_GET_ALL_STUDENTS=URL_HEAD+"api/getStudents";
    public static String URL_CHANGEPASSWORD=URL_HEAD+"api/changePassword";
    public static String URL_FORGOTPASSWORD=URL_HEAD+"api/forgotPassword";

}

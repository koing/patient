package com.greenpoint.patient.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/10/14.
 */
public class Utils {
    /**
     * 鍒涘缓鐩綍
     * @param path
     */
    public static void createDirs(File path) {
        if (path != null && !path.exists()) {
            path.mkdirs();
        }
    }

    /**
     * 鏂囦欢鏄惁瀛樺湪
     * @param file
     * @return
     */
    public static boolean isFileExist(File file) {
        if (file != null && file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 鏍规嵁鎵嬫満鍒嗚鲸鐜囦粠dp杞垚px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 鏍规嵁鎵嬫満鐨勫垎杈ㄧ巼浠?px(鍍忕礌) 鐨勫崟浣?杞垚涓?dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f) - 15;
    }


    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 妫?祴sdcard鏄惁鍙敤
     * @return true涓哄彲鐢紝鍚﹀垯涓轰笉鍙敤
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }

    /**
     * 楠岃瘉鎵嬫満鍙锋牸寮忔槸鍚︽纭?
     *
     * @param mobileNumber
     * @return
     */
    public static boolean validateMobileNumber(String mobileNumber) {
        if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", mobileNumber)) {
            return true;
        }
        return false;
    }

    /**
     * 楠岃瘉瀛楃涓?鏄惁閫傚悎鏌愮鏍煎紡
     * @param expression 姝ｅ垯琛ㄨ揪寮?
     * @param text 鎿嶄綔鐨勫瓧绗︿覆
     * @return
     */
    private static boolean matchingText(String expression, String text) {
        Pattern p = Pattern.compile(expression); // 姝ｅ垯琛ㄨ揪寮?
        Matcher m = p.matcher(text); // 鎿嶄綔鐨勫瓧绗︿覆
        boolean b = m.matches();
        return b;
    }

    /**
     * 妫?煡缃戠粶鐘舵?
     */
    public static boolean hasNetwork(Context context) {
        android.net.ConnectivityManager cManager = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 妫?煡缃戠粶鐘舵?2
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        android.net.ConnectivityManager connectivity = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); //鑾峰彇绯荤粺缃戠粶杩炴帴绠＄悊鍣?
        if (connectivity == null) { //濡傛灉缃戠粶绠＄悊鍣ㄤ负null
            return false; //杩斿洖false琛ㄦ槑缃戠粶鏃犳硶杩炴帴
        }
        else {
            android.net.NetworkInfo[] info = connectivity.getAllNetworkInfo(); //鑾峰彇鎵?湁鐨勭綉缁滆繛鎺ュ璞?
            if (info != null) { //缃戠粶淇℃伅涓嶄负null鏃?
                for (int i = 0; i < info.length; i++) { //閬嶅巻缃戣矾杩炴帴瀵硅薄
                    if (info[i].isConnected()) { //褰撴湁涓?釜缃戠粶杩炴帴瀵硅薄杩炴帴涓婄綉缁滄椂
                        return true; //杩斿洖true琛ㄦ槑缃戠粶杩炴帴姝ｅ父
                    }
                }
            }
        }
        return false;
    }

    public static boolean isMobileNetworkAvailable(Context context) {
        //鑾峰彇搴旂敤涓婁笅鏂?
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //鑾峰彇绯荤粺鐨勮繛鎺ユ湇鍔?
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        //鑾峰彇缃戠粶鐨勮繛鎺ユ儏鍐?
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            //鍒ゆ柇3G缃?
            return true;
        }
        return false;
    }



    /**
     * 瀛楃涓茶浆鎴恑nt
     * @param str
     * @return
     */
    public static int parseStr2Int(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 瀛楃涓茶浆鎴恑nt
     * @param str
     * @return
     */
    public static float parseStr2Float(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Float.parseFloat(str);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 鍒ゆ柇瀛楃涓叉槸鍚︽槸鍚堟硶鐨?6杩涘埗涓?
     * @author: Xue Wenchao
     * @param str
     * @return
     * @return: boolean
     * @date: 2014-1-21 涓婂崍10:13:23
     */
    public static boolean isHexString(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.matches("^[0-9a-fA-F]++$", str);
    }

    /**
     * 瀛楃涓茶浆鎴怢ong
     * @param str
     * @return
     */
    public static long parseStr2Long(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Long.parseLong(str);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 闅愯棌杈撳叆閿洏
     * @param view
     * @param context
     */
    public static void hideSoftInput(EditText view, Context context) {
        InputMethodManager inputMeMana = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMeMana.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 鏄剧ず杞敭鐩?
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMeMana = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMeMana.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 鍒ゆ柇瀛楃涓叉槸鍚︿负绌?
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null && str.length() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 璁＄畻瀛楃涓暟锛屼竴涓眽瀛楃畻涓や釜
     * @param s
     * @return
     */
    public static int countWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length(), a = 0, b = 0;
        int len = 0;
        char c;
        for (int i = 0; i < n; i++) {
            c = s.charAt(i);
            if (Character.isSpaceChar(c)) {
                ++b;
            }
            else if (isAscii(c)) {
                ++a;
            }
            else {
                ++len;
            }
        }
        return len + (int) Math.ceil((a + b) / 2.0);
    }

    public static boolean isAscii(char c) {
        return c <= 0x7f;
    }

    /**
     * 楠岃瘉閭鍦板潃鏄惁鍚堟硶
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }
        catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 杩囨护鏂囨湰涓殑html鑴氭湰淇℃伅
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 鍚玥tml鏍囩鐨勫瓧绗︿覆
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        java.util.regex.Pattern p_html1;
        java.util.regex.Matcher m_html1;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 瀹氫箟script鐨勬鍒欒〃杈惧紡{鎴?script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 瀹氫箟style鐨勬鍒欒〃杈惧紡{鎴?style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 瀹氫箟HTML鏍囩鐨勬鍒欒〃杈惧紡
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 杩囨护script鏍囩

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 杩囨护style鏍囩

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 杩囨护html鏍囩

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 杩囨护html鏍囩

            textStr = htmlStr;

        }
        catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 杩斿洖鏂囨湰瀛楃涓?
    }





    /**
     * 鎷疯礉娴?
     * @param is
     * @param os
     */
    public static void copyStream(InputStream is, OutputStream os) throws IOException {
        if (is == null || os == null) {
            return;
        }
        BufferedInputStream bufIs;
        boolean shouldClose = false;
        if (is instanceof BufferedInputStream) {
            bufIs = (BufferedInputStream) is;
        }
        else {
            bufIs = new BufferedInputStream(is);
            shouldClose = true;
        }

        int bufLen = 102400;
        byte[] buf = new byte[bufLen];
        int len;
        while (true) {
            len = bufIs.read(buf);
            if (len < 0) {
                break;
            }
            os.write(buf, 0, len);
        }
        if (shouldClose) {
            bufIs.close();
        }
    }

    /**
     * 寰楀埌灞忓箷瀹藉害
     * @param context
     * @return
     */
    public static int getWinWidth(Activity context) {
        // TODO Auto-generated constructor stub
        return context.getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * 寰楀埌灞忓箷楂樺害
     * @param context
     * @return
     */
    public static int getWinHight(Activity context) {
        // TODO Auto-generated constructor stub
        return context.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static int calculateCharLength(String src) {
        int counter = -1;
        if (src != null) {
            counter = 0;
            final int len = src.length();
            for (int i = 0; i < len; i++) {
                char sigleItem = src.charAt(i);
                if (isAlphanumeric(sigleItem)) {
                    counter++;
                }
                else if (Character.isLetter(sigleItem)) {
                    counter = counter + 2;
                }
                else {
                    counter++;
                }
            }
        }
        else {
            counter = -1;
        }

        return counter;
    }

    /**
     * 鍒ゆ柇瀛楃鏄惁涓鸿嫳鏂囧瓧姣嶆垨鑰呴樋鎷変集鏁板瓧.
     *
     * @param ch char瀛楃
     * @return true or false
     */
    public static boolean isAlphanumeric(char ch) {
        // 甯搁噺瀹氫箟
        final int DIGITAL_ZERO = 0;
        final int DIGITAL_NINE = 9;
        final char MIN_LOWERCASE = 'a';
        final char MAX_LOWERCASE = 'z';
        final char MIN_UPPERCASE = 'A';
        final char MAX_UPPERCASE = 'Z';

        if ((ch >= DIGITAL_ZERO && ch <= DIGITAL_NINE) || (ch >= MIN_LOWERCASE && ch <= MAX_LOWERCASE)
                || (ch >= MIN_UPPERCASE && ch <= MAX_UPPERCASE)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * decode js鐢╡scape缂栫爜鐨勫瓧绗︿覆
     * @method: unEscape
     * @description: TODO
     * @author: DongFuhai
     * @param src
     * @return
     * @return: String
     * @date: 2013-10-14 涓嬪崍5:57:56
     */
    public static String unEscape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                }
                else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            }
            else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                }
                else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }
}

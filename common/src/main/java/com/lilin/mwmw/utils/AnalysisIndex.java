package com.lilin.mwmw.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisIndex {


    public static void main(String[] args) {
        //System.out.println("#dasadsa#".replace("#",""));
        //System.out.println(HasDigit("qweqw"));

        //System.out.println("123456All".substring(("eqweqwAll".length())-1));

        System.out.println("currentTime-1".substring(12));


        /*String reasonContainsSymbol ="$value1$年内有过不良贷款";
        int start=reasonContainsSymbol.indexOf("$");
        int end=start+8;
        String replaceString=reasonContainsSymbol.substring(start,end);
        System.out.println(replaceString);
*/
       /* String reasonContainsSymbol="$value2$年内贷款本息逾期大于等于$value3$次(信用卡、按揭除外)";
        String[] strs={"2","4"};
        int count=-1;
        while (reasonContainsSymbol.contains("$")){
            count++;
            int start=reasonContainsSymbol.indexOf("$");
            int end=start+8;
            String replaceValue=reasonContainsSymbol.substring(start,end);
            reasonContainsSymbol=reasonContainsSymbol.replace(replaceValue,strs[count]);
        }

        System.out.println(reasonContainsSymbol);*/


    }
    public static String replaceValue(String str){
        if(str.contains("$")){
            int start=str.indexOf("$");
            int end=start+8;
            String replaceValue=str.substring(start,end);
            str=str.replace(replaceValue,"1");
            return replaceValue(str);
        }else{
            return str;
        }
    }

    public static String getReason(int index, Boolean ifGetValue, String expressionTxt, String expression) {


        return null;
    }

    // 判断一个字符串是否含有数字
    public static boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

}

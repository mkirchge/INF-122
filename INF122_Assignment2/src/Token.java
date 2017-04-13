/**
 * Created by maxkirchgesner on 2/5/17.
 */
import java.util.*;
import java.text.*;

public class Token {

    String tokenType;
    Date date;
    int tokenID;
    int amount;
    String note;
    int hour;
    int min;
    int sec;

    public Token (){
        note = "";
        Calendar calendar = Calendar.getInstance();
        date = new Date();
        calendar.setTime(date);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        sec = calendar.get(Calendar.SECOND);
    }
    public Token (String n){
        Calendar calendar = Calendar.getInstance();
        date = new Date();
        calendar.setTime(date);
        note = n;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        sec = calendar.get(Calendar.SECOND);
    }

    public String getTokenType(){
        return tokenType;
    }

    public void setTokenType(String s){
        tokenType = s;
    }

    public void setTime(Date d){
        date = d;
    }

    public Date getTime(){
        return date;
    }

    public int getTokenID(){
        return tokenID;
    }

    public void setTokenID(int num){
        tokenID = num;
    }

    public String getNote() { return note; }

}

/**
 * Created by maxkirchgesner on 2/5/17.
 */

import java.util.*;

public class Child implements User{

    String username;
    int token;
    String mode;
    int childID;
    int tokenlimit;
    ArrayList<Token> tokens = new ArrayList<Token>();

    public Child(){
        username = "";
        mode = "";
        childID = 0;
    }

    public Child (String name, int ID, Token t, Mode m){
        childID = ID;
        mode = m.mode;
        username = name;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String s){
        username = s;
    }

    public String viewStatus(){
        String status = "Name: " + username + "\nMode: " + mode + "\nTokens: " + Integer.toString(token);
        return status;
    }

    public Mode redeemTokens(Mode m){
        token = 0;
        if (m.mode == "Positive" && token >= tokenlimit){
            System.out.println("You receive a reward!");
        } else if (m.mode == "Negative" && token >= tokenlimit){
            System.out.println("You receive a consequence.");
        }
        System.out.println("Tokens have been redeemed");
        return m;
    }

    public int getID(){
        return childID;
    }

    public void setID(int num){
        childID = num;
    }

    public Mode getMode(){
        Mode m = new Mode();
        Negative n = new Negative();
        Positive p = new Positive();
        if (mode == "Negative"){
            return n;
        } else if (mode == "Positive"){
            return p;
        }
        return m;
    }

}

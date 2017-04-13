/**
 * Created by maxkirchgesner on 2/5/17.
 */
import java.util.*;

public class Parent extends Adult{
    private static Map<Integer,Parent> parentRecord = new HashMap<Integer,Parent>();
    int parentID;
    public Parent(){
        parentID = 0;
    }
    private Parent(int ID){
        parentID = ID;
    }
    public int getParentID(){
        return parentID;
    }
    public void setParentID(int num){
        parentID = num;
    }


    public static Parent getInstance(int ID) {
        if (!parentRecord.containsKey(ID)){
            parentRecord.put(ID, new Parent(ID));
        }
        return parentRecord.get(ID);
    }
}

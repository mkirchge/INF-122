/**
 * Created by maxkirchgesner on 2/5/17.
 */

import java.util.*;

public class Mode {

    String mode = "Positive";

    public String getType(){
        return mode;
    }

    public void selectModeType(){
        Scanner in = new Scanner(System.in);
        System.out.println("What mode do you want the child in? (positive or negative)");
        String m = in.nextLine();
        Positive p = new Positive();
        Negative n = new Negative();
        if (m.contains("positive")){
            mode = "Positive";
        } else if (m.contains("negative")){
            mode = "Negative";
        }
    }

}

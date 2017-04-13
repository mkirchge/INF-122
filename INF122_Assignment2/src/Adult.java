/**
 * Created by maxkirchgesner on 2/6/17.
 */
import java.util.*;

public class Adult implements User{

    ArrayList<Child> childarray = new ArrayList<Child>();
    String username;

    public Adult(){
        username = "";
    }

    private Adult (String name){
        username = name;
    }

    public Child addChild(String name, Token t, Mode m, int ID){
        Child c = new Child(name, ID, t, m);
        childarray.add(c);
        return c;
    }

    public List getChildren(){
        return Collections.unmodifiableList(childarray);
    }

    public Child editChild(ChildParameterObject cpo){
        Scanner in = new Scanner(System.in);
        String name = "";
        for (Child child : childarray){
            if (child.getID() == cpo.getID()){
                name = child.username;
            }
        }
        Child c = new Child(name, cpo.getID(), cpo.getToken(), cpo.getMode());
        System.out.println("How would you like to edit this child?");
        System.out.println("1 - Delete Child");
        System.out.println("2 - Change name");
        System.out.println("3 - Switch mode");
        System.out.println("4 - Change number of tokens");
        System.out.println("5 - Quit Edit Child");
        System.out.println("Enter number: ");
        int num = in.nextInt();
        if (num == 1){
            deleteChild(cpo.getID());
        }
        else if (num == 2){
            System.out.println("What do you want to change the name to?");
            String new_name = in.nextLine();
            c.username = new_name;
            System.out.println("Changed name to " + new_name);
        }
        else if (num == 3){
            if (c.mode == "Positive"){
                c.mode = "Negative";
            } else if (c.mode == "Negative"){
                c.mode = "Positive";
            }
            System.out.println("Changed mode to" + c.mode + "for " + c.username);
        }
        else if (num == 4){
            System.out.println("How many tokens do you want the child to have?");
            int num_tokens = in.nextInt();
            c.token = num_tokens;
            System.out.println("Changed number of tokens for " + c.username + " to " + Integer.toString(num_tokens));
        }
        else if (num == 5){
            System.out.println("Quitting Edit Child...");
        }
        return c;
    }

    public Child deleteChild(int ID){
        Child temp = new Child();
        boolean found = false;
        for (Child c : childarray){
            if (c.childID == ID){
                found = true;
                temp = c;
                childarray.remove(c);
                System.out.println(c.username + " was deleted");
            }
        }
        if (!found){
            System.out.println("That child was not found in the registry");
        }
        return temp;
    }

    public void addToken(ChildParameterObject cpo){
        boolean found = false;
        for (Child c : childarray){
            if (c.childID == cpo.getID()){
                found = true;
                Token t = new Token();
                t.amount = c.token;
                c.tokens.add(t);
                c.token+=1;
            }
        }
        if (!found){
            System.out.println("That child was not found in the registry");
        }
    }

    public Child editToken(ChildParameterObject cpo){
        Child temp = new Child();
        boolean found = false;
        for (Child c : childarray){
            if (c.getID() == cpo.getID()){
                found = true;
                temp = c;
                Scanner in = new Scanner(System.in);
                System.out.println("How would you like to edit the token?");
                System.out.println("1 - Add Token");
                System.out.println("2 - Delete Token");
                System.out.println("3 - See note on Token");
                System.out.println("4 - Quit Edit Token");
                System.out.println("Enter number: ");
                int num = in.nextInt();
                if (num == 1){
                    addToken(cpo);
                }
                else if (num == 2){
                    deleteToken(cpo.getID());
                }
                else if (num == 3){
                    System.out.println(cpo.getToken().note);
                }
                else if (num == 4){
                    System.out.println("Quitting Edit Token...");
                }
            }
        }
        if (!found){
            System.out.println("That child was not found in the registry");
        }
        return temp;
    }

    public Child deleteToken(int ID){
        boolean found = false;
        Child c = new Child();
        for (Child child : childarray){
            if (child.getID() == ID){
                found = true;
                c = child;
                child.token = child.token - 1;
            }
        }
        if (!found){
            System.out.println("That child was not found in the registry");
        }
        return c;
    }

    public String addNote(String s, Token t, Child c){
        t.note = s;
        c.tokens.add(t);
        return s;
    }

    public String viewStatus(Child c){
        for (Child m : childarray){
            if (m == c){
                return m.viewStatus();
            }
        }
        return "Can't find that child";
    }

    public Mode redeemTokens(Child c, Mode m){
        //c.token = 0;
        if (m.mode == "Positive" && c.token >= c.tokenlimit){
            c.token = 0;
            System.out.println(c.username + " receives a reward!");
        } else if (m.mode == "Negative" && c.token >= c.tokenlimit){
            c.token = 0;
            System.out.println(c.username + " receives a consequence.");
        } else {
            System.out.println("Not enough tokens to redeem yet");
            System.out.println("Current number of tokens: " + c.token);
            System.out.println("Need " + c.tokenlimit + " tokens to redeem");
        }
        return m;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String s){
        username = s;
    }



}


/**
 * Created by maxkirchgesner on 2/6/17.
 */

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

public class UI {

    // DefaultCategoryDataset from JFreeChart to save data for line graph
    public static DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    // arraylist for registered users
    static ArrayList<Adult> adultUsers = new ArrayList<Adult>();
    static ArrayList<Child> childUsers = new ArrayList<Child>();

    // loads application for children
    public static void renderElements(Child c) {
        Scanner in = new Scanner(System.in);
        boolean running = true;
        if (c.getClass().toString().contains("Child")) {
            while (running) {
                System.out.println("\n1 - View Number of Tokens");
                System.out.println("2 - Redeem Tokens");
                System.out.println("3 - View Behavior History");
                System.out.println("4 - Log out of application (change user)");
                System.out.println("5 - Quit Application");
                System.out.println("Enter Number: ");
                int num = in.nextInt();

                // redeem tokens
                if (num == 1){
                    for (User u : childUsers){
                        if (u.getUsername().contains(c.getUsername())){
                            System.out.println(c.viewStatus());
                        }
                    }
                }

                // view # of tokens
                if (num == 2){
                    for (User u : childUsers){
                        if (u.getUsername().contains(c.getUsername())){
                            c.redeemTokens(c.getMode());
                        }
                    }
                }

                // log out of application
                if (num == 3){
                    for (Token t : c.tokens){
                        String h = Integer.toString(t.hour);
                        String m = Integer.toString(t.min);
                        String s = Integer.toString(t.sec);
                        String time = h + ":" + m + ":" + s;
                        dataset.addValue(t.amount, "Tokens", time);
                    }

                    // creates the line graph using the dataset that was created at top of class
                    LineChart chart = new LineChart("Behavior History of " + c.username , "Behavior History", dataset);

                    chart.pack();
                    RefineryUtilities.centerFrameOnScreen(chart);
                    chart.setVisible(true);
                }

                // log out of application
                if (num == 4){
                    System.out.println("Goodbye " + c.username.toUpperCase() + "!");
                    app();
                }

                // quit application
                if (num == 5){
                    running = false;
                }
            }
        }
    }

    // Loads application for adult
    public static void renderElements(Adult a){
        Scanner in = new Scanner(System.in);
        boolean running = true;
        if (a.getClass().toString().contains("Adult")){
            while (running){
                System.out.println("\n1  - View Number of Children");
                System.out.println("2  - Redeem Child's Tokens");
                System.out.println("3  - Add Child");
                System.out.println("4  - Edit Child");
                System.out.println("5  - Delete Child");
                System.out.println("6  - Add Tokens to Child");
                System.out.println("7  - Edit Tokens");
                System.out.println("8  - Delete Tokens from Child");
                System.out.println("9  - Set Token Limit for Child");
                System.out.println("10 - View Number of Tokens for a Child");
                System.out.println("11 - Set Token Addition Schedule (automatically add tokens every # seconds)");
                System.out.println("12 - Switch Mode on Child");
                System.out.println("13 - View Behavior History of Child (New Feature)");
                System.out.println("14 - Log Out of Application (change user)");
                System.out.println("15 - Quit Application");
                System.out.println("Enter Number: ");
                int num = in.nextInt();

                // view # of children
                if (num == 1){
                    System.out.println("Number of children is: " + a.childarray.size() + "\n");
                    System.out.println("Children: ");
                    for (int i = 0; i < a.getChildren().size(); i++){
                        System.out.println(" - " + childUsers.get(i).getUsername());
                    }
                }

                // redeem childs tokens
                if (num == 2){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("What child would you like to redeem tokens for?");
                    String child = temp.nextLine();
                    for (Child c : childUsers){
                        if (c.username.contains(child)){
                            a.redeemTokens(c, c.getMode());
                        }
                    }
                }

                // add child
                if (num == 3){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("What is the name of the child you want to add?");
                    String name = temp.nextLine();
                    int id = (int)(Math.random() * 100);
                    Token t = new Token();
                    Mode m = new Mode();
                    Child c = a.addChild(name,t,m,id);
                    childUsers.add(c);
                    System.out.println("Added " + name);
                    System.out.println("ID: " + Integer.toString(id));
                    System.out.println("Mode: " + m.mode);
                }

                // edit child
                if (num == 4){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("Which child do you want to edit?");
                    String child = temp.nextLine();
                    Token t = new Token();
                    for (Child c : childUsers){
                        if (c.username.contains(child)){
                            ChildParameterObject cpo = new ChildParameterObject(t, c.getMode(), c.getID());
                            a.editChild(cpo);
                        }
                    }
                }

                // delete child
                if (num == 5){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("Which child do you want to delete?");
                    String delete = temp.nextLine();
                    int tempID = 0;
                    for (int i = 0; i < a.childarray.size(); i++){
                        if (a.childarray.get(i).getUsername().contains(delete)){
                            tempID = a.childarray.get(i).getID();
                        }
                    }
                    a.deleteChild(tempID);
                }

                // add tokens to child
                if (num == 6){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("Which child do you want to add tokens to?");
                    String name = temp.nextLine();
                    for (Child c : childUsers) {
                        if (c.username.contains(name)) {
                            Scanner scanner = new Scanner(System.in);
                            Token t1 = new Token();
                            ChildParameterObject cpo = new ChildParameterObject(t1, c.getMode(), c.getID());
                            a.addToken(cpo);
                            System.out.println(c.username + " now has " + Integer.toString(c.token) + " tokens");
                            System.out.println("\nWould you like to add a note to this token? (yes or no)");
                            String add = scanner.nextLine();
                            if (add.contains("yes")){
                                Token t = new Token();
                                Scanner t2 = new Scanner(System.in);
                                System.out.println("What would you like to say on the note?");
                                String n = t2.nextLine();
                                a.addNote(n,t,c);
                            } else {
                                System.out.println("No note added.");
                            }
                        }
                    }
                }

                // edit tokens for child
                if (num == 7){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("Which child do you want to edit tokens for?");
                    String child = temp.nextLine();
                    Token t = new Token();
                    for (Child c : childUsers){
                        if (c.username.contains(child)){
                            ChildParameterObject cpo = new ChildParameterObject(t, c.getMode(), c.getID());
                            a.editToken(cpo);
                        }
                    }
                }

                // delete tokens for child
                if (num == 8){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("Which child do you want to delete tokens for?");
                    String child = temp.nextLine();
                    Token t = new Token();
                    for (Child c : childUsers){
                        if (c.username.contains(child)){
                            ChildParameterObject cpo = new ChildParameterObject(t, c.getMode(), c.getID());
                            a.deleteToken(c.getID());
                        }
                    }
                }

                //set token limit for child
                if (num == 9){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("Which child do you want to set token limit for?");
                    String child = temp.nextLine();
                    System.out.println("How many tokens do you want the child to have before redeeming?");
                    int token_limit = in.nextInt();
                    Token t = new Token();
                    for (Child c : childUsers){
                        if (c.username.contains(child)){
                            c.tokenlimit = token_limit;
                        }
                    }
                }

                // view # of tokens for child
                if (num == 10){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("Which child do you want to view # of tokens for?");
                    String child = temp.nextLine();
                    int counter = 0;
                    for (Child c : childUsers){
                        if (c.username.contains(child)){
                            System.out.println(c.viewStatus());
                            for (Token t : c.tokens){
                                counter +=1;
                                System.out.println("Token " + counter + ": " + t.getNote());
                            }
                        }
                    }
                }

                // set token schedule
                if (num == 11){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("How often would you like to add tokens to your children? (put answer in terms of seconds)");
                    int secs = temp.nextInt();
                    Timer timer = new Timer();
                    TimerTask tt = new TimerTask() {
                        public void run(){
                            for (Child c : childUsers){
                                c.token += 1;
                                Token t = new Token();
                                t.amount = c.token;
                                ChildParameterObject cpo = new ChildParameterObject(t, c.getMode(), c.getID());
                                a.addToken(cpo);
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(tt, 1000, 1000 * secs);
                    System.out.println("Task scheduled for every " + Integer.toString(secs) + " seconds.");
                }

                // switch mode
                if (num == 12){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("Which child do you want to switch the mode on?");
                    String name = temp.nextLine();
                    for (Child c : childUsers){
                        if (c.username.contains(name)){
                            if (c.mode.contains("Positive")){
                                c.mode = "Negative";
                                System.out.println("Switched " + c.username + " to negative");
                            } else if (c.mode.contains("Negative")){
                                c.mode = "Positive";
                                System.out.println("Switched " + c.username + " to positive");
                            }
                        }
                    }
                }


                // View History of a Child
                if (num == 13){
                    Scanner temp = new Scanner(System.in);
                    System.out.println("Which child do you want to view behavior history for?");
                    String name = temp.nextLine();

                    for (Child c : childUsers){
                        if (c.username.contains(name)){
                            for (Token t : c.tokens){
                                String h = Integer.toString(t.hour);
                                String m = Integer.toString(t.min);
                                String s = Integer.toString(t.sec);
                                String time = h + ":" + m + ":" + s;
                                dataset.addValue(t.amount, "Tokens", time);
                            }

                            // creates the line graph using the dataset that was created at top of class
                            LineChart chart = new LineChart("Behavior History of " + c.username , "Behavior History", dataset);

                            chart.pack();
                            RefineryUtilities.centerFrameOnScreen(chart);
                            chart.setVisible(true);
                        }
                    }

                    try {
                        File f = new File("userdata.txt");
                        FileOutputStream fos = new FileOutputStream(f);
                        PrintWriter pw = new PrintWriter(fos);

                        for (Adult adult : adultUsers){
                            String type = "adult";
                            String name1 = adult.getUsername();
                            String num_children = Integer.toString(adult.childarray.size());
                            pw.write(type);
                            pw.write("\n");
                            pw.write(name1);
                            pw.write("\n");
                            pw.write(num_children);
                            pw.write("\n");
                        }

                        for (Child c : childUsers){
                            String type = "child";
                            String name1 = c.getUsername();
                            String id = Integer.toString(c.getID());
                            String mode = c.getMode().mode;
                            String tokens = Integer.toString(c.token);
                            String tlimit = Integer.toString(c.tokenlimit);
                            pw.write(type);
                            pw.write("\n");
                            pw.write(name1);
                            pw.write("\n");
                            pw.write(id);
                            pw.write("\n");
                            pw.write(mode);
                            pw.write("\n");
                            pw.write(tokens);
                            pw.write("\n");
                            pw.write(tlimit);
                            pw.write("\n");
                            for (Token t : c.tokens){
                                String amount = Integer.toString(t.amount);
                                String h = Integer.toString(t.hour);
                                String m = Integer.toString(t.min);
                                String s = Integer.toString(t.sec);
                                String time = h + ":" + m + ":" + s;
                                pw.write(time);
                                pw.write("\n");
                                pw.write(amount);
                                pw.write("\n");
                                pw.write(t.note);
                                pw.write("\n");
                            }
                        }

                        pw.flush();
                        fos.close();
                        pw.close();
                    } catch (Exception e) {
                        System.out.println("File not created");
                    }

                }

                // log out of application
                if (num == 14){
                    System.out.println("Goodbye " + a.username.toUpperCase() + "!");
                    app();
                }

                // quit application
                if (num == 15){
                    try {
                        File f = new File("userdata.txt");
                        FileOutputStream fos = new FileOutputStream(f);
                        PrintWriter pw = new PrintWriter(fos);

                        for (Adult adult : adultUsers){
                            String type = "adult";
                            String name = adult.getUsername();
                            String num_children = Integer.toString(adult.childarray.size());
                            pw.write(type);
                            pw.write("\n");
                            pw.write(name);
                            pw.write("\n");
                            pw.write(num_children);
                            pw.write("\n");
                        }

                        for (Child c : childUsers){
                            String type = "child";
                            String name = c.getUsername();
                            String id = Integer.toString(c.getID());
                            String mode = c.getMode().mode;
                            String tokens = Integer.toString(c.token);
                            String tlimit = Integer.toString(c.tokenlimit);
                            pw.write(type);
                            pw.write("\n");
                            pw.write(name);
                            pw.write("\n");
                            pw.write(id);
                            pw.write("\n");
                            pw.write(mode);
                            pw.write("\n");
                            pw.write(tokens);
                            pw.write("\n");
                            pw.write(tlimit);
                            pw.write("\n");
                            for (Token t : c.tokens){
                                String amount = Integer.toString(t.amount);
                                String h = Integer.toString(t.hour);
                                String m = Integer.toString(t.min);
                                String s = Integer.toString(t.sec);
                                String time = h + ":" + m + ":" + s;
                                pw.write(time);
                                pw.write("\n");
                                pw.write(amount);
                                pw.write("\n");
                                pw.write(t.note);
                                pw.write("\n");
                            }
                        }

                        pw.flush();
                        fos.close();
                        pw.close();
                    } catch (Exception e) {
                        System.out.println("File not created");
                    }

                    running = false;
                }
            }
        }
    }

    public static void app(){
        System.out.print("Welcome to the Behave! application. ");
        System.out.println("\nPLEASE TYPE ALL ANSWERS IN LOWER CASE, INCLUDING NAMES!!!! (except for when asked for an integer)");
        Scanner in = new Scanner(System.in);
        boolean registered = true;

        while (registered) {
            System.out.println("\nAre you a registered user already? (\"yes\" or \"no\")");
            String isUser = in.nextLine();

            if (isUser.contains("yes")){

                try {
                    BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("adult")){
                            Adult a = new Adult();
                            a.username = reader.readLine();
                            int num_children = Integer.parseInt(reader.readLine());
                            adultUsers.add(a);
                        }
                        if (line.contains("child")){
                            Child c = new Child();
                            c.username = reader.readLine();
                            c.childID = Integer.parseInt(reader.readLine());
                            c.mode = reader.readLine();
                            c.token = Integer.parseInt(reader.readLine());
                            c.tokenlimit = Integer.parseInt(reader.readLine());
                            for (int i = 0; i < c.token; i++){
                                Token t = new Token();
                                String time = reader.readLine();
                                int a = Integer.parseInt(reader.readLine());
                                String note = reader.readLine();
                                String[] tokens = time.split(":");
                                t.amount = a;
                                t.hour = Integer.parseInt(tokens[0]);
                                t.min = Integer.parseInt(tokens[1]);
                                t.sec = Integer.parseInt(tokens[2]);
                                t.note = note;
                                c.tokens.add(t);
                            }
                            childUsers.add(c);
                            for (Adult a : adultUsers){
                                a.childarray.add(c);
                            }
                        }
                    }
                    reader.close();
                } catch (Exception e) {
                    System.out.println("");
                }

                Scanner t = new Scanner(System.in);
                System.out.println("What is your username?");
                String name = t.nextLine();

                for (Adult a : adultUsers){
                    if (a.getUsername().contains(name)){
                        System.out.println("Welcome back " + name.toLowerCase() + "!");
                        renderElements(a);
                    }
                }

                for (Child c : childUsers){
                    if (c.getUsername().contains(name)){
                        System.out.println("Welcome back " + name.toLowerCase() + "!");
                        renderElements(c);
                    }
                }
                registered = false;
            }

            else if (isUser.contains("no")){
                System.out.println("Making account for parent or child? (Type \"parent\" or \"child\")");
                String user_type = in.nextLine();
                System.out.println("What is your name?");
                String name = in.nextLine();

                if (user_type.contains("parent")){
                    int parentID = (int)(Math.random() * 1000);
                    //Parent p = usermaker.createParent();
                    Parent p = Parent.getInstance(parentID);
                    p.username = name;
                    Adult a = new Adult();
                    a.username = name;
                    adultUsers.add(p);
                    System.out.println("Your ID is: " + Integer.toString(parentID));
                    ArrayList<Child> children = new ArrayList<Child>();
                    System.out.println("\nHow many children do you have?");
                    int num = in.nextInt();

                    for (int i = 1; i <= num; i++){
                        Scanner g = new Scanner(System.in);
                        System.out.println("What is the name of child " + Integer.toString(i) + "?");
                        String c_name = g.nextLine();
                        Token t1 = new Token();
                        Mode m = new Mode();
                        Child temp = new Child(c_name, i, t1, m);
                        childUsers.add(temp);
                        children.add(temp);
                        a.childarray.add(temp);
                    }
                    renderElements(a);
                }

                else if (user_type.contains("child")){
                    int id = (int)(Math.random() * 10000);
                    Token t = new Token();
                    Mode m = new Mode();
                    //Child c = usermaker.createChild(name, id, t, m);
                    Child c = new Child(name, id, t, m);
                    for (Child child : childUsers){
                        if (child.username.contains("name")){
                            c = child;
                        }
                    }
                    if (childUsers.contains(c)){
                        renderElements(c);
                    } else {
                        childUsers.add(c);
                        renderElements(c);
                    }
                }

                registered = false;
            }

            else {
                System.out.println("That is not a valid command. Type \"yes\" or \"no\"");
            }
        }
    }

    public static void main(String [] args){
        // calls on main application
        app();
        System.out.println("Leaving Behave! application");
    }

}
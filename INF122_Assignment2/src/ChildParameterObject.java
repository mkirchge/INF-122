/**
 * Created by maxkirchgesner on 2/15/17.
 */
public class ChildParameterObject {

    private final Token t;
    private final Mode m;
    private final int ID;

    public ChildParameterObject(final Token token, final Mode mode, final int id){
        this.t = token;
        this.m = mode;
        this.ID = id;
    }

    public Token getToken(){
        return this.t;
    }

    public Mode getMode(){
        return this.m;
    }

    public int getID(){
        return this.ID;
    }

}

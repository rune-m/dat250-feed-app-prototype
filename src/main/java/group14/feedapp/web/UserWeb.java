package group14.feedapp.web;

public class UserWeb {

    private String id;
    private String name;
    private boolean isAdmin;
//    private List<VoteWeb> votes;
//    private List<PollWeb> polls;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

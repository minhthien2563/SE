package Account;


enum Roles {
    Librarian,
    Lecturers,
    Student,
    Other
}

public class Account {
    private String username;
    private String password;
    private Roles roles;
    private boolean activity;

    public Account() {
        this.username = null;
        this.password = null;
        this.roles = Roles.Other;
        this.activity = false;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void login(String username, String password, Roles roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.activity = true;
    }

    public void logout() {
        this.username = null;
        this.password = null;
        this.roles = null;
        this.activity = false;
    }

    public boolean isActivity() {
        if (!activity) {
            System.out.println("[ERROR]: Ban chua dang nhap de co the su dung chuc nang nay.");
            AccountUI accountUI = new AccountUI();
            accountUI.cmdUI();
        }

        return activity;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public boolean isLibrarian() {
        if (this.roles == Roles.Librarian)
            return true;

        return false;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getActivity() {
        return this.activity;
    }
}

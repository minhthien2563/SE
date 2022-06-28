package Account;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Book.BookDb;
import Book.BookUI;

public class AccountController {
    private AccountDb accountDb;

    public void register(Account account) {
        accountDb = new AccountDb("accounts.json");

        int idx = -1;

        idx = accountDb.search(accountDb.getKeyUsername(), account.getUsername()); //Find username from database

        if (idx != -1) {
            System.out.println("[ERROR]: Tai khoan nay da ton tai.");
            return;
        }

        accountDb.insert(account.getUsername(), account.getPassword());
        accountDb.write();
    }

    public void login(Account account) {
        accountDb = new AccountDb("accounts.json");
        JsonArray readData = accountDb.getAll();

        if (!account.getActivity()) {

            int idx = -1;

            idx = accountDb.search(accountDb.getKeyUsername(), account.getUsername()); //Find username from database

            if (idx != -1) {

                JsonObject object = accountDb.read(readData, idx);

                String tempUsername = object.get(accountDb.getKeyUsername()).getAsString();
                if (!tempUsername.equals(account.getUsername())) {
                    System.out.println("[ERROR]: Tai khoan khong ton tai trong he thong.");
                    return;
                }

                String tempPassword = object.get(accountDb.getKeyPassword()).getAsString();
                if (!tempPassword.equals(account.getPassword())) {
                    System.out.println("[ERROR]: Mat khau khong chinh xac.");
                    return;
                }

                String str_tempRoles = object.get(accountDb.getKeyRoles()).getAsString();
                Roles tempRoles = Roles.Other;
                for(Roles roles : Roles.values()) {
                    System.out.println(roles.toString());
                    if (str_tempRoles.equals(roles.toString()))
                        tempRoles = roles;
                }

                account.login(tempUsername, tempPassword, tempRoles);
                System.out.println("[SYSTEM]: Ban da dang nhap thanh cong!");
                System.out.println("[ROLES]: Ban dang su dung voi vai tro " + account.getRoles().toString());

                BookUI bookUI = new BookUI();
                bookUI.menuUI(account);
            }
        }
    }

    public void logout(Account account) {
        account.logout(); // Account Logout
        System.out.println("[SYSTEM]: Ban da dang xuat tai khoan!");
    }

    public boolean canBorrow(Account account) {
        boolean success = false;
        switch(account.getRoles()) {
            case Lecturers: {
                accountDb = new AccountDb("lecturers_limit.json");
                int idx = -1;

                idx = accountDb.search(accountDb.getKeyUsername(), account.getUsername());
                if (idx != -1) {
                    System.out.println("[ERROR]: Tai khoan cua ban thuoc vai tro " + account.getRoles().toString() + ", vi the ban chi co the muon toi da 5 quyen sach trong nam nay.");
                }
                break;
            }
            case Other: {
                BookDb bookDb = new BookDb("borrowed_book.json");
                JsonArray readData = bookDb.getAll();

                int count = 0;
                for(int i = 0; i < readData.size(); i++) {
                    JsonObject object = readData.get(i).getAsJsonObject();
                    String tempUsername = object.get(accountDb.getKeyUsername()).getAsString();
                    if (tempUsername.equals(account.getUsername())) {
                        if (count < 3) count++;
                    }
                }
                if (count >= 3) System.out.println("[ERROR]: Tai khoan cua ban thuoc vai tro " + account.getRoles().toString() + ", vi the ban chi co muon toi da 3 quyen sach.");
                break;
            }
            default: success = true;
        }
        return success;
    }

    public void sendMessageBorrow(Account account, String bookName) {
        System.out.println("[SYSTEM]: Ban da muon thanh cong quyen sach " + bookName);

        if (account.getRoles() == Roles.Student || account.getRoles() == Roles.Other) {
            System.out.println("[SYSTEM]: Thoi gian muon cua ban la 7 ngay, hay tra sach dung thoi han.");
        }

        System.out.println("[SYSTEM]: Hay den nhan sach tai chi nhanh. Cam on ban!");
    }
}

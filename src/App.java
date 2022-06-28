import Account.Account;
import Book.BookUI;

public class App {
    public static void main(String[] args) throws Exception {
        Account account = new Account();
        BookUI bookUI = new BookUI();
        bookUI.menuUI(account);
    }
}

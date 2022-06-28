package Book;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Account.Account;
import Account.AccountController;
import Account.AccountUI;

public class BookController {
    AccountUI accountUI = new AccountUI();
    AccountController accountController = new AccountController();

    public void showBorrowBook(Account account) {

        BookDb bookDb = new BookDb("borrowed_book.json");
        JsonArray readData = bookDb.getAll();

        for(int i = 0; i < readData.size(); i++) {
            JsonObject object = readData.get(i).getAsJsonObject();
            String tempUser = object.get("Username").getAsString();
            if (tempUser.equals(account.getUsername())) {
                String tempTitle = object.get(bookDb.getKeyTitle()).getAsString();
                String tempAuthor = object.get(bookDb.getKeyAuthor()).getAsString();
                Book book = new Book();
                book.setTitle(tempTitle);
                book.setAuthor(tempAuthor);
                book.show(false);
            }
        }
    }

    public void borrowBook(Account account, String bookName) {
        if (account.isActivity()) {
            if (accountController.canBorrow(account)) {
                BookDb bookDb = new BookDb("books.json");
                JsonArray readData = bookDb.getAll();

                for(int i = 0; i < readData.size(); i++) {
                    JsonObject object = readData.get(i).getAsJsonObject();
                    String tempTitle = object.get(bookDb.getKeyTitle()).getAsString();
                    if (tempTitle.equals(bookName)) {
                        String tempAuthor = object.get(bookDb.getKeyAuthor()).getAsString();
                        int tempPublishingYear = object.get(bookDb.getKeyPublishingYear()).getAsInt();
                        String tempRating = object.get(bookDb.getKeyRating()).getAsString();
                        String tempIntroduce = object.get(bookDb.getKeyIntroduce()).getAsString();
                        int tempRemainingAmount = object.get(bookDb.getKeyRemainingAmount()).getAsInt();
                        
                        Book book = new Book(tempTitle, tempAuthor, tempPublishingYear, tempRating, tempIntroduce, tempRemainingAmount);
                        book.show(true);
                        
                        bookDb = new BookDb("borrowed_book.json");
                        if (bookDb.insertBorrow(account.getUsername(), tempTitle, tempAuthor)) 
                            accountController.sendMessageBorrow(account, tempTitle);
                        else
                            System.out.println("[ERROR]: Ban da muon quyen sach nay roi, khong the muon tiep.");

                        break;
                    }
                }
            }
        }
    }

    public void requestBook(Account account, String title, String author) {
        BookDb bookDb = new BookDb("request_books.json");
        if (bookDb.insertRequest(account.getUsername(), title, author)) {
            System.out.println("[SYSTEM]: Gui yeu cau thanh cong!");
            System.out.println("> " + title);
        }
        else
            System.out.println("[ERROR]: Ban da yeu cau quyen sach nay roi, khong the yeu cau them.");
    }

    public void addBook(Account account, String Title, String Author, int PublishingYear, int RemainingAmount) {
        BookDb bookDb = new BookDb("books.json");

        int idx = -1;

        idx = bookDb.search(bookDb.getKeyTitle(), Title); //Find username from database

        if (idx != -1) {
            System.out.println("[ERROR]: Sach nay da ton tai.");
            return;
        }

        bookDb.insert(Title, Author, PublishingYear, RemainingAmount);
        bookDb.write();
    }

    public void showRequestBook() {
        BookDb bookDb = new BookDb("request_books.json");
        JsonArray readData = bookDb.getAll();

        for(int i = 0; i < readData.size(); i++) {
            JsonObject object = readData.get(i).getAsJsonObject();
            String tempUsername = object.get("Username").getAsString();
            String tempTitle = object.get(bookDb.getKeyTitle()).getAsString();
            String tempAuthor = object.get(bookDb.getKeyAuthor()).getAsString();

            Table tab = new Table(2, BorderStyle.UNICODE_BOX);
            tab.addCell("Tai khoan yeu cau");
            tab.addCell(tempUsername);
            tab.addCell("Ten sach");
            tab.addCell(tempTitle);
            tab.addCell("Tac gia");
            tab.addCell(tempAuthor);
            System.out.println(tab.render());
        }
    }
}

package Book;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BookSearch {

    public boolean searchWithAuthor(String author) {
        BookDb bookDb = new BookDb("books.json");
        JsonArray readData = bookDb.getAll();

        boolean found = false;
        for(int i = 0; i < readData.size(); i++) {
            JsonObject object = readData.get(i).getAsJsonObject();
            String tempAuthor = object.get(bookDb.getKeyAuthor()).getAsString();
            if (tempAuthor.equals(author)) {
                String tempTitle = object.get(bookDb.getKeyTitle()).getAsString();
                int tempPublishingYear = object.get(bookDb.getKeyPublishingYear()).getAsInt();
                String tempRating = object.get(bookDb.getKeyRating()).getAsString();
                String tempIntroduce = object.get(bookDb.getKeyIntroduce()).getAsString();
                int tempRemainingAmount = object.get(bookDb.getKeyRemainingAmount()).getAsInt();
                
                Book book = new Book(tempTitle, tempAuthor, tempPublishingYear, tempRating, tempIntroduce, tempRemainingAmount);
                book.show(true);
                book = null;
                
                found = true;
            }
        }
        if (!found) System.out.println("[ERROR]: Khong tim thay sach tuong ung voi tac gia " + author);
        return found;
    }

    public boolean searchWithTitle(String title) {
        BookDb bookDb = new BookDb("books.json");
        JsonArray readData = bookDb.getAll();

        boolean found = false;
        for(int i = 0; i < readData.size(); i++) {
            JsonObject object = readData.get(i).getAsJsonObject();
            String tempTitle = object.get(bookDb.getKeyTitle()).getAsString();
            if (tempTitle.equals(title)) {
                String tempAuthor = object.get(bookDb.getKeyAuthor()).getAsString();
                int tempPublishingYear = object.get(bookDb.getKeyPublishingYear()).getAsInt();
                String tempRating = object.get(bookDb.getKeyRating()).getAsString();
                String tempIntroduce = object.get(bookDb.getKeyIntroduce()).getAsString();
                int tempRemainingAmount = object.get(bookDb.getKeyRemainingAmount()).getAsInt();
                
                Book book = new Book(tempTitle, tempAuthor, tempPublishingYear, tempRating, tempIntroduce, tempRemainingAmount);
                book.show(true);
                found = true;
                break;
            }
        }
        if (!found) System.out.println("[ERROR]: Khong tim thay sach tuong ung voi ten sach " + title);
        return found;
    }
} 

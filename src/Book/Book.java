package Book;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

public class Book {
    private String title;
    private String author;
    private int publishingYear;
    private String rating;
    private String introduce;
    private int remainingAmount;


    public Book() {
        this.title = null;
        this.author = null;
        this.publishingYear = 0;
        this.rating = null;
        this.introduce = null;
        this.remainingAmount = 0;
    }


    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }


    public void setRemainingAmount(int remainingAmount) {
        this.remainingAmount = remainingAmount;
    }


    public Book(String title, String author, int publishingYear, String rating, String introduce, int remainingAmount) {
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
        this.rating = rating;
        this.introduce = introduce;
        this.remainingAmount = remainingAmount;
    }

    public void show(boolean inBranch) {
        Table tab = new Table(2, BorderStyle.UNICODE_BOX_WIDE);
        tab.addCell("Ten sach");
        tab.addCell(title);

        tab.addCell("Tac gia");
        tab.addCell(author);
        
        if (inBranch) {
            tab.addCell("Nam xuat ban");
            tab.addCell(String.valueOf(publishingYear));

            tab.addCell("Danh gia");
            tab.addCell(rating);

            tab.addCell("Gioi thieu");
            tab.addCell(introduce);

            tab.addCell("So luong con lai o chi nhanh");
            tab.addCell(String.valueOf(remainingAmount));
        }

        System.out.println(tab.render());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

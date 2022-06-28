package Book;

import java.util.Scanner;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import Account.Account;
import Account.AccountController;
import Account.AccountUI;

public class BookUI {
    BookController bookController = new BookController();
    BookSearch bookSearch = new BookSearch();
    AccountUI accountUI = new AccountUI();
    AccountController accountController = new AccountController();
    
    Scanner scanner = new Scanner(System.in);
    
    public void menuUI(Account account) {
        int select = 0;
        boolean cont = true;
        do {
            Table tab = new Table(1, BorderStyle.UNICODE_BOX);
            tab.addCell("MENU CHINH");
            if (!account.getActivity()) tab.addCell("0. Tai khoan");
            tab.addCell("1. Tim kiem sach");
            tab.addCell("2. Muon sach");
            tab.addCell("3. Sach dang muon");
            tab.addCell("4. Yeu cau sach moi");
            if (account.getActivity()) tab.addCell("5. Dang xuat");
            if (account.isLibrarian()) tab.addCell("6. Quan ly");
            System.out.println(tab.render());

            System.out.println("Hay nhap lua chon cua ban?");
            System.out.print(":: ");
            
            select = scanner.nextInt();
            scanner.nextLine();

            switch(select) {
                case 0: {
                    accountUI.cmdUI();
                    break;
                }
                case 1: {
                    do {
                        tab = new Table(1, BorderStyle.UNICODE_BOX);
                        tab.addCell("LUA CHON TIM KIEM");
                        tab.addCell("1. Tim kiem theo ten tac gia");
                        tab.addCell("2. Tim kiem theo ten sach");
                        System.out.println(tab.render());

                        System.out.println("Hay nhap lua chon cua ban?");
                        System.out.print(":: ");

                        select = scanner.nextInt();
                        scanner.nextLine();
                    } while(select < 0 || select > 2);
                    
                    String tempInput = null;
                    if (select == 1) {
                        System.out.println("Hay nhap ten tac gia ban muon tim kiem");
                        System.out.print(":: ");
                        tempInput = scanner.nextLine();

                        if (bookSearch.searchWithAuthor(tempInput))
                            selectUI(account);
    
                    } else if (select == 2) {
                        System.out.println("Hay nhap ten sach ban muon tim kiem");
                        System.out.print(":: ");
                        tempInput = scanner.nextLine();

                        if (bookSearch.searchWithTitle(tempInput))
                            selectUI(account);
                    }
                    break;
                }
                case 2: {
                    if (account.isActivity())
                        selectUI(account);
                        
                    break;
                }
                case 3: {
                    if (account.isActivity())
                        bookController.showBorrowBook(account);
                    
                    break;
                }
                case 4: {
                    if (account.isActivity()) {
                     
                        System.out.println("Ban muon yeu cau thu vien bo sung them sach nao?");
                        System.out.println("\t(Hay nhap ten sach)");
                        System.out.print(":: ");

                        String tempTitle = scanner.nextLine();
                        
                        System.out.println("Hay nhap ten tac gia cua sach?");
                        System.out.println("\t(Hay nhap ten)");
                        System.out.print(":: ");

                        String tempAuthor = scanner.nextLine();

                        bookController.requestBook(account, tempTitle, tempAuthor);
                    }
                    break;
                }
                case 5: {
                    if (account.isActivity())
                        accountController.logout(account);

                    cont = false;
                    break;
                }
                case 6: {
                    if (account.isLibrarian()) {
                        tab = new Table(1, BorderStyle.UNICODE_BOX);
                        tab.addCell("MENU QUAN LY");
                        tab.addCell("1. Them sach moi");
                        tab.addCell("2. Xem yeu cau bo sung");
                        System.out.println(tab.render());

                        select = scanner.nextInt();

                        if (select == 1) {
                            System.out.println("Hay nhap ten sach ban muon them?");
                            System.out.print(":: ");

                            String tempTitle = scanner.nextLine();

                            System.out.println("Hay nhap ten tac gia cua quyen sach");
                            System.out.print(":: ");

                            String tempAuthor = scanner.nextLine();

                            System.out.println("Hay nhap nam xuat ban cua quyen sach");

                            int tempPublishingYear = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("Hay nhap so luong them");
                            int tempRemainingAmount = scanner.nextInt();

                            bookController.addBook(account, tempTitle, tempAuthor, tempPublishingYear, tempRemainingAmount);
                        } else if (select == 2) {
                            bookController.showRequestBook();
                        }
                    }
                }
            }
        } while(cont);
    }

    public void selectUI(Account account) {
        System.out.println("Ban muon muon quyen sach nao?");
        System.out.println("\t(Hay nhap ten sach)");
        System.out.print(":: ");
        String bookName = scanner.nextLine();
        bookController.borrowBook(account, bookName);
    }
}

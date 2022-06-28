package Account;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

public class AccountUI {
    Scanner scanner = new Scanner(System.in);

    AccountController accountController = new AccountController();

    private List<Object> inputUI() {
        List<Object> list = new ArrayList<>();

        System.out.print("Tai khoan: ");
        list.add(scanner.nextLine());

        System.out.print("Mat khau: ");
        list.add(scanner.nextLine());

        return list;
    }

    public void cmdUI() {
        int select = 0;
        do {
            Table tab = new Table(1, BorderStyle.UNICODE_BOX);
            tab.addCell("TAI KHOAN");
            tab.addCell("1. Dang nhap");
            tab.addCell("2. Dang ky");
            System.out.println(tab.render());

            System.out.println("Hay nhap lua chon cua ban?");
            System.out.print(":: ");

            select = scanner.nextInt();
            scanner.nextLine();
        } while (select < 0 || select > 2);

        List<Object> list = inputUI();
        String Username = list.get(0).toString();
        String Password = list.get(1).toString();
        Account account = new Account(Username, Password);

        if (select == 1) 
            accountController.login(account);
        else if (select == 2) {
            accountController.register(account);
        }
    }
}

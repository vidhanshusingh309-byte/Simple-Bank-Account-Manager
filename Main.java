
import java.util.*;
import java.io.*;

class Account {
    String id, name;
    double balance;

    Account(String id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
}

public class Main {
    static ArrayList<Account> list = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int id = 101;

    static Account find(String s) {
        for (Account a : list)
            if (a.id.equalsIgnoreCase(s)) return a;
        return null;
    }

    static void save() throws Exception {
        PrintWriter pw = new PrintWriter("accounts.txt");
        for (Account a : list)
            pw.println(a.id + "|" + a.name + "|" + a.balance);
        pw.close();
    }

    static void load() throws Exception {
        File f = new File("accounts.txt");
        if (!f.exists()) return;
        Scanner fs = new Scanner(f);
        while (fs.hasNextLine()) {
            String[] p = fs.nextLine().split("\\|");
            list.add(new Account(p[0], p[1], Double.parseDouble(p[2])));
            id = Math.max(id, Integer.parseInt(p[0].substring(1)) + 1);
        }
        fs.close();
    }

    public static void main(String[] args) throws Exception {
        load();

        while (true) {
            System.out.println("\n1.Create 2.Deposit 3.Withdraw");
            System.out.println("4.Search 5.Sort 0.Exit");
            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 0) {
                save();
                break;
            }

            if (ch == 1) {
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Opening balance: ");
                double b = Double.parseDouble(sc.nextLine());
                if (b < 0) {
                    System.out.println("Invalid balance");
                    continue;
                }
                String aid = "A" + id++;
                list.add(new Account(aid, name, b));
                System.out.println("Account created. ID: " + aid
                        + " | " + name + " | Balance: " + b);
            } else if (ch == 2 || ch == 3) {
                System.out.print("Account ID: ");
                Account a = find(sc.nextLine());
                System.out.print("Amount: ");
                double amt = Double.parseDouble(sc.nextLine());

                if (a == null || amt <= 0)
                    System.out.println("Invalid account or amount");
                else if (ch == 2) {
                    a.balance += amt;
                    System.out.println("Deposited " + amt
                            + ". New balance: " + a.balance);
                } else if (amt > a.balance) {
                    System.out.println("Insufficient balance. Current balance: "
                            + a.balance);
                } else {
                    a.balance -= amt;
                    System.out.println("Withdrawn " + amt
                            + ". New balance: " + a.balance);
                }
            } else if (ch == 4) {
                System.out.print("Name: ");
                String name = sc.nextLine();
                for (Account a : list)
                    if (a.name.equalsIgnoreCase(name))
                        System.out.println(a.id + " | " + a.name
                                + " | " + a.balance);
            } else if (ch == 5) {
                // Manual bubble sort
                for (int i = 0; i < list.size(); i++)
                    for (int j = 0; j < list.size() - 1 - i; j++)
                        if (list.get(j).balance < list.get(j + 1).balance) {
                            Account t = list.get(j);
                            list.set(j, list.get(j + 1));
                            list.set(j + 1, t);
                        }

                for (Account a : list)
                    System.out.println(a.id + " | " + a.name
                            + " | " + a.balance);
            }
        }
    }
}

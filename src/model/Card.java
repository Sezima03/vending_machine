package model;
import enums.ActionLetter;
import model.*;
import paymentReseiver.PaymentReseiver;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class Card {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private BankCard bankCard;

    private static boolean isExit = false;

    public Card() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        bankCard = new BankCard(200);
    }

    public static void run() {
        Card app = new Card();
        while (!isExit) {
            app.startSimulation();
        }
    }

    public void startSimulation() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Введите номер карточки: ");
        int number= sc.nextInt();


        System.out.println("Введите пароль: ");
        int paasword=sc.nextInt();

        print("В автомате доступны:");
        showProducts(products);

        print("Монет на сумму: " + bankCard.getAmount());


        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

        while (bankCard.getAmount() > 0) {
            UniversalArray<Product> allowedProducts = getAllowedProducts();
            if (allowedProducts.size() == 0) {
                print("На вашем балансе недостаточно средств для покупки продуктов.");
                break;
            }
            chooseAction(allowedProducts);
        }

        if (bankCard.getAmount() <= 0) {
            print("У вас недостаточно средств для покупки продуктов. Программа завершена.");
        }

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (bankCard.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            bankCard.setAmount(bankCard.getAmount() + 10);
            print("Вы пополнили баланс на 10");
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    bankCard.setAmount(bankCard.getAmount() - products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попрбуйте еще раз.");
                chooseAction(products);
            }
        }


    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public String toString() {
        return "Карта с балансом: " + bankCard.getAmount();
    }
}

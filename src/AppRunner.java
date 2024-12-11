import enums.ActionLetter;
import model.*;
import paymentReseiver.PaymentReseiver;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final CoinAcceptor coinAcceptor;

    private static boolean isExit = false;
    private PaymentReseiver paymentReseiver;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        coinAcceptor = new CoinAcceptor(100);

    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("Выберите тип платежного приемника: ");
        print("1-монетоприемник");
        print("2-Карта");

        String choice=fromConsole().trim();
        if ("1".equals(choice)){
            paymentReseiver=new CoinAcceptor(0);
            print("вы выбрали монетоприемник");
        } else if ("2".equals(choice)) {
            paymentReseiver=new BankCard();
            print("Вы выбрали картоприемник");
        }else {
            print("Недопустимый выбор");
            return;
        }

        Machine machine=new Machine(paymentReseiver);
        if (paymentReseiver instanceof CoinAcceptor) {
            print("Введите сумму монет для оплаты:");
        } else if (paymentReseiver instanceof BankCard) {
            print("Введите номер карты для оплаты:");
        }
        String inputAmount = fromConsole().trim();
        try {
            int amount = Integer.parseInt(inputAmount);
            machine.prosessPay(amount);
            print("Сумма в платёжном приёмнике: " + machine.getPay());
        } catch (NumberFormatException e) {
            print("Неверный ввод. Введите число.");
        }
        print("В автомате доступны:");
        showProducts(products);



        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (coinAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    coinAcceptor.setAmount(coinAcceptor.getAmount() - products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    break;
                } else if ("h".equalsIgnoreCase(action)) {
                    isExit = true;
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попрбуйте еще раз.");
            chooseAction(products);
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
}

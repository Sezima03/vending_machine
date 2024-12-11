package paymentReseiver;

public interface PaymentReseiver {
    boolean acceptPayment(int amount);

    int getAmount();

    void resetAmount();
}

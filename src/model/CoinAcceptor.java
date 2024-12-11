package model;

import paymentReseiver.PaymentReseiver;

public class CoinAcceptor implements PaymentReseiver {
    private int amount;

    public CoinAcceptor(int initial) {
        this.amount = initial;
    }

    public void setAmount(int amount){
        this.amount=amount;
    }


    @Override
    public boolean acceptPayment(int amount) {
        if (amount>0){
            this.amount+=amount;
            return true;
        }
        return false;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void resetAmount() {
        this.amount=0;
    }
}

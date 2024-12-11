package model;

import paymentReseiver.PaymentReseiver;

import java.util.Scanner;

public class BankCard implements PaymentReseiver {
    private int amount;

    public BankCard() {
        this.amount = 0;
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

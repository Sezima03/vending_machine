package model;


import paymentReseiver.PaymentReseiver;

public class Machine {
    private PaymentReseiver paymentReseiver;
    private int currentPay;

    public Machine(PaymentReseiver paymentReseiver) {
        this.paymentReseiver = paymentReseiver;
        this.currentPay=0;
    }

    public void prosessPay(int amount){
        //return paymentReseiver.acceptPayment(amount);
        boolean success = paymentReseiver.acceptPayment(amount);
        if (success) {
            currentPay = paymentReseiver.getAmount();
        } else {
            System.out.println("Ошибка при приеме платежа.");
        }
    }

    public int getPay(){
        return currentPay;
    }


}

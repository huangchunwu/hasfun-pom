package cn.hasfun.framework.Observer;

import java.util.Observable;

public class Provider extends Observable {


    public void produceGift(){
        super.setChanged();
        super.notifyObservers(Message.builder().content("hello world").build());
    }

    public static void main(String[] args) {
        Provider provider  = new Provider();
        provider.addObserver(new Customer());
        provider.produceGift();
    }

}

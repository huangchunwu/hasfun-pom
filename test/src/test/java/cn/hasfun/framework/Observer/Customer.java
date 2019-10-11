package cn.hasfun.framework.Observer;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Observer;

@Slf4j
public class Customer implements Observer {


    @Override
    public void update(Observable o, Object arg) {
       log.info("Consumer update..." + o + ";arg1=" + arg);
        log.info("test()->,{}","这里是logback");
    }


}

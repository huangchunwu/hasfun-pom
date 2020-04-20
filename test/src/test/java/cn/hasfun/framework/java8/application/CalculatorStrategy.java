package cn.hasfun.framework.java8.application;

@FunctionalInterface
public interface CalculatorStrategy {

    public double calcTax(double salary,double bonus);

}

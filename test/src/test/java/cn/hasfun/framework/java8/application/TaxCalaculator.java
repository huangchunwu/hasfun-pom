package cn.hasfun.framework.java8.application;

public class TaxCalaculator {

    private final double salary;

    private final double bonus;

    private CalculatorStrategy calculatorStrategy;

    public TaxCalaculator(double salary,double bonus,CalculatorStrategy calculatorStrategy){
        this.salary = salary;
        this.bonus = bonus;
        this.calculatorStrategy = calculatorStrategy;
    }

    public double calcTax(){
        return  calculatorStrategy.calcTax(getSalary(),getBonus());
    }

    public double getSalary() {
        return salary;
    }

    public double getBonus() {
        return bonus;
    }

//    public CalculatorStrategy getCalculatorStrategy() {
//        return calculatorStrategy;
//    }
//
//    public void setCalculatorStrategy(CalculatorStrategy calculatorStrategy) {
//        this.calculatorStrategy = calculatorStrategy;
//    }
}

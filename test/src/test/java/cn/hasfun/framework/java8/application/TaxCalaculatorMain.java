package cn.hasfun.framework.java8.application;

public class TaxCalaculatorMain {

    public static void main(String[] args) {
//        TaxCalaculator calaculator = new TaxCalaculator(10000d,2000d){
//            @Override
//            public double calcTax() {
//                return getSalary() * 0.1 + getBonus() *0.05;
//            }
//        };

//        TaxCalaculator calaculator = new TaxCalaculator(10000d,2000d);
//        calaculator.setCalculatorStrategy(((salary, bonus) -> {
//            return salary*0.1+bonus*0.05;
//        }));
//        System.out.println(calaculator.calcTax());

        TaxCalaculator calaculator = new TaxCalaculator(10000d,2000d,((salary, bonus) -> {return salary*0.1+bonus*0.05;}));
        System.out.println(calaculator.calcTax());
    }
}

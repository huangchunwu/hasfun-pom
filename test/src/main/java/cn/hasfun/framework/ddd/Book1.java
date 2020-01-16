package cn.hasfun.framework.ddd;

public class Book1 {

    /**
     * 允许10%超订
     *
     * @param cargo
     * @param voyage
     * @return
     */
    public int makeBooking(Cargo cargo, Voyage voyage) {
        if (!OverBookingPolice.isAllowed(cargo, voyage)) {
            System.out.println("注意！超载了.当前运载量："+voyage.getCargoSize());
            return -1;
        }
        voyage.addCargo(cargo);
        return 1;
    }







    public static void main(String[] args) {
        Cargo cargo = new Cargo();
        Voyage voyage = new Voyage();
        var i = 0;
        for (; ; ) {
            if (i > 100) {
                break;
            }
            new Book1().makeBooking(cargo, voyage);
            i++;
        }
        System.out.println("=====航程装配货物成功====");
    }
}

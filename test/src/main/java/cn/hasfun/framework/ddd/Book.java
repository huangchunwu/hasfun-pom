package cn.hasfun.framework.ddd;

public class Book {

    /**
     * 允许10%超订
     * @param cargo
     * @param voyage
     * @return
     */
    public int makeBooking(Cargo cargo,Voyage voyage){
        var maxBooking = voyage.getCapacity()*1.1;
        if (voyage.getCargoSize() + cargo.getSize()>maxBooking){
            return -1;
        }
        voyage.addCargo(cargo);
        return 1;
    }

    public static void main(String[] args) {

    }
}

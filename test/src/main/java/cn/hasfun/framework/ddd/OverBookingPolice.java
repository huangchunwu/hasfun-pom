package cn.hasfun.framework.ddd;

/**
 * 超定规则：允许10%超订
 */
public class OverBookingPolice {

    public static boolean isAllowed(Cargo cargo,Voyage voyage){
        var maxBooking = voyage.getCapacity()*1.1;
        if (voyage.getCargoSize() + cargo.getSize()>maxBooking){
            return false;
        }
        return true;
    }
}

package cn.hasfun.framework.ddd;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 领域对象：航程
 */
@Data
public class Voyage {
    // 载货量
    private int capacity = 10;

    private List<Cargo> cargoList = new ArrayList<>();

    public void addCargo(Cargo cargo){
        cargoList.add(cargo);
    }

    public int getCargoSize(){
        return cargoList.stream().mapToInt(v->v.hashCode()).sum();
    }

}

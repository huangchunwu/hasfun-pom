package cn.hasfun.framework.algorithm;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 1 收益能力有待提高，点击后跳组合超市
 *
 * 2 择时能力有待提高，跳持仓分析；
 *
 * 3 风控能力有待提高，跳持仓分析；
 *
 * 4 换手率有待提高，跳持仓分析；
 *
 * 5 选股能力有待提高，跳股票池；
 *
 * 6 个人投顾服务，跳投顾服务功能（如果5个维度都>=90分：）
 *
 * 7 资产配置计划方面有待提高，点击后跳资产配置建议页面；
 *
 * 8 您的账户收益本月无变化，可以尝试先做一些理财哦，跳现金理财页面
 */
public class TestAlgorithm {


    @Test
    public void testSort(){
//        unSortList.add(Map.of("001","92"));//风控
//        unSortList.add(Map.of("002","37"));//收益
//        unSortList.add(Map.of("014","2"));//换手
//        unSortList.add(Map.of("016","45"));//择时
//        unSortList.add(Map.of("017","49"));//选股
        //如果5个维度都为空，和未0一样处理，
        // 如果多个维度都是0，或者都是相等的值，
        // 按以下顺序取：收益能力、择时能力、风控能力、换手率、选股能力
        Map<String,String> unSortMap = Map.of("001","","002","","014","","016","","017","");
        Map<Integer,String> sortMap = new HashMap<>(5);
        unSortMap.entrySet().forEach(v-> {
            String value = v.getValue();
            if (StringUtils.isEmpty(value)) {
                value = "0";
            }
            if (v.getKey().equals("002")){
                sortMap.put(1,value);
            }
            if (v.getKey().equals("016")){
                sortMap.put(2,value);
            }
            if (v.getKey().equals("001")){
                sortMap.put(3,value);
            }
            if (v.getKey().equals("014")){
                sortMap.put(4,value);
            }
            if (v.getKey().equals("017")){
                sortMap.put(5,value);
            }
        });

        System.out.println(sortMap.entrySet().stream().min(Comparator.comparing(v -> Integer.valueOf(v.getValue()))).get());


    }

}

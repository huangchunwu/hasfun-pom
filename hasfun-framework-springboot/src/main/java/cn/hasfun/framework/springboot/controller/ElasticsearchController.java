package cn.hasfun.framework.springboot.controller;

import cn.hasfun.framework.springboot.constant.ExecResult;
import cn.hasfun.framework.springboot.kafka.Commodity;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/es")
@RestController
public class ElasticsearchController {


    @Autowired
    public ElasticsearchTemplate elasticsearchTemplate;


    @RequestMapping("/q")
    public ExecResult query(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", "吐司"))
                .build();
        List<Commodity> list = elasticsearchTemplate.queryForList(searchQuery, Commodity.class);
        System.out.println(list);
        return ExecResult.builder().data(list).build();
    }


    @RequestMapping("/save")
    public ExecResult save(){
        Commodity commodity = new Commodity();
        commodity.setSkuId("1501009005");
        commodity.setName("葡萄吐司面包（10片装）");
        commodity.setCategory("101");
        commodity.setPrice(160);
        commodity.setBrand("良品铺子");
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(commodity).build();
        return ExecResult.builder().data(elasticsearchTemplate.index(indexQuery)).build();
    }

}

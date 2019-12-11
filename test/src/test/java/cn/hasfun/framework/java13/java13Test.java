package cn.hasfun.framework.java13;

import org.junit.Test;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class java13Test {


    /**
     * jdk13'new feature is  text block
     */
    @Test
    public void textBlock(){
        String sql = """
                select * from system_user
                where user_id = '543255'
                and age >12
                and birth>'1991'
        """;

        String html = """
..............<html>
..............    <body>
..............        <p>Hello, world</p>
..............    </body>
..............</html>
..............""";

        System.out.println(html);
        System.out.println(sql);
    }


    /**
     * jdk12
     * @throws IOException
     */
    @Test
    public void switchFeature() throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String inputString = br.readLine();
        switch ("A") {
            case "A" -> System.out.println("you is A");
            case "B" -> System.out.println("you is B");
        }
    }


    /**
     * jdk13
     * @throws IOException
     */
    @Test
    public void switchFeature2() throws IOException {
        String a = "A";
       var result =  switch (a) {
           case "A" :yield "1";
           case "B": yield "2";
           default:
               throw new IllegalStateException("Unexpected value: " + a);
       };
       System.out.println(result);
    }
}

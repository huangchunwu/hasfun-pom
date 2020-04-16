package cn.hasfun.framework.referencr;

import org.springframework.expression.spel.ast.NullLiteral;

public class NormalReference {

    public static void main(String[] args) {
        M m = new M();
        m = null;
        System.gc();
    }
}

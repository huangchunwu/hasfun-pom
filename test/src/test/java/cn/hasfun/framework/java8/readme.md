# java8 函数式编程

## Lombda 表达式

Lambda 表达式”(lambda expression)是一个匿名函数，Lambda表达式基于数学中的λ演算得名，直接对应于其中的lambda抽象(lambda abstraction)

跟js类比， var f = function login(){};

Java 可以声明 变量， 赋值。  int a =1；
Java8之后可以声明 变量， 赋值给一个 函数方法。  dosomething = public void doSomething(String value){System.out.println(value);};
                          public是多余的        dosomething = void doSomething(String value){System.out.println(value);}; 
doSomething方法名是多余的，因为已经赋值给变量了 dosomething = void (String value){System.out.println(value);};
void 是多余的，因为编译器可以判断返回类型 dosomething =  (String value){System.out.println(value);};      
String 是多余的，因为编译器可以判断入参类型 dosomething =  (value){System.out.println(value);};
因为方法体只有一句话，大括号可以去掉，参数只有一个，参数的小括号去掉，在参数与方法体之间用—> 表示。
 dosomething = value->System.out.println("hello world);      
 
 这样，我们就成功的非常优雅的把“一块代码”赋给了一个变量。而“这块代码”，或者说“这个被赋给一个变量的函数”，就是一个Lambda表达式。 
 
   
# 函数接口

在Java 8里面，所有的Lambda的类型都是一个接口，而Lambda表达式本身，也就是”那段代码“，需要是这个接口的实现

如上，Lambda表达式 赋值给了变量 dosomething。 那么这个变量的类型 就是 函数接口，

现在，我们给dosomething 声明一个类型， 
interface MyInterface{
    public void dosomething(String s);
}
则
MyInterface dosomething = value->System.out.println("hello world);   
这个接口叫做函数接口，为了防止后面的人在里面追加方法，Java8 加了注解@FunctionalInterface声明函数接口，用于与普通接口做区分。
@FunctionalInterface
interface MyInterface{
    public void dosomething(String s);
}

这样，我们就得到了一个完整的Lambda表达式声明


参考 https://www.zhihu.com/question/20125256


- Lambda不包含参数，使用()表示没有参数

`Runnable function =  ()->System.out.println("ds");`

- 包含且只包含一个参数，可省略参数的括号

`ActionListener oneArg = event -> System.out.println("button clicked");`

- Lambda 表达式的主体不仅可以是一个表达式，而且也可以是一段代码块。

`
Runnable multiStatement = () ->{
    System.out.println("Hello");
    System.out.println("World");
};
`

- 可以包含多个参数。这里需要考虑如何读该 Lambda 表达式了。这等代码并不是将两个数字相加，而是创建了一个函数，用来计算两个数字相加的结果。

`BinaryOperator<Long> add = (x,y) -> x + y;`

- 前面的Lambda表达式中的参数类型都是由编译器推断得出的。但是有时最好使用显式声明参数类型。

`BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;`

- Lambda 表达式引用的是值，而不是变量,Java 8 虽然放松了这一限制， 可以引用非 final 变量， 但是该变量在既成事实上必须是final。 虽然无需将变量声明为 final， 但在 Lambda 表达式中， 也无法用作非终态变量。 如果坚持用作非终态变量， 编译器就会报错

`String name = getUserName();
button.addActionListener(event -> System.out.println("hi " + name));`


- 函数接口是只有一个抽象方法的接口， 用作 Lambda 表达式的类型

`public interface ActionListener extends EventListener {
    public void actionPerformed(ActionEvent event);
}

`

# 总结

- Lambda 表达式是一个匿名方法，将行为像数据一样进行传递。
- 函数接口指仅具有单个抽象方法的接口，用来表示 Lambda 表达式的类型。
- Lambda 表达式的常见结构： BinaryOperator<Integer> add = (x,y) -> x +y。
wangzhijie@bogon jvm % javac Hello.java
wangzhijie@bogon jvm % javap -c Hello
警告: 文件 ./Hello.class 不包含类 Hello
Compiled from "Hello.java"
// Hello 类是默认的一个无参数的构造函数
public class Hello {
  public Hello();
    Code:
       // aload_0 从局部变量0中装载引用类型值
       0: aload_0
       // invokespecial 根据编译时类型来调用实例方法，表示调用当前类的父类 Object 初始化方法，位置1
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       // return 从方法中返回，返回值为void
       4: return

  public static void main(java.lang.String[]);
    Code:
       // 将int类型常量1压入栈
       0: iconst_1
       // 将int类型值存入局部变量1
       1: istore_1 
       // ldc2_w 把常量池中long类型或者double类型的项压入栈，位置2
       2: ldc2_w        #2                  // double 2.0d
       // 将double类型值存入局部变量2
       5: dstore_2
       // ldc2_w 把常量池中long类型或者double类型的项压入栈，位置4
       6: ldc2_w        #4                  // long 3l
       // 将long类型值存入局部变量，位置4
       9: lstore        4
      // 将int类型常量4压入栈
      11: iconst_4
      // 将int类型值存入局部变量，位置6
      12: istore        6
      // ldc 把常量池中的项压入栈，位置6
      14: ldc           #6                  // String
      // invokevirtual 调度对象的实便方法，位置7
      16: invokevirtual #7                  // Method java/lang/String.length:()I
      // 把数值常量10压栈
      19: bipush        10
      // if_icmpge 如果一个int类型值大于或者等于另外一个int类型值，则跳转，位置38
      21: if_icmpge     38
      // getstatic 从类中获取静态字段，位置8
      24: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
      // 从局部变量2中装载double类型值
      27: dload_2
      // 从局部变量中装载long类型值，位置4
      28: lload         4
      // invokedynamic 先在运行时动态解析出调用点限定符所引用的方法,然后再执行该方法，位置9压栈，自增0
      30: invokedynamic #9,  0              // InvokeDynamic #0:makeConcatWithConstants:(DJ)Ljava/lang/String;
      // invokevirtual 调度对象的实便方法，位置10
      35: invokevirtual #10                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      // 将int类型常量0压入栈
      38: iconst_0
      // 写入一个int类型到本地变量表里，位置7
      39: istore        7
      // 从局部变量中装载int类型值，位置7
      41: iload         7
      // 从局部变量1中装载int类型值
      43: iload_1
      // if_icmpge 如果一个int类型值大于或者等于另外一个int类型值，则跳转，位置71
      44: if_icmpge     71
      // getstatic 从类中获取静态字段，位置8
      47: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
      // ldc 把常量池中的项压入栈，位置11
      50: ldc           #11                 // String 四则运算: num1 * num4 =
      // invokevirtual 调度对象的实便方法，位置12
      52: invokevirtual #12                 // Method java/io/PrintStream.print:(Ljava/lang/String;)V
      // getstatic 从类中获取静态字段，位置8
      55: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
      // 从局部变量1中装载int类型值
      58: iload_1
      // 从局部变量中装载int类型值，位置6
      59: iload         6
      // 执行int类型的乘法
      61: imul
      // invokevirtual 调度对象的实便方法，位置13
      62: invokevirtual #13                 // Method java/io/PrintStream.println:(I)V
      // iinc 把一个常量值加到一个int类型的局部变量上，位置7，自增1
      65: iinc          7, 1
      // goto 无条件跳转41位置，继续执行循环
      68: goto          41
      // return 从方法中返回，返回值为void
      71: return
}

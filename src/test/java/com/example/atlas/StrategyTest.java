package com.example.atlas;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrategyTest {

    @Test
    public void TestOne() {
        Context c = new Context();
        Strategy s = new ConcreteStrategyA();
        c.setStrategy(s);
        c.strategyMethod();
        System.out.println("-----------------");
        s = new ConcreteStrategyB();
        c.setStrategy(s);
        c.strategyMethod();
    }

    //抽象策略类
    interface Strategy {
        public void strategyMethod();    //策略方法
    }

    //具体策略类A
    class ConcreteStrategyA implements Strategy {
        public void strategyMethod() {
            System.out.println("具体策略A的策略方法被访问！");
        }
    }

    //具体策略类B
    class ConcreteStrategyB implements Strategy {
        public void strategyMethod() {
            System.out.println("具体策略B的策略方法被访问！");
        }
    }

    //环境类
    class Context {
        private Strategy strategy;

        public Strategy getStrategy() {
            return strategy;
        }

        public void setStrategy(Strategy strategy) {
            this.strategy = strategy;
        }

        public void strategyMethod() {
            strategy.strategyMethod();
        }
    }

    @Test
    public void TestTwo() {
        int a = 4;
        int num = 0;
        int index = -1;
        //1111000
        System.out.println(Integer.toBinaryString(a));
        if (a >= 5) {
            String s = Integer.toBinaryString(a);
            for (int i = 0; i < s.length() - 2; i++) {
                System.out.println(s.substring(s.length() - i - 3, s.length() - i));
                if (StringUtils.equals(s.substring(s.length() - i - 3, s.length() - i), "101")) {
                    num += 1;
                    if (index == -1) {
                        index = i;
                    }
                }
            }
        }
        System.out.println("" + num + " " + index);
    }
//    /a///b//c/..////././
//    /a/b/c/../././

    //    /a/b
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            String next = in.next();
            char[] chars = next.toCharArray();
            List<String> strings = new ArrayList<>();
            char A = ' ';
            boolean flag = false;
            for (char B : chars) {
                if (flag && !Character.toString(A).equals(".")) {
                    strings = strings.subList(0, strings.size() - 1);
                    A = strings.get(strings.size() - 1).toCharArray()[0];
                    flag = false;
                } else if (A == ' ') {
                    A = B;
                    strings.add(Character.toString(A));
                } else {
                    if (A == B) {
                        if (Character.toString(A).equals("/")) {

                        } else if (Character.toString(A).equals(".")) {
                            if (strings.size() > 3) {
                                strings = strings.subList(0, strings.size() - 3);
                                A = strings.get(strings.size() - 1).toCharArray()[0];
                                flag = false;
                            } else {
                                strings.clear();
                            }
                        }
                    } else {
                        if (Character.toString(B).equals(".") && Character.toString(A).equals("/")) {
                            A = B;
                            flag = true;
                        } else {
                            A = B;
                            strings.add(Character.toString(B));
                        }
                    }
                }
            }
            if (strings.size() > 1) {
                while (strings.get(strings.size() - 1).equals("/")) {
                    strings = strings.subList(0, strings.size() - 1);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            strings.forEach(stringBuilder::append);
            System.out.println(stringBuilder.toString());
        }
    }

    @org.junit.Test
    public void JTest() {
        String str = "祝愿A1B2C3伟大祖国AB1繁荣昌1AB盛";
        String s = "\\w+";
        Pattern pattern = Pattern.compile(s);
        Matcher ma = pattern.matcher(str);
        while (ma.find()) {
            System.out.println(ma.group());
        }
    }
}

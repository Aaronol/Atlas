package com.example.atlas;

import org.junit.jupiter.api.Test;

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
}

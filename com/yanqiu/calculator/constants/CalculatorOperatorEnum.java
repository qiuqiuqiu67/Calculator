package com.yanqiu.calculator.constants;

/**
 * @program: com.yanqiu.Calculator
 * @description: 操作符枚举值
 * @author: huyq
 * @create: 2023-02-27 21:55
 **/


public enum CalculatorOperatorEnum {
    ADD("add"),
    SUB("sub"),
    MULT("mult"),
    DIV("div");
    private String operator;

    private CalculatorOperatorEnum(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return this.operator;
    }

    public static CalculatorOperatorEnum find(String operator) {
        for (CalculatorOperatorEnum calculatorOperator : CalculatorOperatorEnum.values()) {
            if (calculatorOperator.operator.equals(operator)) {
                return calculatorOperator;
            }
        }
        return null;
    }

}
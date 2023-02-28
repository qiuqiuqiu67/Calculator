package com.yanqiu.operator;

/**
 * @program: Calculator
 * @description:
 * @author: huyq
 * @create: 2023-02-28 21:09
 **/
public class SubOperator extends CommonOperator implements OperatorInterface {
    @Override
    public void subCheck(double operand, double currentVal, double maxLimit, double minLimit) {
        if (operand < 0 && currentVal > 0) {
            if (currentVal > maxLimit + operand) {
                throw new RuntimeException("The SubSum is overlimited");
            }
        }
        if (operand > 0 && currentVal < 0) {
            if (currentVal < minLimit + operand) {
                throw new RuntimeException("The SubSum is overlimited");
            }
        }
    }

    @Override
    public double doCompute(double operand, double currentVal) {
        return currentVal - operand;
    }
}

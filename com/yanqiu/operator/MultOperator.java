package com.yanqiu.operator;

/**
 * @program: Calculator
 * @description:
 * @author: huyq
 * @create: 2023-02-28 21:16
 **/
public class MultOperator extends CommonOperator implements OperatorInterface {

    @Override
    public void subCheck(double operand, double currentVal, double maxLimit, double minLimit) {
        if (operand > 1 && currentVal > 1 || operand < -1 && currentVal < -1) {
            if (currentVal > maxLimit / operand) {
                throw new RuntimeException("The MultSum is overlimited");
            }
        }

        if (operand > 1 && currentVal < -1 || operand < -1 && currentVal > 1) {
            if (currentVal < minLimit / operand) {
                throw new RuntimeException("The MultSum is overlimited");
            }
        }
    }

    @Override
    public double doCompute(double operand, double currentVal) {
        return currentVal * operand;
    }
}

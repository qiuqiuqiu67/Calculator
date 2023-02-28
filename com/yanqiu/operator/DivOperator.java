package com.yanqiu.operator;

/**
 * @program: Calculator
 * @description:
 * @author: huyq
 * @create: 2023-02-28 21:20
 **/
public class DivOperator extends CommonOperator implements OperatorInterface {
    @Override
    public void subCheck(double operand, double currentVal, double maxLimit, double minLimit) {
        if (operand == 0) {
            throw new RuntimeException("Cannot divide by zero.");
        }

        if (operand > 0 && operand < 1 && currentVal > 1 || operand > -1 && operand < 0 && currentVal < -1) {
            if (currentVal > maxLimit * operand) {
                throw new RuntimeException("The DivSum is overlimited");
            }
        }

        if (operand > 0 && operand < 1 && currentVal < -1 || operand > -1 && operand < 0 && currentVal > 1) {
            if (currentVal < minLimit * operand) {
                throw new RuntimeException("The DivSum is overlimited");
            }
        }
    }

    @Override
    public double doCompute(double operand, double currentVal) {
        return currentVal / operand;
    }
}

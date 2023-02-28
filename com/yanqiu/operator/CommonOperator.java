package com.yanqiu.operator;

/**
 * @program: CommonOperator
 * @description:公共操作
 * @author: huyq
 * @create: 2023-02-28 21:24
 **/
public abstract class CommonOperator implements OperatorInterface{
    private void checkOperand(double operand, double maxLimit, double minLimit) {
        if (operand > maxLimit && operand < minLimit) {
            throw new RuntimeException("The operand is overLimted");
        }
    }
    @Override
    public void doCheck(double operand, double currentVal, double maxLimit, double minLimit) {
        checkOperand(operand, maxLimit, minLimit);
        subCheck(operand, currentVal, maxLimit, minLimit);
    }

    abstract void subCheck(double operand, double currentVal, double maxLimit, double minLimit);
}

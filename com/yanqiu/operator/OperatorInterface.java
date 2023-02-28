package com.yanqiu.operator;

/**
 * @program: Calculator
 * @description:
 * @author: huyq
 * @create: 2023-02-28 20:34
 **/
public interface OperatorInterface {

    void doCheck(double operand,double currentVal,double maxLimit,double minLimit);

    double doCompute(double operand,double currentVal);
}

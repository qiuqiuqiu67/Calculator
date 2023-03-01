package com.yanqiu; /**
 * @program: com.yanqiu.Calculator
 * @description: 简易计算器
 * @author: huyq
 * @create: 2023-02-27 21:55
 **/

import com.yanqiu.calculator.constants.CalculatorOperatorEnum;
import com.yanqiu.operator.OperatorInterface;

import java.util.Stack;


public class Calculator {
    private double currentVal;
    private Stack<Double> undoStack;
    private Stack<Double> redoStack;

    private double MAXNUMBER = 1000000000000.0;
    private double MINNUMBER = -1000000000000.0;

    public Calculator() {
        this.currentVal = 0;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }





    /**
     * 简易计算
     *
     * @param operator//运算符
     * @param operand//运算数
     * @return 计算后的结果
     */
    public double compute(String operator, double operand) throws Exception {
        try {
            OperatorInterface targetOperator = OperatorFactory.getOperation(operator);
            if(targetOperator==null){
                throw new RuntimeException("The operator Factory is not defined");
            }
            targetOperator.doCheck(operand,currentVal,MAXNUMBER,MINNUMBER);

            this.undoStack.push(this.currentVal);
            this.redoStack.clear();

            currentVal = targetOperator.doCompute(operand, currentVal);
            return currentVal;
        } catch (Exception e) {
            //todo log someting exection
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 获得当前结果
     *
     * @return
     */
    public double getCurrentVal() {
        return this.currentVal;
    }


    /**
     * 撤销上一次的计算
     */
    public void undo() {
        if (this.undoStack.isEmpty()) {
            throw new RuntimeException(" Cannot undo.");
        }
        double previousVal = this.undoStack.pop();
        this.redoStack.push(this.currentVal);
        this.currentVal = previousVal;
    }

    /**
     * 重复上一次被撤销的计算
     */
    public void redo() {
        if (this.redoStack.isEmpty()) {
            throw new RuntimeException(" Cannot redo.");
        }
        double nextVal = this.redoStack.pop();
        this.undoStack.push(this.currentVal);
        this.currentVal = nextVal;
    }

    //清理并初始化
    public void clear() {
        currentVal = 0.0;
        this.undoStack.clear();
        this.redoStack.clear();
    }


    public static void main(String[] args) {
        Calculator calc = new Calculator();
        try {
            calc.compute(CalculatorOperatorEnum.ADD.getOperator(), 5);
            //测试正常用例
            System.out.println(calc.getCurrentVal());// 输出5.0

            // 测试基本操作和undo/redo

            calc.compute(CalculatorOperatorEnum.ADD.getOperator(), 2);
            System.out.println(calc.getCurrentVal()); // 输出7.0

            calc.compute(CalculatorOperatorEnum.SUB.getOperator(), 3);
            System.out.println(calc.getCurrentVal()); // 输出4.0

            calc.compute(CalculatorOperatorEnum.MULT.getOperator(), 4);
            System.out.println(calc.getCurrentVal()); // 输出16.0

            calc.compute(CalculatorOperatorEnum.DIV.getOperator(), 5);
            System.out.println(calc.getCurrentVal()); // 输出3.2

            calc.undo();
            System.out.println(calc.getCurrentVal()); // 输出16.0
            calc.undo();
            System.out.println(calc.getCurrentVal()); // 输出4.0
            calc.undo();
            System.out.println(calc.getCurrentVal()); // 输出7.0
            calc.undo();
            System.out.println(calc.getCurrentVal()); // 输出5.0

            calc.redo();
            System.out.println(calc.getCurrentVal()); // 输出7.0

            calc.redo();
            System.out.println(calc.getCurrentVal()); // 输出4.0

            calc.redo();
            System.out.println(calc.getCurrentVal()); // 输出16.0

            calc.redo();
            System.out.println(calc.getCurrentVal()); // 输出3.2

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        // 测试异常用例
        //除0
        try {
            calc.clear();
            calc.compute(CalculatorOperatorEnum.DIV.getOperator(), 0);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 输出 "Cannot divide by zero."
        }
        //加法溢出
        try {
            calc.clear();
            calc.compute(CalculatorOperatorEnum.ADD.getOperator(), 1.0);
            calc.compute(CalculatorOperatorEnum.ADD.getOperator(), 1000000000000.0);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 输出 "The AddSum is overlimited."
        }

        try {
            calc.clear();
            calc.compute(CalculatorOperatorEnum.ADD.getOperator(), -1.0);
            calc.compute(CalculatorOperatorEnum.ADD.getOperator(), -1000000000000.0);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 输出 "The AddSum is overlimited."
        }

        //减法溢出
        try {
            calc.clear();
            calc.compute(CalculatorOperatorEnum.ADD.getOperator(), -1000000000000.0);
            calc.compute(CalculatorOperatorEnum.SUB.getOperator(), 1.0);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 输出 "The SubSum is overlimited"
        }

        try {
            calc.clear();
            calc.compute(CalculatorOperatorEnum.ADD.getOperator(), 1000000000000.0);
            calc.compute(CalculatorOperatorEnum.SUB.getOperator(), -1.0);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 输出 "The SubSum is overlimited"
        }

        //乘法溢出
        try {
            calc.clear();
            calc.compute(CalculatorOperatorEnum.ADD.getOperator(), 100);
            calc.compute(CalculatorOperatorEnum.MULT.getOperator(), 1000000000000.0);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 输出 "The MultSum is overlimited."
        }

        //除法溢出
        try {
            calc.clear();
            calc.compute(CalculatorOperatorEnum.ADD.getOperator(), 1000000000000.0);
            calc.compute(CalculatorOperatorEnum.DIV.getOperator(), 0.1);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 输出 "The DivSum is overlimited"
        }

    }
}


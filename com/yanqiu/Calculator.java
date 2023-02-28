package com.yanqiu; /**
 * @program: com.yanqiu.Calculator
 * @description: 简易计算器
 * @author: huyq
 * @create: 2023-02-27 21:55
 **/

import com.yanqiu.calculator.constants.CalculatorOperatorEnum;

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

    public Calculator(double original) {
        checkOperand(original);
        this.currentVal = original;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    private void checkOperand(double original) {
        if (original > MAXNUMBER && original < MINNUMBER) {
            throw new RuntimeException("The operand is overLimted");
        }
    }

    /**
     * 简易计算
     * @param operator//运算符
     * @param operand//运算数
     * @return 计算后的结果
     */
    public double compute(String operator, double operand) {
        checkCompute(operator, operand);
        return doCompute(operator, operand);
    }

    private double doCompute(String operator, Double operand) {
        CalculatorOperatorEnum calculatorOperator = CalculatorOperatorEnum.find(operator);
        double result = 0;
        switch (calculatorOperator) {
            case ADD:
                result = add(operand);
                break;
            case SUB:
                result = sub(operand);
                break;
            case MULT:
                result = mult(operand);
                break;
            case DIV:
                result = div(operand);
                break;
            default:
                break;
        }
        return result;
    }


    private void checkCompute(String operator, Double operand) {
        CalculatorOperatorEnum calculatorOperator = CalculatorOperatorEnum.find(operator);
        if (calculatorOperator == null) {
            throw new IllegalArgumentException("The operator is not defined");
        }
        checkOperand(operand);
        switch (calculatorOperator) {
            case ADD:
                checkAdd(operand);
                break;
            case SUB:
                checkSub(operand);
                break;
            case MULT:
                checkMult(operand);
                break;
            case DIV:
                checkDiv(operand);
                break;
            default:
                break;
        }
    }

    private void checkAdd(double operand) {
        if (operand > 0 && currentVal > 0) {
            if (currentVal > MAXNUMBER - operand) {
                throw new RuntimeException("The AddSum is overlimited");
            }
        }
        if (operand < 0 && currentVal < 0) {
            if (currentVal < MINNUMBER - operand) {
                throw new RuntimeException("The AddSum is overlimited");
            }
        }
    }

    private void checkSub(double operand) {
        if (operand < 0 && currentVal > 0) {
            if (currentVal > MAXNUMBER + operand) {
                throw new RuntimeException("The SubSum is overlimited");
            }
        }
        if (operand > 0 && currentVal < 0) {
            if (currentVal < MINNUMBER + operand) {
                throw new RuntimeException("The SubSum is overlimited");
            }
        }
    }

    private void checkMult(double operand) {
        if (operand > 1 && currentVal > 1 || operand < -1 && currentVal < -1) {
            if (currentVal > MAXNUMBER / operand) {
                throw new RuntimeException("The MultSum is overlimited");
            }
        }

        if (operand > 1 && currentVal < -1 || operand < -1 && currentVal > 1) {
            if (currentVal < MINNUMBER / operand) {
                throw new RuntimeException("The MultSum is overlimited");
            }
        }
    }

    private void checkDiv(double operand) {
        if (operand == 0) {
            throw new RuntimeException("Cannot divide by zero.");
        }

        if (operand > 0 && operand < 1 && currentVal > 1 || operand > -1 && operand < 0 && currentVal < -1) {
            if (currentVal > MAXNUMBER * operand) {
                throw new RuntimeException("The DivSum is overlimited");
            }
        }

        if (operand > 0 && operand < 1 && currentVal < -1 || operand > -1 && operand < 0 && currentVal > 1) {
            if (currentVal < MINNUMBER * operand) {
                throw new RuntimeException("The DivSum is overlimited");
            }
        }
    }


    /**
     * 获得当前结果
     * @return
     */
    public double getCurrentVal() {
        return this.currentVal;
    }

    //加法
    private double add(double num) {
        this.undoStack.push(this.currentVal);
        this.currentVal += num;
        this.redoStack.clear();
        return currentVal;

    }

    //减法
    private double sub(double num) {
        this.undoStack.push(this.currentVal);
        this.currentVal -= num;
        this.redoStack.clear();
        return currentVal;

    }

    //乘法
    private double mult(double num) {
        this.undoStack.push(this.currentVal);
        this.currentVal *= num;
        this.redoStack.clear();
        return currentVal;
    }

    //除法
    private double div(double num) {
        this.undoStack.push(this.currentVal);
        this.currentVal /= num;
        this.redoStack.clear();
        return currentVal;
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
        Calculator calc = new Calculator(5);
        //测试正常用例
        System.out.println(calc.getCurrentVal());// 输出7.0

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


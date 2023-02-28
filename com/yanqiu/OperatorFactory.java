package com.yanqiu;

import com.yanqiu.operator.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: Calculator
 * @description: 操作符工厂类
 * @author: huyq
 * @create: 2023-02-28 20:25
 **/
public class OperatorFactory {

    static Map<String, OperatorInterface> operatorMap = new HashMap<>();
    static{
        operatorMap.put("add",new AddOperator());
        operatorMap.put("sub",new SubOperator());
        operatorMap.put("mult",new MultOperator());
        operatorMap.put("div",new DivOperator());
    }

    public static OperatorInterface getOperation(String operation) {
        return operatorMap.get(operation);
    }

}
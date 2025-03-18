package com.ewa.test.springboot.operator;

import com.ewa.operator.ComponentFn;
import com.ewa.operator.core.ConditionOperator;
import com.ewa.test.springboot.context.OrderContext;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
//@Service
@ComponentFn
public class CheckTopNode extends ConditionOperator<OrderContext, Boolean> {

    @Override
    public Boolean doExecute(OrderContext ctx) {
        System.out.println(String.format("[%s]check_top_node execute", Thread.currentThread().getName()));
        return true;
    }
}

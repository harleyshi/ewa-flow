package com.ewa.test.springboot.operator.rec;

import com.ewa.operator.ComponentFn;
import com.ewa.operator.core.ConditionOperator;
import com.ewa.test.springboot.context.OrderContext;
import org.springframework.stereotype.Service;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
@ComponentFn
public class SwitchNode extends ConditionOperator<OrderContext, Integer> {

    @Override
    public Integer doExecute(OrderContext ctx) {
        System.out.println(String.format("[%s]switch_node execute", Thread.currentThread().getName()));
        return 1;
    }
}

package com.ewa.test.springboot.operator;

import com.ewa.operator.ComponentFn;
import com.ewa.operator.node.AbstractOperator;
import com.ewa.test.springboot.context.OrderContext;
import com.ewa.test.springboot.operator.params.OrderParams;
import org.springframework.stereotype.Service;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
@Service
@ComponentFn(paramType = OrderParams.class)
public class MergeRecallNode extends AbstractOperator<OrderContext> {

    @Override
    public void doExecute(OrderContext ctx) {
        System.out.println(String.format("[%s]merge_recall execute", Thread.currentThread().getName()));

    }
}

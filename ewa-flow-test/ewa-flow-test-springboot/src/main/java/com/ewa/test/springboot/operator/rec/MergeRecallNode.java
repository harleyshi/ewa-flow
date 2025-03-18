package com.ewa.test.springboot.operator.rec;

import com.alibaba.fastjson2.JSON;
import com.ewa.operator.ComponentFn;
import com.ewa.operator.core.AbstractOperator;
import com.ewa.test.springboot.context.OrderContext;
import com.ewa.test.springboot.operator.rec.params.OrderParams;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
@ComponentFn(nodeParamsType = OrderParams.class)
public class MergeRecallNode extends AbstractOperator<OrderContext> {

    private OrderParams orderParams;

    @Override
    public void doExecute(OrderContext ctx) {
        System.out.println(String.format("[%s]merge_recall execute, orderParams: %s",
                Thread.currentThread().getName(), JSON.toJSONString(orderParams)));

    }
}

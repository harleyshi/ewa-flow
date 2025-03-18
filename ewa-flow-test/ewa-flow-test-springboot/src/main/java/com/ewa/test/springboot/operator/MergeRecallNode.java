package com.ewa.test.springboot.operator;

import com.alibaba.fastjson2.JSON;
import com.ewa.operator.ComponentFn;
import com.ewa.operator.core.AbstractOperator;
import com.ewa.test.springboot.context.OrderContext;
import com.ewa.test.springboot.operator.params.OrderParams;
import org.springframework.stereotype.Service;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
@Service
@ComponentFn(paramType = OrderParams.class)
public class MergeRecallNode extends AbstractOperator<OrderContext, OrderParams> {


    @Override
    public void doExecute(OrderContext ctx) {
        OrderParams orderParams = getNodeParams();

        System.out.println(String.format("[%s]merge_recall execute, orderParams: %s", Thread.currentThread().getName(), JSON.toJSONString(orderParams)));

    }
}

package com.ewa.test.springboot.operator.order;

import com.ewa.operator.ComponentFn;
import com.ewa.operator.core.AbstractOperator;
import com.ewa.test.springboot.context.OrderContext;
import com.ewa.test.springboot.operator.rec.params.OrderParams;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
@ComponentFn(nodeParamsType = OrderParams.class)
public class LockCouponNode extends AbstractOperator<OrderContext> {

    @Override
    public void doExecute(OrderContext ctx) {
        System.out.println(String.format("[%s]lock coupon execute", Thread.currentThread().getName()));
    }
}

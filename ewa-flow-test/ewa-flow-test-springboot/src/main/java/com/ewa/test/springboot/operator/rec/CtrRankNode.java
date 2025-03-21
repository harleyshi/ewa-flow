package com.ewa.test.springboot.operator.rec;

import com.ewa.operator.ComponentFn;
import com.ewa.operator.core.FallbackOperator;
import com.ewa.test.springboot.context.OrderContext;
import org.springframework.stereotype.Service;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
@ComponentFn
public class CtrRankNode extends FallbackOperator<OrderContext> {

    @Override
    public void doExecute(OrderContext ctx) {
        System.out.println(String.format("[%s]ctr_rank execute", Thread.currentThread().getName()));

    }

    @Override
    public void doFallback(OrderContext ctx) {

    }
}

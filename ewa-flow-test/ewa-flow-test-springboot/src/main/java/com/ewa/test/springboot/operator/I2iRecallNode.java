package com.ewa.test.springboot.operator;

import com.ewa.operator.ComponentFn;
import com.ewa.operator.core.FallbackOperator;
import com.ewa.test.springboot.context.OrderContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
@ComponentFn
public class I2iRecallNode extends FallbackOperator<OrderContext> {
    @Override
    public void doExecute(OrderContext ctx) {
        System.out.println(String.format("[%s]i2i_recall execute", Thread.currentThread().getName()));
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add("i2i_recall_item_" + i);
        }
        ctx.addItems(result);


    }

    @Override
    public void doFallback(OrderContext ctx) {

    }
}

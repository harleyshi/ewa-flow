package com.ewa.test.springboot.operator;

import com.ewa.operator.ComponentFn;
import com.ewa.operator.core.AbstractOperator;
import com.ewa.test.springboot.context.OrderContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
@Service
@ComponentFn
public class UserCFRecallNode extends AbstractOperator<OrderContext, Void> {
    @Override
    public void doExecute(OrderContext ctx) {
        System.out.println(String.format("[%s]user_cf_recall execute", Thread.currentThread().getName()));
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add("user_cf_recall_item_" + i);
        }
        ctx.addItems(result);
    }
}

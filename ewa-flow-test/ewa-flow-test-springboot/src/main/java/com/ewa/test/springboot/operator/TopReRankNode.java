package com.ewa.test.springboot.operator;

import com.ewa.operator.ComponentFn;
import com.ewa.operator.node.AbstractOperator;
import com.ewa.test.springboot.context.OrderContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
@Service
@ComponentFn
public class TopReRankNode extends AbstractOperator<OrderContext> {

    @Override
    public void doExecute(OrderContext ctx) {
        System.out.println(String.format("[%s]top_rerank execute", Thread.currentThread().getName()));
        List<String> items = List.of("top-item-1");
        ctx.addItems(items);
//        throw   new RuntimeException("top_rerank");
    }
}

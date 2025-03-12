package com.ewa.test.springboot.context;

import com.ewa.operator.ctx.AbstractFlowCtx;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
public class OrderContext extends AbstractFlowCtx {

    private List<String> items = new CopyOnWriteArrayList<>();

    public void addItems(List<String> items) {
        this.items.addAll(items);
    }

    public List<String> getItems() {
        return items;
    }
}

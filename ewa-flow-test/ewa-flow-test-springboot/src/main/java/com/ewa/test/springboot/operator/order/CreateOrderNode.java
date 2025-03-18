package com.ewa.test.springboot.operator.order;

import com.ewa.operator.ComponentFn;
import com.ewa.operator.core.AbstractOperator;
import com.ewa.test.springboot.context.OrderContext;
import com.ewa.test.springboot.domain.TestADO;
import com.ewa.test.springboot.domain.TestBDO;
import com.ewa.test.springboot.domain.TestCDO;
import com.ewa.test.springboot.mapper.TestAMapper;
import com.ewa.test.springboot.mapper.TestBMapper;
import com.ewa.test.springboot.mapper.TestCMapper;
import com.ewa.test.springboot.operator.rec.params.OrderParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author harley.shi
 * @date 2025/1/20
 */
@ComponentFn(nodeParamsType = OrderParams.class)
public class CreateOrderNode extends AbstractOperator<OrderContext> {

    @Autowired
    private TestAMapper testAMapper;

    @Autowired
    private TestBMapper testBMapper;

    @Autowired
    private TestCMapper testCMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private OrderService orderService;

    @Override
    public void doExecute(OrderContext ctx) {
        System.out.println(String.format("[%s]create order execute", Thread.currentThread().getName()));

        // 如果用声明式事务，需要单独定义个服务类，然后注入到这里，然后调用服务类的方法
        orderService.save();

        // 编程式事务测试
        transactionTemplate.execute(status -> {
            testAMapper.insert(new TestADO("testA"));
            testBMapper.insert(new TestBDO("testB"));
            int i = 1 / 0;
            testCMapper.insert(new TestCDO("testC"));
            return true;
        });
    }
}

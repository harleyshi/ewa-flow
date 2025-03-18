package com.ewa.test.springboot.operator.order;

import com.ewa.test.springboot.domain.TestADO;
import com.ewa.test.springboot.domain.TestBDO;
import com.ewa.test.springboot.domain.TestCDO;
import com.ewa.test.springboot.mapper.TestAMapper;
import com.ewa.test.springboot.mapper.TestBMapper;
import com.ewa.test.springboot.mapper.TestCMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author harley.shi
 * @date 2025/3/18
 */
@Component
public class OrderService {
    @Autowired
    private TestAMapper testAMapper;

    @Autowired
    private TestBMapper testBMapper;

    @Autowired
    private TestCMapper testCMapper;

    @Transactional
    public void save(){
        System.out.println(String.format("[%s]create order execute", Thread.currentThread().getName()));
        testAMapper.insert(new TestADO("testA"));
        testBMapper.insert(new TestBDO("testB"));
        int i = 1 / 0;
        testCMapper.insert(new TestCDO("testC"));
    }
}

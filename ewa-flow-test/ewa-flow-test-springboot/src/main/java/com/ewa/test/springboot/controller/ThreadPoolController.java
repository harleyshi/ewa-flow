package com.ewa.test.springboot.controller;

import com.alibaba.fastjson2.JSON;
import com.ewa.engine.threadpool.Parallel;
import com.ewa.engine.threadpool.ThreadPoolStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author harley.shi
 * @date 2024/11/5
 */
@Slf4j
@RestController
@RequestMapping("/threadPool")
public class ThreadPoolController {

    @GetMapping("/stats")
    public String stats() {
        ThreadPoolStats stats = Parallel.stats();
        return JSON.toJSONString(stats.stats());
    }
}

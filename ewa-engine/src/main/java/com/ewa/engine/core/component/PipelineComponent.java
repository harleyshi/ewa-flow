package com.ewa.engine.core.component;

import com.ewa.engine.threadpool.Parallel;
import com.ewa.engine.threadpool.ThreadPool;
import com.ewa.operator.core.context.FlowCtx;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * pipeline component
 * @author harley.shi
 * @date 2024/7/2
 */
@Slf4j
@Getter
@Setter
public class PipelineComponent<C extends FlowCtx> extends Component<C> {

    /**
     * component description
     */
    private String desc;

    /**
     * is async
     */
    private boolean async;

    /**
     * child components of pipeline
     */
    private List<Component<C>> pipeline;

    public PipelineComponent(String name) {
        super(name);
    }

    @Override
    public void doExecute(C context) {
        // 异步执行
        if(isAsync()){
            Executor executor = Parallel.getExecutor(ThreadPool.Names.KERNEL);
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (Component<C> component : pipeline) {
                futures.add(CompletableFuture.runAsync(()-> component.execute(context), executor));
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }else{
            // 同步执行
            Invoker.invoke(context, pipeline);
        }
    }
}

package com.im.sky.tomcat.diy;

import com.im.sky.tomcat.diy.catalina.LifecycleException;
import com.im.sky.tomcat.diy.catalina.LifecycleState;
import com.im.sky.tomcat.diy.catalina.Pipeline;
import com.im.sky.tomcat.diy.catalina.core.StandardPipeline;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author jiangchangwei
 * @date 2020-9-21 下午 8:42
 **/
@Slf4j
public class ContainerBase extends LifecycleBase implements Container {

    /**
     * 一个容器对象的子容器对象
     */
    protected final Map<String, Container> children = new HashMap<>();

    protected Container parent = null;

    protected ExecutorService startStopExecutor = Executors.newCachedThreadPool();

    protected final Pipeline pipeline = new StandardPipeline(this);

    protected String name = null;

    @Override
    protected void startInternal() throws LifecycleException {
        Container[] children = findChildren();
        List<Future<Void>> results = new ArrayList<>();
        for (int i = 0; i < children.length; i++) {
            results.add(startStopExecutor.submit(new StartChild(children[i])));
        }
        for (Future<Void> result : results) {
            try {
                result.get();
            } catch (Throwable e) {
               throw new LifecycleException("containerBase.threadedStartFailed");
            }

        }
        if (pipeline instanceof Lifecycle) {
            ((Lifecycle) pipeline).start();
        }
        setState(LifecycleState.START);
    }

    @Override
    protected void stopInternal() throws LifecycleException {
        setState(LifecycleState.STOP);
        // Stop the Valves in our pipeline (including the basic), if any
        if (pipeline instanceof Lifecycle &&
                ((Lifecycle) pipeline).getState().isAvailable()) {
            ((Lifecycle) pipeline).stop();
        }

        // Stop our child containers, if any
        Container children[] = findChildren();
        List<Future<Void>> results = new ArrayList<>();
        for (int i = 0; i < children.length; i++) {
            results.add(startStopExecutor.submit(new StopChild(children[i])));
        }
        boolean fail = false;
        for (Future<Void> result : results) {
            try {
                result.get();
            } catch (Exception e) {
                log.error("containerBase.threadedStopFailed", e);
                fail = true;
            }
        }
        if(fail) {
            throw new LifecycleException("containerBase.threadedStopFailed");
        }
    }

    @Override
    protected void destroyInternal() throws LifecycleException {
        // Stop the Valves in our pipeline (including the basic), if any
        if (pipeline instanceof Lifecycle) {
            ((Lifecycle) pipeline).destroy();
        }

        // Remove children now this container is being destroyed
        for (Container child : findChildren()) {
            removeChild(child);
        }

        // Required if the child is destroyed directly.
        if (parent != null) {
            parent.removeChild(this);
        }

        // If init fails, this may be null
        if (startStopExecutor != null) {
            startStopExecutor.shutdownNow();
        }

    }

    @Override
    public Container[] findChildren() {
        synchronized (children) {
            Container[] results = new Container[children.size()];
            return children.values().toArray(results);
        }
    }

    @Override
    public void removeChild(Container child) {
        if (child == null) {
            return;
        }

        try {
            if (child.getState().isAvailable()) {
                child.stop();
            }
        } catch (LifecycleException e) {
            log.error("containerBase.child.stop", e);
        }

        synchronized(children) {
            if (children.get(child.getName()) == null)
                return;
            children.remove(child.getName());
        }
    }

    @Override
    public void addChild(Container child) {
        synchronized (children) {
            child.setParent(this);
            children.put(child.getName(), child);
        }
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public void setParent(Container container) {
        this.parent = container;
    }

    @Override
    public String getName() {
        return name;
    }

    private static class StartChild implements Callable<Void> {

        private Container child;

        public StartChild(Container child) {
            this.child = child;
        }

        @Override
        public Void call() throws LifecycleException {
            child.start();
            return null;
        }
    }

    private static class StopChild implements Callable<Void> {

        private Container child;

        public StopChild(Container child) {
            this.child = child;
        }

        @Override
        public Void call() throws LifecycleException {
            if (child.getState().isAvailable()) {
                child.stop();
            }
            return null;
        }
    }
}

package com.im.sky.tomcat.diy.catalina.core;

import com.im.sky.tomcat.diy.Container;
import com.im.sky.tomcat.diy.Lifecycle;
import com.im.sky.tomcat.diy.LifecycleBase;
import com.im.sky.tomcat.diy.catalina.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author jiangchangwei
 * @date 2020-9-22 下午 8:47
 **/
@Slf4j
public class StandardPipeline extends LifecycleBase implements Pipeline {

    public StandardPipeline() {
        this(null);
    }

    public StandardPipeline(Container container) {
        super();
        setContainer(container);
    }

    protected Valve basic = null;

    protected Container container = null;

    protected  Valve first = null;


    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void setBasic(Valve valve) {
        this.basic = valve;
    }

    @Override
    public void addValve(Valve valve) {
        Valve oldBasic = basic;
        if(oldBasic == valve) {
            return;
        }
        if(valve == null) {
            return;
        }
        if(valve instanceof Contained) {
            ((Contained)valve).setContainer(this.container);
        }
        if(getState().isAvailable() && valve instanceof Lifecycle) {
            try {
                ((Lifecycle) valve).start();
            } catch (LifecycleException e) {
                log.error("standardPipeline.basic.start", e);
            }
        }
        // Update the pipeline
        Valve current = first;
        while (current != null) {
            if (current.getNext() == oldBasic) {
                current.setNext(valve);
                break;
            }
            current = current.getNext();
        }

        this.basic = valve;
    }

    @Override
    public Valve[] getValves() {
        List<Valve> valveList = new ArrayList<>();
        Valve current = first;
        if (current == null) {
            current = basic;
        }
        while (current != null) {
            valveList.add(current);
            current = current.getNext();
        }

        return valveList.toArray(new Valve[0]);
    }

    @Override
    public void removeValve(Valve valve) {
        Valve current;
        if(first == valve) {
            first = first.getNext();
            current = null;
        } else {
            current = first;
        }
        while (current != null) {
            if (current.getNext() == valve) {
                current.setNext(valve.getNext());
                break;
            }
            current = current.getNext();
        }

        if (first == basic) first = null;

        if (valve instanceof Contained)
            ((Contained) valve).setContainer(null);

        if (valve instanceof Lifecycle) {
            // Stop this valve if necessary
            if (getState().isAvailable()) {
                try {
                    ((Lifecycle) valve).stop();
                } catch (LifecycleException e) {
                    log.error("standardPipeline.valve.stop", e);
                }
            }
            try {
                ((Lifecycle) valve).destroy();
            } catch (LifecycleException e) {
                log.error("standardPipeline.valve.destroy", e);
            }
        }
    }

    @Override
    public Valve getFirst() {
        if(first != null) {
            return first;
        }
        return basic;
    }

    @Override
    public boolean isAsyncSupported() {
        Valve valve = (first != null) ? first:basic;
        boolean supported = true;
        while(supported && valve != null) {
            supported = valve.isAsyncSupported();
            valve = valve.getNext();
        }
        return supported;
    }

    @Override
    public void findNonAsyncValves(Set<String> result) {
        Valve valve = (first != null) ? first :basic;
        while (valve != null) {
            if(!valve.isAsyncSupported()) {
                result.add(valve.getClass().getName());
            }
            valve = valve.getNext();
        }
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    protected void startInternal() throws LifecycleException {
        Valve current = first;
        if (current == null) {
            current = basic;
        }
        while (current != null) {
            if (current instanceof Lifecycle)
                ((Lifecycle) current).start();
            current = current.getNext();
        }

        setState(LifecycleState.START);
    }

    @Override
    protected void stopInternal() throws LifecycleException {
        setState(LifecycleState.STOP);

        // Stop the Valves in our pipeline (including the basic), if any
        Valve current = first;
        if (current == null) {
            current = basic;
        }
        while (current != null) {
            if (current instanceof Lifecycle)
                ((Lifecycle) current).stop();
            current = current.getNext();
        }
    }

    @Override
    protected void destroyInternal() throws LifecycleException {
        Valve[] valves = getValves();
        for (Valve valve : valves) {
            removeValve(valve);
        }
    }
}

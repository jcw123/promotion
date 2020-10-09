package com.im.sky.javacore.classloader.connectionloader;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author jiangchangwei
 * @date 2020-9-21 下午 4:16
 **/
public abstract class ConnectionLoader {

    private final ConcurrentMap<String, Connection> loadedConnections = new ConcurrentHashMap<>();

    private ConnectionLoader parent;

    private ConcurrentMap<String, Object> parallelLockMap = new ConcurrentHashMap<>();

    public Connection loadConnection(String name) {
        synchronized (getConnectionLoadingLock(name)) {
            Connection connection = findLoadedConnection(name);
            if (connection == null) {
                try {
                    if (parent != null) {
                        connection = parent.loadConnection(name);
                    }
                }catch (ConnectionNotFoundException ignored) {

                }
            }
            if (connection == null) {
                connection = findConnection(name);
                if(connection != null) {
                    loadedConnections.put(name, connection);
                }
            }
            return connection;
        }
    }

    protected abstract Connection findConnection(String name);

    public ConnectionLoader getParent() {
        return parent;
    }

    public void setParent(ConnectionLoader parent) {
        this.parent = parent;
    }

    private Connection findLoadedConnection(String name) {
        return loadedConnections.get(name);
    }

    private Object getConnectionLoadingLock(String name) {
        Object o = new Object();
        Object old = parallelLockMap.putIfAbsent(name, o);
        return old == null ? o : old;
    }
}

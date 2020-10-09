package com.im.sky.mysql.mvcc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangchangwei
 * @date 2020-9-16 下午 8:19
 **/
public class Table {

    private Map<Long, Row> map = new ConcurrentHashMap<>();

    private Map<Long, Object> locks = new ConcurrentHashMap<>();

    private Set<Long> ids = new HashSet<>();

    public boolean insert(Row row) {
        long trx_id = TransactionIdGenerator.generate();
        ids.add(trx_id);
        Long id = row.getId();
        Row current_row = map.get(id);
        if(current_row != null && !current_row.isDelete()) {
            throw new RuntimeException("duplicate primary key");
        }
        row.setTrx_id(trx_id);
        row.setHistory(current_row);
        locks.putIfAbsent(id, new Object());
        map.put(id, row);
        ids.remove(trx_id);
        return true;
    }

    public boolean update(Long id, String name) throws CloneNotSupportedException {
        Object o = locks.get(id);
        if(o == null) {
            return false;
        }
        synchronized (o) {
            if(!map.containsKey(id)) {
                return false;
            }
            long trx_id = TransactionIdGenerator.generate();
            ids.add(trx_id);
            Row oldRow = map.get(id);
            Row newRow = oldRow.clone();
            newRow.setName(name);
            newRow.setTrx_id(trx_id);
            newRow.setHistory(oldRow);
            map.put(id, newRow);
            ids.remove(trx_id);
            return true;
        }
    }

    public boolean delete(Long id) throws CloneNotSupportedException {
        Object o = locks.get(id);
        if(o == null) {
            return true;
        }
        synchronized (o) {
            if(!map.containsKey(id)) {
                return true;
            }
            long trx_id = TransactionIdGenerator.generate();
            ids.add(trx_id);
            Row oldRow = map.get(id);
            Row newRow = oldRow.clone();
            newRow.setDelete(true);
            newRow.setTrx_id(trx_id);
            newRow.setHistory(oldRow);
            ids.remove(trx_id);
            return true;
        }
    }

    public List<Row> select(List<Long> keys) throws CloneNotSupportedException {
        Set<Long> tmpIds = new HashSet<>(ids);
        Long current_trx_id = TransactionIdGenerator.generate();
        tmpIds.add(current_trx_id);
        long maxId = Long.MIN_VALUE;
        long minId = Long.MAX_VALUE;
        for(Long id : tmpIds) {
            maxId = Math.max(maxId, id);
            minId = Math.min(minId, id);
        }
        List<Row> result = new ArrayList<>();
        Row row;
        long trx_id;
        for(Long id : keys) {
            row = map.get(id);
            while(row != null) {
                trx_id = row.getTrx_id();
                if(trx_id < minId) {
                    break;
                }
                row = row.getHistory();
            }
            if(row != null) {
                result.add(row.clone());
            }
        }
        return result;
    }

    public Map<Long, Row> getMap() {
        return map;
    }

    public void setMap(Map<Long, Row> map) {
        this.map = map;
    }

    public Map<Long, Object> getLocks() {
        return locks;
    }

    public void setLocks(Map<Long, Object> locks) {
        this.locks = locks;
    }

    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(Set<Long> ids) {
        this.ids = ids;
    }
}

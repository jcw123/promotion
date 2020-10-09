package com.im.sky.generator;

/**
 * @author jiangchangwei
 * @date 2020-8-12 下午 5:36
 **/
public class DBGenerator extends AbstractGenerator {

    private DBService dbService;

    public DBGenerator(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public Long id() {
        return dbService.id();
    }
}

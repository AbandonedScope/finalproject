package com.mahanko.finalproject.model.dao.impl;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class AbstractDaoTest {

    @BeforeTest
    public void dataBaseCreation() throws ManagedProcessException, FileNotFoundException {
        DB database = DB.newEmbeddedDB(9000);
        var is = new FileInputStream("src/test/resources/script.sql");

        database.start();
        database.source(is);
        ConnectionPool.getInstance();
    }
}

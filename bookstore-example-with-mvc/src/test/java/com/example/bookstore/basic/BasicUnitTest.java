package com.example.bookstore.basic;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.bookstore.dao.DummyDao;

public class BasicUnitTest {

    private List<String> database;
    private DummyDao dummyDao;

    @Before
    public void dataSetup() {
        database = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            database.add("test" + i);
        }
        dummyDao = new DummyDao(database);
    }

    @Test
    public void testDeleteQuery() {
        assertEquals(1, dummyDao.find("test0").size());
        dummyDao.delete("test0");
        assertEquals(19, database.size());
        assertEquals(0, dummyDao.find("test0").size());
    }

    @Test
    public void testAddQuery() {
        assertEquals(0, dummyDao.find("test20").size());
        dummyDao.add("test20");
        assertEquals(21, database.size());
        assertEquals(1, dummyDao.find("test20").size());
    }

    @Test
    public void testFindQuery() {
        List<String> results = dummyDao.find("2");
        assertEquals(2, results.size());
        for (String result : results) {
            assertTrue(result.equals("test2") || result.equals("test12"));
        }
    }
}

package com.devsda.platform.shepherdcore.dao;

import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface PingDao {

    @SqlQuery("select 1;")
    public int ping();

}

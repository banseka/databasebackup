package com.dbbackup.backup.services;

import com.dbbackup.backup.constants.Constants;

public interface DumpService {
    String dumpDatabase(String host, String port, Constants.DBTYPES dbType, String username, String password, String filepath, String dbname);
}

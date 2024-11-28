package com.dbbackup.backup.services;


import com.dbbackup.backup.constants.Constants;

public interface RestoreService {
    String restoreDatabase(String host, String port, Constants.DBTYPES dbType, String username, String password, String filepath, String dbname);
}

package com.dbbackup.backup.services.implementations;

import com.dbbackup.backup.constants.Constants;
import com.dbbackup.backup.services.DumpService;
import com.dbbackup.backup.services.RestoreService;
import org.springframework.stereotype.Service;

@Service
public class MysqlService implements DumpService, RestoreService {
    @Override
    public String dumpDatabase(String host, String port, Constants.DBTYPES dbType, String username, String password, String filepath, String dbname) {
        String command = "";
        if (dbname.isEmpty()){
            command = String.format("mysqldump -u%s -p%s -h%s -P%s --all-databases --result-file=%s", username, password, host,port, filepath);
        }else {
            command = String.format("mysqldump -u%s -p%s -h%s -P%s %s --result-file=%s", username,password,host,port,dbname,filepath);
        }

        try{
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return "Success fully dumped the database to " + filepath;
        }catch (Exception e){
            return "Failed to execute database dump" +e.getMessage();
        }
    }

    @Override
    public String restoreDatabase(String host, String port, Constants.DBTYPES dbType, String username, String password, String filepath, String dbname) {
        String command = "";
        if (dbname.isEmpty()){
            command = String.format("mysql -u%s -p%s -h%s -P%s  < %s", username, password, host,port, filepath);
        }else {
            command = String.format("mysql -u%s -p%s -h%s -P%s %s < %s", username,password,host,port,dbname,filepath);
        }

        try{
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return "Success fully dumped the database to " + filepath;
        }catch (Exception e){
            return "Failed to execute database dump" +e.getMessage();
        }
    }
}

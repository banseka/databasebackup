package com.dbbackup.backup.services.implementations;

import com.dbbackup.backup.constants.Constants;
import com.dbbackup.backup.services.DumpService;
import com.dbbackup.backup.services.RestoreService;
import org.springframework.stereotype.Service;

@Service
public class PostgresqlService implements DumpService, RestoreService {
    @Override
    public String dumpDatabase(String host, String port, Constants.DBTYPES dbType, String username, String password, String filepath, String dbname) {
        String command = "";
        if (dbname.isEmpty()){
            command = String.format("pg-dumpall  -U %s  -h %s -F c -b -v -f =%s", username, host, filepath);

        }else {
            command = String.format("pg-dump  -U %s  -h %s -p %s -F c -b -v -f %s %s", username, host,port, filepath, dbname);
        }
        System.getenv().put("PGPASSWORD", password);

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
            command = String.format("psql -U %s  -h %s -F c -b -v -f =%s", username, host, filepath);

        }else {
            command = String.format("psql -U %s  -h %s -F c -b -v -w -f %s %s", username, host, filepath, dbname);
        }
        System.getenv().put("PGPASSWORD", password);

        try{
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return "Success fully dumped the database to " + filepath;
        }catch (Exception e){
            return "Failed to execute database dump" +e.getMessage();
        }
    }
}

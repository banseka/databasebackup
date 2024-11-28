package com.dbbackup.backup.services.implementations;

import com.dbbackup.backup.constants.Constants;
import com.dbbackup.backup.services.DumpService;
import com.dbbackup.backup.services.RestoreService;
import org.springframework.stereotype.Service;

import static com.dbbackup.backup.utils.Utils.constructUrl;


@Service
public class MongodbService implements DumpService, RestoreService {
    @Override
    public String dumpDatabase(String host, String port, Constants.DBTYPES dbType, String username, String password, String filepath, String dbname) {
        String command = "";

        if (dbname.isEmpty()) return "Please provide the database name";
        command = String.format("mongodump --uri=\"%s\" --username=%s --password=%s --out=%s", constructUrl(host, port, dbname), username, password, filepath);

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

        if (dbname.isEmpty()) return "Please provide the database name";
        command = String.format("mongorestore --host %s --port %s --username=%s --password=%s -d %s %s/%s",host, port, username, password, dbname, filepath, dbname);

        try{
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return "Success fully dumped the database to " + filepath;
        }catch (Exception e){
            return "Failed to execute database dump" +e.getMessage();
        }    }
}

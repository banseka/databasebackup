package com.dbbackup.backup.utils;


import com.dbbackup.backup.constants.Constants;

public class Utils {

    public static String constructUrl(String host, String port, String dbname){
        return  String.format("mongodb://%s:%s/%s", host, port, dbname);

    }

    public static String constructCommand(String host, String port, Constants.DBTYPES dbType, String username, String password, String filepath, String dbname){
        String command = "";
        switch (dbType){
            case mysql:
                if (dbname.isEmpty()){
                    command = String.format("mysqldump -u%s -p%s -h%s -P%s --all-databases --result-file=%s", username, password, host,port, filepath);
                }else {
                    command = String.format("mysqldump -u%s -p%s -h%s -P%s %s --result-file=%s", username,password,host,port,dbname,filepath);
                }
                break;

            case mongodb:
                if (dbname.isEmpty()) return "Please provide the database name";
                command = String.format("mongodump --uri=\"%s\" --username=%s --password=%s --out=%s", constructUrl(host, port, dbname), username, password, filepath);
                break;
            case postgresql:
                if (dbname.isEmpty()){
                    command = String.format("pg-dumpall  -U %s  -h %s -F c -b -v -f =%s", username, host, filepath);

                }else {
                    command = String.format("pg-dump  -U %s  -h %s -F c -b -v -w -f %s %s", username, host, filepath, dbname);
                }
                System.getenv().put("PGPASSWORD", password);
                break;
            default:
                break;
        }

        return command;
    }

}

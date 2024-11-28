package com.dbbackup.backup.comands;

import com.dbbackup.backup.constants.Constants;
import com.dbbackup.backup.services.implementations.MongodbService;
import com.dbbackup.backup.services.implementations.MysqlService;
import com.dbbackup.backup.services.implementations.PostgresqlService;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command
public class DumpDataBase {

    private final MysqlService mysqlService;
    private final MongodbService mongodbService;

    private final PostgresqlService postgresqlService;

    public DumpDataBase(MysqlService mysqlService, MongodbService mongodbService, PostgresqlService postgresqlService) {
        this.mysqlService = mysqlService;
        this.mongodbService = mongodbService;
        this.postgresqlService = postgresqlService;
    }

    @Command(command = "dbdump", description = "This command dumps a data bases")
    public String dumpDataBase(
            @Option(shortNames = 'H', longNames = "host", description = "", required = true) String host,
            @Option(shortNames = 'P', longNames = "port", description = "", required = true) String port,
            @Option(shortNames = 'n', longNames = "dbname", description = "") String dbname,
            @Option(shortNames = 'u', longNames = "username", description = "", required = true) String username,
            @Option(shortNames = 'p', longNames = "password", description = "",required = true) String password,
            @Option(shortNames = 'f', longNames = "filepath", description = "",required = true) String filepath,
            @Option(shortNames = 't', longNames = "dbtype", description = "",required = true) Constants.DBTYPES dbType) {

        System.out.println("HOST" +host + " PORT" + port);

        return switch (dbType){
            case mysql -> mysqlService.dumpDatabase( host,  port,  dbType,  username,  password,  filepath,  dbname);
            case mongodb -> mongodbService.dumpDatabase( host,  port,  dbType,  username,  password,  filepath,  dbname);
            case postgresql -> postgresqlService.dumpDatabase( host,  port, dbType,  username,  password,  filepath,  dbname);
            default -> "Unsupported database type";
        };
    }

}

package com.dbbackup.backup.comands;

import com.dbbackup.backup.constants.Constants;
import com.dbbackup.backup.services.implementations.MongodbService;
import com.dbbackup.backup.services.implementations.MysqlService;
import com.dbbackup.backup.services.implementations.PostgresqlService;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command
public class RestoreDataBase {
    private final MysqlService mysqlService;
    private final MongodbService mongodbService;

    private final PostgresqlService postgresqlService;

    public RestoreDataBase(MysqlService mysqlService, MongodbService mongodbService, PostgresqlService postgresqlService) {
        this.mysqlService = mysqlService;
        this.mongodbService = mongodbService;
        this.postgresqlService = postgresqlService;
    }

    @Command(command = "dbrestore", description = "")
    public String restoreDataBase(@Option(shortNames = 'h', longNames = "host", description = "", required = true) String host,
                                  @Option(shortNames = 'P', longNames = "port", description = "", required = true) String port,
                                  @Option(shortNames = 'n', longNames = "dbname", description = "") String dbname,
                                  @Option(shortNames = 'u', longNames = "username", description = "", required = true) String username,
                                  @Option(shortNames = 'p', longNames = "password", description = "",required = true) String password,
                                  @Option(shortNames = 'f', longNames = "filepath", description = "",required = true) String filepath,
                                  @Option(shortNames = 't', longNames = "dbtype", description = "",required = true) Constants.DBTYPES dbType){
        return switch (dbType){
            case mysql -> mysqlService.restoreDatabase( host,  port,  dbType,  username,  password,  filepath,  dbname);
            case mongodb -> mongodbService.restoreDatabase( host,  port,  dbType,  username,  password,  filepath,  dbname);
            case postgresql -> postgresqlService.restoreDatabase( host,  port, dbType,  username,  password,  filepath,  dbname);
            default -> "Unsupported database type";
        };
    }
}

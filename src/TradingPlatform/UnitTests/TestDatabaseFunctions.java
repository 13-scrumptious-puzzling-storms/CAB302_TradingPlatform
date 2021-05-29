package TradingPlatform.UnitTests;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used for created a test database with test data.
 */
public class TestDatabaseFunctions {
    private static Connection connection;
    private static final String testDbName = "testdb.db";

    /**
     * Initialises the test database file and connection.
     */
    public static void InitDb(){
        try {
            if (connection != null)
                connection.close();
            connection = DriverManager.getConnection("jdbc:sqlite:" + testDbName);
            ResetDb();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Resets the database.
     */
    private static void ResetDb(){
        try {
            var statement = connection.createStatement();
            for (String sql : INIT_TEST_DATABASE) {
                statement.execute(sql);
                if (statement.getWarnings() != null) {
                    System.out.println(sql);
                    System.out.println(statement.getWarnings().getMessage());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the connection to the test database
     * @return the connection to the test database
     */
    public static Connection getConnection() {
        if (connection == null)
            InitDb();
        return connection;
    }

    public static void CloseDatabase(){
        try {
            connection.close();
            File db = new File(testDbName);
            boolean deleted = db.delete();
            System.out.println(db.exists());
            System.out.println("Deleted test db " + testDbName + ": " + deleted);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //region SQL to init database
    private static final String[] INIT_TEST_DATABASE = new String[]{
// Foreign key constraints
            "PRAGMA foreign_keys = off",

//Drop tables
            "DROP TABLE IF EXISTS AssetType",
            "DROP TABLE IF EXISTS OrganisationAsset",
            "DROP TABLE IF EXISTS OrganisationUnit",
            "DROP TABLE IF EXISTS TradeOrders",
            "DROP TABLE IF EXISTS TradeRecon",
            "DROP TABLE IF EXISTS User",

//Table: AssetType
            "CREATE TABLE AssetType (\n" +
                    "    assetTypeID INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                    "                        NOT NULL,\n" +
                    "    name        VARCHAR UNIQUE\n" +
                    "                        NOT NULL\n" +
                    ")",

            "INSERT INTO AssetType (assetTypeID, name) VALUES (1, 'Pen')",
            "INSERT INTO AssetType (assetTypeID, name) VALUES (2, 'Printer')",
            "INSERT INTO AssetType (assetTypeID, name) VALUES (3, 'Ruler')",
            "INSERT INTO AssetType (assetTypeID, name) VALUES (4, 'Hard Drive')",

//Table: OrganisationAsset
            "CREATE TABLE OrganisationAsset (\n" +
                    "    organisationAssetID INTEGER PRIMARY KEY ASC AUTOINCREMENT\n" +
                    "                                NOT NULL,\n" +
                    "    organisationUnitID  INTEGER REFERENCES OrganisationUnit (OrganisationUnitID) \n" +
                    "                                NOT NULL,\n" +
                    "    assetTypeID         INTEGER REFERENCES AssetType (assetTypeID) \n" +
                    "                                NOT NULL,\n" +
                    "    Quantity            INTEGER NOT NULL\n" +
                    ")",

            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (1, 2, 1, 50)",
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (2, 2, 2, 200)",
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (3, 2, 1, 100)",
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (4, 3, 3, 100)",
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (5, 4, 1, 0)",
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (6, 4, 4, 0)",
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (7, 5, 1, 0)",
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (8, 5, 4, 0)",
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (9, 5, 2, 0)",
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (10, 5, 3, 0)",

//Table: OrganisationUnit
            "CREATE TABLE OrganisationUnit (\n" +
                    "    OrganisationUnitID INTEGER       PRIMARY KEY ASC AUTOINCREMENT\n" +
                    "                                     NOT NULL,\n" +
                    "    name               VARCHAR (100) UNIQUE\n" +
                    "                                     NOT NULL,\n" +
                    "    credits            INTEGER\n" +
                    ")",

            "INSERT INTO OrganisationUnit (OrganisationUnitID, name, credits) VALUES (1, 'ITAdmin', 1000)",
            "INSERT INTO OrganisationUnit (OrganisationUnitID, name, credits) VALUES (2, 'Thirteen', 125)",
            "INSERT INTO OrganisationUnit (OrganisationUnitID, name, credits) VALUES (3, 'Scrumptious', 200)",
            "INSERT INTO OrganisationUnit (OrganisationUnitID, name, credits) VALUES (4, 'Puzzling', 500)",
            "INSERT INTO OrganisationUnit (OrganisationUnitID, name, credits) VALUES (5, 'Storms', 50)",

//Table: TradeOrders
            "CREATE TABLE TradeOrders (\n" +
                    "    tradeOrderID        INTEGER  PRIMARY KEY AUTOINCREMENT\n" +
                    "                                 NOT NULL,\n" +
                    "    organisationAssetID INTEGER  REFERENCES OrganisationAsset (organisationAssetID) \n" +
                    "                                 NOT NULL,\n" +
                    "    quantity            INTEGER  NOT NULL,\n" +
                    "    remainingQuantity   INTEGER  NOT NULL,\n" +
                    "    isSellOrder         BOOLEAN  NOT NULL,\n" +
                    "    price               INTEGER,\n" +
                    "    cancelled           BOOLEAN  NOT NULL,\n" +
                    "    createdTime         DATETIME\n" +
                    ")",

            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (1, 5, 25, 25, 'false', 5, 'false', '2021-05-21 16:43:00.000')",
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (2, 4, 50, 50, 'true', 4, 'false', '2021-05-21 16:44:00.000')",
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (3, 1, 25, 25, 'true', 2, 'false', '2021-05-21 18:12:00.000')",
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (4, 3, 50, 15, 'false', 1, 'false', '2021-05-21 18:14:00.000')",
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (5, 5, 50, 50, 'true', 1, 'false', '2021-05-21 18:15:00.000')",
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (6, 6, 50, 50, 'true', 1, 'false', '2021-05-21 18:16:00.000')",
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (7, 7, 50, 40, 'false', 3, 'false', '2021-05-21 18:17:00.000')",
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (8, 8, 50, 15, 'false', 1, 'false', '2021-05-21 18:18:00.000')",
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (9, 9, 50, 15, 'true', 1, 'false', '2021-05-21 18:19:00.000')",
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (10, 10, 50, 15, 'true', 1, 'false', '2021-05-21 18:20:00.000')",

//Table: TradeRecon
            "CREATE TABLE TradeRecon (\n" +
                    "    tradeReconId INTEGER  PRIMARY KEY AUTOINCREMENT\n" +
                    "                          NOT NULL,\n" +
                    "    sellOrderId  INTEGER  REFERENCES TradeOrders (tradeOrderID) \n" +
                    "                          NOT NULL,\n" +
                    "    buyOrderId   INTEGER  REFERENCES TradeOrders (tradeOrderID) \n" +
                    "                          NOT NULL,\n" +
                    "    quantity     INTEGER  NOT NULL,\n" +
                    "    createdTime  DATETIME NOT NULL\n" +
                    ")",


//Table: User
            "CREATE TABLE User (\n" +
                    "    userID             INTEGER PRIMARY KEY ASC AUTOINCREMENT\n" +
                    "                               NOT NULL,\n" +
                    "    username           VARCHAR UNIQUE\n" +
                    "                               NOT NULL,\n" +
                    "    password           VARCHAR,\n" +
                    "    organisationUnitID INTEGER REFERENCES OrganisationUnit (OrganisationUnitID) \n" +
                    "                               NOT NULL,\n" +
                    "    userRole           INTEGER\n" +
                    ")",

            "INSERT INTO User (userID, username, password, organisationUnitID, userRole) VALUES (1, 'Radmin', 'HaveYouTriedTurningItOffAndOnAgain?', 1, 1)",
            "INSERT INTO User (userID, username, password, organisationUnitID, userRole) VALUES (2, 'NolanGrayson', 'Omniman', 2, 0)",
            "INSERT INTO User (userID, username, password, organisationUnitID, userRole) VALUES (3, 'KateCha', 'Duplikate', 3, 0)",
            "INSERT INTO User (userID, username, password, organisationUnitID, userRole) VALUES (4, 'SammyEve', 'AtomEve', 4, 0)",
            "INSERT INTO User (userID, username, password, organisationUnitID, userRole) VALUES (5, 'MarkGrayson', 'Invincible', 5, 0)",

// Foreign key constraints
            "PRAGMA foreign_keys = on",
    };
    //endregion
}

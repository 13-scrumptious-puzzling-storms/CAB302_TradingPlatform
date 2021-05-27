package TradingPlatform.UnitTests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used for created an in-memory test database with test data.
 */
public class TestDatabaseFunctions {
    private static Connection connection;

    /**
     * Initialises the in-memory test database.
     */
    public static void InitTestDatabase(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            var statement = connection.createStatement();
            statement.execute(INIT_TEST_DATABASE);
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
            InitTestDatabase();
        return connection;
    }

    //region SQL to init database
    private static final String INIT_TEST_DATABASE = "PRAGMA foreign_keys = off;\n" +
            "BEGIN TRANSACTION;\n" +
            "\n" +
            "-- Drop tables\n" +
            "DROP TABLE IF EXISTS AssetType;\n" +
            "DROP TABLE IF EXISTS OrganisationAsset;\n" +
            "DROP TABLE IF EXISTS OrganisationUnit;\n" +
            "DROP TABLE IF EXISTS TradeOrders;\n" +
            "DROP TABLE IF EXISTS TradeRecon;\n" +
            "DROP TABLE IF EXISTS User;\n" +
            "\n" +
            "-- Table: AssetType\n" +
            "CREATE TABLE AssetType (\n" +
            "    assetTypeID INTEGER PRIMARY KEY AUTOINCREMENT\n" +
            "                        NOT NULL,\n" +
            "    name        VARCHAR UNIQUE\n" +
            "                        NOT NULL\n" +
            ");\n" +
            "\n" +
            "INSERT INTO AssetType (assetTypeID, name) VALUES (1, 'Pen');\n" +
            "INSERT INTO AssetType (assetTypeID, name) VALUES (2, 'Printer');\n" +
            "INSERT INTO AssetType (assetTypeID, name) VALUES (3, 'Ruler');\n" +
            "\n" +
            "-- Table: OrganisationAsset\n" +
            "CREATE TABLE OrganisationAsset (\n" +
            "    organisationAssetID INTEGER PRIMARY KEY ASC AUTOINCREMENT\n" +
            "                                NOT NULL,\n" +
            "    organisationUnitID  INTEGER REFERENCES OrganisationUnit (OrganisationUnitID) \n" +
            "                                NOT NULL,\n" +
            "    assetTypeID         INTEGER REFERENCES AssetType (assetTypeID) \n" +
            "                                NOT NULL,\n" +
            "    Quantity            INTEGER NOT NULL\n" +
            ");\n" +
            "\n" +
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (1, 1, 1, 50);\n" +
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (2, 1, 2, 200);\n" +
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (3, 2, 1, 100);\n" +
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (4, 2, 3, 100);\n" +
            "INSERT INTO OrganisationAsset (organisationAssetID, organisationUnitID, assetTypeID, Quantity) VALUES (5, 1, 3, 0);\n" +
            "\n" +
            "-- Table: OrganisationUnit\n" +
            "CREATE TABLE OrganisationUnit (\n" +
            "    OrganisationUnitID INTEGER       PRIMARY KEY ASC AUTOINCREMENT\n" +
            "                                     NOT NULL,\n" +
            "    name               VARCHAR (100) UNIQUE\n" +
            "                                     NOT NULL,\n" +
            "    credits            INTEGER\n" +
            ");\n" +
            "\n" +
            "INSERT INTO OrganisationUnit (OrganisationUnitID, name, credits) VALUES (1, 'Test', 125);\n" +
            "INSERT INTO OrganisationUnit (OrganisationUnitID, name, credits) VALUES (2, 'Storms', 200);\n" +
            "\n" +
            "-- Table: TradeOrders\n" +
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
            ");\n" +
            "\n" +
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (1, 5, 25, 25, 'false', 5, 'true', '2021-05-21 16:43:00.000');\n" +
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (2, 4, 50, 50, 'true', 4, 'false', '2021-05-21 16:44:00.000');\n" +
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (3, 1, 25, 25, 'true', 2, 'false', '2021-05-21 18:12:00.000');\n" +
            "INSERT INTO TradeOrders (tradeOrderID, organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (4, 3, 50, 15, 'false', 1, 'false', '2021-05-21 18:14:00.000');\n" +
            "\n" +
            "-- Table: TradeRecon\n" +
            "CREATE TABLE TradeRecon (\n" +
            "    tradeReconId INTEGER  PRIMARY KEY AUTOINCREMENT\n" +
            "                          NOT NULL,\n" +
            "    sellOrderId  INTEGER  REFERENCES TradeOrders (tradeOrderID) \n" +
            "                          NOT NULL,\n" +
            "    buyOrderId   INTEGER  REFERENCES TradeOrders (tradeOrderID) \n" +
            "                          NOT NULL,\n" +
            "    quantity     INTEGER  NOT NULL,\n" +
            "    createdTime  DATETIME NOT NULL\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Table: User\n" +
            "CREATE TABLE User (\n" +
            "    userID             INTEGER PRIMARY KEY ASC AUTOINCREMENT\n" +
            "                               NOT NULL,\n" +
            "    username           VARCHAR UNIQUE\n" +
            "                               NOT NULL,\n" +
            "    password           VARCHAR,\n" +
            "    organisationUnitID INTEGER REFERENCES OrganisationUnit (OrganisationUnitID) \n" +
            "                               NOT NULL,\n" +
            "    userRole           INTEGER\n" +
            ");\n" +
            "\n" +
            "INSERT INTO User (userID, username, password, organisationUnitID, userRole) VALUES (1, 'PLEASE', 'WORK', 1, 0);\n" +
            "\n" +
            "COMMIT TRANSACTION;\n" +
            "PRAGMA foreign_keys = on;\n";
    //endregion
}

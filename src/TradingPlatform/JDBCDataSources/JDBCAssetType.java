package TradingPlatform.JDBCDataSources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCAssetType {
    private static final String INSERT_ASSETTYPE = "INSERT INTO AssetType (name) VALUES (?);";
    private static final String GET_ASSETNAME = "SELECT name FROM AssetType WHERE assetTypeID=?";
    private static final String NAME_HEADING = "name";
    private static final String ID_HEADING = "assetTypeID";
    private static final String GET_ALL_ASSETNAMES = "SELECT name FROM AssetType";
    private static final String GET_ALL_ASSETS = "SELECT * FROM AssetType";
    private static final String COUNT_ASSETS = "SELECT count(name) as num FROM AssetType";
    private static final String GET_ASSET_ID = "SELECT assetTypeID FROM AssetType WHERE name=?";


    private PreparedStatement addAssetType;
    private PreparedStatement getAssetName;
    private PreparedStatement getAllAssetNames;
    private PreparedStatement getAllAssets;
    private PreparedStatement countAssets;
    private PreparedStatement getAssetId;


    private Connection connection;


    public JDBCAssetType(Connection connection) {
        this.connection = connection;

        try {
            // Preparing Statement
            addAssetType = connection.prepareStatement(INSERT_ASSETTYPE);
            getAssetName = connection.prepareStatement(GET_ASSETNAME);
            getAllAssetNames = connection.prepareStatement(GET_ALL_ASSETNAMES);
            getAllAssets = connection.prepareStatement(GET_ALL_ASSETS);
            countAssets = connection.prepareStatement(COUNT_ASSETS);
            getAssetId = connection.prepareStatement(GET_ASSET_ID);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int addAssetType(String assetName){
        try {
            addAssetType.clearParameters();
            addAssetType.setString(1, assetName);

            int affectedRows = addAssetType.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("JDBCAssetType unable to retrieve ID: no affected rows");
            }
            ResultSet generatedKeys = addAssetType.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("JDBCAssetType unable to retrieve ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public String getAssetName(int assetId) {
        try {
            getAssetName.clearParameters();
            getAssetName.setInt(1, assetId);
            ResultSet rs = getAssetName.executeQuery();

            if (rs.next()) {
                String name = rs.getString(NAME_HEADING);
                return name;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public String[] getAllAssetNames(){
        try{
            int num = 0;
            ResultSet count = countAssets.executeQuery();
            if(count.next()){
                num = count.getInt("num");
            }

            ResultSet rs = getAllAssetNames.executeQuery();
            String[] assets = new String[num];
            int i = 0;
            while (rs.next()){
                String name = rs.getString(NAME_HEADING);
                assets[i] = name;
                i++;
            }
            return assets;
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    // Returns asset types in the form String[numAssets][id, assetName]
    public String[][] getAllAssetTypes(){
        try{
            int num = 0;
            ResultSet count = countAssets.executeQuery();
            if(count.next()){
                num = count.getInt("num");
            }

            ResultSet rs = getAllAssets.executeQuery();
            String[][] assets = new String[num][];
            int i = 0;
            while (rs.next()){
                int id = rs.getInt(ID_HEADING);
                String name = rs.getString(NAME_HEADING);
                assets[i] = new String[]{ Integer.toString(id), name };
                i++;
            }
            return assets;
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return new String[0][2];
    }

    // Returns the id of the asset with the given name, or -1 on error
    public int getAssetId(String assetName) {
        try {
            getAssetId.clearParameters();
            getAssetId.setString(1, assetName);
            ResultSet rs = getAssetId.executeQuery();

            if (rs.next()) {
                int id = rs.getInt(ID_HEADING);
                return id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

}

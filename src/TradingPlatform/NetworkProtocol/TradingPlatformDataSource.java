package TradingPlatform.NetworkProtocol;
import TradingPlatform.OrganisationalUnit;

/**
 * Provides functionality needed by any data source for the
 * Trading Platform application.
 */
public interface TradingPlatformDataSource {

    // IS THIS SOMETHING WE WANT? DO WE WANT TO BAN DUPLICATE NAMES?
    // WOULD DUPLICATE NAMES BE A GOOD / BAD THING 'if it is not already in the table'
    /**
     * Adds an Organisational Unit to the organisational unit table
     * in the database, if it is not already in the table
     * @param orgUnit
     */
    void addOrganisationalUnit(OrganisationalUnit orgUnit);

    /**
     * Extracts all the details of an OrganisationalUnit from the
     * database based on the ID passed in.
     *
     * @param id The id as an integer to search for.
     * @return all details in an OrganisationalUnit object for the id
     */
    OrganisationalUnit getOrganisationalUnit(int id);

    //
    // There will be many more functions
    // However we interact with the database will
    // need to be done through this class
    //

    /**
     * Finalises any resources used by the data source and
     * ensures data is persisted.
     *
     * @author Malcolm Corney
     */
    void close();
}

package TradingPlatform.NetworkProtocol;
import TradingPlatform.OrganisationalUnit;

/**
 * Provides functionality needed by any data source for the
 * Trading Platform application.
 */
public interface TradingPlatformDataSource {

    // IS THIS SOMETHING WE WANT? DO WE WANT TO BAN DUPLICATE NAMES?
    // WOULD DUPLICATE NAMES BE A GOOD / BAD THING 'if it is not already in the table'
    // I personally don't think duplicate names should be allowed. It's likely the user
    // will only ever see trades for 'this organisational unit' and 'that organisational unit'
    // they won't be faced with 'the trades for organisational unit ID:5 are ...' the actual
    // name will probably be used whilst the ID used in the backend. As such, banning duplicate
    // names will allow a user to know that the organisational unit they are investigating is
    // unique and not multiple with the same name. - Liam
    /**
     * Adds an Organisational Unit to the organisational unit table
     * in the database, if it is not already in the table
     * @param orgUnit
     */

        +
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

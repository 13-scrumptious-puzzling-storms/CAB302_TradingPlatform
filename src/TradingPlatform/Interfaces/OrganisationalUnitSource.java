package TradingPlatform.Interfaces;

import TradingPlatform.OrganisationalUnit;

import java.sql.Connection;
import java.util.HashMap;

public interface OrganisationalUnitSource {

    /**
     * Creates new organisationalUnit using organisation name and number of credits
     * returns organisationalUnit ID
     * @param orgName organisation's name
     * @param orgCredits number of credits belonging to the organisational unit
     * @return organisationalUnit ID
     */
    int addOrganisationalUnit(String orgName, int orgCredits);

    /**
     * retrieves name of organisational unit with the given ID
     * @param orgUnitId organisationalUnit ID
     * @return name of the organisation
     */
    String getOrganisationalUnitName(int orgUnitId);

    /**
     * retrieves number of credits belonging to the organisational unit
     * @param OrgUnitId organisationalUnit ID
     * @return number of credits belonging to the organisational unit
     */
    int getOrganisationalUnitCredits(int OrgUnitId);

    /**
     * retrieves constructed organisational unit with all details given
     * the organisationalUnit ID
     * @param orgUnitId organisationalUnit ID
     * @return OrganisationalUnit of the given organisationalUnitID
     */
    OrganisationalUnit getOrganisationalUnit(int orgUnitId);

    /**
     * updates the number of credits of a given organisational unit
     * @param OrgUnitID organisationalUnit ID
     * @param updatedCredits new number of credits owned by given organisationalUnit
     * @return returns true if successful, false otherwise
     */
    Boolean UpdateOrganisationalUnitCredits(int OrgUnitID, int updatedCredits);

    /**
     * Returns the names and ID of the organisationalUnit. For IT admin when editing
     * users and organisationalUnits
     * @return double array of organisationalUnit names and IDs
     */
    String[][] getAllOrganisationalUnits();
}

package TradingPlatform.UnitTests;

import TradingPlatform.OrganisationalUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//
public class OrganisationalUnitTests {

    OrganisationalUnit org1;
    OrganisationalUnit org2;
    OrganisationalUnit org3;


    @Test
    public void emptyCaseOrg(){
        org1 = new OrganisationalUnit();
    }

    @Test
    public void baseCaseOrg(){
        org2 = new OrganisationalUnit("Shanelle", 200);
        System.out.println(org2.getID());
    }

    @Test
    public void twoBaseCaseOrg(){
        org2 = new OrganisationalUnit("Shanelle", 200);
        System.out.println(org2.getID());

        org3 = new OrganisationalUnit("Bella", 500);
        System.out.println(org3.getID());
    }

    @Test
    public void testSetID(){

    }

}

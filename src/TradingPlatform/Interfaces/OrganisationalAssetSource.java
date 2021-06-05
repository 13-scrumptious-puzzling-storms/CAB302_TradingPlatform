package TradingPlatform.Interfaces;

public interface OrganisationalAssetSource {
    /**
     * Retrieves organisationalUnitID of the organisationAsset
     * @param orgAssetId organisationAssetID
     * @return organisationalUnitID
     */
    int getOrganisationAssetOrgUnitID(int orgAssetId);

    /**
     * Retrieves quantity of the organisationAsset
     * @param orgAssetId organisationAsset ID
     * @return quantity of the organisationAssetID
     */
    int getOrganisationAssetQuantity(int orgAssetId);

    /**
     * Retrieves the asset ID that belongs to
     * the given organisationAssetID
     * @param orgAssetId organisationAsset ID
     * @return asset ID of the given organisationAssetID
     */
    int getOrganisationAssetTypeID(int orgAssetId);

    /**
     * Retrieves the organisation's asset names and quantity
     * @param orgUnitId organisationUnit ID
     * @return organisationUnits assets and quantities in double string array
     */
    String[][] getOrganisationAssetsAndQuantity(int orgUnitId);

    /**
     * Adds new organisationAsset to the database using organisationUnitID, assetTypeId, and quantity
     * @param orgUnitID organisationUnit ID to add organisationAsset to
     * @param assetTypeID Asset type ID of asset that is to be added
     * @param quantity quantity to add organisationAsset to
     * @return ID of the organisation asset
     */
    int addOrganisationAsset(int orgUnitID, int assetTypeID, int quantity);

    /**
     * Updates the quantity of an organisation asset with given ID
     * @param orgAssetID organisationAsset ID to be updated
     * @param quantity new quantity of the organisationAsset
     */
    void UpdateOrganisationAssetQuantity(int orgAssetID, int quantity);

    /**
     * Returns the organisationAssetID for a given organisationalUnit and AssetType
     * @param orgUnitId organisationUnit ID
     * @param assetTypeId assetTypeID
     * @return organisationAssetID (-1 on error)
     */
    int getOrganisationAssetId(int orgUnitId, int assetTypeId);
}

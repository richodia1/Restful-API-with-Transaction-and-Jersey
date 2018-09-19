package com.iita.jersey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Created by Simeon on 10/09/2015.
 */
public class DataAccess
{
    Gson gson = new Gson();
    Type usertype = new TypeToken<Users>() {}.getType();
    Type itemArrayType = new TypeToken<ArrayList<Item>>() {}.getType();
    Type lotArrayType = new TypeToken<ArrayList<Lot>>() {}.getType();
    Type lotlistArrayType = new TypeToken<ArrayList<LotList>>() {}.getType();
    Type lotlistlotArrayType = new TypeToken<ArrayList<LotListLot>>() {}.getType();
    Type fieldVarArrayType = new TypeToken<ArrayList<FieldVariable>>() {}.getType();
    Type varArrayType = new TypeToken<ArrayList<Variable>>() {}.getType();
    Type containerTypeArrayType = new TypeToken<ArrayList<ContainerType>>() {}.getType();
    Type lotVarArrayType = new TypeToken<ArrayList<LotVariable>>() {}.getType();
    Type locationArrayType = new TypeToken<ArrayList<Location>>() {}.getType();
    Type itemTypeArrayType = new TypeToken<ArrayList<ItemType>>() {}.getType();
    Type migrationArrayType = new TypeToken<ArrayList<Migration>>() {}.getType();
    ArrayList<LotList> lotListList;
    ArrayList<LotListLot> lotListLotList;
    ArrayList<Item> itemList;
    ArrayList<Lot> lotList;
    ArrayList<Variable> varList;
    ArrayList<FieldVariable> fieldVariables;
    ArrayList<LotVariable> lotVariables;
    ArrayList<ItemType> itemTypeList;
    ArrayList<ContainerType> containerTypeList;
    ArrayList<Location> locationList;
    ArrayList<Migration> migrationList;

    Context ctx = null;
    Connection con = null;
    java.sql.PreparedStatement ps = null;
    Statement stmt = null;
    ResultSet rs = null;
         
    public DataAccess(){}

    public ArrayList<Item> GetAllItem(String jsonItems)
    {
        itemList = new ArrayList<Item>();
        try
        {
            ArrayList<Item> allItemFromJson = gson.fromJson(jsonItems, itemArrayType);
            itemList = allItemFromJson;
        }
        catch (Exception e)
        {}
        return itemList;
    }

    public ArrayList<Lot> GetAllLot(String jsonLots)
    {
        lotList = new ArrayList<Lot>();
        try
        {
            ArrayList<Lot> allLotFromJson = gson.fromJson(jsonLots, lotArrayType);
            lotList = allLotFromJson;
        }
        catch (Exception e)
        {}
        return lotList;
    }

    public ArrayList<LotListLot> GetAllLotListLot(String jsonLots)
    {
        lotListLotList = new ArrayList<LotListLot>();
        try
        {
            ArrayList<LotListLot> allLotListLotFromJson = gson.fromJson(jsonLots, lotlistlotArrayType);
            lotListLotList = allLotListLotFromJson;
        }
        catch (Exception e)
        {}
        return lotListLotList;
    }

    public ArrayList<LotList> GetAllLotList(String jsonLots)
    {
        lotListList = new ArrayList<LotList>();
        try
        {
            ArrayList<LotList> allLotListFromJson = gson.fromJson(jsonLots, lotlistArrayType);
            lotListList = allLotListFromJson;
        }
        catch (Exception e)
        {}
        return lotListList;
    }

    public ArrayList<FieldVariable> GetAllFieldVariable(String jsonLots)
    {
        fieldVariables = new ArrayList<FieldVariable>();
        try
        {
            ArrayList<FieldVariable> allFieldVariableFromJson = gson.fromJson(jsonLots, fieldVarArrayType);
            fieldVariables = allFieldVariableFromJson;
        }
        catch (Exception e)
        {}
        return fieldVariables;
    }

    public ArrayList<LotVariable> GetAllLotVariable(String jsonLotVars)
    {
        lotVariables = new ArrayList<LotVariable>();
        try
        {
            ArrayList<LotVariable> allLotVariableFromJson = gson.fromJson(jsonLotVars, lotVarArrayType);
            lotVariables = allLotVariableFromJson;
        }
        catch (Exception e)
        {}
        return lotVariables;
    }

    public ArrayList<Variable> GetAllVariable(String jsonvars)
    {
       varList = new ArrayList<Variable>();
        try
        {
            ArrayList<Variable> allVariableFromJson = gson.fromJson(jsonvars, varArrayType);
            varList = allVariableFromJson;
        }
        catch (Exception e)
        {}
        return varList;
    }

    public ArrayList<ItemType> GetAllItemType(String jsonIT)
    {
        itemTypeList = new ArrayList<ItemType>();
        try
        {
            ArrayList<ItemType> allItemTypeFromJson = gson.fromJson(jsonIT, itemTypeArrayType);
            itemTypeList = allItemTypeFromJson;
        }
        catch (Exception e)
        {}
        return itemTypeList;
    }

    public ArrayList<Location> GetAllLocation(String jsonlocation)
    {
        locationList = new ArrayList<Location>();
        try
        {
            ArrayList<Location> allLocationFromJson = gson.fromJson(jsonlocation, locationArrayType);
            locationList = allLocationFromJson;
        }
        catch (Exception e)
        {}
        return locationList;
    }

    public ArrayList<Migration> GetAllMigration(String jsonMigration)
    {
        migrationList = new ArrayList<Migration>();
        try
        {
            ArrayList<Migration> allMigrationFromJson = gson.fromJson(jsonMigration, migrationArrayType);
            migrationList = allMigrationFromJson;
        }
        catch (Exception e)
        {}
        return migrationList;
    }

    public ArrayList<ContainerType> GetAllContainerType(String jsonCT)
    {
        containerTypeList = new ArrayList<ContainerType>();
        try
        {
            ArrayList<ContainerType> allContainerTypeFromJson = gson.fromJson(jsonCT, containerTypeArrayType);
            containerTypeList = allContainerTypeFromJson;
        }
        catch (Exception e)
        {}
        return containerTypeList;
    }
    
    public String GetLocationNameDetail(ArrayList<Location> allLocation, long locationId)
    {
        String divider = " > ";
        Location lo = new Location();

        //get location detail from id
        for(Location l : allLocation)
        {
            if(locationId == l.getLocationId())
            {
                lo = l;
            }
        }

        String locatn = lo.getName();
        while (lo.getParentId() > 0)
        {
            for(Location l : allLocation)
            {
                if(lo.getParentId() == l.getLocationId())
                {
                    lo = l;
                }
            }
            locatn = lo.getName() + divider + locatn;
        }
        return locatn;
    }  
    
    public Boolean IsLotlistLot(ArrayList<LotListLot> lotlistlotArray, long lotlistId, long lotId)
    {
    	Boolean lotExist = false;
    	
    	for(LotListLot lll : lotlistlotArray)
    	{
    		if (lll.getLotlistId() == lotlistId && lll.getLotId() == lotId)
    			lotExist = true;
    	}
    	return lotExist;
    }
    
    public Item GetLotItem(ArrayList<Item> itemList, long itemId)
    {
    	Item itm = new Item();
    	
    	for(Item it : itemList)
    	{
    		if (it.getItemId() == itemId)
    			itm = it;
    	}
    	return itm;
    }
    
    public ArrayList<LotList> GetAllNewLotlists(ArrayList<LotList> AlllotlistArray)
    {
    	ArrayList<LotList> newlotlistArray = new ArrayList<LotList>();
    	
    	for(LotList ll : AlllotlistArray)
		{
			// add new lot list and all their lot items. NB: lot list id of the new entries were assigned negative to be able to identify them
			if(ll.getLotlistId() < 0)
			{
				newlotlistArray.add(ll);
			}
		}
    	return newlotlistArray;
    }

    public ArrayList<LotList> GetAllOldLotlists(ArrayList<LotList> AlllotlistArray)
    {
    	ArrayList<LotList> oldlotlistArray = new ArrayList<LotList>();
    	
    	for(LotList ll : AlllotlistArray)
		{
			// add new lot list and all their lot items. NB: lot list id of the new entries were assigned negative to be able to identify them
			if(ll.getLotlistId() > 0)
			{
				oldlotlistArray.add(ll);
			}
		}
    	return oldlotlistArray;
    }

    public ArrayList<Lot> GetAllOldLotlistNewLots(ArrayList<Lot> AlllotArray)
    {
    	ArrayList<Lot> newlotArray = new ArrayList<Lot>();
    	
    	// get all new lot
    	for(Lot lo : AlllotArray)
		{
    		if(lo.getLotId() < 0)
    		{
    			newlotArray.add(lo);
    		}
		}
    	return newlotArray;
    }

    public ArrayList<Lot> GetAllDeviceUpdatedLots(ArrayList<Lot> AlllotArray)
    {
    	ArrayList<Lot> newlotArray = new ArrayList<Lot>();
    	
    	// get all new lot
    	for(Lot lo : AlllotArray)
		{
    		if(lo.getLotId() > 0 && lo.getUpdated() == 1)
    		{
    			newlotArray.add(lo);
    		}
		}
    	return newlotArray;
    }

    public ArrayList<FieldVariable> GetAllExistingLotNewFieldVariables(ArrayList<FieldVariable>fieldVariableArray)
    {
    	ArrayList<FieldVariable> newFieldVariableArray = new ArrayList<FieldVariable>();
    	
    	for(FieldVariable fv : fieldVariableArray)
    	{
    		if (fv.getLotId() > 0 && fv.getFieldvarId() < 0)
    			newFieldVariableArray.add(fv);
    	}
    	return newFieldVariableArray;
    }

    public ArrayList<FieldVariable> GetAllDeviceUpdatedFieldVariables(ArrayList<FieldVariable>fieldVariableArray)
    {
    	ArrayList<FieldVariable> newFieldVariableArray = new ArrayList<FieldVariable>();
    	
    	for(FieldVariable fv : fieldVariableArray)
    	{
    		if (fv.getUpdated() == 1 && fv.getFieldvarId() > 0)
    			newFieldVariableArray.add(fv);
    	}
    	return newFieldVariableArray;
    }

    public ArrayList<LotVariable> GetAllExistingLotNewLotVariables(ArrayList<LotVariable>lotvariableArray)
    {
    	ArrayList<LotVariable> newLotVariableArray = new ArrayList<LotVariable>();
    	
    	for(LotVariable lv : lotvariableArray)
    	{
    		if (lv.getLotId() > 0 && lv.getLotvariableId() < 0)
    			newLotVariableArray.add(lv);
    	}
    	return newLotVariableArray;
    }

    public ArrayList<LotVariable> GetAllDeviceUpdatedLotVariables(ArrayList<LotVariable>lotvariableArray)
    {
    	ArrayList<LotVariable> newLotVariableArray = new ArrayList<LotVariable>();
    	
    	for(LotVariable lv : lotvariableArray)
    	{
    		if (lv.getUpdated() == 1 && lv.getLotvariableId() > 0)
    			newLotVariableArray.add(lv);
    	}
    	return newLotVariableArray;
    }

    public ArrayList<SubtypeTransaction> GetAllExistingLotNewSubtypeTransactions(ArrayList<SubtypeTransaction>subtypeTransactionArray)
    {
    	ArrayList<SubtypeTransaction> newSubtypeTransactionArray = new ArrayList<SubtypeTransaction>();
    	
    	for(SubtypeTransaction stt : subtypeTransactionArray)
    	{
    		if (stt.getLotId() > 0 && stt.getSubtypeTransId() < 0)
    			newSubtypeTransactionArray.add(stt);
    	}
    	return newSubtypeTransactionArray;
    }

    public ArrayList<Migration> GetAllExistingLotNewMigrations(ArrayList<Migration> migrationArray)
    {
    	ArrayList<Migration> newMigrationArray = new ArrayList<Migration>();
    	
    	for(Migration mig : migrationArray)
    	{
    		if (mig.getLotId() > 0 && mig.getMigId() < 0)
    			newMigrationArray.add(mig);
    	}
    	return newMigrationArray;
    }
    
    public LotListLot GetALotList(ArrayList<LotListLot> lllList, long lotId)
    {
    	LotListLot lllot = new LotListLot();
    	
    	for(LotListLot lll : lllList)
    	{
    		if (lll.getLotId() == lotId)
    			lllot = lll;
    	}
    	return lllot;
    }
    
    public ArrayList<Lot> GetSpecificLotlistLots(ArrayList<Lot> AlllotArray, ArrayList<LotListLot> lotlistlotArray, long LotlistId)
    {
    	ArrayList<Lot> newlotArray = new ArrayList<Lot>();
    	ArrayList<LotListLot> newlotlistlotArray = new ArrayList<LotListLot>();
    	
    	// get all lot list lot of a specific lot list id
    	for(LotListLot lll : lotlistlotArray)
		{
			if(LotlistId == lll.getLotlistId())
			{
				newlotlistlotArray.add(lll);
			}
		}
    	
    	// get all lot of a specific lot list id
    	for(Lot l : AlllotArray)
		{
    		for(LotListLot lll : newlotlistlotArray)
    		{
    			if(lll.getLotId() == l.getLotId())
    			{
    				newlotArray.add(l);
    			}
    		}
		}
    	return newlotArray;
    }

    public ArrayList<FieldVariable> GetLotFieldVariables(ArrayList<FieldVariable>fieldVariableArray, long LotId)
    {
    	ArrayList<FieldVariable> newFieldVariableArray = new ArrayList<FieldVariable>();
    	
    	for(FieldVariable fv : fieldVariableArray)
    	{
    		if (fv.getLotId() == LotId)
    			newFieldVariableArray.add(fv);
    	}
    	return newFieldVariableArray;
    }

    public ArrayList<LotVariable> GetLotLotVariables(ArrayList<LotVariable>lotVariableArray, long LotId)
    {
    	ArrayList<LotVariable> newLotVariableArray = new ArrayList<LotVariable>();
    	
    	for(LotVariable lv : lotVariableArray)
    	{
    		if (lv.getLotId() == LotId)
    			newLotVariableArray.add(lv);
    	}
    	return newLotVariableArray;
    }

    public ArrayList<SubtypeTransaction> GetLotSubtypeTransactions(ArrayList<SubtypeTransaction>subtypetransArray, long LotId)
    {
    	ArrayList<SubtypeTransaction> newSubtypeTransactionArray = new ArrayList<SubtypeTransaction>();
    	
    	for(SubtypeTransaction stt : subtypetransArray)
    	{
    		if (stt.getLotId() == LotId)
    			newSubtypeTransactionArray.add(stt);
    	}
    	return newSubtypeTransactionArray;
    }

    public ArrayList<Migration> GetLotMigrations(ArrayList<Migration>migrationArray, long LotId)
    {
    	ArrayList<Migration> newMigrationArray = new ArrayList<Migration>();
    	
    	for(Migration mig : migrationArray)
    	{
    		if (mig.getLotId() == LotId)
    			newMigrationArray.add(mig);
    	}
    	return newMigrationArray;
    }
    

    public ArrayList<FieldVariable> GetDeletedLotFieldVariables(ArrayList<FieldVariable>devicefieldVariableArray, ArrayList<FieldVariable>databasefieldVariableArray)
    {
    	ArrayList<FieldVariable> newFieldVariableArray = new ArrayList<FieldVariable>();
    	ArrayList<FieldVariable> remainingFieldVariableArray = databasefieldVariableArray;
    	
    	//get all field variables that are both in the device and database
    	for(FieldVariable dbfv : databasefieldVariableArray)
    	{
    		for(FieldVariable devfv : devicefieldVariableArray)
        	{
    			if (devfv.getFieldvarId() == dbfv.getFieldvarId())
    				newFieldVariableArray.add(devfv);
        	}
    	}
    	
    	//remove all field variables that are both in the device and database to remain only the field variables that are in the database but not in the device
    	for(FieldVariable dbfv : databasefieldVariableArray)
    	{
    		for(FieldVariable fv : newFieldVariableArray)
        	{
    			if (fv.getFieldvarId() == dbfv.getFieldvarId())
    				remainingFieldVariableArray.remove(fv);
        	}
    	}
    	return remainingFieldVariableArray;
    }
    
    public int RecordFieldVariableLists(ArrayList<FieldVariable>fvArray, String serverDatabase) throws SQLException
    {
		int NumOfFieldVariableRecorded = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
    		
    		String query = "INSERT INTO FieldVariables (createdBy, createdDate, lastUpdated, lastUpdatedBy, version, date, qty, var, lot_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
    		ps = con.prepareStatement(query);
    		
    		con.setAutoCommit(false);
    		
    		for(FieldVariable fv : fvArray)
    		{
				ps.setString(1, fv.getCreatedBy());
				ps.setString(2, fv.getCreatedDate());
				ps.setString(3, fv.getLastUpdated());
				ps.setString(4, fv.getLastUpdatedBy());
				ps.setInt(5, fv.getVersion());
				ps.setString(6, fv.getDate());
				ps.setString(7, fv.getQty());
				ps.setString(8, fv.getVar());
    			ps.setLong(9, fv.getLotId());
    			ps.addBatch();
    		}
    		int [] numOfRecords = ps.executeBatch();
    		NumOfFieldVariableRecorded = numOfRecords.length;
    		con.commit();
		}
    	catch (Exception e)
    	{
    		con.rollback();
    	}
		finally 
		{
		    try { if (ps != null) ps.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NumOfFieldVariableRecorded;
    }
    
    public int RecordLotVariableLists(ArrayList<LotVariable>lvArray, String serverDatabase) throws SQLException
    {
		int NumOfLotVariableRecorded = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
    		
    		String query = "INSERT INTO LotVariable (createdBy, createdDate, lastUpdated, lastUpdatedBy, version, variabledate, quantity, variable, lot) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
    		ps = con.prepareStatement(query);
    		
    		con.setAutoCommit(false);
    		
    		for(LotVariable lv : lvArray)
    		{
				ps.setString(1, lv.getCreatedBy());
				ps.setString(2, lv.getCreatedDate());
				ps.setString(3, lv.getLastUpdated());
				ps.setString(4, lv.getLastUpdatedBy());
				ps.setInt(5, lv.getVersion());
				ps.setString(6, lv.getVariabledate());
				ps.setDouble(7, lv.getQuantity());
				ps.setLong(8, lv.getVariableId());
    			ps.setLong(9, lv.getLotId());
    			ps.addBatch();
    		}
    		int [] numOfRecords = ps.executeBatch();
    		NumOfLotVariableRecorded = numOfRecords.length;
    		con.commit();
		}
    	catch (Exception e)
    	{
    		con.rollback();
    	}
		finally 
		{
		    try { if (ps != null) ps.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NumOfLotVariableRecorded;
    }
    
    public int AddNewLotMigrations(ArrayList<Migration> lotMigrations, String serverDatabase) throws SQLException
    {
		int NumOfLotMigrationAdded = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
    		
    		String query = "INSERT INTO Migration (migrationDate, newLocationId, newLocationName, oldLocationId, oldLocationName, lot_id, reason, createdBy, createdDate, lastUpdated, lastUpdatedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
    		ps = con.prepareStatement(query);
    		
    		con.setAutoCommit(false);
    		
    		for(Migration lm : lotMigrations)
    		{    			
				ps.setString(1, lm.getMigrationDate());
    			ps.setLong(2, lm.getNewLocationId());
				ps.setString(3, lm.getNewLocationName());
    			ps.setLong(4, lm.getOldLocationId());
				ps.setString(5, lm.getOldLocationName());
    			ps.setLong(6, lm.getLotId());
				ps.setString(7, lm.getReason());				
				ps.setString(8, lm.getCreatedBy());
				ps.setString(9, lm.getCreatedDate());
				ps.setString(10, lm.getLastUpdated());
				ps.setString(11, lm.getLastUpdatedBy());
    			ps.addBatch();
    		}
    		int [] numOfRecords = ps.executeBatch();
    		NumOfLotMigrationAdded = numOfRecords.length;
    		con.commit();
		}
    	catch (Exception e)
    	{
    		con.rollback();
    	}
		finally 
		{
		    try { if (ps != null) ps.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NumOfLotMigrationAdded;
    }
    
    public int AddNewSubtypeTransactions(ArrayList<SubtypeTransaction> lotSubtypeTransactions, String serverDatabase) throws SQLException
    {
		int NumOfSubtypeTransactionAdded = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
    		
    		String query = "INSERT INTO Transaction2 (subtype, lot, quantity, date, scale, createdBy, createdDate, lastUpdated, lastUpdatedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
    		ps = con.prepareStatement(query);
    		
    		con.setAutoCommit(false);
    		
    		for(SubtypeTransaction stt : lotSubtypeTransactions)
    		{   
				ps.setString(1, stt.getSubtype());
    			ps.setLong(2, stt.getLotId());
    			ps.setDouble(3, stt.getQuantity());
				ps.setString(4, stt.getDate());
				ps.setString(5, stt.getScale());				
				ps.setString(6, stt.getCreatedBy());
				ps.setString(7, stt.getCreatedDate());
				ps.setString(8, stt.getLastUpdated());
				ps.setString(9, stt.getLastUpdatedBy());
    			ps.addBatch();
    		}
    		int [] numOfRecords = ps.executeBatch();
    		NumOfSubtypeTransactionAdded = numOfRecords.length;
    		con.commit();
		}
    	catch (Exception e)
    	{
    		con.rollback();
    	}
		finally 
		{
		    try { if (ps != null) ps.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NumOfSubtypeTransactionAdded;
    }
    
    public long InsertLotList(LotList ll, String serverDatabase) throws SQLException
    {
		long NewLotListId = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
	        stmt = con.createStatement(); 
    		
    		String lotlistQuery = "INSERT INTO LotList (createdBy, createdDate, lastUpdated, lastUpdatedBy, name, owner_id) VALUES ('" + ll.getCreatedBy() + "','" + ll.getCreatedDate() + "','" + ll.getLastUpdated() + "','" + ll.getLastUpdatedBy() + "','" + ll.getName() + "','" + ll.getOwner_id() + "')";      	
            stmt.executeUpdate(lotlistQuery, Statement.RETURN_GENERATED_KEYS);      	
			rs = stmt.getGeneratedKeys();
			if(rs.next())
				NewLotListId = rs.getLong(1);
		}
    	catch (Exception e){}
		finally 
		{
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NewLotListId;
    }

    public int ItemExist(Item lotItem, String serverDatabase) throws SQLException
    {
		int exist = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
	        stmt = con.createStatement(); 
    		
	        rs = stmt.executeQuery("SELECT * FROM Item WHERE name = '" + lotItem.getName() + "' AND itemType =" + "'" + lotItem.getItemTypeId() + "'");
			if(rs.next())
				exist = 1;
		}
    	catch (Exception e){exist=-1;}
		finally 
		{
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return exist;
    }
    
    public long GetMaxAccessionIdentifier(String serverDatabase) throws SQLException
    {
		long MaxAccessionIdentifier = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
	        stmt = con.createStatement(); 
    		
	        rs = stmt.executeQuery("SELECT MAX(accessionIdentifier) as MaxAccessionIdentifier FROM Item");
			if(rs.next())
				MaxAccessionIdentifier = rs.getLong(1);
		}
    	catch (Exception e){}
		finally 
		{
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return MaxAccessionIdentifier;
    }
    
    public long InsertItem(Item lotItem, String serverDatabase) throws SQLException
    {
		long NewItemId = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
	        stmt = con.createStatement(); 
    		
	        String itemQuery = "INSERT INTO Item (accessionIdentifier, alternativeIdentifier, dateLastModified, name, prefix, version, itemType, latinName) VALUES ('" + lotItem.getAccessionIdentifier() + "','" + lotItem.getAlternativeIdentifier() + "','" + lotItem.getDateLastModified() + "','" + lotItem.getName() + "','" + lotItem.getPrefix() + "','" + lotItem.getVersion() + "','" + lotItem.getItemTypeId() + "','" + lotItem.getLatinName() + "')";
			stmt.executeUpdate(itemQuery, Statement.RETURN_GENERATED_KEYS);      	
			rs = stmt.getGeneratedKeys();
			if(rs.next())
				NewItemId = rs.getLong(1);
		}
    	catch (Exception e){NewItemId = -1;}
		finally 
		{
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NewItemId;
    }
    
    public long InsertLot(double nQuantity, String nScale, int nStatus, int nVersion, long nContainerId, long nItemId, long nLocationId, String nCreatedBy, String nCreatedDate, String nLastUpdated, String nLastUpdatedBy, String serverDatabase) throws SQLException
    {
		long NewLotId = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
	        stmt = con.createStatement(); 
    		
	        String lotQuery = "INSERT INTO Lot (quantity, scale, status, version, container, item, location, createdBy, createdDate, lastUpdated, lastUpdatedBy) VALUES ('" + nQuantity + "','" + nScale + "','" + nStatus + "','" + nVersion + "','" + nContainerId + "','" + nItemId + "','" + nLocationId + "','" + nLastUpdatedBy + "','" + nLastUpdated + "','" + nLastUpdated + "','" + nLastUpdatedBy + "')";
			stmt.executeUpdate(lotQuery, Statement.RETURN_GENERATED_KEYS);      	
			rs = stmt.getGeneratedKeys();
			if(rs.next())
				NewLotId = rs.getLong(1);
		}
    	catch (Exception e){}
		finally 
		{
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NewLotId;
    }
    
    public long InsertBarcode(String nDateAssigned, long nLotId, String serverDatabase) throws SQLException
    {
		long NewBarcodeId = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
	        stmt = con.createStatement(); 
    		
	        stmt.executeUpdate("INSERT INTO BarCode (dateAssigned, lotId) VALUES ('" + nDateAssigned + "','" + nLotId + "')", Statement.RETURN_GENERATED_KEYS); 
			rs = stmt.getGeneratedKeys();
			if(rs.next())
				NewBarcodeId = rs.getLong(1);
		}
    	catch (Exception e){NewBarcodeId = -1;}
		finally 
		{
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NewBarcodeId;
    }
    
    public long InsertLotlistLot(long nLotlistId, long nLotId, String serverDatabase) throws SQLException
    {
    	long lotListAdded = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
    		
	        String query = "INSERT INTO LotListLots (LotList_id, element) VALUES (?, ?)"; 
    		ps = con.prepareStatement(query);
    		
    		con.setAutoCommit(false);
    		
			ps.setLong(1, nLotlistId);
			ps.setLong(2, nLotId);
    		ps.addBatch();
    		
    		int [] numOfRecords = ps.executeBatch();
    		lotListAdded = numOfRecords.length;
    		con.commit();
		}

    	catch (Exception e)
    	{
    		con.rollback();
    	}
		finally 
		{
		    try { if (ps != null) ps.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return lotListAdded;
    }
    
    public int UpdateLots(ArrayList<Lot>lArray, String serverDatabase) throws SQLException
    {
		int NumOfLotUpdated = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
    		
    		String query = "UPDATE Lot Set quantity = ?, version = ?, lastUpdated = ?, lastUpdatedBy = ?, location = ? WHERE id = ? and version = ?"; 
    		ps = con.prepareStatement(query);
    		
    		con.setAutoCommit(false);
    		
    		for(Lot lo : lArray)
    		{
    			int newVersion = lo.getVersion() + 1;
				ps.setDouble(1, lo.getQuantity());
				ps.setInt(2, newVersion);
				ps.setString(3, lo.getLastUpdated());
				ps.setString(4, lo.getLastUpdatedBy());
				ps.setLong(5, lo.getLocationId());
				ps.setLong(6, lo.getLotId());
				ps.setInt(7, lo.getVersion());
    			ps.addBatch();
    		}
    		int [] numOfRecords = ps.executeBatch();
    		NumOfLotUpdated = numOfRecords.length;
    		con.commit();
		}
    	catch (Exception e)
    	{
    		con.rollback();
    	}
		finally 
		{
		    try { if (ps != null) ps.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NumOfLotUpdated;
    }
    
    public int UpdateFieldVariableLists(ArrayList<FieldVariable>fvArray, String serverDatabase) throws SQLException
    {
		int NumOfFieldVariableUpdated = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
    		
    		String query = "UPDATE FieldVariables Set qty = ?, var = ?, date = ?, version = ?, lastUpdated = ?, lastUpdatedBy = ? WHERE id = ? and version = ?"; 
    		ps = con.prepareStatement(query);
    		
    		con.setAutoCommit(false);
    		
    		for(FieldVariable fv : fvArray)
    		{
    			int newVersion = fv.getVersion() + 1;
				ps.setString(1, fv.getQty());
				ps.setString(2, fv.getVar());
				ps.setString(3, fv.getDate());
				ps.setInt(4, newVersion);
				ps.setString(5, fv.getLastUpdated());
				ps.setString(6, fv.getLastUpdatedBy());
    			ps.setLong(7, fv.getFieldvarId());
				ps.setInt(8, fv.getVersion());
    			ps.addBatch();
    		}
    		int [] numOfRecords = ps.executeBatch();
    		NumOfFieldVariableUpdated = numOfRecords.length;
    		con.commit();
		}
    	catch (Exception e)
    	{
    		con.rollback();
    	}
		finally 
		{
		    try { if (ps != null) ps.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NumOfFieldVariableUpdated;
    }
    
    public int DeleteFieldVariableLists(ArrayList<DeletedFieldVariable>dfvArray, String serverDatabase) throws SQLException
    {
		int NumOfFieldVariableDeleted = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
    		
    		String query = "DELETE FROM FieldVariables WHERE id = ?"; 
    		ps = con.prepareStatement(query);
    		
    		con.setAutoCommit(false);
    		
    		for(DeletedFieldVariable dfv : dfvArray)
    		{
    			ps.setLong(1, dfv.getFieldvarId());
    			ps.addBatch();
    		}
    		int [] numOfRecords = ps.executeBatch();
    		NumOfFieldVariableDeleted = numOfRecords.length;
    		con.commit();
		}
    	catch (Exception e)
    	{
    		con.rollback();
    	}
		finally 
		{
		    try { if (ps != null) ps.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NumOfFieldVariableDeleted;
    }
    
    public int UpdateLotVariableLists(ArrayList<LotVariable>lvArray, String serverDatabase) throws SQLException
    {
		int NumOfLotVariableUpdated = 0;
    	try
		{	
    		ctx = new InitialContext();
    		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
    		con = ds.getConnection(); 
    		
    		String query = "UPDATE LotVariable Set quantity = ?, variable = ?, variabledate = ?, version = ?, lastUpdated = ?, lastUpdatedBy = ? WHERE id = ? and version = ?";
    		ps = con.prepareStatement(query);
    		
    		con.setAutoCommit(false);
    		
    		for(LotVariable lv : lvArray)
    		{
    			int newVersion = lv.getVersion() + 1;
				ps.setDouble(1, lv.getQuantity());
				ps.setLong(2, lv.getVariableId());
				ps.setString(3, lv.getVariabledate());
				ps.setInt(4, newVersion);
				ps.setString(5, lv.getLastUpdated());
				ps.setString(6, lv.getLastUpdatedBy());
    			ps.setLong(7, lv.getLotvariableId());
				ps.setInt(8, lv.getVersion());
    			ps.addBatch();
    		}
    		int [] numOfRecords = ps.executeBatch();
    		NumOfLotVariableUpdated = numOfRecords.length;
    		con.commit();
		}
    	catch (Exception e)
    	{
    		con.rollback();
    	}
		finally 
		{
		    try { if (ps != null) ps.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
    	return NumOfLotVariableUpdated;
    }
    
    public String gethash(String password) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
        byte[] passBytes = password.getBytes();
        byte[] passHash = sha256.digest(passBytes);
        return passHash.toString();
    }
    
    public String GenerateHash(String input) throws NoSuchAlgorithmException 
    {
        MessageDigest objSHA = MessageDigest.getInstance("SHA-512");
        byte[] bytSHA = objSHA.digest(input.getBytes());
        BigInteger intNumber = new BigInteger(1, bytSHA);
        String strHashCode = intNumber.toString(16);
		
        // pad with 0 if the hexa digits are less then 128.
        while (strHashCode.length() < 128) {
            strHashCode = "0" + strHashCode;
        }
        return strHashCode;
    }
}


package com.iita.jersey;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.Statement;
import java.text.SimpleDateFormat;

public class AccessManager
{
	Context ctx = null;
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
	DataAccess da = new DataAccess();
	Gson gson = new Gson();
    int updated = 0;
    
	public Database getDatabaseDetail(String username, String serverDatabase) throws Exception
	{

		Database dbObj = new Database();
		try
		{
		    ArrayList<LotList> lotlistArray = new ArrayList<LotList>();		
			ArrayList<Lot> lotArray = new ArrayList<Lot>();
			ArrayList<FieldVariable> fieldVariableArray = new ArrayList<FieldVariable>();		
			ArrayList<LotVariable> lotvariableArray = new ArrayList<LotVariable>();
			ArrayList<Migration> migrationArray = new ArrayList<Migration>();
			ArrayList<Variable> variableArray = new ArrayList<Variable>();
			ArrayList<LotListLot> lotlistlotArray = new ArrayList<LotListLot>();
			ArrayList<ContainerType> containertypeArray = new ArrayList<ContainerType>();
			ArrayList<ItemType> itemtypeArray = new ArrayList<ItemType>();
			ArrayList<Subtype> subtypeArray = new ArrayList<Subtype>();
			ArrayList<SubtypeTransaction> subtypetransArray = new ArrayList<SubtypeTransaction>(); 
			ArrayList<Location> locationArray = new ArrayList<Location>();
			ArrayList<Location> parentlocationArray = new ArrayList<Location>();
			ArrayList<Users> usersArray = new ArrayList<Users>();
			
			lotlistArray = new AccessManager().getLotLists(username, serverDatabase);			
			lotArray = new AccessManager().getLots(username, serverDatabase);
			fieldVariableArray = new AccessManager().getFieldVariables(username, serverDatabase);			
			lotvariableArray = new AccessManager().getLotVariables(username, serverDatabase);
			migrationArray = new AccessManager().getMigrations(username, serverDatabase);
			lotlistlotArray = new AccessManager().getLotListLots(username, serverDatabase);
			variableArray = new AccessManager().getVariables(serverDatabase);
			containertypeArray = new AccessManager().getContainerTypes(serverDatabase);
			itemtypeArray = new AccessManager().getItemTypes(serverDatabase);
			subtypeArray = new AccessManager().getSubtypes(serverDatabase);
			subtypetransArray = new AccessManager().getSubtypeTransactions(username, serverDatabase);			
			locationArray = new AccessManager().getChildLocations(username, serverDatabase);	
			parentlocationArray = new AccessManager().getParentLocations(username, serverDatabase);
			usersArray = new AccessManager().getUsers(serverDatabase);

			dbObj.setLotList(gson.toJson(lotlistArray) != null ? gson.toJson(lotlistArray) : "");			
			dbObj.setLot(gson.toJson(lotArray) != null ? gson.toJson(lotArray) : "");
			dbObj.setFieldVariable(gson.toJson(fieldVariableArray) != null ? gson.toJson(fieldVariableArray) : "");			
			dbObj.setLotVariable(gson.toJson(lotvariableArray) != null ? gson.toJson(lotvariableArray) : "");
			dbObj.setMigration(gson.toJson(migrationArray) != null ? gson.toJson(migrationArray) : "");
			dbObj.setLotListLot(gson.toJson(lotlistlotArray) != null ? gson.toJson(lotlistlotArray) : "");
			dbObj.setVariable(gson.toJson(variableArray) != null ? gson.toJson(variableArray) : "");
			dbObj.setContainerType(gson.toJson(containertypeArray) != null ? gson.toJson(containertypeArray) : "");
			dbObj.setItemType(gson.toJson(itemtypeArray) != null ? gson.toJson(itemtypeArray) : "");
			dbObj.setSubtype(gson.toJson(subtypeArray) != null ? gson.toJson(subtypeArray) : "");
			dbObj.setSubtypeTransaction(gson.toJson(subtypetransArray) != null ? gson.toJson(subtypetransArray) : "");			
			dbObj.setChildLocation(gson.toJson(locationArray) != null ? gson.toJson(locationArray) : "");		
			dbObj.setParentLocation(gson.toJson(parentlocationArray) != null ? gson.toJson(parentlocationArray) : "");
			dbObj.setAllusers(gson.toJson(usersArray) != null ? gson.toJson(usersArray) : "");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dbObj;
	}

	public wsSQLResult uploadDatabase(Database db, String serverDatabase) throws Exception
	{       
        ArrayList<LotList> lotlistArray = null;		
    	ArrayList<Lot> lotArray = null;	
    	ArrayList<FieldVariable> fieldVariableArray = null;			
    	ArrayList<LotVariable> lotvariableArray = null;	
    	ArrayList<Migration> migrationArray = null;	
    	ArrayList<LotListLot> lotlistlotArray = null;	
    	ArrayList<SubtypeTransaction> subtypetransArray = null;	
        ArrayList<Item> itemArray = new ArrayList<Item>();	
    	ArrayList<DeletedFieldVariable> deletedFieldVariableArray =null;	
    	
        Type lotlistArrayType = new TypeToken<ArrayList<LotList>>() {}.getType();
        Type lotArrayType = new TypeToken<ArrayList<Lot>>() {}.getType();
    	Type itemArrayType = new TypeToken<ArrayList<Item>>() {}.getType();
        Type fieldVarArrayType = new TypeToken<ArrayList<FieldVariable>>() {}.getType();
        Type lotVarArrayType = new TypeToken<ArrayList<LotVariable>>() {}.getType();
        Type lotlistlotArrayType = new TypeToken<ArrayList<LotListLot>>() {}.getType();
        Type migrationArrayType = new TypeToken<ArrayList<Migration>>() {}.getType();
        Type subtypeTransArrayType = new TypeToken<ArrayList<SubtypeTransaction>>() {}.getType();
        Type deletedFieldVarArrayType = new TypeToken<ArrayList<DeletedFieldVariable>>() {}.getType();

        ArrayList<Lot> newlotlistLots = new ArrayList<Lot>();	
        ArrayList<Lot> oldlotlistNewLots = new ArrayList<Lot>();
        ArrayList<Lot> allDeviceUpdatedLots = new ArrayList<Lot>();	
        ArrayList<FieldVariable> lotFieldVariable = new ArrayList<FieldVariable>();	
        ArrayList<FieldVariable> newlotFieldVariable = new ArrayList<FieldVariable>();	
        ArrayList<FieldVariable> existinglotNewFieldVariables = new ArrayList<FieldVariable>();	
        ArrayList<FieldVariable> updatedFieldVariables = new ArrayList<FieldVariable>();	
        ArrayList<LotVariable> lotLotVariable = new ArrayList<LotVariable>();	
        ArrayList<LotVariable> newlotLotVariable = new ArrayList<LotVariable>();
        ArrayList<LotVariable> existinglotNewLotVariables = new ArrayList<LotVariable>();	
        ArrayList<LotVariable> updatedLotVariables = new ArrayList<LotVariable>();	
        ArrayList<SubtypeTransaction> lotsubtypetransactions = new ArrayList<SubtypeTransaction>();	
        ArrayList<SubtypeTransaction> existinglotNewSubtypeTransactions = new ArrayList<SubtypeTransaction>();	
        ArrayList<SubtypeTransaction> newSubtypetransactions = new ArrayList<SubtypeTransaction>();	
        ArrayList<Migration> existinglotNewMigrations = new ArrayList<Migration>();		
        ArrayList<Migration> newLotMigrations = new ArrayList<Migration>();		
        StringBuilder sb = new StringBuilder();
        
        wsSQLResult result = new wsSQLResult();
		try
		{	
			ctx = new InitialContext();
	        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
	        con = ds.getConnection(); 
	        stmt = con.createStatement(); 

			lotlistArray = gson.fromJson(db.getLotList(), lotlistArrayType);
			lotArray = gson.fromJson(db.getLot(), lotArrayType);
			itemArray = gson.fromJson(db.getItem(), itemArrayType);
			fieldVariableArray = gson.fromJson(db.getFieldVariable(), fieldVarArrayType);
			lotvariableArray = gson.fromJson(db.getLotVariable(), lotVarArrayType);
			lotlistlotArray = gson.fromJson(db.getLotListLot(), lotlistlotArrayType);
			migrationArray = gson.fromJson(db.getMigration(), migrationArrayType);
			subtypetransArray = gson.fromJson(db.getSubtypeTransaction(), subtypeTransArrayType);
			deletedFieldVariableArray = gson.fromJson(db.getDeletedFieldVariable(), deletedFieldVarArrayType);
			
			long newLotListId = 0;
			long newItemId = 0, newLotId = 0;  
	        long NewItemAccessionIdentifier = 0;
	        int version = 0;
	        int numOfFieldVariableRecorded = 0;
	        int numOfFieldVariableUpdated = 0;
	        int numOfLotVariableRecorded = 0;
	        int numOfLotVariableUpdated = 0;
	        int numOfLotMigrationAdded = 0;
	        int numOfSubtypeTransactionAdded = 0;
	        int numOfLotListRecorded = 0;
	        int numOfItemRecorded = 0;
	        int numOfLotRecorded = 0;
	        int numOfLotUpdated = 0;
	        int numOfBarcodeRecorded = 0;
	        int numOfLotlistLotRecorded = 0;
	        int numOfFieldVariableDeleted = 0;
	        
	        // add new lot list and all their new lot items.
	        if (lotlistArray!=null && lotlistArray.size() > 0)
	        {
	        	for(LotList ll : lotlistArray)
				{ 
					newLotListId = da.InsertLotList(ll, serverDatabase);
					if(newLotListId > 0)
					{
						numOfLotListRecorded++;
						if(lotArray != null && lotlistlotArray != null)
						{
							newlotlistLots = da.GetSpecificLotlistLots(lotArray, lotlistlotArray, ll.getLotlistId());
							if(newlotlistLots != null && newlotlistLots.size() > 0)
							{
								for(Lot lo : newlotlistLots)
								{
									if(itemArray != null && itemArray.size() > 0)
									{
										Item lotItem = new Item();
										Item loItem = new Item();
										loItem = da.GetLotItem(itemArray, lo.getItemId());					
										
										if(loItem != null)
										{
											//check for unique item name and item type combination and ensure duplicate match are not added
											if(da.ItemExist(lotItem, serverDatabase) != 1)
											{									
												//add each and every items of each new lot list
												NewItemAccessionIdentifier = da.GetMaxAccessionIdentifier(serverDatabase) + 1;
												lotItem.setItemId(loItem.getItemId());
												lotItem.setAccessionIdentifier(NewItemAccessionIdentifier);
												lotItem.setAlternativeIdentifier(loItem.getAlternativeIdentifier());
												lotItem.setDateLastModified(loItem.getDateLastModified());
												lotItem.setName(loItem.getName());
												lotItem.setPrefix(loItem.getPrefix());
												lotItem.setVersion(version);
												lotItem.setItemTypeId(loItem.getItemTypeId());
												lotItem.setLatinName(loItem.getLatinName());
												newItemId = da.InsertItem(lotItem, serverDatabase);							
																					
												if(newItemId > 0)
												{
													numOfItemRecorded++;
													//add each and every lot of each new lot list
													newLotId = da.InsertLot(lo.getQuantity(), lo.getScale(), lo.getStatus(), version, lo.getContainerId(), newItemId, lo.getLocationId(), lo.getLastUpdatedBy(), lo.getLastUpdated(), lo.getLastUpdated(), lo.getLastUpdatedBy(), serverDatabase);
													if(newLotId > 0)
													{
														numOfLotRecorded++;
														//add bar code and lot list lot of each new lot
														long newBarcodeId = da.InsertBarcode(lo.getLastUpdated(), newLotId, serverDatabase);
														if(newBarcodeId > 0)
															numOfBarcodeRecorded++;
														
														long newlotlistlot = da.InsertLotlistLot(newLotListId, newLotId, serverDatabase);
														if (newlotlistlot > 0)
															numOfLotlistLotRecorded++;
																									
														//add all field variables of each new lot
														if (fieldVariableArray != null && fieldVariableArray.size() > 0)
														{
															lotFieldVariable = da.GetLotFieldVariables(fieldVariableArray, lo.getLotId());
															if (lotFieldVariable.size() > 0)
															{
																for(FieldVariable fv : lotFieldVariable)
																{
																	FieldVariable fvar = new FieldVariable();
																	fvar.setFieldvarId(fv.getFieldvarId());
																	fvar.setCreatedBy(fv.getCreatedBy());
																    fvar.setCreatedDate(fv.getCreatedDate());
																    fvar.setLastUpdated(fv.getLastUpdated());
																    fvar.setLastUpdatedBy(fv.getLastUpdatedBy());
																    fvar.setVersion(fv.getVersion());
																    fvar.setDate(fv.getDate());
																    fvar.setQty(fv.getQty());
																    fvar.setVar(fv.getVar());
																    fvar.setLotId(newLotId);
																    fvar.setUpdated(fv.getUpdated());
																	newlotFieldVariable.add(fvar);
																}
																numOfFieldVariableRecorded = numOfFieldVariableRecorded + da.RecordFieldVariableLists(newlotFieldVariable, serverDatabase);
															}
														}
														
														//add all lot variables of each new lot
														if (lotvariableArray != null && lotvariableArray.size() > 0)
														{
															lotLotVariable = da.GetLotLotVariables(lotvariableArray, lo.getLotId());
															if (lotLotVariable.size() > 0)
															{
																newlotLotVariable.clear();
																
																for(LotVariable lv : lotLotVariable)
																{
																	LotVariable lvar = new LotVariable();
																	lvar.setLotvariableId(lv.getLotvariableId());
																	lvar.setCreatedBy(lv.getCreatedBy());
																    lvar.setCreatedDate(lv.getCreatedDate());
																    lvar.setLastUpdated(lv.getLastUpdated());
																    lvar.setLastUpdatedBy(lv.getLastUpdatedBy());
																    lvar.setVersion(lv.getVersion());
																    lvar.setVariabledate(lv.getVariabledate());
																    lvar.setQuantity(lv.getQuantity());
																    lvar.setVariableId(lv.getVariableId());
																    lvar.setLotId(newLotId);
																    lvar.setUpdated(lv.getUpdated());
																	newlotLotVariable.add(lvar);
																}
																numOfLotVariableRecorded = numOfLotVariableRecorded + da.RecordLotVariableLists(newlotLotVariable, serverDatabase);
															}
														}
														
														//add all sub type transactions of each new lot
														if (subtypetransArray != null && subtypetransArray.size() > 0)
														{
															lotsubtypetransactions = da.GetLotSubtypeTransactions(subtypetransArray, lo.getLotId());
															if (lotsubtypetransactions.size() > 0)
															{
																newSubtypetransactions.clear();
																
																for(SubtypeTransaction stt : lotsubtypetransactions)
																{
																	SubtypeTransaction sttrans = new SubtypeTransaction();
																	sttrans.setSubtypeTransId(stt.getSubtypeTransId());
																	sttrans.setSubtype(stt.getSubtype());
																	sttrans.setLotId(newLotId);
																	sttrans.setQuantity(stt.getQuantity());
																	sttrans.setDate(stt.getDate());
																	sttrans.setScale(stt.getScale());
																	sttrans.setCreatedBy(stt.getCreatedBy());
																	sttrans.setCreatedDate(stt.getCreatedDate());
																	sttrans.setLastUpdatedBy(stt.getLastUpdatedBy());
																	sttrans.setLastUpdated(stt.getLastUpdated());
																	newSubtypetransactions.add(sttrans);
																}
																numOfSubtypeTransactionAdded = numOfSubtypeTransactionAdded + da.AddNewSubtypeTransactions(newSubtypetransactions, serverDatabase);
															}
														}													
													}
												}	
											}
										}
									}
								}
							}
						}
					}
				}	
	        }
			// add new lots to existing lot lists with all their new connected table records like field variables, lot variables, etc.
			if (lotArray != null && lotArray.size() > 0)
			{
				oldlotlistNewLots = da.GetAllOldLotlistNewLots(lotArray);
		        if(oldlotlistNewLots != null && oldlotlistNewLots.size() > 0)
				{
					for(Lot lo : oldlotlistNewLots)
					{
						if (itemArray != null && itemArray.size() > 0)
						{
							LotListLot lotll = new LotListLot();
							lotll = da.GetALotList(lotlistlotArray, lo.getLotId());
							
							Item lotItem = new Item();
							Item loItem = new Item();
							loItem = da.GetLotItem(itemArray, lo.getItemId());					
							
							if(loItem != null)
							{
								//check for unique item name and item type combination and ensure duplicate match are not added
								if(da.ItemExist(lotItem, serverDatabase) != 1)
								{									
									//add each and every items of each new lot list
									NewItemAccessionIdentifier = da.GetMaxAccessionIdentifier(serverDatabase) + 1;
									lotItem.setItemId(loItem.getItemId());
									lotItem.setAccessionIdentifier(NewItemAccessionIdentifier);
									lotItem.setAlternativeIdentifier(loItem.getAlternativeIdentifier());
									lotItem.setDateLastModified(loItem.getDateLastModified());
									lotItem.setName(loItem.getName());
									lotItem.setPrefix(loItem.getPrefix());
									lotItem.setVersion(version);
									lotItem.setItemTypeId(loItem.getItemTypeId());
									lotItem.setLatinName(loItem.getLatinName());
									newItemId = da.InsertItem(lotItem, serverDatabase);	
									
									//add each and every items of each new lot list
									if(newItemId > 0)
									{
										numOfItemRecorded++;
										//add each and every lot of each new lot list
										newLotId = da.InsertLot(lo.getQuantity(), lo.getScale(), lo.getStatus(), version, lo.getContainerId(), newItemId, lo.getLocationId(), lo.getLastUpdatedBy(), lo.getLastUpdated(), lo.getLastUpdated(), lo.getLastUpdatedBy(), serverDatabase);
										if(newLotId > 0)
										{
											numOfLotRecorded++;
											//add bar code and lot list lot of each new lot
											long newBarcodeId = da.InsertBarcode(lo.getLastUpdated(), newLotId, serverDatabase);
											if(newBarcodeId > 0)
												numOfBarcodeRecorded++;	
											long newlotlistlot = da.InsertLotlistLot(lotll.getLotlistId(), newLotId, serverDatabase);
											if (newlotlistlot > 0)
												numOfLotlistLotRecorded++;
									
											//add all field variables of each new lot
											if (fieldVariableArray!= null && fieldVariableArray.size() > 0)
											{
												lotFieldVariable.clear();
												lotFieldVariable = da.GetLotFieldVariables(fieldVariableArray, lo.getLotId());
												if (lotFieldVariable!= null && lotFieldVariable.size() > 0)
												{
													newlotFieldVariable.clear();
													
													for(FieldVariable fv : lotFieldVariable)
													{
														FieldVariable fvar = new FieldVariable();
														fvar.setFieldvarId(fv.getFieldvarId());
														fvar.setCreatedBy(fv.getCreatedBy());
													    fvar.setCreatedDate(fv.getCreatedDate());
													    fvar.setLastUpdated(fv.getLastUpdated());
													    fvar.setLastUpdatedBy(fv.getLastUpdatedBy());
													    fvar.setVersion(fv.getVersion());
													    fvar.setDate(fv.getDate());
													    fvar.setQty(fv.getQty());
													    fvar.setVar(fv.getVar());
													    fvar.setLotId(newLotId);
													    fvar.setUpdated(fv.getUpdated());
														newlotFieldVariable.add(fvar);
													}
													numOfFieldVariableRecorded = numOfFieldVariableRecorded + da.RecordFieldVariableLists(newlotFieldVariable, serverDatabase);
												}
											}

											//add all lot variables of each new lot
											if (lotvariableArray != null && lotvariableArray.size() > 0)
											{
												lotLotVariable.clear();
												lotLotVariable = da.GetLotLotVariables(lotvariableArray, lo.getLotId());
												if (lotLotVariable!=null && lotLotVariable.size() > 0)
												{
													newlotLotVariable.clear();
													
													for(LotVariable lv : lotLotVariable)
													{
														LotVariable lvar = new LotVariable();
														lvar.setLotvariableId(lv.getLotvariableId());
														lvar.setCreatedBy(lv.getCreatedBy());
													    lvar.setCreatedDate(lv.getCreatedDate());
													    lvar.setLastUpdated(lv.getLastUpdated());
													    lvar.setLastUpdatedBy(lv.getLastUpdatedBy());
													    lvar.setVersion(lv.getVersion());
													    lvar.setVariabledate(lv.getVariabledate());
													    lvar.setQuantity(lv.getQuantity());
													    lvar.setVariableId(lv.getVariableId());
													    lvar.setLotId(newLotId);
													    lvar.setUpdated(lv.getUpdated());
														newlotLotVariable.add(lvar);
													}
													numOfLotVariableRecorded = numOfLotVariableRecorded + da.RecordLotVariableLists(newlotLotVariable, serverDatabase);
												}
											}
											
											//add all sub type transactions of each new lot
											if (subtypetransArray != null && subtypetransArray.size() > 0)
											{
												lotsubtypetransactions.clear();
												lotsubtypetransactions = da.GetLotSubtypeTransactions(subtypetransArray, lo.getLotId());
												if (lotsubtypetransactions != null && lotsubtypetransactions.size() > 0)
												{
													newSubtypetransactions.clear();
													
													for(SubtypeTransaction stt : lotsubtypetransactions)
													{
														SubtypeTransaction sttrans = new SubtypeTransaction();
														sttrans.setSubtypeTransId(stt.getSubtypeTransId());
														sttrans.setSubtype(stt.getSubtype());
														sttrans.setLotId(newLotId);
														sttrans.setQuantity(stt.getQuantity());
														sttrans.setDate(stt.getDate());
														sttrans.setScale(stt.getScale());
														sttrans.setCreatedBy(stt.getCreatedBy());
														sttrans.setCreatedDate(stt.getCreatedDate());
														sttrans.setLastUpdatedBy(stt.getLastUpdatedBy());
														sttrans.setLastUpdated(stt.getLastUpdated());
														newSubtypetransactions.add(sttrans);
													}
													numOfSubtypeTransactionAdded = numOfSubtypeTransactionAdded + da.AddNewSubtypeTransactions(newSubtypetransactions, serverDatabase);
												}
											}													
										}
									}	
								}
							}
						}
					}
				}
			}
			
	        // update all device updated lots
			if(lotArray != null && lotArray.size() > 0)
			{
				allDeviceUpdatedLots = da.GetAllDeviceUpdatedLots(lotArray);
		        if(allDeviceUpdatedLots != null && allDeviceUpdatedLots.size() > 0)
		        	numOfLotUpdated = numOfLotUpdated + da.UpdateLots(allDeviceUpdatedLots, serverDatabase);
			}
			
	        //delete all device deleted field variables
	        if(deletedFieldVariableArray != null && deletedFieldVariableArray.size() > 0)
	        	numOfFieldVariableDeleted = da.DeleteFieldVariableLists(deletedFieldVariableArray, serverDatabase);
	        
	        
	        if (fieldVariableArray != null && fieldVariableArray.size() > 0)
	        {
	        	//add all existing lots new field variables
	        	existinglotNewFieldVariables = da.GetAllExistingLotNewFieldVariables(fieldVariableArray);
		        if(existinglotNewFieldVariables != null && existinglotNewFieldVariables.size() > 0)
			        numOfFieldVariableRecorded = numOfFieldVariableRecorded + da.RecordFieldVariableLists(existinglotNewFieldVariables, serverDatabase);
		        
		        //update all device updated field variables
		        updatedFieldVariables = da.GetAllDeviceUpdatedFieldVariables(fieldVariableArray);
		        if(updatedFieldVariables != null && updatedFieldVariables.size() > 0)
		        	numOfFieldVariableUpdated = numOfFieldVariableUpdated + da.UpdateFieldVariableLists(updatedFieldVariables, serverDatabase);
		    }
	        
	        if (lotvariableArray != null && lotvariableArray.size() > 0)
	        {
	        	//add all existing lots new lot variables
		        existinglotNewLotVariables = da.GetAllExistingLotNewLotVariables(lotvariableArray);
		        if(existinglotNewLotVariables.size() > 0)
		        	numOfLotVariableRecorded = numOfLotVariableRecorded + da.RecordLotVariableLists(existinglotNewLotVariables, serverDatabase);
		        
		        //update all device updated lot variables
		        updatedLotVariables = da.GetAllDeviceUpdatedLotVariables(lotvariableArray);
		        if(updatedLotVariables.size() > 0)
		        	numOfLotVariableUpdated = numOfLotVariableUpdated + da.UpdateLotVariableLists(updatedLotVariables, serverDatabase);
	        }
	        
	        //add all existing lots new lot sub type transactions
	        if(subtypetransArray != null && subtypetransArray.size() > 0)
	        {
	        	existinglotNewSubtypeTransactions = da.GetAllExistingLotNewSubtypeTransactions(subtypetransArray);
		        if(existinglotNewSubtypeTransactions != null && existinglotNewSubtypeTransactions.size() > 0)
		        	numOfSubtypeTransactionAdded = numOfSubtypeTransactionAdded + da.AddNewSubtypeTransactions(existinglotNewSubtypeTransactions, serverDatabase);
	        }
	        
	        //add all existing lots new migrations
	        if (migrationArray != null && migrationArray.size() > 0)
	        {
	        	existinglotNewMigrations = da.GetAllExistingLotNewMigrations(migrationArray);
		        if(existinglotNewMigrations != null && existinglotNewMigrations.size() > 0)
		        {
		        	newLotMigrations.clear();
		        	
		        	for(Migration mig : existinglotNewMigrations)
		        	{
		        		String newLocationName = da.GetLocationNameDetail(getLocations(serverDatabase), mig.getNewLocationId());
						String oldLocationName = da.GetLocationNameDetail(getLocations(serverDatabase), mig.getOldLocationId());
		        	    
		        		Migration lm = new Migration();
		        		lm.setMigId(mig.getMigId());
		        		lm.setMigrationDate(mig.getMigrationDate());
		        		lm.setNewLocationId(mig.getNewLocationId());
		        		lm.setNewLocationName(newLocationName);
		        		lm.setOldLocationId(mig.getOldLocationId());
		        		lm.setOldLocationName(oldLocationName);
		        		lm.setLotId(mig.getLotId());
		        		lm.setReason(mig.getReason());
						lm.setCreatedBy(mig.getCreatedBy());
					    lm.setCreatedDate(mig.getCreatedDate());
					    lm.setLastUpdated(mig.getLastUpdated());
					    lm.setLastUpdatedBy(mig.getLastUpdatedBy());
					    newLotMigrations.add(lm);
		        	}
		        	numOfLotMigrationAdded = numOfLotMigrationAdded + da.AddNewLotMigrations(newLotMigrations, serverDatabase);
		        }
	        }
	        
	        sb.append("Number of Lot List records added:" + numOfLotListRecorded +"\r\n ");
	        sb.append("Number of Item records added:" + numOfItemRecorded +"\r\n ");
	        sb.append("Number of Barcode records added:" + numOfBarcodeRecorded +"\r\n ");
	        sb.append("Number of Lot List Lot records added:" + numOfLotlistLotRecorded +"\r\n ");
	        sb.append("Number of Lot records added:" + numOfLotRecorded +"\r\n ");
	        sb.append("Number of Lot records updated:" + numOfLotUpdated +"\r\n ");
	        sb.append("Number of Field Variable records added:" + numOfFieldVariableRecorded +"\r\n ");
	        sb.append("Number of Field Variable records updated:" + numOfFieldVariableUpdated +"\r\n ");
	        sb.append("Number of Field Variable records deleted:" + numOfFieldVariableDeleted +"\r\n ");
	        sb.append("Number of Lot Variable records added:" + numOfLotVariableRecorded +"\r\n ");
	        sb.append("Number of Lot Variable records updated:" + numOfLotVariableUpdated +"\r\n ");
	        sb.append("Number of Lot Migration records added:" + numOfLotMigrationAdded +"\r\n ");
	        sb.append("Number of Lot Subtype Transaction records added:" + numOfSubtypeTransactionAdded +"\r\n ");
	        
			result.setWasSuccessful("1");
			result.setException(sb.toString());	
		} 
		catch (Exception e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
		finally 
		{
			try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return result;
	}

	public ArrayList<Lot> getLots(String username, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement(); 
        rs = stmt.executeQuery("SELECT l.id,l.item,l.container,l.location,b.id as barcode,i.name, lo.name as locationDetail, l.quantity,l.status,l.version,l.scale,l.lastUpdated,l.lastUpdatedBy FROM Lot l INNER JOIN BarCode b ON l.id = b.lotId INNER JOIN Item i ON l.item = i.id INNER JOIN Location lo ON l.location = lo.id LEFT JOIN LotListLots lll ON l.id = lll.element LEFT JOIN LotList ll ON lll.LotList_id = ll.id Where ll.createdBy = '" + username + "'");
             
		ArrayList<Lot> lotList = new ArrayList<Lot>();
		try
		{
			while(rs.next())
			{
				Lot lotObj = new Lot();
				lotObj.setLotId(rs.getLong("id"));
				lotObj.setItemId(rs.getLong("item"));
				lotObj.setContainerId(rs.getLong("container"));
				lotObj.setLocationId(rs.getLong("location"));
				lotObj.setBarcode(rs.getLong("barcode"));
				lotObj.setItemName(rs.getString("name"));
				lotObj.setLocationDetail(rs.getString("locationDetail"));
				lotObj.setQuantity(rs.getDouble("quantity"));
				lotObj.setStatus(rs.getInt("status"));
				lotObj.setVersion(rs.getInt("version"));
				lotObj.setScale(rs.getString("scale"));
				lotObj.setLastUpdated(rs.getString("lastUpdated"));
				lotObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				lotObj.setUpdated(updated);
				lotList.add(lotObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return lotList;
	}

	public ArrayList<Lot> getASpecificLotListLots(int LotListId, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement(); 
        rs = stmt.executeQuery("SELECT l.id,l.item,l.container,l.location,b.id as barcode,i.name, lo.name as locationDetail, l.quantity,l.status,l.version,l.scale,l.lastUpdated,l.lastUpdatedBy FROM Lot l INNER JOIN BarCode b ON b.lotId = l.id JOIN Item i ON i.id = l.item INNER JOIN Location lo ON lo.id = l.location INNER JOIN LotListLots lol ON lol.element = l.id WHERE lol.LotList_id = '" + LotListId + "' ORDER BY l.id DESC");
        
		ArrayList<Lot> lotList = new ArrayList<Lot>();
		try
		{
			while(rs.next())
			{
				Lot lotObj = new Lot();
				lotObj.setLotId(rs.getLong("id"));
				lotObj.setItemId(rs.getLong("item"));
				lotObj.setContainerId(rs.getLong("container"));
				lotObj.setLocationId(rs.getLong("location"));
				lotObj.setBarcode(rs.getLong("barcode"));
				lotObj.setItemName(rs.getString("name"));
				lotObj.setLocationDetail(rs.getString("locationDetail"));
				lotObj.setQuantity(rs.getDouble("quantity"));
				lotObj.setStatus(rs.getInt("status"));
				lotObj.setVersion(rs.getInt("version"));
				lotObj.setScale(rs.getString("scale"));
				lotObj.setLastUpdated(rs.getString("lastUpdated"));
				lotObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				lotObj.setUpdated(updated);
				lotList.add(lotObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return lotList;
	}

	public ArrayList<Location> getParentLocations(String username, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();  
        //String query = "SELECT loc.id, loc.name, loc.version, loc.parentId, (Select locat.name from Location locat Where locat.id = loc.parentId) as parentName Location loc INNER JOIN (SELECT distinct(lo.parentId) from Location lo INNER JOIN Lot l ON lo.id = l.location INNER JOIN LotListLots lll ON l.id = lll.element INNER JOIN LotList ll ON lll.LotList_id = ll.id Where ll.createdBy = '" + username + "') AS PLoc ON loc.id = PLoc.parentId";
        String query = "SELECT loc.id, loc.locationType, loc.name, loc.version, loc.parentId, (Select locat.name from Location locat Where locat.id = loc.parentId) as parentName from Location loc INNER JOIN (SELECT distinct(lo.parentId) from Location lo INNER JOIN Lot l ON lo.id = l.location INNER JOIN LotListLots lll ON l.id = lll.element INNER JOIN LotList ll ON lll.LotList_id = ll.id Where ll.createdBy ='" + username + "') AS PLoc ON loc.id = PLoc.parentId";  
		rs = stmt.executeQuery(query);
        ArrayList<Location> locationList = new ArrayList<Location>();
		try
		{
			while(rs.next())
			{
				Location locatnObj = new Location();
				locatnObj.setLocationId(rs.getLong("id"));
				locatnObj.setLocationType(rs.getString("parentName") + ">>" + rs.getString("name"));
				locatnObj.setName(rs.getString("name"));
				locatnObj.setVersion(rs.getInt("version"));
				locatnObj.setParentId(rs.getLong("parentId"));
				locationList.add(locatnObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return locationList; 
	}

	public ArrayList<Location> getChildLocations(String username, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();  
        String query = "SELECT loc.id, loc.locationType, loc.name, loc.version, loc.parentId, (Select locat.name from Location locat Where locat.id = loc.parentId) as parentName from Location loc INNER JOIN (SELECT distinct(lo.parentId) from Location lo INNER JOIN Lot l ON lo.id = l.location INNER JOIN LotListLots lll ON l.id = lll.element INNER JOIN LotList ll ON lll.LotList_id = ll.id Where ll.createdBy ='" + username + "') AS PLoc ON loc.parentId = PLoc.parentId";  
		rs = stmt.executeQuery(query);
        ArrayList<Location> locationList = new ArrayList<Location>();
		try
		{
			while(rs.next())
			{
				Location locatnObj = new Location();
				locatnObj.setLocationId(rs.getLong("id"));
				locatnObj.setLocationType(rs.getString("parentName") + ">>" + rs.getString("name"));
				locatnObj.setName(rs.getString("name"));
				locatnObj.setVersion(rs.getInt("version"));
				locatnObj.setParentId(rs.getLong("parentId"));
				locationList.add(locatnObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return locationList; 
	}

	public ArrayList<Location> getAparentChildLocations(int parentId, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();          
		rs = stmt.executeQuery("SELECT loc.id, loc.name, (Select locat.name from Location locat Where locat.id = loc.parentId) as parentName, loc.version, loc.parentId from Location loc Where parentId ='" + parentId + "'"); 
        ArrayList<Location> locationList = new ArrayList<Location>();
		try
		{
			while(rs.next())
			{
				Location locatnObj = new Location();
				locatnObj.setLocationId(rs.getLong("id"));
				locatnObj.setLocationType(rs.getString("parentName") + ">>" + rs.getString("name"));
				locatnObj.setName(rs.getString("name"));
				locatnObj.setVersion(rs.getInt("version"));
				locatnObj.setParentId(rs.getLong("parentId"));
				locationList.add(locatnObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return locationList; 
	}

	public ArrayList<Location> getLocations(String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();          
        rs = stmt.executeQuery("SELECT * FROM Location");
          
		ArrayList<Location> locationList = new ArrayList<Location>();
		try
		{
			while(rs.next())
			{
				Location locatnObj = new Location();
				locatnObj.setLocationId(rs.getLong("id"));
				locatnObj.setLocationType(rs.getString("locationType"));
				locatnObj.setName(rs.getString("name"));
				locatnObj.setVersion(rs.getInt("version"));
				locatnObj.setParentId(rs.getLong("parentId"));
				locationList.add(locatnObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return locationList; 
	}

	public ArrayList<Subtype> getSubtypes(String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();          
        rs = stmt.executeQuery("SELECT distinct(subtype) FROM Transaction2");
          
		ArrayList<Subtype> stList = new ArrayList<Subtype>();
		try
		{
			while(rs.next())
			{
				Subtype stObj = new Subtype();
				stObj.setName(rs.getString("subtype"));
				stList.add(stObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return stList;
	}

	public ArrayList<SubtypeTransaction> getSubtypeTransactions(String username, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();  
        rs = stmt.executeQuery("SELECT * from inventory.Transaction2 t LEFT JOIN LotListLots lll ON t.lot = lll.element LEFT JOIN LotList ll ON lll.LotList_id = ll.id Where ll.createdBy = '" + username + "'");
		ArrayList<SubtypeTransaction> sttList = new ArrayList<SubtypeTransaction>();
		try
		{
			while(rs.next())
			{
				SubtypeTransaction sttObj = new SubtypeTransaction();
				sttObj.setSubtypeTransId(rs.getLong("id"));
				sttObj.setSubtype(rs.getString("subtype"));
				sttObj.setLotId(rs.getLong("lot"));
				sttObj.setQuantity(rs.getDouble("quantity"));
				sttObj.setDate(rs.getString("date"));
				sttObj.setScale(rs.getString("scale"));
				sttObj.setCreatedBy(rs.getString("createdBy"));
				sttObj.setCreatedDate(rs.getString("createdDate"));
				sttObj.setLastUpdated(rs.getString("lastUpdated"));
				sttObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				sttList.add(sttObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return sttList;
	}

	public ArrayList<SubtypeTransaction> getALotSubtypeTransactions(int LotId, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement(); 
        rs = stmt.executeQuery("SELECT * FROM Transaction2 WHERE lot = '" + LotId + "' ORDER BY date DESC");
        
        ArrayList<SubtypeTransaction> sttList = new ArrayList<SubtypeTransaction>();
		try
		{
			while(rs.next())
			{
				SubtypeTransaction sttObj = new SubtypeTransaction();
				sttObj.setSubtypeTransId(rs.getLong("id"));
				sttObj.setSubtype(rs.getString("subtype"));
				sttObj.setLotId(rs.getLong("lot"));
				sttObj.setQuantity(rs.getDouble("quantity"));
				sttObj.setDate(rs.getString("date"));
				sttObj.setScale(rs.getString("scale"));
				sttObj.setCreatedBy(rs.getString("createdBy"));
				sttObj.setCreatedDate(rs.getString("createdDate"));
				sttObj.setLastUpdated(rs.getString("lastUpdated"));
				sttObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				sttList.add(sttObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return sttList;
	}

	public ArrayList<LotVariable> getLotVariables(String username, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();   
        rs = stmt.executeQuery("SELECT lv.id, lv.createdBy, lv.createdDate, lv.lastUpdated, lv.lastUpdatedBy, lv.version, lv.version, lv.quantity, lv.variabledate, lv.lot, lv.variable, v.name from LotVariable lv INNER JOIN Variables v ON v.id = lv.variable LEFT JOIN LotListLots lll ON lv.lot = lll.element LEFT JOIN LotList ll ON lll.LotList_id = ll.id Where ll.createdBy = '" + username + "'");
         
		ArrayList<LotVariable> lotvariableList = new ArrayList<LotVariable>();
		try
		{
			while(rs.next())
			{
				LotVariable lotvariabObj = new LotVariable();
				lotvariabObj.setLotvariableId(rs.getLong("id"));
				lotvariabObj.setCreatedBy(rs.getString("createdBy"));
				lotvariabObj.setCreatedDate(rs.getString("createdDate"));
				lotvariabObj.setLastUpdated(rs.getString("lastUpdated"));
				lotvariabObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				lotvariabObj.setVersion(rs.getInt("version"));
				lotvariabObj.setQuantity(rs.getDouble("quantity"));
				lotvariabObj.setVariabledate(rs.getString("variabledate"));
				lotvariabObj.setLotId(rs.getLong("lot"));
				lotvariabObj.setVariableId(rs.getLong("variable"));
				lotvariabObj.setVariableName(rs.getString("name"));
				lotvariabObj.setUpdated(updated);
				lotvariableList.add(lotvariabObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return lotvariableList;
	}

	public ArrayList<LotList> getLotLists(String username, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT * from LotList Where createdBy ='" + username + "'");
          
		ArrayList<LotList> lotlstList = new ArrayList<LotList>();
		try
		{
			while(rs.next())
			{
				LotList lotlistObj = new LotList();
				lotlistObj.setLotlistId(rs.getLong("id"));
				lotlistObj.setCreatedBy(rs.getString("createdBy"));
				lotlistObj.setCreatedDate(rs.getString("createdDate"));
				lotlistObj.setLastUpdated(rs.getString("lastUpdated"));
				lotlistObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				lotlistObj.setName(rs.getString("name"));
				lotlistObj.setOwner_id(rs.getLong("owner_id"));
				lotlstList.add(lotlistObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return lotlstList;
	}

	public ArrayList<LotListLot> getLotListLots(String username, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();   
        rs = stmt.executeQuery("Select * from LotListLots lll LEFT JOIN LotList ll ON lll.LotList_id = ll.id Where ll.createdBy = '" + username + "'");
         
		ArrayList<LotListLot> lllList = new ArrayList<LotListLot>();
		try
		{
			while(rs.next())
			{
				LotListLot lll = new LotListLot();
				lll.setLotlistId(rs.getLong("LotList_id"));
				lll.setLotId(rs.getLong("element"));
				lllList.add(lll);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return lllList;
	}

	public ArrayList<FieldVariable> getFieldVariables(String username, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();
        rs = stmt.executeQuery("SELECT * from FieldVariables fv LEFT JOIN LotListLots lll ON fv.lot_id = lll.element LEFT JOIN LotList ll ON lll.LotList_id = ll.id Where ll.createdBy = '" + username + "'"); 
		ArrayList<FieldVariable> fieldVariableList = new ArrayList<FieldVariable>();
		try
		{
			while(rs.next())
			{
				FieldVariable fieldVarObj = new FieldVariable();
				fieldVarObj.setFieldvarId(rs.getLong("id"));
				fieldVarObj.setCreatedBy(rs.getString("createdBy"));
				fieldVarObj.setCreatedDate(rs.getString("createdDate"));
				fieldVarObj.setLastUpdated(rs.getString("lastUpdated"));
				fieldVarObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				fieldVarObj.setVersion(rs.getInt("version"));
				fieldVarObj.setDate(rs.getString("date"));
				fieldVarObj.setQty(rs.getString("qty"));
				fieldVarObj.setVar(rs.getString("var"));
				fieldVarObj.setLotId(rs.getLong("lot_id"));
				fieldVarObj.setUpdated(updated);
				fieldVariableList.add(fieldVarObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return fieldVariableList;
	}

	public ArrayList<Variable> getVariables(String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT * from Variables");
          
		ArrayList<Variable> varList = new ArrayList<Variable>();
		try
		{
			while(rs.next())
			{
				Variable var = new Variable();
				var.setVarId(rs.getLong("id"));
				var.setCreatedBy(rs.getString("createdBy"));
				var.setCreatedDate(rs.getString("createdDate"));
				var.setLastUpdated(rs.getString("lastUpdated"));
				var.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				var.setName(rs.getString("name"));
				varList.add(var);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return varList;
	}

	public ArrayList<ContainerType> getContainerTypes(String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT * from ContainerType");
          
		ArrayList<ContainerType> ctList = new ArrayList<ContainerType>();
		try
		{
			while(rs.next())
			{
				ContainerType ct = new ContainerType();
				ct.setContainertypeId(rs.getLong("id"));
				ct.setName(rs.getString("name"));
				ct.setVersion(rs.getInt("version"));
				ctList.add(ct);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return ctList;
	}

	public ArrayList<ItemType> getItemTypes(String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT * from ItemType");
          
		ArrayList<ItemType> itList = new ArrayList<ItemType>();
		try
		{
			while(rs.next())
			{
				ItemType it = new ItemType();
				it.setItemtypeId(rs.getLong("id"));
				it.setName(rs.getString("name"));
				it.setVersion(rs.getInt("version"));
				it.setShortName(rs.getString("shortName"));
				itList.add(it);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return itList;
	}

	public ArrayList<Migration> getMigrations(String username, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();    
        rs = stmt.executeQuery("SELECT * from Migration mig LEFT JOIN LotListLots lll ON mig.lot_id = lll.element LEFT JOIN LotList ll ON lll.LotList_id = ll.id Where ll.createdBy = '" + username + "'"); 
		ArrayList<Migration> migList = new ArrayList<Migration>();
		try
		{
			while(rs.next())
			{
				Migration mig = new Migration();
				mig.setMigId(rs.getLong("id"));
				mig.setMigrationDate(rs.getString("migrationDate"));
				mig.setNewLocationId(rs.getLong("newLocationId"));
				mig.setNewLocationName(rs.getString("newLocationName"));
				mig.setOldLocationId(rs.getLong("oldLocationId"));
				mig.setOldLocationName(rs.getString("oldLocationName"));
				mig.setLotId(rs.getLong("lot_id"));
				mig.setReason(rs.getString("reason"));
				mig.setCreatedBy(rs.getString("createdBy"));
				mig.setCreatedDate(rs.getString("createdDate"));
				mig.setLastUpdated(rs.getString("lastUpdated"));
				mig.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				migList.add(mig);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return migList;
	}

	public ArrayList<Migration> getAlotMigrations(int LotId, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement(); 
        rs = stmt.executeQuery("SELECT * FROM Migration WHERE lot_id = '" + LotId + "' ORDER BY migrationDate DESC");
        
		ArrayList<Migration> migList = new ArrayList<Migration>();
		try
		{
			while(rs.next())
			{
				Migration mig = new Migration();
				mig.setMigId(rs.getLong("id"));
				mig.setMigrationDate(rs.getString("migrationDate"));
				mig.setNewLocationId(rs.getLong("newLocationId"));
				mig.setNewLocationName(rs.getString("newLocationName"));
				mig.setOldLocationId(rs.getLong("oldLocationId"));
				mig.setOldLocationName(rs.getString("oldLocationName"));
				mig.setLotId(rs.getLong("lot_id"));
				mig.setReason(rs.getString("reason"));
				mig.setCreatedBy(rs.getString("createdBy"));
				mig.setCreatedDate(rs.getString("createdDate"));
				mig.setLastUpdated(rs.getString("lastUpdated"));
				mig.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				migList.add(mig);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return migList;
	}

	public ArrayList<FieldVariable> getlotfieldVariables(int LotId, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT * FROM FieldVariables WHERE lot_id = '" + LotId + "'");
          
		ArrayList<FieldVariable> fieldVariableList = new ArrayList<FieldVariable>();
		try
		{
			while(rs.next())
			{
				FieldVariable fieldVarObj = new FieldVariable();
				fieldVarObj.setFieldvarId(rs.getLong("id"));
				fieldVarObj.setCreatedBy(rs.getString("createdBy"));
				fieldVarObj.setCreatedDate(rs.getString("createdDate"));
				fieldVarObj.setLastUpdated(rs.getString("lastUpdated"));
				fieldVarObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				fieldVarObj.setVersion(rs.getInt("version"));
				fieldVarObj.setDate(rs.getString("date"));
				fieldVarObj.setQty(rs.getString("qty"));
				fieldVarObj.setVar(rs.getString("var"));
				fieldVarObj.setLotId(rs.getLong("lot_id"));
				fieldVarObj.setUpdated(updated);
				fieldVariableList.add(fieldVarObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return fieldVariableList;
	}

	public ArrayList<LotVariable> getlotVariables(int LotId, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT lv.id, lv.createdBy, lv.createdDate, lv.lastUpdated, lv.lastUpdatedBy, lv.version, lv.version, lv.quantity, lv.variabledate, lv.lot, lv.variable, v.name from LotVariable lv JOIN Variables v ON v.id = lv.variable WHERE lv.lot = '" + LotId + "'");
          
		ArrayList<LotVariable> lotvariableList = new ArrayList<LotVariable>();
		try
		{
			while(rs.next())
			{
				LotVariable lotvariabObj = new LotVariable();
				lotvariabObj.setLotvariableId(rs.getLong("id"));
				lotvariabObj.setCreatedBy(rs.getString("createdBy"));
				lotvariabObj.setCreatedDate(rs.getString("createdDate"));
				lotvariabObj.setLastUpdated(rs.getString("lastUpdated"));
				lotvariabObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				lotvariabObj.setVersion(rs.getInt("version"));
				lotvariabObj.setQuantity(rs.getDouble("quantity"));
				lotvariabObj.setVariabledate(rs.getString("variabledate"));
				lotvariabObj.setLotId(rs.getLong("lot"));
				lotvariabObj.setVariableId(rs.getLong("variable"));
				lotvariabObj.setVariableName(rs.getString("name"));
				lotvariabObj.setUpdated(updated);
				lotvariableList.add(lotvariabObj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return lotvariableList;
	}
		
	public Users getUserLoginStatus(Users userdetail, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        
        String date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        
        Users userDetail = new Users();
		try
		{
	        rs = stmt.executeQuery("SELECT * FROM User WHERE username = '" + userdetail.getUsername() + "' AND password=" + "'" + userdetail.getPassword() + "'");
	        
            if(rs.next())
			{
            	//use user name and login status for error handling
            	userDetail.setUsername("You are successfully logged in");
				userDetail.setPassword("");
				userDetail.setLoginStatus(1); 
				userDetail.setId(rs.getInt("id"));
            	stmt.executeUpdate("UPDATE User Set lastLogin = '" + date + "' WHERE username =" + "'" + userdetail.getUsername() + "'");            	
			}    
			else
			{
				rs = stmt.executeQuery("SELECT * FROM User WHERE username = '" + userdetail.getUsername() + "'");
				if(rs.next())
				{
					stmt.executeUpdate("UPDATE User Set lastLoginFailed = '" + date + "' WHERE username =" + "'" + userdetail.getUsername() + "'");
					userDetail.setUsername("Incorrect Password");
					userDetail.setPassword("");
					userDetail.setLoginStatus(-1); 
					userDetail.setId(0);
				}   
				else
				{
					userDetail.setUsername("Username does not exist");
					userDetail.setPassword("");
					userDetail.setLoginStatus(-2); 
					userDetail.setId(0);
				} 
			}
			//close connection
		    rs.close();
		    stmt.close();
		} 
		catch (SQLException e)
		{
			userDetail.setUsername(e.getMessage());
			userDetail.setPassword("");
			userDetail.setLoginStatus(0); 
			userDetail.setId(0);
		}
		return userDetail;
	}
	
	public wsSQLResult UpdateLotDetail(Lot lot, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
                
        wsSQLResult result = new wsSQLResult();
		try
		{
	        rs = stmt.executeQuery("SELECT * FROM Lot WHERE id = '" + lot.getLotId() + "'");
	        
            if(rs.next())
			{
	            rs = stmt.executeQuery("SELECT * FROM Lot WHERE id = '" + lot.getLotId() + "' AND version =  '" + lot.getVersion() + "'");
            	if(rs.next())
    			{
            		int newVersion = lot.getVersion() + 1;
                	stmt.executeUpdate("UPDATE Lot Set quantity = '" + lot.getQuantity() + "', version ='" + newVersion + "', lastUpdated ='" + lot.getLastUpdated() + "', lastUpdatedBy ='" + lot.getLastUpdatedBy() + "' WHERE id ='" + lot.getLotId() + "'");
    				result.setWasSuccessful("1");
    	            result.setException("Server Lot quantity was successfully updated");
    			}
            	else
            	{
            		result.setWasSuccessful("-2");
    	            result.setException("Unable to update selected lot record; a latest version have been updated");
            	}
			}    
			else
			{
				result.setWasSuccessful("-1");
				result.setException("The selected lot was not found on the server");
			}
			//close connection
		    rs.close();
		    stmt.close();
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
		return result;
	}

	public wsSQLResult deleteAfieldVariable(FieldVariable fv, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
                
        wsSQLResult result = new wsSQLResult();
		try
		{
	        rs = stmt.executeQuery("SELECT * FROM FieldVariables WHERE id = '" + fv.getFieldvarId() + "'");
	        
            if(rs.next())
			{
            	stmt.executeUpdate("DELETE FROM FieldVariables WHERE id = '" + fv.getFieldvarId() + "'");
				result.setWasSuccessful("1");
	            result.setException("The selected field variable was successfully deleted from the server");
			}    
			else
			{
				result.setWasSuccessful("-1");
				result.setException("The selected field variable was not found on the server");
			}
			//close connection
		    rs.close();
		    stmt.close();
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
		return result;
	}

	public wsSQLResult deleteALotList(LotList ll, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
                
        wsSQLResult result = new wsSQLResult();
		try
		{
	        rs = stmt.executeQuery("SELECT * FROM LotListLots WHERE LotList_id = '" + ll.getLotlistId() + "'");
	        
            if(rs.next())
			{
            	stmt.executeUpdate("DELETE FROM LotListLots WHERE LotList_id = '" + ll.getLotlistId() + "'");
            	stmt.executeUpdate("DELETE FROM LotList WHERE id = '" + ll.getLotlistId() + "'");
				result.setWasSuccessful("1");
	            result.setException("The selected lot list was successfully deleted from the server");
			}    
			else
			{
				result.setWasSuccessful("-1");
				result.setException("The selected lot list was not found on the server");
			}
			//close connection
		    rs.close();
		    stmt.close();
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
		return result;
	}

	public wsSQLResult deleteAlotVariable(LotVariable lv, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
                
        wsSQLResult result = new wsSQLResult();
		try
		{
	        rs = stmt.executeQuery("SELECT * FROM LotVariable WHERE id = '" + lv.getLotvariableId() + "'");
	        
            if(rs.next())
			{
            	stmt.executeUpdate("DELETE FROM LotVariable WHERE id = '" + lv.getLotvariableId() + "'");
				result.setWasSuccessful("1");
	            result.setException("The selected lot variable was successfully deleted from the server");
			}    
			else
			{
				result.setWasSuccessful("-1");
				result.setException("The selected lot variable was not found on the server");
			}
			//close connection
		    rs.close();
		    stmt.close();
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
		return result;
	}

	public wsSQLResult deleteALotFieldVariables(Lot lot, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
                
        wsSQLResult result = new wsSQLResult();
		try
		{
	        rs = stmt.executeQuery("SELECT * FROM FieldVariables WHERE lot_id = '" + lot.getLotId() + "'");
	        
            if(rs.next())
			{
            	stmt.executeUpdate("DELETE FROM FieldVariables WHERE lot_id = '" + lot.getLotId() + "'");      	
				result.setWasSuccessful("1");
	            result.setException("The selected lot Field variables were successfully deleted from the server");
			}    
			else
			{
				result.setWasSuccessful("-1");
				result.setException("There were no field variables found on the server for the selected lot");
			}
			//close connection
		    rs.close();
		    stmt.close();
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
		return result;
	}

	public wsSQLResult deleteLocation(Location lo, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
                
        wsSQLResult result = new wsSQLResult();
		try
		{
	        rs = stmt.executeQuery("SELECT * FROM Location WHERE id = '" + lo.getLocationId() + "'");
	        
            if(rs.next())
			{
            	stmt.executeUpdate("DELETE FROM Location WHERE id = '" + lo.getLocationId() + "'");
				result.setWasSuccessful("1");
	            result.setException("The selected location was successfully deleted from the server");
			}    
			else
			{
				result.setWasSuccessful("-1");
				result.setException("The selected location was not found on the server");
			}
			//close connection
		    rs.close();
		    stmt.close();
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
		return result;
	}

	public wsSQLResult recordFieldVariable(FieldVariable fv, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();            
        wsSQLResult result = new wsSQLResult();
		try
		{
			String query = "INSERT INTO FieldVariables (createdBy, createdDate, lastUpdated, lastUpdatedBy, version, date, qty, var, lot_id) VALUES ('" + fv.getCreatedBy() + "','" + fv.getCreatedDate() + "','" + fv.getLastUpdated() + "','" + fv.getLastUpdatedBy() + "','" + fv.getVersion() + "','" + fv.getDate() + "','" + fv.getQty() + "','" + fv.getVar() + "','" + fv.getLotId() + "')";
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);      	
			rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				result.setWasSuccessful("1");
				result.setException("The field variable was successfully recorded on the server ");
			}
			else
			{
				result.setWasSuccessful("-1");
		        result.setException("The server failed to record the field variable");
			}
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
        
		//close connection
		stmt.close();
		return result;
	}

	public wsSQLResult recordLotVariable(LotVariable lv, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();            
        wsSQLResult result = new wsSQLResult();
		try
		{
			String query = "INSERT INTO LotVariable (createdBy, createdDate, lastUpdated, lastUpdatedBy, version, variabledate, quantity, variable, lot) VALUES ('" + lv.getCreatedBy() + "','" + lv.getCreatedDate() + "','" + lv.getLastUpdated() + "','" + lv.getLastUpdatedBy() + "','" + lv.getVersion() + "','" + lv.getVariabledate() + "','" + lv.getQuantity() + "','" + lv.getVariableId() + "','" + lv.getLotId() + "')";
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);      	
			rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				result.setWasSuccessful("1");
				result.setException("The lot variable was successfully recorded on the server ");
			}
			else
			{
				result.setWasSuccessful("-1");
		        result.setException("The server failed to record the lot variable");
			}
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
        
		//close connection
		stmt.close();
		return result;
	}

	public wsSQLResult recordSubtypeTransaction(SubtypeTransaction stt, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();            
        wsSQLResult result = new wsSQLResult();
		try
		{
			String locQuery = "INSERT INTO Transaction2 (subtype, lot, quantity, date, scale, createdBy, createdDate, lastUpdated, lastUpdatedBy) VALUES ('" + stt.getSubtype() + "','" + stt.getLotId() + "','" + stt.getQuantity() + "','" + stt.getDate() + "','" + stt.getScale() + "','" + stt.getCreatedBy() + "','" + stt.getCreatedDate() + "','" + stt.getLastUpdated() + "','" + stt.getLastUpdatedBy() +"')";      	
            stmt.executeUpdate(locQuery, Statement.RETURN_GENERATED_KEYS);      	
			rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				result.setWasSuccessful("1");
				result.setException("The subtype transaction was successfully added on the server");
			}
			else
			{
				result.setWasSuccessful("-1");
		        result.setException("The server failed to add the subtype transaction");
			}
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
        
		//close connection
		stmt.close();
		return result;
	}

	public wsSQLResult addNewLotlistItemLot(LotlistItemLot llil, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();   
              
        long newItemId = 0, newLotId = 0;  
        long NewItemAccessionIdentifier = 0;
        int status = 1;
		int version = 0;
        
        wsSQLResult result = new wsSQLResult();
		try
		{
	        rs = stmt.executeQuery("SELECT MAX(accessionIdentifier) as MaxAccessionIdentifier FROM Item");
			if(rs.next())
			{
				NewItemAccessionIdentifier = rs.getLong("MaxAccessionIdentifier") + 1;
				String itemQuery = "INSERT INTO Item (accessionIdentifier, alternativeIdentifier, dateLastModified, name, prefix, version, itemType, latinName) VALUES ('" + NewItemAccessionIdentifier + "','" + llil.getAlternativeIdentifier() + "','" + llil.getLastUpdated() + "','" + llil.getItemName() + "','" + llil.getPrefix() + "','" + version + "','" + llil.getItemTypeId() + "','" + llil.getLatinName() + "')";
				stmt.executeUpdate(itemQuery, Statement.RETURN_GENERATED_KEYS);      	
				rs = stmt.getGeneratedKeys();
				if(rs.next())
				{
					newItemId = rs.getLong(1);
					String lotQuery = "INSERT INTO Lot (quantity, scale, status, version, container, item, location, createdBy, createdDate, lastUpdated, lastUpdatedBy) VALUES ('" + llil.getQuantity() + "','" + llil.getScale() + "','" + status + "','" + version + "','" + llil.getContainerId() + "','" + newItemId + "','" + llil.getLocationId() + "','" + llil.getLastUpdatedBy() + "','" + llil.getLastUpdated() + "','" + llil.getLastUpdated() + "','" + llil.getLastUpdatedBy() + "')";
					stmt.executeUpdate(lotQuery, Statement.RETURN_GENERATED_KEYS);      	
					rs = stmt.getGeneratedKeys();
					if(rs.next())
					{
						newLotId = rs.getLong(1);
						stmt.executeUpdate("INSERT INTO BarCode (dateAssigned, lotId) VALUES ('" + llil.getLastUpdated() + "','" + newLotId + "')"); 
						stmt.executeUpdate("INSERT INTO LotListLots (LotList_id, element) VALUES ('" + llil.getLotlistId() + "','" + newLotId + "')"); 
					}
				}
				result.setWasSuccessful("1");
				result.setException("The new item was successfully added on the server ");
			}
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
        
		//close connection
		stmt.close();
		return result;
	}

	public wsSQLResult createNewLotList(LotList ll, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
                
        wsSQLResult result = new wsSQLResult();
		try
		{
	        String lotlistQuery = "INSERT INTO LotList (createdBy, createdDate, lastUpdated, lastUpdatedBy, name, owner_id) VALUES ('" + ll.getCreatedBy() + "','" + ll.getCreatedDate() + "','" + ll.getLastUpdated() + "','" + ll.getLastUpdatedBy() + "','" + ll.getName() + "','" + ll.getOwner_id() + "')";      	
            stmt.executeUpdate(lotlistQuery, Statement.RETURN_GENERATED_KEYS);      	
			rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				result.setWasSuccessful("1");
				result.setException("The lot list was successfully created on the server");
			}
			else
			{
				result.setWasSuccessful("-1");
		        result.setException("The server failed to create the new lot list");
			}
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
        
		//close connection
		stmt.close();
		return result;
	}

	public wsSQLResult migrateLot(Migration mig, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        int newVersion = 0;
        
        wsSQLResult result = new wsSQLResult();
		try
		{
			String newLocationName = da.GetLocationNameDetail(getLocations(serverDatabase), mig.getNewLocationId());
			String oldLocationName = da.GetLocationNameDetail(getLocations(serverDatabase), mig.getOldLocationId());
            String migQuery = "INSERT INTO Migration (migrationDate, newLocationId, newLocationName, oldLocationId, oldLocationName, lot_id, reason, createdBy, createdDate, lastUpdated, lastUpdatedBy) VALUES ('" + mig.getMigrationDate() + "','" + mig.getNewLocationId() + "','" + newLocationName + "','" + mig.getOldLocationId() + "','" + oldLocationName + "','" + mig.getLotId() + "','" + mig.getReason() + "','" + mig.getCreatedBy() + "','" + mig.getCreatedDate() + "','" + mig.getLastUpdated() + "','" + mig.getLastUpdatedBy() + "')";      	
            stmt.executeUpdate(migQuery, Statement.RETURN_GENERATED_KEYS);      	
			rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				rs = stmt.executeQuery("SELECT version from Lot WHERE id = '" + mig.getLotId() + "'");
				if(rs.next())
				{
					newVersion = rs.getInt("version") + 1;						
					stmt.executeUpdate("UPDATE Lot Set location = '" + mig.getNewLocationId()  + "', version = '" + newVersion + "', lastUpdated = '" + mig.getLastUpdated() + "', lastUpdatedBy = '" + mig.getLastUpdatedBy() + "' WHERE id ='" + mig.getLotId() + "'");
					result.setWasSuccessful("1");
					result.setException("The lot was successfully migrated on the server");
				}
			}
			else
			{
				result.setWasSuccessful("-1");
		        result.setException("The lot migration failed on the server");
			}
            
            
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
        
		//close connection
		stmt.close();
		return result;
	}

	public wsSQLResult addNewLocation(Location lo, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();     
        
        wsSQLResult result = new wsSQLResult();
		try
		{
            String locQuery = "INSERT INTO Location (locationType, name, version, parentId) VALUES ('" + lo.getLocationType() + "','" + lo.getName() + "','" + lo.getVersion() + "','" + lo.getParentId() + "')";      	
            stmt.executeUpdate(locQuery, Statement.RETURN_GENERATED_KEYS);      	
			rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				result.setWasSuccessful("1");
				result.setException("The new location was successfully added on the server");
			}
			else
			{
				result.setWasSuccessful("-1");
		        result.setException("The server failed to add the new location");
			}
            
            
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
        
		//close connection
		stmt.close();
		return result;
	}

	public wsSQLResult updateFieldVariable(FieldVariable fv, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
                
        wsSQLResult result = new wsSQLResult();
		try
		{
	        rs = stmt.executeQuery("SELECT * FROM FieldVariables WHERE id = '" + fv.getFieldvarId() + "'");
	        
            if(rs.next())
			{
            	rs = stmt.executeQuery("SELECT * FROM FieldVariables WHERE id = '" + fv.getFieldvarId() + "' AND version =  '" + fv.getVersion() + "'");
            	if(rs.next())
    			{
            	int newVersion = fv.getVersion() + 1;
            	stmt.executeUpdate("UPDATE FieldVariables Set qty = '" + fv.getQty() + "', var = '" + fv.getVar() + "', date = '" + fv.getDate() + "', version ='" + newVersion + "', lastUpdated ='" + fv.getLastUpdated() + "', lastUpdatedBy ='" + fv.getLastUpdatedBy() + "' WHERE id ='" + fv.getFieldvarId() + "'");
				result.setWasSuccessful("1");
	            result.setException("Server field variable was successfully updated");
    			}
            	else
            	{
            		result.setWasSuccessful("-2");
    	            result.setException("Unable to update selected field variable record; a latest version have been updated");
            	}
			}    
			else
			{
				result.setWasSuccessful("-1");
				result.setException("The selected field variable was not found on the server");
			}
			//close connection
		    rs.close();
		    stmt.close();
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
		return result;
	}


	public wsSQLResult updateLotVariable(LotVariable lv, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
                
        wsSQLResult result = new wsSQLResult();
		try
		{
	        rs = stmt.executeQuery("SELECT * FROM LotVariable WHERE id = '" + lv.getLotvariableId() + "'");
	        
            if(rs.next())
			{
            	rs = stmt.executeQuery("SELECT * FROM LotVariable WHERE id = '" + lv.getLotvariableId() + "' AND version =  '" + lv.getVersion() + "'");
            	if(rs.next())
    			{
            	int newVersion = lv.getVersion() + 1;
            	stmt.executeUpdate("UPDATE LotVariable Set quantity = '" + lv.getQuantity() + "', variable = '" + lv.getVariableId() + "', variabledate = '" + lv.getVariabledate() + "', version ='" + newVersion + "', lastUpdated ='" + lv.getLastUpdated() + "', lastUpdatedBy ='" + lv.getLastUpdatedBy() + "' WHERE id ='" + lv.getLotvariableId() + "'");
				result.setWasSuccessful("1");
	            result.setException("Server lot variable was successfully updated");
    			}
            	else
            	{
            		result.setWasSuccessful("-2");
    	            result.setException("Unable to update selected lot variable record; a latest version have been updated");
            	}
			}    
			else
			{
				result.setWasSuccessful("-1");
				result.setException("The selected lot variable was not found on the server");
			}
			//close connection
		    rs.close();
		    stmt.close();
		} 
		catch (SQLException e)
		{
			result.setWasSuccessful("0");
            result.setException(e.getMessage());
		}
		return result;
	}


	public ArrayList<Users> getUsers(String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT * from User");
          
		ArrayList<Users> userList = new ArrayList<Users>();
		try
		{
			while(rs.next())
			{
				Users userDetail = new Users();
				userDetail.setUsername(rs.getString("username"));
				userDetail.setPassword(rs.getString("password"));
				userDetail.setLoginStatus(rs.getInt("status")); 
				userDetail.setId(rs.getInt("id"));
				userList.add(userDetail);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return userList;
	}

	
	public Users getUserDetail(String uname, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT * FROM User");
          
		Users userDetail = new Users();
		try
		{
			while(rs.next())
			{
				if(rs.getString("username").matches(uname))
				{
					userDetail.setUsername(rs.getString("username"));
					userDetail.setPassword(rs.getString("password"));
					userDetail.setLoginStatus(rs.getInt("status")); 
					userDetail.setId(rs.getInt("id"));
				}
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return userDetail;
	}
	
	public Lot getLotDetail(int lotID, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT l.id,l.item,l.container,l.location,b.id as barcode,i.name, lo.name as locationDetail, l.quantity,l.status,l.version,l.scale,l.lastUpdated,l.lastUpdatedBy FROM Lot l JOIN BarCode b ON b.lotId = l.id JOIN Item i ON i.id = l.item JOIN Location lo ON lo.id = l.location WHERE l.id = '" + lotID + "'");
        
        Lot lotObj = new Lot();
		try
		{
			if(rs.next())
			{
				lotObj.setLotId(rs.getLong("id"));
				lotObj.setItemId(rs.getLong("item"));
				lotObj.setContainerId(rs.getLong("container"));
				lotObj.setLocationId(rs.getLong("location"));
				lotObj.setBarcode(rs.getLong("barcode"));
				lotObj.setItemName(rs.getString("name"));
				lotObj.setLocationDetail(rs.getString("locationDetail"));
				lotObj.setQuantity(rs.getDouble("quantity"));
				lotObj.setStatus(rs.getInt("status"));
				lotObj.setVersion(rs.getInt("version"));
				lotObj.setScale(rs.getString("scale"));
				lotObj.setLastUpdated(rs.getString("lastUpdated"));
				lotObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				lotObj.setUpdated(updated);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return lotObj;
	}
	
	public FieldVariable getFieldVariableDetail(int fieldvarID, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT * FROM FieldVariables WHERE id = '" + fieldvarID + "'");

		FieldVariable fieldVarObj = new FieldVariable();
		try
		{
			if(rs.next())
			{
				fieldVarObj.setFieldvarId(rs.getLong("id"));
				fieldVarObj.setCreatedBy(rs.getString("createdBy"));
				fieldVarObj.setCreatedDate(rs.getString("createdDate"));
				fieldVarObj.setLastUpdated(rs.getString("lastUpdated"));
				fieldVarObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				fieldVarObj.setVersion(rs.getInt("version"));
				fieldVarObj.setDate(rs.getString("date"));
				fieldVarObj.setQty(rs.getString("qty"));
				fieldVarObj.setVar(rs.getString("var"));
				fieldVarObj.setLotId(rs.getLong("lot_id"));
				fieldVarObj.setUpdated(updated);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return fieldVarObj;
	}

	public LotVariable getLotVariableDetail(int lotvarID, String serverDatabase) throws Exception
	{
		ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/" + serverDatabase);         
        con = ds.getConnection();
        stmt = con.createStatement();         
        rs = stmt.executeQuery("SELECT lv.id, lv.createdBy, lv.createdDate, lv.lastUpdated, lv.lastUpdatedBy, lv.version, lv.version, lv.quantity, lv.variabledate, lv.lot, lv.variable, v.name from LotVariable lv JOIN Variables v ON v.id = lv.variable WHERE lv.id = '" + lotvarID + "'");
        
        LotVariable lotvariabObj = new LotVariable();
		try
		{
			if(rs.next())
			{				
				lotvariabObj.setLotvariableId(rs.getLong("id"));
				lotvariabObj.setCreatedBy(rs.getString("createdBy"));
				lotvariabObj.setCreatedDate(rs.getString("createdDate"));
				lotvariabObj.setLastUpdated(rs.getString("lastUpdated"));
				lotvariabObj.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
				lotvariabObj.setVersion(rs.getInt("version"));
				lotvariabObj.setQuantity(rs.getDouble("quantity"));
				lotvariabObj.setVariabledate(rs.getString("variabledate"));
				lotvariabObj.setLotId(rs.getLong("lot"));
				lotvariabObj.setVariableId(rs.getLong("variable"));
				lotvariabObj.setVariableName(rs.getString("name"));
				lotvariabObj.setUpdated(updated);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return lotvariabObj;
	}
}

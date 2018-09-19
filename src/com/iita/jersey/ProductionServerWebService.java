package com.iita.jersey;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType; 
import javax.ws.rs.PathParam;
import com.google.gson.Gson;


@Path("/PSWebService")
public class ProductionServerWebService
{
	Gson gson = new Gson();
	String serverDatabase = "ProductionServer";
	
	@GET
	@Path("/getDatabase/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getDatabase(@PathParam("username") String username)
	{

		Database dbObj = new Database();
		String db = null;
		try
		{
			dbObj = new AccessManager().getDatabaseDetail(username, serverDatabase); 
			db = gson.toJson(dbObj);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return db;
	}

	@POST
	@Path("/UploadDatabase")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult UploadDatabase(Database db) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().uploadDatabase(db, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@GET
	@Path("/lots/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String lots(@PathParam("username") String username)
	{
		String lots = null;
		ArrayList<Lot> lotList = new ArrayList<Lot>();
		try
		{
			lotList = new AccessManager().getLots(username, serverDatabase);
			lots = gson.toJson(lotList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return lots;
	}

	@GET
	@Path("/parentlocations/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String parentlocations(@PathParam("username") String username)
	{
		ArrayList<Location> locationList = new ArrayList<Location>();
		String locatn = null;
		try
		{
			locationList = new AccessManager().getParentLocations(username, serverDatabase);
			locatn = gson.toJson(locationList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return locatn;
	}
	
	
	@GET
	@Path("/childlocations/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String childlocations(@PathParam("username") String username)
	{
		ArrayList<Location> locationList = new ArrayList<Location>();
		String locatn = null;
		try
		{
			locationList = new AccessManager().getChildLocations(username, serverDatabase);
			locatn = gson.toJson(locationList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return locatn;
	}

	@GET
	@Path("/getAparentChildLocations/{parentId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getAparentChildLocations(@PathParam("parentId") int parentId)
	{
		ArrayList<Location> locationList = new ArrayList<Location>();
		String locatn = null;
		try
		{
			locationList = new AccessManager().getAparentChildLocations(parentId, serverDatabase);
			locatn = gson.toJson(locationList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return locatn;
	}	

	@GET
	@Path("/lotvariables/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String lotvariables(@PathParam("username") String username)
	{
		ArrayList<LotVariable> lotvariableList = new ArrayList<LotVariable>();
		String lotvar = null;
		try
		{
			lotvariableList = new AccessManager().getLotVariables(username, serverDatabase);
			lotvar = gson.toJson(lotvariableList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return lotvar;
	}
	
	@GET
	@Path("/getMyLotlists/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getMyLotlists(@PathParam("username") String username)
	{
		ArrayList<LotList> lotlstList = new ArrayList<LotList>();
		String lotlist = null;
		try
		{
			lotlstList = new AccessManager().getLotLists(username, serverDatabase);
			lotlist = gson.toJson(lotlstList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return lotlist;
	}

	@GET
	@Path("/lotlistlots/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String lotlistlots(@PathParam("username") String username)
	{
		ArrayList<LotListLot> lllList = new ArrayList<LotListLot>();
		String lll = null;
		try
		{
			lllList = new AccessManager().getLotListLots(username, serverDatabase);
			lll = gson.toJson(lllList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return lll;
	}

	@GET
	@Path("/containertypes")
	@Produces({ MediaType.APPLICATION_JSON })
	public String containertypes()
	{
		ArrayList<ContainerType> ctList = new ArrayList<ContainerType>();
		String ct = null;
		try
		{
			ctList = new AccessManager().getContainerTypes(serverDatabase);
			ct = gson.toJson(ctList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ct;
	}

	@GET
	@Path("/getAllUsers")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getAllUsers()
	{
		ArrayList<Users> UsersList = new ArrayList<Users>();
		String Users = null;
		try
		{
			UsersList = new AccessManager().getUsers(serverDatabase);
			Users = gson.toJson(UsersList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return Users;
	}

	@GET
	@Path("/itemtypes")
	@Produces({ MediaType.APPLICATION_JSON })
	public String itemtypes()
	{
		ArrayList<ItemType> itList = new ArrayList<ItemType>();
		String it = null;
		try
		{
			itList = new AccessManager().getItemTypes(serverDatabase);
			it = gson.toJson(itList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return it;
	}

	@GET
	@Path("/subtypeTransactions/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String subtypeTransactions(@PathParam("username") String username)
	{
		ArrayList<SubtypeTransaction> sttList = new ArrayList<SubtypeTransaction>();
		String stt = null;
		try
		{
			sttList = new AccessManager().getSubtypeTransactions(username, serverDatabase);
			stt = gson.toJson(sttList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return stt;
	}

	@GET
	@Path("/variables")
	@Produces({ MediaType.APPLICATION_JSON })
	public String variables()
	{
		ArrayList<Variable> varList = new ArrayList<Variable>();
		String var = null;
		try
		{
			varList = new AccessManager().getVariables(serverDatabase);
			var = gson.toJson(varList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return var;
	}

	@GET
	@Path("/subtypes")
	@Produces({ MediaType.APPLICATION_JSON })
	public String subtypes()
	{
		ArrayList<Subtype> stList = new ArrayList<Subtype>();
		String st = null;
		try
		{
			stList = new AccessManager().getSubtypes(serverDatabase);
			st = gson.toJson(stList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return st;
	}
	
	@GET
	@Path("/migrations/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String migrations(@PathParam("username") String username)
	{
		ArrayList<Migration> migList = new ArrayList<Migration>();
		String mig = null;
		try
		{
			migList = new AccessManager().getMigrations(username, serverDatabase);
			mig = gson.toJson(migList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return mig;
	}
		
	@GET
	@Path("/fieldvariables/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String fieldvariables(@PathParam("username") String username)
	{
		ArrayList<FieldVariable> fieldVarList = new ArrayList<FieldVariable>();
		String fieldvar = null;
		try
		{
			fieldVarList = new AccessManager().getFieldVariables(username, serverDatabase);
			fieldvar = gson.toJson(fieldVarList);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return fieldvar;
	}

	@GET
	@Path("/GetSubtypeTransactions/{username}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String GetSubtypeTransactions(@PathParam("username") String username)
	{
		String stt = null;
		ArrayList<SubtypeTransaction> sttList = new ArrayList<SubtypeTransaction>();
		try
		{
			sttList = new AccessManager().getSubtypeTransactions(username, serverDatabase); 
			stt = gson.toJson(sttList);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return stt;
	}	

	@POST
	@Path("/LoginUser")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Users LoginUser(Users userdetail) 
	{
		Users ud = new Users();
        try
        {
        	ud = new AccessManager().getUserLoginStatus(userdetail, serverDatabase); 
		} 
		catch (Exception e) 
		{
			ud.setUsername(e.getMessage());
			ud.setPassword("");
			ud.setLoginStatus(-3); 
			ud.setId(0);
		} 
		return ud;
	}
	
	@POST
	@Path("/UpdateLot")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult UpdateLot(Lot lot) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().UpdateLotDetail(lot, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/MigrateLot")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult MigrateLot(Migration mig) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().migrateLot(mig, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@GET
	@Path("/getAlotfieldVariables/{LotId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getAlotfieldVariables(@PathParam("LotId") int LotId)
	{
		String fieldvar = null;
		ArrayList<FieldVariable> fieldVarList = new ArrayList<FieldVariable>();
		try
		{
			fieldVarList = new AccessManager().getlotfieldVariables(LotId, serverDatabase); 
			fieldvar = gson.toJson(fieldVarList);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return fieldvar;
	}	

	@GET
	@Path("/GetALotSubtypeTransactions/{LotId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String GetALotSubtypeTransactions(@PathParam("LotId") int LotId)
	{
		String stt = null;
		ArrayList<SubtypeTransaction> sttList = new ArrayList<SubtypeTransaction>();
		try
		{
			sttList = new AccessManager().getALotSubtypeTransactions(LotId, serverDatabase); 
			stt = gson.toJson(sttList);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return stt;
	}	

	@GET
	@Path("/GetAlotMigrations/{LotId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String GetAlotMigrations(@PathParam("LotId") int LotId)
	{
		String mig = null;
		ArrayList<Migration> migList = new ArrayList<Migration>();
		try
		{
			migList = new AccessManager().getAlotMigrations(LotId, serverDatabase); 
			mig = gson.toJson(migList);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mig;
	}	

	@GET
	@Path("/GetASpecificLotListLots/{LotListId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String GetASpecificLotListLots(@PathParam("LotListId") int LotListId)
	{
		String lot = null;
		ArrayList<Lot> lotList = new ArrayList<Lot>();
		try
		{
			lotList = new AccessManager().getASpecificLotListLots(LotListId, serverDatabase); 
			lot = gson.toJson(lotList);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return lot;
	}	

	@GET
	@Path("/getAlotVariables/{LotId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getAlotVariables(@PathParam("LotId") int LotId)
	{
		String lotvar = null;
		ArrayList<LotVariable> lotVarList = new ArrayList<LotVariable>();
		try
		{
			lotVarList = new AccessManager().getlotVariables(LotId, serverDatabase); 
			lotvar = gson.toJson(lotVarList);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return lotvar;
	}	

	@POST
	@Path("/DeleteAfieldVariable")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult DeleteAfieldVariable(FieldVariable fv) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().deleteAfieldVariable(fv, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/DeleteALotList")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult DeleteALotList(LotList ll) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().deleteALotList(ll, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/DeleteLocation")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult DeleteLocation(Location lo) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().deleteLocation(lo, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/DeleteAlotVariable")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult DeleteAlotVariable(LotVariable lv) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().deleteAlotVariable(lv, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/DeleteALotFieldVariables")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult DeleteALotFieldVariables(Lot lot) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().deleteALotFieldVariables(lot, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/RecordFieldVariable")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult RecordFieldVariable(FieldVariable fv) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().recordFieldVariable(fv, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/RecordLotVariable")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult RecordLotVariable(LotVariable lv) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().recordLotVariable(lv, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/RecordSubtypeTransaction")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult RecordSubtypeTransaction(SubtypeTransaction stt) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().recordSubtypeTransaction(stt, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/AddNewLocation")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult AddNewLocation(Location lo) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().addNewLocation(lo, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/AddNewLotlistItemLot")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult AddNewLotlistItemLot(LotlistItemLot llil) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().addNewLotlistItemLot(llil, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/CreateNewLotList")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult CreateNewLotList(LotList ll) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().createNewLotList(ll, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/UpdateFieldVariable")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult UpdateFieldVariable(FieldVariable fv) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().updateFieldVariable(fv, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@POST
	@Path("/UpdateLotVariable")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public wsSQLResult UpdateLotVariable(LotVariable lv) 
	{
		wsSQLResult result = new wsSQLResult();
        try
        {
        	result = new AccessManager().updateLotVariable(lv, serverDatabase); 
		} 
		catch (Exception e) 
		{
			result.setWasSuccessful("-3");
            result.setException(e.getMessage());
		} 
		return result;
	}

	@GET
	@Path("/GetLotDetail/{LotId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String GetLotDetail(@PathParam("LotId") int LotId)
	{
		String ld = null;
		Lot lotdetail = new Lot();
		try
		{
			lotdetail = new AccessManager().getLotDetail(LotId, serverDatabase); 
			ld = gson.toJson(lotdetail);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ld;
	}	

	@GET
	@Path("/GetFieldVariableDetail/{fvId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String GetFieldVariableDetail(@PathParam("fvId") int fvId)
	{
		String fv = null;
		FieldVariable fvdetail = new FieldVariable();
		try
		{
			fvdetail = new AccessManager().getFieldVariableDetail(fvId, serverDatabase); 
			fv = gson.toJson(fvdetail);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return fv;
	}	

	@GET
	@Path("/GetLotVariableDetail/{lvId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String GetLotVariableDetail(@PathParam("lvId") int lvId)
	{
		String lv = null;
		LotVariable lvdetail = new LotVariable();
		try
		{
			lvdetail = new AccessManager().getLotVariableDetail(lvId, serverDatabase); 
			lv = gson.toJson(lvdetail);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return lv;
	}	
	
	@GET
	@Path("/getAuser/{uname}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getAuser(@PathParam("uname") String uname)
	{
		String user = null;
		Users userDetail = new Users();
		try
		{
			userDetail = new AccessManager().getUserDetail(uname, serverDatabase); 
			user = gson.toJson(userDetail);		
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return user;
	}	
}
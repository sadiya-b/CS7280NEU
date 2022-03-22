import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import jnr.ffi.annotations.In;

public class Customer {

    public static void createSchema(ODatabaseSession db){
        OClass customer = db.getClass("Customer");
        OClass claims = db.getClass("Claims");
        OClass claimStatus = db.getClass("ClaimStatus");

        if(customer == null){
            customer = db.createVertexClass("Customer");
        }

        if(claims == null){
            claims = db.createVertexClass("Claims");
        }

        if(claimStatus == null){
            claimStatus = db.createVertexClass("ClaimStatus");
        }

        if(customer.getProperty("name") == null){
            customer.createProperty("name", OType.STRING);
            customer.createIndex("customer_id", OClass.INDEX_TYPE.UNIQUE,"name");
        }

        if(claims.getProperty("claim_number") == null){
            claims.createProperty("claim_number", OType.INTEGER);
            claims.createIndex("claim_id", OClass.INDEX_TYPE.UNIQUE,"claim_number");
        }

        if(claims.getProperty("status") == null){
            claims.createProperty("status", OType.STRING);
            claims.createIndex("status_id", OClass.INDEX_TYPE.NOTUNIQUE,"status");
        }

        if(db.getClass("ClaimsBy")== null){
            db.createEdgeClass("ClaimsBy");
        }
        if(db.getClass("Status") == null){
            db.createEdgeClass("Status");
        }
    }
    private static OVertex createCustomer(ODatabaseSession db, String name,Integer claim_id) {
        OVertex result = db.newVertex("Customer");
        result.setProperty("name", name);
        result.setProperty("claim_id",claim_id);
        result.save();
        return result;
    }
    private static OVertex createClaims(ODatabaseSession db, Integer claim_number) {
        OVertex result2 = db.newVertex("Claims");
        result2.setProperty("claim_number", claim_number);
        result2.save();
        return result2;
    }
    private static OVertex createClaimStatus(ODatabaseSession db, String claim_Status) {
        OVertex result3 = db.newVertex("ClaimStatus");
        result3.setProperty("status", claim_Status);
        result3.save();
        return result3;
    }

    private  static void createInsuranceData(ODatabaseSession db){
        OVertex sam = createCustomer(db,"Sam",12);
        OVertex bill = createCustomer(db,"Bill",10);
        OVertex cat = createCustomer(db,"Cat",11);

        OVertex claim1 = createClaims(db,10);
        OVertex claim2 = createClaims(db,11);


        OEdge edge1 = bill.addEdge(claim1,"ClaimsBy");
        edge1.save();
        OEdge edge2 = cat.addEdge(claim2,"ClaimsBy");
        edge2.save();


        OVertex status1 = createClaimStatus(db,"Approved");
        OVertex status2 = createClaimStatus(db,"Pending");

        OEdge edge4 = claim2.addEdge(status1,"Status");
        edge2.save();
        OEdge edge5 = claim1.addEdge(status1,"Status");
        edge1.save();

    }
    private static void executeAQuery(ODatabaseSession db) {
        String query = "SELECT expand(out('ClaimsBy').out('Status')) from Customer where name = ?";
        OResultSet rs = db.query(query, "Bill");

        while (rs.hasNext()) {
            OResult item = rs.next();
            System.out.println("Customer Claim Status: " + item.getProperty("status"));
        }

        rs.close();
    }
    public static void main(String[] args) {
        //1. connecting to OrientDB
        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        ODatabaseSession db = orient.open("insurance","root","root");

        createSchema(db);

       createInsuranceData(db);

        executeAQuery(db);

        db.close();
        orient.close();
    }
}

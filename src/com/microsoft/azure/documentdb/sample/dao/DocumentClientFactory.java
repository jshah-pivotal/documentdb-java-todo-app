package com.microsoft.azure.documentdb.sample.dao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.sample.model.DocDbCredentials;

public class DocumentClientFactory {
    
    /*
    private CloudFactory cloudFactory = new CloudFactory();
    private Cloud cloud = cloudFactory.getCloud();
    // ServiceInfo has all the information necessary to connect to the underlying service
    List<ServiceInfo> serviceInfos = cloud.getServiceInfos();
    // Find the `ServiceInfo` definitions suitable for connecting to a particular service type
    List<ServiceInfo> databaseInfos = cloud.getServiceInfos(Database.class);
	*/
    
    private static DocumentClient documentClient;
    private static DocDbCredentials docDbCredentials = new DocDbCredentials();

    public static DocumentClient getDocumentClient() {
    	
    	try {
    		String vcap_services = System.getenv("VCAP_SERVICES");
    		if (vcap_services != null && vcap_services.length() > 0) {
    			ObjectMapper mapper = new ObjectMapper();
    		    JsonParser jp = mapper.getFactory().createParser(vcap_services);
    		    JsonNode rootNode = mapper.readTree(jp);
    		    JsonNode documentDbNode = rootNode.get("documentdb");
    		    if(documentDbNode != null){
    		    	System.out.println("brokered service");
    		    	JsonNode credentials = documentDbNode.get(0).get("credentials");
    		    	docDbCredentials.setHost(credentials.get("documentdb_host").asText());
    		    	docDbCredentials.setMasterKey(credentials.get("documentdb_key").asText());
    		    } else if(rootNode.get("user-provided") != null){
     		    	System.out.println("user provided service");
     		    	documentDbNode = rootNode.get("user-provided");
     		    	JsonNode credentials = documentDbNode.get(0).get("credentials");
     		    	System.out.print("credentials: " + credentials.toString());
     		    	docDbCredentials.setHost(credentials.get("documentdb_host").asText());
     		    	System.out.print("host: " + docDbCredentials.getHost());
    		    	docDbCredentials.setMasterKey(credentials.get("documentdb_key").asText()); 
    		    	System.out.print("master key: " + docDbCredentials.getMasterKey());
    		    }
    		    System.out.println(docDbCredentials.getHost());
    		    System.out.println(docDbCredentials.getMasterKey());
    		    
    		    if (documentClient == null) {
                    documentClient = new DocumentClient(docDbCredentials.getHost(), docDbCredentials.getMasterKey(),
                            ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);
                } 
                
                return documentClient;
    		}
    		
        } catch (Exception e) {
            e.printStackTrace();
        }

        return documentClient;
    }
    
    public static DocDbCredentials getDocumentDbCredentials() {
    	
    	try {
    		String vcap_services = System.getenv("VCAP_SERVICES");
    		if (vcap_services != null && vcap_services.length() > 0) {
    			ObjectMapper mapper = new ObjectMapper();
    		    JsonParser jp = mapper.getFactory().createParser(vcap_services);
    		    JsonNode rootNode = mapper.readTree(jp);
    		    JsonNode documentDbNode = rootNode.get("documentdb");
    		    if(documentDbNode != null){
    		    	System.out.println("brokered service");
    		    	JsonNode credentials = documentDbNode.get(0).get("credentials");
    		    	docDbCredentials.setHost(credentials.get("documentdb_host").asText());
    		    	docDbCredentials.setMasterKey(credentials.get("documentdb_key").asText());
    		    	docDbCredentials.setDocumentDbName(credentials.get("documentdb_database").asText());
    		    	docDbCredentials.setDocumentDbResourceId(credentials.get("documentdb_resource_id").asText());
    		    } else if(rootNode.get("user-provided") != null){
     		    	System.out.println("user provided service");
     		    	documentDbNode = rootNode.get("user-provided");
     		    	JsonNode credentials = documentDbNode.get(0).get("credentials");
     		    	docDbCredentials.setHost(credentials.get("documentdb_host").asText());
    		    	docDbCredentials.setMasterKey(credentials.get("documentdb_key").asText());
    		    	docDbCredentials.setDocumentDbName(credentials.get("documentdb_database").asText());
    		    	docDbCredentials.setDocumentDbResourceId(credentials.get("documentdb_resource_id").asText());  		    	
    		    }
    		    System.out.println(docDbCredentials.getHost());
    		    System.out.println(docDbCredentials.getMasterKey());
    		    System.out.println(docDbCredentials.getDocumentDbName());
    		    System.out.println(docDbCredentials.getDocumentDbResourceId());
    		    
    		    if (documentClient == null) {
                    documentClient = new DocumentClient(docDbCredentials.getHost(), docDbCredentials.getMasterKey(),
                            ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);
                } 
                
                return docDbCredentials;
    		}
    		
        } catch (Exception e) {
            e.printStackTrace();
        }

        return docDbCredentials;
    }

}

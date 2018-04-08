import java.util.Scanner;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSetFormatter;

public class FusekiTest {
    private static Scanner userInput;

	public static void main(String[] args) {
    	System.out.println("Enter - 1 or Exit - any");
    	userInput = new Scanner(System.in);
        String name = userInput.next();
       while (name.equals("1")){
        System.out.println("Person's name?");
        @SuppressWarnings("resource")
		Scanner userName = new Scanner(System.in);       
        QueryExecution qe = QueryExecutionFactory.sparqlService(       		
        "http://localhost:3030/PD_Royal_Family_Tree/query",
        "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
        + "PREFIX dbo: <http://dbpedia.org/ontology/> "
        + "PREFIX ns3: <http://purl.org/vocab/> "
        + "SELECT Distinct ?name ?birth ?spouse (group_concat(?parent;separator='---') as ?Parents) (group_concat(?child;separator='--') as ?Children) "
        + "WHERE { "
        + "?person foaf:name  ?name;"
        + "dbo:birthDate  ?birth. "
        + "OPTIONAL {?person ns3:relationshipchildOf ?parent.} "
        + "OPTIONAL {?person ns3:relationshipspouseOf ?spouse.} "   
        + "OPTIONAL {?person ns3:relationshipparentOf ?child.} "
        + "FILTER regex(?name,'"+userName.nextLine()+"') } "
        		+ "group by ?name ?birth ?spouse"
        );  	
        org.apache.jena.query.ResultSet results = qe.execSelect();
        ResultSetFormatter.out(System.out, results); 
        qe.close();
        //System.out.println("Enter - 1 or Exit - 0:");
        }
    }
}


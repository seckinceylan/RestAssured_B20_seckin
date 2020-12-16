package Utility;



import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DB_Utility {
    private static Connection conn;
    private static Statement stmnt;
    private static ResultSet rs;


    /*
     * a static method to create connection
     * with valid url and username password
     * */
    public static void createConnection() {

        String connectionStr = ConfigurationReader.getProperty("database.url");
        String username = ConfigurationReader.getProperty("database.username");
        String password = ConfigurationReader.getProperty("database.password");

        try {
            conn = DriverManager.getConnection(connectionStr, username, password);
            System.out.println("**2 CONNECTION SUCCESSFUL 2**");
        } catch (SQLException e) {
            System.out.println("!!2 CONNECTION HAS FAILED 2!!");
            e.printStackTrace();
        }
//         createConnection(connectionStr,username,password);

    }

    public static void createConnection(String env){
        // add validation to avoid invalid input
        // because we currently only have 2 env : test , dev
        // or add some condition for invalid env
        //  to directly get the information as database.url , database.username, database.password
        // without any env
        System.out.println("You are in "+env+" environment");

        String connectionStr = ConfigurationReader.getProperty(env+".database.url");
        String username = ConfigurationReader.getProperty(env+".database.username");
        String password = ConfigurationReader.getProperty(env+".database.password");

        createConnection(connectionStr,username,password);

    }


    /**
     *  Overload createConnection method to accept url, username, password
     *     * so we can provide those information for different database
     * @param url  The connection String that used to connect to the database
     * @param username the username of database
     * @param password the password of database
     */
    public static void createConnection(String url, String username, String password){
        //String connectionStr = "jdbc:oracle:thin:@100.24.238.65:1521:XE";
        //String username = "hr";
        //String password = "hr";

        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("**1 CONNECTION SUCCESSFUL 1**");
        } catch (SQLException e) {
            System.out.println("!!1 CONNECTION HAS FAILED 1!!  "+e.getMessage());
        }

    }

    /**
     * a static method to get the ResultSet object
     * with valid connection by executing query
     * @param query
     * @return ResultSet object that contain the result just in cases needed outside the class
     */
    public static ResultSet runQuery(String query){
        // reusing the connection built from previous

        try {
            stmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmnt.executeQuery(query);
            System.out.println("*** QUERY RUN SUCCESSFUL ***");
        } catch (SQLException e) {
            System.out.println("Error while getting resultset  "+ e.getMessage());
        }
        return rs;

    }

    // gets the count of Row
    public static int getRowCount(){

        int rowCount = 0 ;

        try {
            rs.last(); // move to last row
            rowCount = rs.getRow(); // get the row number and assign it to rowCount
            // moving back the cursor to before first location just in case
            rs.beforeFirst();
            //System.out.println("Row count is : "+rowCount);

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ROW COUNT"+ e.getMessage());
        }
        return rowCount ;
    }

    /**
     * a method to get the column count of the current ResultSet
     * @return count of of the column
     *
     */
        public static int getColumnCount() {

        int columnCount = 0;

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();
            //System.out.println("Column count is : "+ colCount);

        } catch (SQLException e) {
            System.out.println("ERROR WHILE COUNTING THE COLUMNS"+e.getMessage());
            e.printStackTrace();
        }

        return columnCount;
    }

    /**
     * a method that return all column names
     * @return a list<String>
     */
    public static List<String> getColumnNames(){
        List<String> columnList = new ArrayList<>() ;

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int colNum = 1; colNum <= getColumnCount() ; colNum++) {
                String columnName = rsmd.getColumnLabel( colNum ) ;
                columnList.add( columnName ) ;
            }
        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ALL COLUMN NAMES " + e.getMessage() );
        }
        return columnList ;

    }
    /**
     *  VALUES OF ALL THE CELLS IN THAT column by index number
     * @param colNum the column you want to get a list out of
     * @return List of String that contains entire column data from 1st row to last row
     */
    public static List<String> getColumnDataAsList(int colNum){

        List<String> columnDataList = new ArrayList<>();
        try {
            rs.beforeFirst();  // moving the cursor to before first location

            while(rs.next() ){

                String data =  rs.getString(colNum) ;
                // getting the data from that column and adding to the the list
                columnDataList.add( data  );

            }
            rs.beforeFirst();  // moving the cursor to before first location after we are done
        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING THE COLUMN DATA AS LIST "+e.getMessage());
        }

        return columnDataList;
    }

    /**
     * gets the data of the corresponding column by column name
     * @param colName the column you want to get a list out of
     * @return List of String that contains entire column data from the column name specified
     */
    public static List<String> getColumnDataAsList(String colName){

        List<String> columnDataLst = new ArrayList<>();
        try {
            rs.beforeFirst();  // moving the cursor to before first location

            while(rs.next() ){

                String data =  rs.getString(colName) ;
                // getting the data from that column and adding to the the list
                columnDataLst.add( data  );

            }
            rs.beforeFirst();  // moving the cursor to before first location after we are done
        } catch (SQLException e) {
            System.out.println("ERROR WHILE getColumnDataAsList ");
            e.printStackTrace();
        }

        return columnDataLst;
    }


    /**
     *  getting the entire row data as a List<String>
     * @param rowNum the row number you want the list from
     * @return List of String that contains the row data
     */
    public static List<String> getRowDataAsList(int rowNum){

        List<String> rowDataList = new ArrayList<>();

        try {
            // how to move to that Row with rowNum
            rs.absolute(rowNum);

            // iterate over each and every column and add the value to the list
            for (int colNum = 1; colNum <=  getColumnCount() ; colNum++) {
                rowDataList.add( rs.getString( colNum) );

            }
            //moving the cursor back to before first location just in case
            rs.beforeFirst();

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ROW DATA AS LIST "+e.getMessage());

        }

        return rowDataList;
    }

    /**
     * Getting single column cell value at certain row
     * @param rowNum    row number we want to get data from
     * @param colNum  column index we want to get the data from
     * @return the data in String
     */
    public static String getColumnDataAtRow (int rowNum , int colNum){
        // take home tasks
        // improve this method and check for valid rowNum and columnIndex
        // if invalid return emptyString
        String result = "" ;
        try {
            rs.absolute( rowNum ) ;
            result = rs.getString( colNum ) ;
            rs.beforeFirst();// moving back to the before first location
        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING CELL VALUE AT ROWNUM AND COLNUM "+e.getMessage());
        }

        return result ;
    }

    /**
     * Getting single column cell value at certain row
     * @param rowNum
     * @param colName
     * @return the data at that row with that column name
     */
    public static String getColumnDataAtRow (int rowNum, String colName){
        // take home tasks
        // improve this method and check for valid rowNum and columnIndex
        // if invalid return emptyString
        String result = "" ;
        try {
            rs.absolute( rowNum ) ;
            result = rs.getString( colName );
            rs.beforeFirst();// moving back to the before first location
        } catch (SQLException e) {
            System.out.println("ERROR WHILE getColumnDataAtRow ");
            e.printStackTrace();
        }

        return result ;
    }

    /**
     * We want to store certian row data as a map
     * give me number 3 row  --->> Map<String,String>   {region_id : 3 , region_name : Asia}
     * @param rowNum
     * @return map -- coulumn name as key and cell value as a value
     *
     */
    public static Map<String,String> getRowMap( int rowNum ){

        Map<String,String> rowMap =  new LinkedHashMap<>() ; //new HashMap<>();
        try{
            rs.absolute(rowNum);
            ResultSetMetaData rsmd = rs.getMetaData();
            //System.out.println("Map of row "+ rowNum+ " :");
            for (int colNum = 1; colNum <= getColumnCount() ; colNum++) {
                String colName = rsmd.getColumnName( colNum );
                String colValue= rs.getString( colNum ) ;
                rowMap.put(colName, colValue);

            }

            rs.beforeFirst();

        }catch (SQLException e){
            System.out.println("ERROR GETTING MAP OF ROW "+ rowNum + e.getMessage());
        }

        return rowMap;
    }


    /**
     * each row is represented as a map
     * and we want to get List of each row data as map
     * so the data type of my List is Map -->> since map has key and value data type
     * it becomes List< Map<String,String> >
     * @return The entire resultset as List of Row Map
     */
    public static List<Map<String,String> > getAllDataAsListOfMap(){

        List< Map<String,String> > rowMapList = new ArrayList<>();
        // we can get one rowMap using getRowMap(i) methods
        // so we can iterate over each row and get Map object and put it into the List

        for (int i = 1; i <= getRowCount(); i++) {
            rowMapList.add(   getRowMap(i)    ) ;
        }
        return rowMapList ;

    }

    /**
     * a method to display all the data in the result set
     *
     * */
    public static void displayAllData() {

        // get the first row data  | without knowing the column names
        //int colCount = DB_Utility.getColumnCount();
        // in order to get whole result cursor must be at before first location !

        try {
            // in order to start from beginning , we should be at beforefirst location
            rs.beforeFirst(); // this is for below loop to work
            System.out.println("All Data in the Table ");
            while (rs.next()) { // row iteration

                for (int i = 1; i <= getColumnCount(); i++) { // column iteration
                    System.out.print(rs.getString(i) + "\t");
                    //System.out.printf("%-35s", rs.getString(i));
                }

                System.out.println(); /// adding a blank line for next line
            }
            // now the cursor is at after last location
            // move it back to before first location so we can have no issue calling the method again
            rs.beforeFirst(); // this is for next method that might need to be at before first location

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ALL DATA"+e.getMessage());

        }

    }



    // method for to clean up
    public static void destroy(){
        try{
            if(rs!=null){
                rs.close();
                System.out.println("*** rs closed ***");
            }
            if(stmnt!=null){
                stmnt.close();
                System.out.println("*** stmnt closed ***");
            }
            if(conn!=null){
                conn.close();
                System.out.println("*** connection closed ***");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("");
        }
    }



}


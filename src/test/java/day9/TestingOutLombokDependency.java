package day9;

import TestBase.HR_ORDS_TestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Department;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;

public class TestingOutLombokDependency extends HR_ORDS_TestBase {
    @Test
    public void testDepartmentPOJO(){
        Department d = new Department();
        Department d2 = new Department(100,"abd", 12, 1700);


    }

    @DisplayName("GET /departments and save List of POJO")
    @Test
    public void testDepartmentJsonArrayToListOfPojo(){
        List<Department> allDeps = get("/departments")
                .jsonPath().getList("items", Department.class) ;
        allDeps.forEach(System.out::println);
        // only print the manager id =0
        // copy paste a new list
        List<Department> allDepsCopy = new ArrayList<>(allDeps);
        allDepsCopy.removeIf(eachDep -> eachDep.getManager_id()==0);

    }
    @DisplayName("GET /departments and filter the result with JsonPath groovy")
    @Test
    public void testFilterResultWithGroovy(){
        JsonPath jp = get("/departments").jsonPath();
        List<Department> allDeps = jp.getList("items.findAll{it.manager_id != null}", Department.class);
        allDeps.forEach(System.out::println);


        // what if I just wanted to get List<String> to store DepartmentName
        List<String> depNames = jp.getList("items.department_name") ;
        System.out.println("depNames = " + depNames);

        // -->> items.department_name (all)
        // -->> items.findAll {it.manager_id>0 }.department_name (filtered for manager_id more than 0)
        List<String> depNamesFiltered = jp.getList("items.findAll {it.manager_id>0 }.department_name") ;
        System.out.println("depNamesFiltered = " + depNamesFiltered);

        // what if we have more than one condition for example : department_id between 70 - 100
        List<Integer> deps70to100 = jp.getList("items.department_id.findAll{ it >= 70 && it <= 100  }") ;
        System.out.println("deps70to100 = " + deps70to100);

        // get the name of departments if department_id between 70 - 100
        List<String> depNames70_100 = jp.getList("items.findAll{it.department_id >= 70 && it.department_id <= 100}.department_name");
        System.out.println("depNames70_100 = " + depNames70_100);

        // findAll-->> will return all matching result
        // find -->> will return first match for the condition
        String dep10 =  jp.getString("items.find{ it.department_id==10 }.department_name");
        System.out.println("department 10 name = " + dep10);
        //department 10 name = Administration

        // get the sum of entire department id
        int sumOfAllDepIDs = jp.getInt("items.department_id.sum()");
        int sumOfAllDepIDs2 = jp.getInt("items.sum{it.department_id}");
        System.out.println("sumOfAllDepIDs = " + sumOfAllDepIDs);
        System.out.println("sumOfAllDepIDs2 = " + sumOfAllDepIDs2);

        // get the lowest department_id
        int lowestDepID = jp.getInt("items.department_id.min()");
        int highestDepID = jp.getInt("items.department_id.max()");
        System.out.println("lowestDepID = " + lowestDepID);
        System.out.println("highestDepID = " + highestDepID);

        //print 5 dep id
        System.out.println(jp.getInt("items.department_id[4]"));

        // last dep id
        System.out.println(jp.getInt("items.department_id[-1]"));

        // print index between 7-10
        System.out.println(jp.getList("items.department_id[7..10]"));

    }


}

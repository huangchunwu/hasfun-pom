package cn.hasfun.framework.java8;

import lombok.Data;

import java.util.List;

@Data
public class Company {

    private List<Employee> employeeList;


    static class Employee{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

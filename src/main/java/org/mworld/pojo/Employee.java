package org.mworld.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mworld.utils.CustomTimestampDeserializer;

import java.sql.Timestamp;

public class Employee {

    public Employee() {
    }

    @JsonProperty("employee_id")
    private String employeeId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("birth_date")
    @JsonDeserialize(using = CustomTimestampDeserializer.class)
    private Timestamp dateOfBirth;

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}

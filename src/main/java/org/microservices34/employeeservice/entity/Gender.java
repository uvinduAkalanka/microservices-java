package org.microservices34.employeeservice.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE,FEMALE
}
//public enum Gender {
//    MALE("MALE"),
//    FEMALE("FEMALE");
//
//    private final String value;
//
//    Gender(String value) {
//        this.value = value;
//    }
//
//    @JsonValue
//    public String getValue() {
//        return value;
//    }
//
//    @JsonCreator
//    public static Gender fromValue(String value) {
//        for (Gender gender : Gender.values()) {
//            if (gender.value.equalsIgnoreCase(value)) {
//                return gender;
//            }
//        }
//        throw new IllegalArgumentException("Invalid gender value: " + value);
//    }
//}
package com.taoge.vertxspring.entity;

import io.vertx.core.json.JsonObject;

/**
 * Converter and mapper for {@link User}.
 * NOTE: This class has been automatically generated from the {@link User} original class using Vert.x codegen.
 */
public class UserConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, User obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "age":
          if (member.getValue() instanceof Number) {
            obj.setAge(((Number)member.getValue()).intValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "phone":
          if (member.getValue() instanceof String) {
            obj.setPhone((String)member.getValue());
          }
          break;
        case "role":
          if (member.getValue() instanceof Number) {
            obj.setRole(((Number)member.getValue()).longValue());
          }
          break;
        case "tenantId":
          if (member.getValue() instanceof Number) {
            obj.setTenantId(((Number)member.getValue()).longValue());
          }
          break;
        case "testId":
          if (member.getValue() instanceof Number) {
            obj.setTestId(((Number)member.getValue()).longValue());
          }
          break;
        case "testType":
          if (member.getValue() instanceof Number) {
            obj.setTestType(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(User obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(User obj, java.util.Map<String, Object> json) {
    if (obj.getAge() != null) {
      json.put("age", obj.getAge());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPhone() != null) {
      json.put("phone", obj.getPhone());
    }
    if (obj.getRole() != null) {
      json.put("role", obj.getRole());
    }
    if (obj.getTenantId() != null) {
      json.put("tenantId", obj.getTenantId());
    }
    if (obj.getTestId() != null) {
      json.put("testId", obj.getTestId());
    }
    if (obj.getTestType() != null) {
      json.put("testType", obj.getTestType());
    }
  }
}

package com.taoge.vertxspring.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * <p>
 * 
 * </p>
 *
 * @author 滔哥
 * @since 2019-11-19
 */
@DataObject
public class User {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("test_id")
    private Long testId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 名称
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 测试下划线字段命名类型
     */
    private Integer testType;

    /**
     * 日期
     */
    private LocalDateTime testDate;

    /**
     * 测试
     */
    private Long role;

    /**
     * 手机号码
     */
    private String phone;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getTestType() {
        return testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }
    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
        this.testDate = testDate;
    }
    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
            "testId=" + testId +
            ", tenantId=" + tenantId +
            ", name=" + name +
            ", age=" + age +
            ", testType=" + testType +
            ", testDate=" + testDate +
            ", role=" + role +
            ", phone=" + phone +
        "}";
    }

    public User() {
    }

    public User(JsonObject jsonObject) {
        UserConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        UserConverter.toJson(this, json);
        return json;
    }
}

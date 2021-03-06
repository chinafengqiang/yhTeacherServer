package com.study.model;

import java.util.Date;

public class SysClass {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.created_time
     *
     * @mbggenerated
     */
    private Date createdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.creator
     *
     * @mbggenerated
     */
    private Long creator;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.id
     *
     * @return the value of sys_class.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.id
     *
     * @param id the value for sys_class.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.name
     *
     * @return the value of sys_class.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.name
     *
     * @param name the value for sys_class.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.created_time
     *
     * @return the value of sys_class.created_time
     *
     * @mbggenerated
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.created_time
     *
     * @param createdTime the value for sys_class.created_time
     *
     * @mbggenerated
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.creator
     *
     * @return the value of sys_class.creator
     *
     * @mbggenerated
     */
    public Long getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.creator
     *
     * @param creator the value for sys_class.creator
     *
     * @mbggenerated
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }
}
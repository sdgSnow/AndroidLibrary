package com.sdg.objectbox;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Test {
    @Id
    public Long id;
    public String name;
    public String desc;
    public int age;
    public String createTime;
    public String updateTime;
}

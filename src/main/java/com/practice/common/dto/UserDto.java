package com.practice.common.dto;


import com.practice.entity.StudentInfo;
import com.practice.entity.TeacherInfo;
import com.practice.entity.UserInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto extends UserInfo {
    private TeacherInfo teacherInfo;
    private StudentInfo studentInfo;
    private String className;

    public String toString(){
        String str = super.toString();
        if(this.teacherInfo!=null){
            str+="\n\t"+this.teacherInfo;
        }
        if(this.studentInfo!=null){
            str+="\n\t"+this.studentInfo;
        }
        return str;
    }
}

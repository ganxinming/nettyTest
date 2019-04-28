package com.protobuf.test;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @Author Badribbit
 * @create 2019/4/26 9:40
 */
public class ProtoBufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student=DataInfo.Student.newBuilder().
                setName("张三").setId(20).setAddress("江西").build();
        byte[] sb=student.toByteArray();
        DataInfo.Student student1=DataInfo.Student.parseFrom(sb);
        System.out.println(student1.getName()+student1.getId()+student1.getAddress());

    }
}

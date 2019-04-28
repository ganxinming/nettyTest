package com.gan.thriftTest;

import com.thrift.generated.DataException;
import com.thrift.generated.Person;
import com.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * @Author Badribbit
 * @create 2019/4/27 10:05
 */

/**
 * 实现thrift.generated中的PersonService接口中的方法
 */
public class PersonServiceImpl implements PersonService.Iface{
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        /**
         * 这里的逻辑代码随便写的，不一定就是该方法的功能
         */
        System.out.println("Got Client Param"+username);
        //可以使用链式编程，当然也可以分开
        Person person=new Person().setUsername("张三").setAge(14).setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("保存对象");
        System.out.println(person.username);
        System.out.println(person.age);
        System.out.println(person.married);
    }
}

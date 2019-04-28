namespace java thrift.generated
//使用方式：thrift --gen java src/thrift/data.thrift 使用完，会产生一个gen-java
//生成py端代码，thrift --gen py src/thrift/data.thrift
namespace py py.thrift.generated
//起别名，符合java类型习惯
typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

struct Person{
 1: optional String username,
 2: optional int age,
 3: optional boolean married
}

exception DataException{
 1: optional String message,
 2: optional String callStack,
 3: optional String date
}

service PersonService{
 Person getPersonByUsername(1:required String username) throws (1:DataException dataException),
 void savePerson(1:required Person person) throws (1:DataException dataException)
}



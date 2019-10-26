package top.spencercjh.chapter1;

import java.util.Objects;

/**
 * @author SpencerCJH
 * @date 2019/10/17 18:45
 */
public class UserInJava {
    String name;
    Integer age;
    String innerValue = "123";
    String readOnlyInnerValue = "readOnly";
    public UserInJava(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        UserInJava user = new UserInJava("testName", 23);
        System.out.println(user);
        user.innerValue = "456";
        System.out.println(user.innerValue);
        System.out.println(user.readOnlyInnerValue);
        System.out.println(user.hashCode());
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInJava user = (UserInJava) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getInnerValue() {
        return innerValue;
    }

    public UserInJava setInnerValue(String innerValue) {
        this.innerValue = innerValue;
        return this;
    }

    public String getReadOnlyInnerValue() {
        return readOnlyInnerValue;
    }
}
package com.lilin.mwmw.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.function.BooleanSupplier;

@Data
@AllArgsConstructor
public class Login {



    private String userName;
    private String password;
    private String userType;
    private User user;

    public Login(){}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //地址相等
        //非空性：对于任意非空引用x，x.equals(null)应该返回false。
        if (o == null) return false;
        if(o instanceof Login){
            Login other=(Login) o;
            return equalStr(this.userName,other.userName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (userName == null ? 0 : userName.hashCode());
        result = 31 * result + (password == null ? 0 : password.hashCode());
        return result;
    }

    private Boolean equalStr(String userName1,String userName2){
        if(userName1.equals(userName2)){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        Login login1=new Login("liiln","123456","1",null);
        Login login2=new Login("liiln","123456","2",null);
        System.out.println(login1.equals(login2));
        System.out.println(login1.hashCode()==login2.hashCode());

        //重写equals方法和HashCode方法  可以比较两个对象  也可以按照自己的意愿去重
        HashSet<Login> set=new HashSet<>();
        set.add(login1);
        set.add(login2);
        System.out.println(set);
    }

}

package com.czx.shirospringboot.config;

import com.czx.shirospringboot.pojo.User;
import com.czx.shirospringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的 Realm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    //授权

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //打印一个提示
        System.out.println("执行了授权方法");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addStringPermission("user:add");

        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();// 拿到User对象
        //【设置当前用户的权限】
        if(currentUser.getPerms() != null){
            info.addStringPermission(currentUser.getPerms());
        }

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //打印一个提示
        System.out.println("执行了认证方法doGetAuthenticationInfo");

        //通过参数获取登录的控制器中生成的 令牌,这里的token由
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user=userService.queryUserByName(token.getUsername()); //从数据库获取用户名

        if(user==null){//说明查无此人
            return null; //表示控制器中抛出的相关异常
        }
        //【密码认证】， Shiro 自己做，为了避免和密码的接触

        //最后【返回一个 AuthenticationInfo 接口的【实现类】】，这里选择 SimpleAuthenticationInfo
        // 三个参数：获取当前用户的认证 ； 密码 ； 认证名
        String password=user.getPwd();
        return new SimpleAuthenticationInfo(user,password, "");

    }
}

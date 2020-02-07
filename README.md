# ehi-verification

#### 项目介绍
一个好用的快速的参数校验框架
支持直接调用校验并提供Spring-webmvc拦截器对请求参数进行校验

#### 软件架构
没啥架构,不强制依赖第三方Jar包

#### 更新记录
1.0.3 解决返回JSON多出逗号的问题

1.0.4 

1.直接以@RequestBody 形式获取参数的校验
2.新增BodyReaderFilter可协助解决流只能读取一次的问题


#### 安装教程
PS:最新版已同步至Maven中央仓库
```java
 <dependency>
     <groupId>com.yuxuan66</groupId>
     <artifactId>ehi-verification</artifactId>
     <version>1.0.6</version>
 </dependency>
```
如非Maven项目直接打包后找到Jar放入项目中即可

#### 使用说明

1. 首先我们先来认识几个常用的类和注解

Verification,Param,Len

EhiVerification,VerificationResult,Result,ConstFormat

Param:标记一个参数要进行校验

参数:

value(String)=字段名称

require(boolean)=是否需要(默认为需要)

asFor(String)=必须跟asFor的字段内容一致

nullFor(String)=nullFor的字段内容和当前内容不能同时为空

nullMsg(String)=字段为空的错误提示,默认使用全局提示

errorMsg(String)=字段校验不通过的错误提示,默认使用全局提示

pattern(String)=用来校验的正则

len(@Len)=长度校验



Verification:标记一个类,Bean只有此注解才会生效
参数:
params(@Param)=字段列表


```java
@Verification(params = {
        @Param(value = "name", require = false, len = @Len(min = 10, max = 20),errorMsg = "用户名必须为10~20位字符"),
        @Param(value = "password"),
        @Param(value = "email", pattern = ConstFormat.EMAIL)
})
public class User extends HashMap {

    private String name;
    private String password;
    private String email;
    @Param(value = "age")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

```

demo

```java
   public static void main(String ...args){
        User user = new User();
        user.setName("1111111111");
        user.setEmail("eqeq@qq.com");
        user.setPassword("dsadsa");
        VerificationResult verificationResult = new EhiVerification().verification(user);
        System.out.println(verificationResult.isVerification());
        System.out.println(verificationResult.getErrorMsg());

    }
```
```
//校验一个实体 实体可以集成Map会自动区分
VerificationResult verificationResult = new EhiVerification().verification(user);
返回一个校验结果
verificationResult.isVerification();//返回是否有校验不通过字段
verificationResult.getErrorMsg();//返回校验不通过的提示语,如上行代码返回true则此代码返回空字符串 
```
下面我们来学习一下高级的使用方式，这里我们使用springboot进行集成
1. 拦截器配置
```java
package com.km66.knowledge.support.config.web;

import com.km66.knowledge.support.interceptor.AuthInterceptor;
import com.km66.knowledge.support.resolver.DictArgumentResolver;
import com.yuxuan66.ehi.verification.core.EhiVerification;
import com.yuxuan66.ehi.verification.interceptor.ParamInterceptor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        //创建核心校验器
        EhiVerification ehiVerification = new EhiVerification();
        //添加参数拦截器
		registry.addInterceptor(new ParamInterceptor(ehiVerification));
	}
}
```
2. Controller配置
```java
@RequestMapping(path = "updateAccessTokenByRefreshToken", method = RequestMethod.POST)
    @ResponseBody
    @Verification(params = {
            @Param(value = "username", len = @Len(min = 10, max = 20),errorMsg = "用户名格式不正确",nullMsg = "用户名必须输入"),
            @Param(value = "email" ,pattern = ConstFormat.EMAIL),
            @Param(value = "password")
    })
    public RespEntity updateAccessTokenByRefreshToken(@RequestParam Map<String, Object> params) {
        try {
            return usersService.updateAccessTokenByRefreshToken(params);
        } catch (Exception e) {
            logger.error("服务器异常", e);
            return RespEntity.fail();
        }
    }
```
交流群:795272647

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


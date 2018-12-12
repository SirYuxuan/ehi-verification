# ehi-verification

#### 项目介绍
一个好用的快速的参数校验框架
支持直接调用校验并提供Spring-webmvc拦截器对请求参数进行校验

#### 软件架构
没啥架构,不强制依赖第三方Jar包


#### 安装教程
PS:正在往Maven中央仓库发布中
暂时使用拉取项目打包的本地的方式使用
1. 拉取项目
2. 在项目跟目录执行 mvn clean install
3. 如没问题就可以在项目中直接使用了

```
 <dependency>
     <groupId>com.yuxuan66</groupId>
     <artifactId>ehi-verification</artifactId>
     <version>1.0-SNAPSHOT</version>
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


```
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

```
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
#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request



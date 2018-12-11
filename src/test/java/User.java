import com.yuxuan66.ehi.verification.annotation.Len;
import com.yuxuan66.ehi.verification.annotation.Param;
import com.yuxuan66.ehi.verification.annotation.Verification;
import com.yuxuan66.ehi.verification.constant.ConstFormat;

/**
 * @author Sir丶雨轩
 * @date 2018/12/11 13:30
 */
@Verification(params = {
        @Param(value = "name", require = false, len = @Len(min = 10, max = 20)),
        @Param(value = "password"),
        @Param(value = "email", pattern = ConstFormat.EMAIL)
})
public class User {

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

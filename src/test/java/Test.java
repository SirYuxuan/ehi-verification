import com.yuxuan66.ehi.verification.core.EhiVerification;
import com.yuxuan66.ehi.verification.core.VerificationResult;
import org.junit.Assert;

/**
 * @author Sir丶雨轩
 * @date 2018/12/11 13:30
 */
public class Test {
    @org.junit.Test
    public  void test(){
        User user = new User();
        user.setName("1111111111");
        user.setEmail("eqeq@qq.com");
        user.setPassword("dsadsa");
        VerificationResult verificationResult = new EhiVerification().verification(user);
        System.out.println(verificationResult.isVerification());
        System.out.println(verificationResult.getErrorMsg());
        Assert.assertTrue(verificationResult.isVerification());

    }
}

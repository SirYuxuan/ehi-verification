import com.yuxuan66.ehi.verification.core.EhiVerification;
import com.yuxuan66.ehi.verification.core.VerificationResult;

/**
 * @author Sir丶雨轩
 * @date 2018/12/11 13:30
 */
public class Test {

    public static void main(String ...args){
        User user = new User();

        VerificationResult verificationResult = new EhiVerification().verification(user);



    }
}

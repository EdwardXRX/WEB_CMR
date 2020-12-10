import com.edwardxrx.crm.utils.DateTimeUtil;
import com.edwardxrx.crm.utils.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: WEB_CMR
 * @Package: PACKAGE_NAME
 * @ClassName: Tets1
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/9 16:28
 * @Version: 1.0
 */
public class Tets1 {
    public static void main(String[] args) {
        //验证失效时间
        String Time = "2021-11-11 10:10:11";


        String sss = DateTimeUtil.getSysTime();
        System.out.println(sss);

        int m = Time.compareTo(sss);
        System.out.println(m);

        String pwd = "Liuxin5859@";
        String xxx = MD5Util.getMD5(pwd);
        System.out.println(xxx);



    }
}

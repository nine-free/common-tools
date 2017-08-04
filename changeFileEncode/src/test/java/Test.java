import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jifuzhang on 17/7/31.
 */
public class Test {
    public static void main(String[] args) {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse("2017-08-03");
            System.out.println(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);

    }
}

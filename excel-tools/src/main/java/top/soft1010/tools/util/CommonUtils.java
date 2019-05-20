package top.soft1010.tools.util;


import static java.lang.String.format;

/**
 * Created by bjzhangjifu on 2018/8/30.
 */
public final class CommonUtils {

    public static <T> T checkNotNull(T reference,
                                     String errorMessageTemplate,
                                     Object... errorMessageArgs) {
        if (reference == null) {
            throw new RuntimeException(
                    format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }

    public static void main(String[] args) {
        System.out.println(format("123%d1231", 1));
    }
}

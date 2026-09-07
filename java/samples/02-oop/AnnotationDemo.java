import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * アノテーションの基本デモ。
 */
public class AnnotationDemo {
    public static void main(String[] args) throws Exception {
        Greeter greeter = new FriendlyGreeter();
        System.out.println(greeter.greet("Java"));

        // 実行時にカスタムアノテーションを読む例
        Method method = FriendlyGreeter.class.getMethod("greet", String.class);
        if (method.isAnnotationPresent(Audited.class)) {
            Audited audited = method.getAnnotation(Audited.class);
            System.out.println("Audited value=" + audited.value());
        }

        LegacyApi api = new LegacyApi();
        api.oldMethod(); // コンパイル時に deprecated 警告が出る想定
    }
}

interface Greeter {
    String greet(String name);
}

class FriendlyGreeter implements Greeter {
    @Override
    @Audited("greet")
    public String greet(String name) {
        return "Hello, " + name + "!";
    }
}

class LegacyApi {
    @Deprecated(since = "1.0", forRemoval = true)
    public void oldMethod() {
        System.out.println("古い API です。新しい API へ移行してください。");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Audited {
    String value() default "";
}

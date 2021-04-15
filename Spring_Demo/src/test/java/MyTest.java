import com.example.pojo.Hello;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        //获取Spring的上下文对象
        //解析beans.xml文件 , 生成管理相应的Bean对象
        ApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/resources/beans.xml");
        //我们的对象现在都在Spring中管理了，我们要使用，直接去取出来就可以了
        //getBean : 参数即为spring配置文件中bean的id .
        //Hello hello = (Hello) context.getBean("hello");
        Hello hello = context.getBean("hello",Hello.class);
        hello.show();
    }
}

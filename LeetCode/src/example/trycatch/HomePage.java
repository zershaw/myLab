package example.trycatch;

import java.net.MalformedURLException;
import java.net.URL;

public class HomePage {
    String owner;
    URL address;
    public HomePage(String inOwner, String inAddress) throws MalformedURLException {
        // MalformedURLException指使用的地址无效，因此无法创建这样的对象
        this.owner = inOwner;
        this.address = new URL(inAddress);
        System.out.println("第一个" + owner);
        System.currentTimeMillis();
    }
}

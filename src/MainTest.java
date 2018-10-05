import org.junit.Test;

public class MainTest {
    int a = 5;
    int b = 11;

    @Test
    public void MyFirstTest(){
        int b = 10;
        int a = 15;

        System.out.println(a);
        System.out.println(b);
        System.out.println(this.a);
        System.out.println(this.b);
    }
}

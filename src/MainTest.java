import org.junit.Test;

public class MainTest extends CoreTestCase
{
    MathHelper Math = new MathHelper();
    int a = 10;
    int b = 4;

    @Test
    public void MyFirstTest(){
        int a = Math.calc(this.a, this.b, '+');
        System.out.println(a);

        int b = Math.calc(this.a, this.b, '*');
        System.out.println(b);
    }

}

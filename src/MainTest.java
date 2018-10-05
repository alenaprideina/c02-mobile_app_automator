import org.junit.Test;

public class MainTest {

    @Test
    public void MyFirstTest(){
        int a = this.multiply(5);
        System.out.println(a);

        int b = this.multiply(10, 15);
        System.out.println(b);
    }

    public int multiply(int number){
        return number * 2;
    }

    public int multiply(int number, int multiplier){
        return number * multiplier;
    }
}

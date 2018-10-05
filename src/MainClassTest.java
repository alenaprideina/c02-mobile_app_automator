import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass
{
    @Test
    public void testGetClassNumber()
    {
        Assert.assertTrue("class_number <= 45", this.getClassNumber() > 45);
    }
}

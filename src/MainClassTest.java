import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass
{
    @Test
    public void testGetClassString()
    {
        Assert.assertTrue(
                "class_string does not contain substrings 'hello' or 'Hello'",
                (this.getClassString().contains("hello") || this.getClassString().contains("Hello")) );
    }
}

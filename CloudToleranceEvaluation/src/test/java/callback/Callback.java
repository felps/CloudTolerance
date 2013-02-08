package callback;

import org.junit.Test;

public class Callback {

	@Test
    public void shouldRunCorrectly() throws InterruptedException
    {
        Boss b;
        
        b = new Boss(1);
        b.directMethod();
        
        System.out.println("Agora com 10 Threads");
        b = new Boss(10);
        b.directMethod();
    }
}

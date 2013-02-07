package callback;

public class Callback {

	// Main driver.
    public static void main(String[] args) throws InterruptedException
    {
        Boss b;
        
        b = new Boss(1);
        b.directMethod();
        
        System.out.println("Agora com 10 Threads");
        b = new Boss(10);
        b.directMethod();
    }
}

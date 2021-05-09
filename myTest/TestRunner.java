package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner
{
    public static void runTest(Class<?> testedClass)
    {
        System.out.println("Test della classe: " + testedClass.getName() + "\n");

        Result res = JUnitCore.runClasses(testedClass);
        System.out.println("Sono stati eseguiti: " + res.getRunCount() + " test.");
        if (!res.wasSuccessful())
        {   
            System.out.println(res.getFailureCount() +  " test sono falliti.");
            System.out.println("\nA seguire i test che sono falliti:");
            for (Failure fail : res.getFailures())
                System.out.println(fail.getDescription().getMethodName() + 
                                   ":\n" + fail.getTrimmedTrace());
        }
        else
        {
            System.out.println("Tutti i test son stati eseguiti con successo.");
        }
    }

    public static void main(String[] args)
    {
        //Test della classe ListAdapter
        runTest(ListAdapterTester.class);

        //Test della classe MapAdapter
        runTest(ListTester.class);
    }
}
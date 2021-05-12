package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner
{
    public static void runTest(Class<?> testedClass)
    {
        System.out.println("Test della classe: " + testedClass.getName());

        Result res = JUnitCore.runClasses(testedClass);
        System.out.println("\tSono stati eseguiti: " + res.getRunCount() + " test.");
        if (!res.wasSuccessful())
        {   
            System.out.println("\t" + res.getFailureCount() +  " test sono falliti.");
            System.out.println("\nA seguire i test che sono falliti:");
            for (Failure fail : res.getFailures())
                System.out.println("\t" + fail.getDescription().getMethodName() + 
                                   ":\n\t" + fail.getTrimmedTrace());
        }
        else
        {
            System.out.println("\tTutti i test son stati eseguiti con successo.\n");
        }
    }

    public static void main(String[] args)
    {
        //Test della classe ListAdapter
        runTest(ListSuite.class);

        //Test della classe MapAdapter
        runTest(MapSuite.class);
    }
}
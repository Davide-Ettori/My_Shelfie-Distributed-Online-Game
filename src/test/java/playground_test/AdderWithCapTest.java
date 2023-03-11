package playground_test;

import org.junit.*; // importa JUnit
import static org.junit.Assert.assertEquals;
import playground.junit.AdderWithCap; // importo la classe che voglio testare

public class AdderWithCapTest { // questa classe fa la verifica automatica della classe AdderWithCap
    AdderWithCap adder = null;
    @Before // eseguita prima dei test
    public void setUp(){
        this.adder = new AdderWithCap(10);
    }
    @After // eseguita dopo i test
    public void tearDown(){
        return;
    }
    @Test // test 1
    public void getCap_correct(){
        assertEquals(adder.getCap(), 10);
    }
    @Test // test 2
    public void setCap_correct(){
        adder.setCap(12);
        assertEquals(adder.getCap(), 12);
    }
    @Test // test 3
    public void add_correct(){
        assertEquals(adder.add(4, 5), 9);
    }
    @Test // test 4
    public void add_over_cap(){
        assertEquals(adder.add(4, 8), -1);
    }
    @Test (expected = IllegalArgumentException.class) // ecco come testare che vengano lanciate l giuste eccezioni
    public void input_negative_exception(){ // test 5
        adder.add(4, -2);
    }
}

// basta runnare questo file su intellij e si vede chiaramente che passa tutti i test, come ci si aspetterebbe
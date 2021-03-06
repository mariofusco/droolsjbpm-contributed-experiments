package org.drools.testframework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.drools.Cheese;
import org.drools.OuterFact;
import org.drools.Person;
import org.drools.WorkingMemory;
import org.drools.base.ClassTypeResolver;
import org.drools.base.TypeResolver;
import org.drools.base.mvel.DroolsMVELFactory;
import org.drools.common.InternalRuleBase;
import org.drools.common.InternalWorkingMemory;
import org.drools.guvnor.client.modeldriven.testing.ExecutionTrace;
import org.drools.guvnor.client.modeldriven.testing.Expectation;
import org.drools.guvnor.client.modeldriven.testing.FactData;
import org.drools.guvnor.client.modeldriven.testing.FieldData;
import org.drools.guvnor.client.modeldriven.testing.RetractFact;
import org.drools.guvnor.client.modeldriven.testing.Scenario;
import org.drools.guvnor.client.modeldriven.testing.VerifyFact;
import org.drools.guvnor.client.modeldriven.testing.VerifyField;
import org.drools.guvnor.client.modeldriven.testing.VerifyRuleFired;
import org.drools.guvnor.server.util.ScenarioXMLPersistence;
import org.drools.rule.TimeMachine;

public class ScenarioRunnerTest extends RuleUnit {
	
    static {
        try {
			Class.forName( "org.drools.base.mvel.MVELCompilationUnit" );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }   	

	public void setUp() {
		//needed when running stand alone to make sure the converters get loaded.
		DroolsMVELFactory d = new DroolsMVELFactory();
	}

    public void testPopulateFacts() throws Exception {
        Scenario sc = new Scenario();
        List facts = ls( new FactData( "Cheese",
                                       "c1",
                                       ls( new FieldData( "type",
                                                          "cheddar" ),
                                           new FieldData( "price",
                                                          "42" ) ),
                                       false ),
                         new FactData( "Person",
                                       "p1",
                                       ls( new FieldData( "name",
                                                          "mic" ),
                                           new FieldData( "age",
                                                          "=30 + 3" ) ),
                                       false ) );

        sc.fixtures.addAll( facts );
        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );
        resolver.addImport( "org.drools.Person" );
        ScenarioRunner runner = new ScenarioRunner( sc,
                                                    resolver,
                                                    new MockWorkingMemory() );

        assertTrue( runner.populatedData.containsKey( "c1" ) );
        assertTrue( runner.populatedData.containsKey( "p1" ) );

        Cheese c = (Cheese) runner.populatedData.get( "c1" );
        assertEquals( "cheddar",
                      c.getType() );
        assertEquals( 42,
                      c.getPrice() );

        Person p = (Person) runner.populatedData.get( "p1" );
        assertEquals( "mic",
                      p.getName() );
        assertEquals( 33,
                      p.getAge() );

    }

    public void testPopulateNested() throws Exception {
        Scenario sc = new Scenario();
        List facts = ls( new FactData( "Cheese",
                                       "c1",
                                       ls( new FieldData( "type",
                                                          "cheddar" ),
                                           new FieldData( "price",
                                                          "42" ) ),
                                       false ),
                         new FactData( "OuterFact",
                                       "p1",
                                       ls( new FieldData( "name",
                                                          "mic" ),
                                           new FieldData( "innerFact",
                                                          "=c1" ) ),
                                       false ) );

        sc.fixtures.addAll( facts );
        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );
        resolver.addImport( "org.drools.OuterFact" );
        ScenarioRunner runner = new ScenarioRunner( sc,
                                                    resolver,
                                                    new MockWorkingMemory() );

        assertTrue( runner.populatedData.containsKey( "c1" ) );
        assertTrue( runner.populatedData.containsKey( "p1" ) );

        OuterFact o = (OuterFact) runner.populatedData.get("p1");
        assertNotNull(o.getInnerFact());

    }

    public void testPopulateEmpty() throws Exception {
        Scenario sc = new Scenario();
        List facts = ls( new FactData( "Cheese",
                                       "c1", new ArrayList(), false));
        sc.fixtures.addAll( facts );
        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        sc.fixtures.addAll( facts );
        resolver.addImport( "org.drools.Cheese" );
        ScenarioRunner runner = new ScenarioRunner( sc,
                                                    resolver,
                                                    new MockWorkingMemory() );

        assertTrue( runner.populatedData.containsKey( "c1" ) );
        assertTrue(runner.populatedData.get("c1") instanceof Cheese);
    }

    public void testDateField() throws Exception {
        Scenario sc = new Scenario();
        List facts = ls( new FactData( "Cheese",
                                       "c1",
                                       ls( new FieldData( "type",
                                                          "cheddar" ),
                                           new FieldData( "usedBy",
                                                          "10-Jul-2008" ) ),
                                       false ),
                         new FactData( "OuterFact",
                                       "p1",
                                       ls( new FieldData( "name",
                                                          "mic" ),
                                           new FieldData( "innerFact",
                                                          "=c1" ) ),
                                       false ) );

        sc.fixtures.addAll( facts );
        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );
        resolver.addImport( "org.drools.OuterFact" );
        ScenarioRunner runner = new ScenarioRunner( sc,
                                                    resolver,
                                                    new MockWorkingMemory() );

        assertTrue( runner.populatedData.containsKey( "c1" ) );
        assertTrue( runner.populatedData.containsKey( "p1" ) );

        Cheese c = (Cheese) runner.populatedData.get("c1");
        assertNotNull(c.getUsedBy());

    }

    public void testPopulateFactsWithExpressions() throws Exception {
        Scenario sc = new Scenario();
        List facts = ls( new FactData( "Cheese",
                                       "c1",
                                       ls( new FieldData( "type",
                                                          "cheddar" ),
                                           new FieldData( "price",
                                                          "42" ) ),
                                       false ),
                         new FactData( "Cheese",
                                       "c2",
                                       ls( new FieldData( "type",
                                                          "= c1.type" ) ),
                                       false ) );

        sc.fixtures.addAll( facts );
        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );

        ScenarioRunner runner = new ScenarioRunner( sc,
                                                    resolver,
                                                    new MockWorkingMemory() );

        assertTrue( runner.populatedData.containsKey( "c1" ) );
        assertTrue( runner.populatedData.containsKey( "c2" ) );

        Cheese c = (Cheese) runner.populatedData.get( "c1" );
        assertEquals( "cheddar",
                      c.getType() );
        assertEquals( 42,
                      c.getPrice() );

        Cheese c2 = (Cheese) runner.populatedData.get( "c2" );
        assertEquals( c.getType(),
                      c2.getType() );

    }

    public void testPopulateNoData() throws Exception {
        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );
        ScenarioRunner run = new ScenarioRunner( new Scenario(),
                                                 resolver,
                                                 new MockWorkingMemory() );
        run.populatedData.clear();
        Cheese c = new Cheese();
        c.setType( "whee" );
        c.setPrice( 1 );
        run.populatedData.put( "x",
                               c );

        assertEquals( 1,
                      c.getPrice() );

        FactData fd = new FactData( "Cheese",
                                    "x",
                                    ls( new FieldData( "type",
                                                       "" ),
                                        new FieldData( "price",
                                                       "42" ) ),
                                    false );

        run.populateFields( fd,
                            run.populatedData,
                            c );
        assertEquals( "whee",
                      c.getType() );
        assertEquals( 42,
                      c.getPrice() );
    }

    public void testVerifyFacts() throws Exception {

        ScenarioRunner runner = new ScenarioRunner( new Scenario(),
                                                    null,
                                                    new MockWorkingMemory() );
        Cheese f1 = new Cheese( "cheddar",
                                42 );
        runner.populatedData.put( "f1",
                                  f1 );

        Person f2 = new Person( "michael",
                                33 );
        runner.populatedData.put( "f2",
                                  f2 );

        // test all true
        VerifyFact vf = new VerifyFact();
        vf.name = "f1";
        vf.fieldValues = ls( new VerifyField( "type",
                                              "cheddar",
                                              "==" ),
                             new VerifyField( "price",
                                              "42",
                                              "==" ) );

        runner.verify( vf );
        for ( int i = 0; i < vf.fieldValues.size(); i++ ) {
            assertTrue( ((VerifyField) vf.fieldValues.get( i )).successResult );
        }

        vf = new VerifyFact();
        vf.name = "f2";
        vf.fieldValues = ls( new VerifyField( "name",
                                              "michael",
                                              "==" ),
                             new VerifyField( "age",
                                              "33",
                                              "==" ) );

        runner.verify( vf );
        for ( int i = 0; i < vf.fieldValues.size(); i++ ) {
            assertTrue( ((VerifyField) vf.fieldValues.get( i )).successResult );
        }

        // test one false
        vf = new VerifyFact();
        vf.name = "f2";
        vf.fieldValues = ls( new VerifyField( "name",
                                              "mark",
                                              "==" ),
                             new VerifyField( "age",
                                              "33",
                                              "==" ) );

        runner.verify( vf );
        assertFalse( ((VerifyField) vf.fieldValues.get( 0 )).successResult );
        assertTrue( ((VerifyField) vf.fieldValues.get( 1 )).successResult );

        assertEquals( "michael",
                      ((VerifyField) vf.fieldValues.get( 0 )).actualResult );
        assertEquals( "mark",
                      ((VerifyField) vf.fieldValues.get( 0 )).expected );

        // test 2 false
        vf = new VerifyFact();
        vf.name = "f2";
        vf.fieldValues = ls( new VerifyField( "name",
                                              "mark",
                                              "==" ),
                             new VerifyField( "age",
                                              "32",
                                              "==" ) );

        runner.verify( vf );
        assertFalse( ((VerifyField) vf.fieldValues.get( 0 )).successResult );
        assertFalse( ((VerifyField) vf.fieldValues.get( 1 )).successResult );

        assertEquals( "michael",
                      ((VerifyField) vf.fieldValues.get( 0 )).actualResult );
        assertEquals( "mark",
                      ((VerifyField) vf.fieldValues.get( 0 )).expected );

        assertEquals( "33",
                      ((VerifyField) vf.fieldValues.get( 1 )).actualResult );
        assertEquals( "32",
                      ((VerifyField) vf.fieldValues.get( 1 )).expected );

    }

    public void testVerifyAnonymousFacts() throws Exception {
    	MockWorkingMemory wm = new MockWorkingMemory();
        ScenarioRunner runner = new ScenarioRunner( new Scenario(),
                null,
                wm );

        Cheese c = new Cheese();
        c.setPrice(42);
        c.setType("stilton");

        wm.facts.add(c);

        VerifyFact vf = new VerifyFact("Cheese", new ArrayList(), true);
        vf.fieldValues.add(new VerifyField("price", "42", "=="));
        vf.fieldValues.add(new VerifyField("type", "stilton", "=="));

        runner.verify(vf);

        assertTrue(vf.wasSuccessful());

        vf = new VerifyFact("Person", new ArrayList(), true);
        vf.fieldValues.add(new VerifyField("age", "42", "=="));

        runner.verify(vf);

        assertFalse(vf.wasSuccessful());

        vf = new VerifyFact("Cheese", new ArrayList(), true);
        vf.fieldValues.add(new VerifyField("price", "43", "=="));
        vf.fieldValues.add(new VerifyField("type", "stilton", "=="));

        runner.verify(vf);
        assertFalse(vf.wasSuccessful());
        assertEquals(Boolean.FALSE, ((VerifyField)vf.fieldValues.get(0)).successResult);


        vf = new VerifyFact("Cell", new ArrayList(), true);
        vf.fieldValues.add(new VerifyField("value", "43", "=="));

        runner.verify(vf);
        assertFalse(vf.wasSuccessful());
        assertEquals(Boolean.FALSE, ((VerifyField)vf.fieldValues.get(0)).successResult);

    }

    public void testVerifyFactsWithOperator() throws Exception {
        ScenarioRunner runner = new ScenarioRunner( new Scenario(),
                                                    null,
                                                    new MockWorkingMemory() );
        Cheese f1 = new Cheese( "cheddar",
                                42 );
        runner.populatedData.put( "f1",
                                  f1 );

        // test all true
        VerifyFact vf = new VerifyFact();
        vf.name = "f1";
        vf.fieldValues = ls( new VerifyField( "type",
                                              "cheddar",
                                              "==" ),
                             new VerifyField( "price",
                                              "4777",
                                              "!=" ) );
        runner.verify( vf );
        for ( int i = 0; i < vf.fieldValues.size(); i++ ) {
            assertTrue( ((VerifyField) vf.fieldValues.get( i )).successResult );
        }

        vf = new VerifyFact();
        vf.name = "f1";
        vf.fieldValues = ls( new VerifyField( "type",
                                              "cheddar",
                                              "!=" ) );
        runner.verify( vf );
        assertFalse( ((VerifyField) vf.fieldValues.get( 0 )).successResult );

    }

    public void testVerifyFactsWithExpression() throws Exception {
        ScenarioRunner runner = new ScenarioRunner( new Scenario(),
                                                    null,
                                                    new MockWorkingMemory() );
        Cheese f1 = new Cheese( "cheddar",
                                42 );
        runner.populatedData.put( "f1",
                                  f1 );
        f1.setPrice( 42 );
        // test all true
        VerifyFact vf = new VerifyFact();
        vf.name = "f1";
        vf.fieldValues = ls( new VerifyField( "price",
                                              "= 40 + 2",
                                              "==" ) );
        runner.verify( vf );

        assertTrue( ((VerifyField) vf.fieldValues.get( 0 )).successResult );
    }

    public void testVerifyFactExplanation() throws Exception {
        ScenarioRunner runner = new ScenarioRunner( new Scenario(),
                                                    null,
                                                    new MockWorkingMemory() );
        Cheese f1 = new Cheese();
        f1.setType( null );
        runner.populatedData.put( "f1",
                                  f1 );

        VerifyFact vf = new VerifyFact();
        vf.name = "f1";
        vf.fieldValues.add( new VerifyField( "type",
                                             "boo",
                                             "!=" ) );

        runner.verify( vf );
        VerifyField vfl = (VerifyField) vf.fieldValues.get( 0 );
        assertEquals( "[f1] field [type] was not [boo].",
                      vfl.explanation );

    }

    public void testVerifyFieldAndActualIsNull() throws Exception {
        ScenarioRunner runner = new ScenarioRunner( new Scenario(),
                                                    null,
                                                    new MockWorkingMemory() );
        Cheese f1 = new Cheese();
        f1.setType( null );
        runner.populatedData.put( "f1",
                                  f1 );

        VerifyFact vf = new VerifyFact();
        vf.name = "f1";
        vf.fieldValues.add( new VerifyField( "type",
                                             "boo",
                                             "==" ) );

        runner.verify( vf );
        VerifyField vfl = (VerifyField) vf.fieldValues.get( 0 );

        assertEquals( "[f1] field [type] was [] expected [boo].",
                      vfl.explanation );
        assertEquals( "boo",
                      vfl.expected );
        assertEquals( "",
                      vfl.actualResult );

    }

    public void testDummyRunNoRules() throws Exception {
        Scenario sc = new Scenario();
        FactData[] facts = new FactData[]{new FactData( "Cheese",
                                                        "c1",
                                                        ls( new FieldData( "type",
                                                                           "cheddar" ),
                                                            new FieldData( "price",
                                                                           "42" ) ),
                                                        false )};

        VerifyFact[] assertions = new VerifyFact[]{new VerifyFact( "c1",
                                                                   ls( new VerifyField( "type",
                                                                                        "cheddar",
                                                                                        "==" ),
                                                                       new VerifyField( "price",
                                                                                        "42",
                                                                                        "==" ) ) )};

        sc.fixtures.addAll( Arrays.asList( facts ) );
        sc.fixtures.addAll( Arrays.asList( assertions ) );

        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );

        MockWorkingMemory wm = new MockWorkingMemory();
        ScenarioRunner runner = new ScenarioRunner( sc,
                                                    resolver,
                                                    wm );
        assertEquals( 1,
                      wm.facts.size() );
        assertEquals( runner.populatedData.get( "c1" ),
                      wm.facts.get( 0 ) );

        assertTrue( runner.populatedData.containsKey( "c1" ) );
        VerifyFact vf = (VerifyFact) assertions[0];
        for ( int i = 0; i < vf.fieldValues.size(); i++ ) {
            assertTrue( ((VerifyField) vf.fieldValues.get( i )).successResult );
        }

    }

    public void testCountVerification() throws Exception {

        Map<String, Integer> firingCounts = new HashMap<String, Integer>();
        firingCounts.put( "foo",
                          2 );
        firingCounts.put( "bar",
                          1 );
        // and baz, we leave out

        ScenarioRunner runner = new ScenarioRunner( new Scenario(),
                                                    null,
                                                    new MockWorkingMemory() );
        VerifyRuleFired v = new VerifyRuleFired();
        v.ruleName = "foo";
        v.expectedFire = true;
        runner.verify( v,
                       firingCounts );
        assertTrue( v.successResult );
        assertEquals( 2,
                      v.actualResult.intValue() );

        v = new VerifyRuleFired();
        v.ruleName = "foo";
        v.expectedFire = false;
        runner.verify( v,
                       firingCounts );
        assertFalse( v.successResult );
        assertEquals( 2,
                      v.actualResult.intValue() );
        assertNotNull( v.explanation );

        v = new VerifyRuleFired();
        v.ruleName = "foo";
        v.expectedCount = 2;

        runner.verify( v,
                       firingCounts );
        assertTrue( v.successResult );
        assertEquals( 2,
                      v.actualResult.intValue() );

    }

    public void testTestingEventListener() throws Exception {
        Scenario sc = new Scenario();
        sc.rules.add( "foo" );
        sc.rules.add( "bar" );
        ExecutionTrace ext = new ExecutionTrace();

        sc.fixtures.add( ext );

        MockWorkingMemory wm = new MockWorkingMemory();
        ScenarioRunner run = new ScenarioRunner( sc,
                                                 null,
                                                 wm );
        assertEquals( wm,
                      run.workingMemory );
        assertNotNull( wm.agendaEventListener );
        assertTrue( wm.agendaEventListener instanceof TestingEventListener );
        TestingEventListener lnr = (TestingEventListener) wm.agendaEventListener;
        assertEquals( 2,
                      sc.rules.size() );
        assertTrue( sc.rules.contains( "foo" ) );
        assertTrue( sc.rules.contains( "bar" ) );
    }

    public void testWithGlobals() throws Exception {
        Scenario sc = new Scenario();
        FactData[] facts = new FactData[]{new FactData( "Cheese",
                                                        "c2",
                                                        ls( new FieldData( "type",
                                                                           "stilton" ) ),
                                                        false )};
        sc.globals.add( new FactData( "Cheese",
                                      "c",
                                      ls( new FieldData( "type",
                                                         "cheddar" ) ),
                                      false ) );
        sc.fixtures.addAll( Arrays.asList( facts ) );

        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );

        MockWorkingMemory wm = new MockWorkingMemory();
        ScenarioRunner run = new ScenarioRunner( sc,
                                                 resolver,
                                                 wm );
        assertEquals( 1,
                      wm.globals.size() );
        assertEquals( 1,
                      run.globalData.size() );
        assertEquals( 1,
                      run.populatedData.size() );
        assertEquals( 1,
                      wm.facts.size() );

        Cheese c = (Cheese) wm.globals.get( "c" );
        assertEquals( "cheddar",
                      c.getType() );
        Cheese c2 = (Cheese) wm.facts.get( 0 );
        assertEquals( "stilton",
                      c2.getType() );

    }

    @SuppressWarnings("deprecation")
    // F**** dates in java. What a mess. Someone should die.
    public void testSimulatedDate() throws Exception {
        Scenario sc = new Scenario();
        MockWorkingMemory wm = new MockWorkingMemory();
        ScenarioRunner run = new ScenarioRunner( sc,
                                                 null,
                                                 wm );
        TimeMachine tm = run.workingMemory.getTimeMachine();

        // love you
        long time = tm.getNow().getTimeInMillis();

        Thread.sleep( 100 );
        long future = tm.getNow().getTimeInMillis();
        assertTrue( future > time );

        ExecutionTrace ext = new ExecutionTrace();
        ext.scenarioSimulatedDate = new Date( "10-Jul-1974" );
        sc.fixtures.add( ext );
        run = new ScenarioRunner( sc,
                                  null,
                                  wm );
        tm = run.workingMemory.getTimeMachine();

        long expected = ext.scenarioSimulatedDate.getTime();
        assertEquals( expected,
                      tm.getNow().getTimeInMillis() );
        Thread.sleep( 50 );
        assertEquals( expected,
                      tm.getNow().getTimeInMillis() );

    }

    public void testVerifyRuleFired() throws Exception {
        ScenarioRunner runner = new ScenarioRunner( new Scenario(),
                                                    null,
                                                    new MockWorkingMemory() );

        VerifyRuleFired vr = new VerifyRuleFired( "qqq",
                                                  42,
                                                  null );
        Map<String, Integer> f = new HashMap<String, Integer>();
        f.put( "qqq",
               42 );
        f.put( "qaz",
               1 );

        runner.verify( vr,
                       f );
        assertTrue( vr.wasSuccessful() );
        assertEquals( 42,
                      vr.actualResult.intValue() );

        vr = new VerifyRuleFired( "qqq",
                                  41,
                                  null );
        runner.verify( vr,
                       f );
        assertFalse( vr.wasSuccessful() );
        assertEquals( 42,
                      vr.actualResult.intValue() );

        vr = new VerifyRuleFired( "qaz",
                                  1,
                                  null );
        runner.verify( vr,
                       f );
        assertTrue( vr.wasSuccessful() );
        assertEquals( 1,
                      vr.actualResult.intValue() );

        vr = new VerifyRuleFired( "XXX",
                                  null,
                                  false );
        runner.verify( vr,
                       f );
        assertTrue( vr.wasSuccessful() );
        assertEquals( 0,
                      vr.actualResult.intValue() );

        vr = new VerifyRuleFired( "qqq",
                                  null,
                                  true );
        runner.verify( vr,
                       f );
        assertTrue( vr.wasSuccessful() );
        assertEquals( 42,
                      vr.actualResult.intValue() );

        vr = new VerifyRuleFired( "qqq",
                                  null,
                                  false );
        runner.verify( vr,
                       f );
        assertFalse( vr.wasSuccessful() );
        assertEquals( 42,
                      vr.actualResult.intValue() );

    }

    /**
     * Do a kind of end to end test with some real rules.
     */
    public void testIntegrationWithSuccess() throws Exception {

        Scenario sc = new Scenario();
        FactData[] facts = new FactData[]{new FactData( "Cheese",
                                                        "c1",
                                                        ls( new FieldData( "type",
                                                                           "cheddar" ),
                                                            new FieldData( "price",
                                                                           "42" ) ),
                                                        false )

        };
        sc.globals.add( new FactData( "Person",
                                      "p",
                                      new ArrayList(),
                                      false ) );
        sc.fixtures.addAll( Arrays.asList( facts ) );

        ExecutionTrace executionTrace = new ExecutionTrace();

        sc.rules.add( "rule1" );
        sc.rules.add( "rule2" );
        sc.inclusive = true;
        sc.fixtures.add( executionTrace );

        Expectation[] assertions = new Expectation[5];

        assertions[0] = new VerifyFact( "c1",
                                        ls( new VerifyField( "type",
                                                             "cheddar",
                                                             "==" )

                                        ) );

        assertions[1] = new VerifyFact( "p",
                                        ls( new VerifyField( "name",
                                                             "rule1",
                                                             "==" ),
                                            new VerifyField( "status",
                                                             "rule2",
                                                             "==" ) )

        );

        assertions[2] = new VerifyRuleFired( "rule1",
                                             1,
                                             null );
        assertions[3] = new VerifyRuleFired( "rule2",
                                             1,
                                             null );
        assertions[4] = new VerifyRuleFired( "rule3",
                                             0,
                                             null );

        sc.fixtures.addAll( Arrays.asList( assertions ) );

        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );
        resolver.addImport( "org.drools.Person" );

        WorkingMemory wm = getWorkingMemory( "test_rules2.drl" );

        ScenarioRunner run = new ScenarioRunner( sc,
                                                 resolver,
                                                 (InternalWorkingMemory) wm );

        assertEquals( 2,
                      executionTrace.numberOfRulesFired.intValue() );

        assertSame( run.scenario,
                    sc );

        assertTrue( sc.wasSuccessful() );

        Person p = (Person) run.globalData.get( "p" );
        assertEquals( "rule1",
                      p.getName() );
        assertEquals( "rule2",
                      p.getStatus() );
        assertEquals( 0,
                      p.getAge() );

        Thread.sleep( 50 );



        assertTrue( (new Date()).after( sc.lastRunResult ) );
        assertTrue( executionTrace.executionTimeResult != null );

        assertTrue(executionTrace.rulesFired.length > 0);

    }
    
    
    public void testIntegrationInfiniteLoop() throws Exception {

        Scenario sc = new Scenario();
        FactData[] facts = new FactData[]{new FactData( "Cheese",
                                                        "c1",
                                                        ls( new FieldData( "type",
                                                                           "cheddar" ),
                                                            new FieldData( "price",
                                                                           "42" ) ),
                                                        false )

        };
        sc.globals.add( new FactData( "Person",
                                      "p",
                                      new ArrayList(),
                                      false ) );
        sc.fixtures.addAll( Arrays.asList( facts ) );

        ExecutionTrace executionTrace = new ExecutionTrace();

        sc.rules.add( "rule1" );
        sc.rules.add( "rule2" );
        sc.inclusive = true;
        sc.fixtures.add( executionTrace );

        Expectation[] assertions = new Expectation[5];

        assertions[0] = new VerifyFact( "c1",
                                        ls( new VerifyField( "type",
                                                             "cheddar",
                                                             "==" )

                                        ) );

        assertions[1] = new VerifyFact( "p",
                                        ls( new VerifyField( "name",
                                                             "rule1",
                                                             "==" ),
                                            new VerifyField( "status",
                                                             "rule2",
                                                             "==" ) )

        );

        assertions[2] = new VerifyRuleFired( "rule1",
                                             1,
                                             null );
        assertions[3] = new VerifyRuleFired( "rule2",
                                             1,
                                             null );
        assertions[4] = new VerifyRuleFired( "rule3",
                                             0,
                                             null );

        sc.fixtures.addAll( Arrays.asList( assertions ) );

        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );
        resolver.addImport( "org.drools.Person" );

        WorkingMemory wm = getWorkingMemory( "test_rules_infinite_loop.drl" );

        ScenarioRunner run = new ScenarioRunner( sc,
                                                 resolver,
                                                 (InternalWorkingMemory) wm );

        assertEquals( sc.maxRuleFirings,
                      executionTrace.numberOfRulesFired.intValue() );
        

    }

    public void testIntegrationWithDeclaredTypes() throws Exception {
        Scenario sc = new Scenario();
        FactData[] facts = new FactData[]{new FactData( "Coolness",
                                                        "c",
                                                        ls( new FieldData( "num",
                                                                           "42" ),
                                                            new FieldData( "name",
                                                                           "mic" ) ),
                                                        false )

        };
        sc.fixtures.addAll( Arrays.asList( facts ) );

        ExecutionTrace executionTrace = new ExecutionTrace();

        sc.rules.add( "rule1" );
        sc.inclusive = true;
        sc.fixtures.add( executionTrace );

        Expectation[] assertions = new Expectation[2];

        assertions[0] = new VerifyFact( "c",
                                        ls( new VerifyField( "num",
                                                             "42",
                                                             "==" )

                                        ) );



        assertions[1] = new VerifyRuleFired( "rule1",
                                             1,
                                             null );

        sc.fixtures.addAll( Arrays.asList( assertions ) );

        WorkingMemory wm = getWorkingMemory( "test_rules3.drl" );        
        ClassLoader cl = ((InternalRuleBase) wm.getRuleBase()).getRootClassLoader();

        HashSet<String> imports = new HashSet<String>();
        imports.add("foo.bar.*");

        TypeResolver resolver = new ClassTypeResolver( imports,
                                                       cl );

        Class cls = cl.loadClass("foo.bar.Coolness");
        assertNotNull(cls);

        ClassLoader cl_ = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        //resolver will need to have generated beans in it - possibly using a composite classloader from the package,
        //including whatever CL has the generated beans...
        ScenarioRunner run = new ScenarioRunner( sc,
                                                 resolver,
                                                 (InternalWorkingMemory) wm );

        assertEquals( 1,
                      executionTrace.numberOfRulesFired.intValue() );

        assertSame( run.scenario,
                    sc );

        assertTrue( sc.wasSuccessful() );

        Thread.currentThread().setContextClassLoader(cl_);

    }

    public void testIntgerationStateful() throws Exception {
        Scenario sc = new Scenario();
        sc.fixtures.add( new FactData( "Cheese",
                                       "c1",
                                       ls( new FieldData( "price",
                                                          "1" ) ),
                                       false ) );
        ExecutionTrace ex = new ExecutionTrace();
        sc.fixtures.add( ex );
        sc.fixtures.add( new FactData( "Cheese",
                                       "c2",
                                       ls( new FieldData( "price",
                                                          "2" ) ),
                                       false ) );
        sc.fixtures.add( new VerifyFact( "c1",
                                         ls( new VerifyField( "type",
                                                              "rule1",
                                                              "==" ) ) ) );
        ex = new ExecutionTrace();
        sc.fixtures.add( ex );
        sc.fixtures.add( new VerifyFact( "c1",
                                         ls( new VerifyField( "type",
                                                              "rule2",
                                                              "==" ) ) ) );

        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );

        WorkingMemory wm = getWorkingMemory( "test_stateful.drl" );
        ScenarioRunner run = new ScenarioRunner( sc,
                                                 resolver,
                                                 (InternalWorkingMemory) wm );

        Cheese c1 = (Cheese) run.populatedData.get( "c1" );
        Cheese c2 = (Cheese) run.populatedData.get( "c2" );

        assertEquals( "rule2",
                      c1.getType() );
        assertEquals( "rule2",
                      c2.getType() );

        assertTrue( sc.wasSuccessful() );

    }

    public void testIntegrationWithModify() throws Exception {
        Scenario sc = new Scenario();
        sc.fixtures.add( new FactData( "Cheese",
                                       "c1",
                                       ls( new FieldData( "price",
                                                          "1" ) ),
                                       false ) );

        sc.fixtures.add( new ExecutionTrace() );

        sc.fixtures.add( new VerifyFact( "c1",
                                         ls( new VerifyField( "type",
                                                              "rule1",
                                                              "==" ) ) ) );

        sc.fixtures.add( new FactData( "Cheese",
                                       "c1",
                                       ls( new FieldData( "price",
                                                          "42" ) ),
                                       true ) );
        sc.fixtures.add( new ExecutionTrace() );

        sc.fixtures.add( new VerifyFact( "c1",
                                         ls( new VerifyField( "type",
                                                              "rule3",
                                                              "==" ) ) ) );

        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );

        WorkingMemory wm = getWorkingMemory( "test_stateful.drl" );
        ScenarioRunner run = new ScenarioRunner( sc,
                                                 resolver,
                                                 (InternalWorkingMemory) wm );

        Cheese c1 = (Cheese) run.populatedData.get( "c1" );

        assertEquals( "rule3",
                      c1.getType() );

        assertTrue( sc.wasSuccessful() );
    }

    public void testIntegrationWithRetract() throws Exception {
        Scenario sc = new Scenario();
        sc.fixtures.add( new FactData( "Cheese",
                                       "c1",
                                       ls( new FieldData( "price",
                                                          "46" ),
                                           new FieldData( "type",
                                                          "XXX" ) ),
                                       false ) );
        sc.fixtures.add( new FactData( "Cheese",
                                       "c2",
                                       ls( new FieldData( "price",
                                                          "42" ) ),
                                       false ) );
        sc.fixtures.add( new ExecutionTrace() );

        sc.fixtures.add( new VerifyFact( "c1",
                                         ls( new VerifyField( "type",
                                                              "XXX",
                                                              "==" ) ) ) );

        sc.fixtures.add( new RetractFact( "c2" ) );
        sc.fixtures.add( new ExecutionTrace() );

        sc.fixtures.add( new VerifyFact( "c1",
                                         ls( new VerifyField( "type",
                                                              "rule4",
                                                              "==" ) ) ) );

        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );

        WorkingMemory wm = getWorkingMemory( "test_stateful.drl" );
        ScenarioRunner run = new ScenarioRunner( sc,
                                                 resolver,
                                                 (InternalWorkingMemory) wm );

        Cheese c1 = (Cheese) run.populatedData.get( "c1" );

        assertEquals( "rule4",
                      c1.getType() );
        assertFalse( run.populatedData.containsKey( "c2" ) );

        assertTrue( sc.wasSuccessful() );
    }

    public void testIntegrationWithFailure() throws Exception {
        Scenario sc = new Scenario();
        Expectation[] assertions = populateScenarioForFailure(sc);

        TypeResolver resolver = new ClassTypeResolver( new HashSet<String>(),
                                                       Thread.currentThread().getContextClassLoader() );
        resolver.addImport( "org.drools.Cheese" );
        resolver.addImport( "org.drools.Person" );

        WorkingMemory wm = getWorkingMemory( "test_rules2.drl" );

        ScenarioRunner run = new ScenarioRunner( sc,
                                                 resolver,
                                                 (InternalWorkingMemory) wm );

        assertSame( run.scenario,
                    sc );

        assertFalse( sc.wasSuccessful() );

        VerifyFact vf = (VerifyFact) assertions[1];
        assertFalse( ((VerifyField) vf.fieldValues.get( 0 )).successResult );
        assertEquals( "XXX",
                      ((VerifyField) vf.fieldValues.get( 0 )).expected );
        assertEquals( "rule1",
                      ((VerifyField) vf.fieldValues.get( 0 )).actualResult );
        assertNotNull( ((VerifyField) vf.fieldValues.get( 0 )).explanation );

        VerifyRuleFired vr = (VerifyRuleFired) assertions[4];
        assertFalse( vr.successResult );

        assertEquals( 2,
                      vr.expectedCount.intValue() );
        assertEquals( 0,
                      vr.actualResult.intValue() );

    }

    public void testRunAsString() throws Exception {
    	Scenario sc = new Scenario();
    	populateScenarioForFailure(sc);
    	String xml = ScenarioXMLPersistence.getInstance().marshal(sc);
    	WorkingMemory wm = getWorkingMemory( "test_rules2.drl" );
    	ScenarioRunner runner = new ScenarioRunner(xml, wm.getRuleBase());
    	assertFalse(runner.wasSuccess());

    	String failures = runner.getReport();
    	assertFalse("".equals(failures));
    	System.err.println(failures);
    }

	private Expectation[] populateScenarioForFailure(Scenario sc) {
		FactData[] facts = new FactData[]{new FactData( "Cheese",
                                                        "c1",
                                                        ls( new FieldData( "type",
                                                                           "cheddar" ),
                                                            new FieldData( "price",
                                                                           "42" ) ),
                                                        false )

        };
        sc.fixtures.addAll( Arrays.asList( facts ) );
        sc.globals.add( new FactData( "Person",
                                      "p",
                                      new ArrayList(),
                                      false ) );

        ExecutionTrace executionTrace = new ExecutionTrace();
        sc.rules.add( "rule1" );
        sc.rules.add( "rule2" );
        sc.inclusive = true;
        sc.fixtures.add( executionTrace );

        Expectation[] assertions = new Expectation[5];

        assertions[0] = new VerifyFact( "c1",
                                        ls( new VerifyField( "type",
                                                             "cheddar",
                                                             "==" )

                                        ) );

        assertions[1] = new VerifyFact( "p",
                                        ls( new VerifyField( "name",
                                                             "XXX",
                                                             "==" ),
                                            new VerifyField( "status",
                                                             "rule2",
                                                             "==" )

                                        ) );

        assertions[2] = new VerifyRuleFired( "rule1",
                                             1,
                                             null );
        assertions[3] = new VerifyRuleFired( "rule2",
                                             1,
                                             null );
        assertions[4] = new VerifyRuleFired( "rule3",
                                             2,
                                             null );

        sc.fixtures.addAll( Arrays.asList( assertions ) );
		return assertions;
	}

    private <T> List<T> ls(T... objects) {
        return Arrays.asList( objects );
    }

}

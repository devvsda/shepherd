package com.devsda.platform.shepherd.graphgenerator;

import com.devsda.platform.shepherd.graphgenerator.DAGGenerator;
import com.devsda.platform.shepherd.model.Graph;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DAGGeneratorTest {

    private static DAGGenerator dagGenerator;

    @BeforeClass
    public static void setUp() {
        dagGenerator = new DAGGenerator();
    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void generateDAGTest() throws Exception {

        Graph graph = dagGenerator.generate("./src/test/resources/sample_workflow.xml");

        System.out.println(graph);
    }

}

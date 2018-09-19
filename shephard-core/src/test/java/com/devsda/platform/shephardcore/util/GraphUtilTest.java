package com.devsda.platform.shephardcore.util;

import com.devsda.platform.shepherd.graphgenerator.DAGGenerator;
import com.devsda.platform.shepherd.model.Graph;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class GraphUtilTest {

    @Test
    public void getNodeToParentNodeRelationTest() throws Exception {
        DAGGenerator dagGenerator = new DAGGenerator();
        Graph graph = dagGenerator.generate("./src/test/resources/sample_workflow.xml");
        Map<String, List<String>> nodeToParentRelation = GraphUtil.getNodeToParentNodesMapping(graph);
        System.out.println(nodeToParentRelation);
    }
}

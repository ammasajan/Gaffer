/*
 * Copyright 2016 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gaffer.example.gettingstarted.analytic;

import gaffer.data.element.Element;
import gaffer.example.gettingstarted.generator.DataGenerator1;
import gaffer.example.gettingstarted.util.DataUtils;
import gaffer.graph.Graph;
import gaffer.operation.OperationException;
import gaffer.operation.data.EntitySeed;
import gaffer.operation.impl.add.AddElements;
import gaffer.operation.impl.get.GetRelatedEdges;
import java.util.ArrayList;
import java.util.List;

public class LoadAndQuery1 extends LoadAndQuery {

    public static void main(final String[] args) throws OperationException {
        new LoadAndQuery1().run();
    }

    public void run() throws OperationException {
        //create some edges from the data file using our data generator class
        List<Element> elements = new ArrayList<>();
        DataGenerator1 data1Generator = new DataGenerator1();
        System.out.println("Turn the data into Graph Edges\n");
        for (String s : DataUtils.loadData(getData(1))) {
            System.out.println(data1Generator.getElement(s).toString());
            elements.add(data1Generator.getElement(s));
        }
        System.out.println("");

        //create a graph using our schema and store properties
        Graph graph1 = new Graph.Builder()
                .addSchema(getDataSchema(1))
                .addSchema(getDataTypes(1))
                .addSchema(getStoreTypes(1))
                .storeProperties(getStoreProperties())
                .build();

        //add the edges to the graph
        AddElements addElements = new AddElements.Builder()
                .elements(elements)
                .build();

        graph1.execute(addElements);

        //get all the edges that contain the vertex "1"
        GetRelatedEdges query = new GetRelatedEdges.Builder()
                .addSeed(new EntitySeed("1"))
                .build();

        System.out.println("\nAll edges containing the vertex 1. The counts have been aggregated\n");
        for (Element e : graph1.execute(query)) {
            System.out.println(e.toString());
        }
    }
}

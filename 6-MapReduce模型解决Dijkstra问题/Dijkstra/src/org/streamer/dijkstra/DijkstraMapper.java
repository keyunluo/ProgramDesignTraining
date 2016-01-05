package org.streamer.dijkstra;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DijkstraMapper extends Mapper <Object, Text, Text, Text>
{
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException
    {
        String[] split = value.toString().split("_");
        
        String[] secondaryParts = split[0].split("\t");
        String nId = secondaryParts[0];
        int cost = Integer.parseInt(secondaryParts[1]);
        
        String[] neigbor_nodes = split[1].split(",");
        int length = neigbor_nodes.length;
        
        if(cost != -1)
        {
            for(int i=0; i<length; i++)
            {
                if(!neigbor_nodes[i].equals(" "))
                {
                    String[] vertics_value = neigbor_nodes[i].split(":");
                    String v_vertics = vertics_value[0];
                    int v_value = Integer.parseInt(vertics_value[1]);
                    
                    context.write(new Text(v_vertics), new Text(v_value +cost + "_" + " "));
                }
            }
        }
        
        context.write(new Text(nId), new Text(cost + "_" + split[1]));
    }
}
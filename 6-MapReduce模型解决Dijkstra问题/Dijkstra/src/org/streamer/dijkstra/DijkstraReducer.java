package org.streamer.dijkstra;


import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class  DijkstraReducer extends Reducer<Text, Text, Text, Text>
{
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException
    {
        int costMin = -1;
        String neigbor_nodes = " ";
        
        for(Text value : values)
        {

            String parts[] = value.toString().split("_");
            int cost = Integer.parseInt(parts[0]);
            String neighbor = parts[1];
            
            if(cost != -1) 
            {
                if(costMin == -1) 
                { 
                	costMin = cost; 
                	} 
                else
                {
                	if(cost < costMin)
                	{ 
                		costMin = cost;
                	} 
                }
            }

            if(!neighbor.equals(" "))
            { 
            	neigbor_nodes = neighbor; 
            }
        }
        context.write(key, new Text(costMin + "_" + neigbor_nodes));
    }
}
package org.streamer.dijkstra;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;


public class DijkstraDriver {
	
	public static int FileMatcher(String pathFile1, String pathFile2) throws IOException
	  {
		Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        
        Path srcPath1 = new Path(pathFile1);
        Path srcPath2 = new Path(pathFile2);
       
        FSDataInputStream hdfsInStream1= null;
        FSDataInputStream hdfsInStream2 = null;
        try {
        	hdfsInStream1 = fs.open(srcPath1);
        	hdfsInStream2 = fs.open(srcPath2);
        } catch(FileNotFoundException e){
	  		System.out.println("无法打开文件 : " + pathFile1 + " " + pathFile2 + "  异常 : " + e);
	  		return -1;
	  	}
	  	BufferedReader reader1 = null;
	  	BufferedReader reader2 = null;

	    reader1 = new BufferedReader(new InputStreamReader(hdfsInStream1)); 
	    reader2 = new BufferedReader(new InputStreamReader(hdfsInStream2)); 
	  	
	  	String line1="",line2="";
	  	try{
	  		line1=reader1.readLine(); 
	  		line2=reader2.readLine();
	  		
		  	while(line1 != null && line2!=null)
		  	{ 
		  		if(!line1.equals(line2)) 
		  		{
		  			return 0;
		  		}		
		  		line1=reader1.readLine();
	  			line2=reader2.readLine();
		  	}
		  	
		}
		catch(Exception e){
			System.out.println(e);
			return -1;
		}finally {
			reader1.close();
  			reader2.close();
  			hdfsInStream1.close();
        	hdfsInStream2.close();
		}
	  	if(line1==null && line2==null) 
		  		return 1;
		else
			return 0;
	  }
	
	 public static void main(String[] args) throws Exception
	    {
	        String out = "/part-r-00000";
	        Configuration conf = new Configuration();
	        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	        
	        if (otherArgs.length != 2)
	        {
	            System.err.println("请输入input和output:");
	            System.exit(2);
	        }
	        int i = 0;
	        
	        long start = new Date().getTime();
	        
	        while(true)
	        {
	            i++;
	            Job job=Job.getInstance(conf);
	            job.setJarByClass(DijkstraDriver.class);
	            job.setJobName("MapRed" + i);
	            
	            job.setMapperClass(DijkstraMapper.class);
	            job.setReducerClass(DijkstraReducer.class);
	            
	            job.setOutputKeyClass(Text.class);
	            job.setOutputValueClass(Text.class);
	            
	            job.setMapOutputKeyClass(Text.class);
	            job.setMapOutputValueClass(Text.class);
	            
	            if(i == 1) 
	            {
	            	FileInputFormat.addInputPath(job, new Path(otherArgs[0])); 
	            }
	            else 
	            { 
	            	FileInputFormat.addInputPath(job, new Path(otherArgs[1] + (i - 1) + out)); 
	            }
	            
	            FileOutputFormat.setOutputPath(job, new Path(otherArgs[1] + i));
   
	            if(!job.waitForCompletion(true)) 
	            { 
	            	System.exit(1); 
	            }
	            
	            if(i!=1)
	            {
	               if(FileMatcher(otherArgs[1]+(i-1)+out,otherArgs[1]+i+out)==1)
                                     break;
	            }
	            
	        }
	        
	        long end = new Date().getTime();
	        System.out.println("==========================================================================");
	        System.out.println("                                               时间: " + (end - start) + " 毫秒");
	        System.out.println("==========================================================================");
	        
	        System.exit(0);
	    }

}

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.JobConf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class UserRatingsMapper extends MapReduceBase
	implements Mapper<LongWritable, Text, Text, IntWritable> 
{

	private final IntWritable one = new IntWritable(1);
	private Text word = new Text();
	private PrintWriter writer;
	private String userID;
	
	@Override
	public void configure(JobConf job)
	{
		userID = job.get("userID");
		try
		{
			File file = new File("/fslhome/jbelyeu/compute/401R/netflix/output.txt");
			file.createNewFile();
			writer = new PrintWriter(file);
		}
		catch (Exception e)
		{
			System.exit(0);
		}
	}

	@Override
	public void map(LongWritable key, Text value,
  	OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException 
	{		
		writer.println(this.userID);
		writer.close();

		String line = value.toString();
		Text text = new Text(line);
		if (line.contains(this.userID))
		{
			output.collect(text, one);
		}
	}
}

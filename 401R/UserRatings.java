import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

public class UserRatings 
{
	public static void main(String[] args) 
	{
		JobClient client = new JobClient();
		JobConf conf = new JobConf(UserRatings.class);
		conf.set("userID", args[2]);
		// specify output types
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);

		// specify input and output dirs
		FileInputFormat.addInputPath(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		// specify a mapper
		conf.setMapperClass(UserRatingsMapper.class);

		// specify a combiner. For this one we can use the reducer code
		conf.setCombinerClass(UserRatingsReducer.class);

		// specify a reducer
		conf.setReducerClass(UserRatingsReducer.class);

		client.setConf(conf);
		try 
		{
			JobClient.runJob(conf);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

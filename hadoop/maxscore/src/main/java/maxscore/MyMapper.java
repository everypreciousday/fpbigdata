package maxscore;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.commons.lang.math.NumberUtils;
import java.io.IOException;

public class MyMapper extends 
    Mapper<LongWritable, Text, Text, IntWritable> {

    String maxScoreName;
    Integer maxScore;

    protected void setup(Context context) 
            throws IOException, InterruptedException {
        System.out.println("###Mapper Setup###");
    }

    @Override
    protected void map(LongWritable key, 
                       Text value, 
                       Context context) 
        throws IOException, InterruptedException {
        
        final String line = value.toString();
        String[] splitted= line.split(":");

        String name = splitted[0];
        Integer score = Integer.parseInt(splitted[1]);
       
        if (maxScore == null) {
            maxScoreName = name;
            maxScore = score;
        }
        else {
            if (score > maxScore) {
                maxScoreName = name;
                maxScore = score;
            }
        }
    }

    @Override
    protected void cleanup(Context context) 
            throws IOException, InterruptedException {
        context.write(new Text(maxScoreName), new IntWritable(maxScore));
    }
}

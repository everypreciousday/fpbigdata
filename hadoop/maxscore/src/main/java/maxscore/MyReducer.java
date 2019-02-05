package maxscore;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MyReducer extends 
        Reducer<Text, IntWritable, Text, IntWritable> {

    String maxScoreName;
    Integer maxScore;

    @Override
    protected void reduce(Text key, 
                          Iterable<IntWritable> values,
                          Context context) 
            throws IOException, InterruptedException {
        
        for (IntWritable value : values) {
            Integer score = value.get();
            if (maxScore == null) {
                maxScoreName = key.toString();
                maxScore = score;
            }
            else {
                if (score > maxScore) {
                    maxScoreName = key.toString();
                    maxScore = score;
                }
            }
        }
    }

    @Override
    protected void cleanup(Context context) 
            throws IOException, InterruptedException {
        context.write(new Text(maxScoreName), new IntWritable(maxScore));
    }
}

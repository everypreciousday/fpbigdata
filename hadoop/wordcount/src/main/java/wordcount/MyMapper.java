package wordcount;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.commons.lang.math.NumberUtils;
import java.io.IOException;

public class MyMapper extends 
    Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) 
            throws IOException, InterruptedException {
        final String line = value.toString();
        String[] words = line.split(" ");

        for (String word : words) {
            context.write(new Text(word), one);
        }
    }
}

package com.learn.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.context.annotation.Bean;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.learn.springbatch.entity.User;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	// 4: Initial job
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,
				   StepBuilderFactory stepBuilderFactory,
				   ItemReader<User> itemReader,
				   ItemProcessor<User, User> itemProcessor,
				   ItemWriter<User> itemWriter
					) {
		Step step = stepBuilderFactory.get("ETL-file-load")
					.<User, User>chunk(100)
					.reader(itemReader)
					.processor(itemProcessor)
					.writer(itemWriter)
					.build();
		
		return jobBuilderFactory
			   .get("ETL-Load")
			   .incrementer(new RunIdIncrementer())
			   .start(step)
			   .build();
	}
	
	// 2: Read file from users.csv
	@Bean
	public FlatFileItemReader<User> itemReader() {
		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<User>();
		flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	// 3: Mapping domain object from users.csv in a line
	private LineMapper<User> lineMapper() {
		
		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<User>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id","name","dept","salary");
		
		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<User>();
		fieldSetMapper.setTargetType(User.class);
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
	}
}

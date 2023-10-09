package com.example.hellospringbatch.job.player;

import com.example.hellospringbatch.dto.PlayerDto;
import com.example.hellospringbatch.dto.PlayerSalaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FlatFileJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job flatFileJob(Step flatFileStep) {
        return jobBuilderFactory.get("flatFileJob")
                .incrementer(new RunIdIncrementer())
                .start(flatFileStep)
                .build();
    }

    @Bean
    @JobScope
    public Step flatFileStep(FlatFileItemReader<PlayerDto> playerFileItemReader) {
        return stepBuilderFactory.get("flatFileStep")
                .<PlayerDto, PlayerDto>chunk(5)
                .reader(playerFileItemReader)
                .writer(new ItemWriter<PlayerDto>() {
                    @Override
                    public void write(List<? extends PlayerDto> items) throws Exception {
                        items.forEach(System.out::println);
                    }
                })
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<PlayerDto> playerFileItemReader() {
        return new FlatFileItemReaderBuilder<PlayerDto>().name("playerFileItemReader")
                .lineTokenizer(new DelimitedLineTokenizer())
                .linesToSkip(1)
                .fieldSetMapper(new PlayerFileSetMapper())
                .resource(new FileSystemResource("player-list.txt"))
                .build();
    }

}

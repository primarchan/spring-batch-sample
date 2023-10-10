package com.example.hellospringbatch.job.parallel;

import com.example.hellospringbatch.dto.AmountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

/**
 * 단일 프로세스 멀티 Thread 에서 Flow 를 사용해 Step 을 동시에 실행한다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ParallelStepJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parallelJob(Flow splitFlow) {

        return jobBuilderFactory
                .get("parallelJob")
                .incrementer(new RunIdIncrementer())
                .start(splitFlow)
                .build()
                .build();
    }

    @Bean
    public Flow splitFlow(
            Flow flowAmountFileStep,
            Flow flowAnotherStep,
            TaskExecutor taskExecutor
    ) {

        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(taskExecutor)
                .add(flowAmountFileStep, flowAnotherStep)
                .end();
    }

    @Bean
    public Flow flowAmountFileStep(Step amountFileStep) {

        return new FlowBuilder<SimpleFlow>("flowAmountFileStep")
                .start(amountFileStep)
                .end();
    }

    @Bean
    public Step amountFileStep(
            FlatFileItemReader<AmountDto> amountFileItemReader,
            ItemProcessor<AmountDto, AmountDto> amountFileItemProcessor,
            FlatFileItemWriter<AmountDto> amountFiledItemWriter
    ) {
        return stepBuilderFactory.get("multiThreadStep")
                .<AmountDto, AmountDto>chunk(10)
                .reader(amountFileItemReader)
                .processor(amountFileItemProcessor)
                .writer(amountFiledItemWriter)
                .build();
    }

    @Bean
    public Flow flowAnotherStep(Step anotherStep) {

        return new FlowBuilder<SimpleFlow>("anotherStep")
                .start(anotherStep)
                .build();
    }

    @Bean
    public Step anotherStep() {

        return stepBuilderFactory
                .get("anotherStep")
                .tasklet(((contribution, chunkContext) -> {
                    Thread.sleep(500);
                    log.info("Another Step Completed. Thread = {}", Thread.currentThread().getName());
                   return RepeatStatus.FINISHED;
                }))
                .build();
    }

}

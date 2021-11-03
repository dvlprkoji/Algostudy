package com.example.algostudy.config;

import com.example.algostudy.batch.chunk.CustomItemProcessor;
import com.example.algostudy.controller.MissionController;
import com.example.algostudy.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CustomItemProcessor customItemProcessor;

    @Bean
    public Job missionCheckJob() {
        return jobBuilderFactory.get("missionCheckJob")
                .start(missionCheckStep())
                .build();
    }

    @Bean
    public Step missionCheckStep() {
        return stepBuilderFactory.get("missionCheckStep")
                .<Team, Team>chunk(1)
                .reader(teamReader())
                .processor(missionCheckProcessor())
                .writer(teamWriter())
                .build();
    }

    @Bean
    public ItemWriter<? super Team> teamWriter() {
        return new JpaItemWriterBuilder<Team>()
                .usePersist(true)
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemProcessor<? super Team, ? extends Team> missionCheckProcessor() {

        List itemProcessorList = new ArrayList();
        itemProcessorList.add(customItemProcessor);

        return new CompositeItemProcessorBuilder<>()
                .delegates(itemProcessorList)
                .build();
    }

    @Bean
    public ItemReader<? extends Team> teamReader() {

        HashMap<String, Object> parameterHashMap = new HashMap<>();
        parameterHashMap.put("status", "onProgress");

        return new JpaCursorItemReaderBuilder<Team>()
                .name("teamReader")
                .queryString("select t from Team t where t.status=:status")
                .entityManagerFactory(entityManagerFactory)
                .parameterValues(parameterHashMap)
                .build();
    }

}

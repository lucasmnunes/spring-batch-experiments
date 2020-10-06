package poc.springbatch.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import poc.springbatch.batch.model.Player;
import poc.springbatch.batch.processing.JobNotificationListener;
import poc.springbatch.batch.processing.model.PlayerCSV;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Integer HEADER_LINE = 1;
    private static final String FILE_DELIMITER = ",";
    private static final String[] FILE_HEADER = {"id", "name", "position", "jerseyNumber"};

    JobBuilderFactory jobBuilderFactory;
    StepBuilderFactory stepBuilderFactory;

    @Value("${batch.job.name}")
    private String jobName;

    @Value("${batch.job.builder.name}")
    private String jobBuilderName;

    @Value("${batch.job.reader.name}")
    private String jobReaderName;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job job(JobNotificationListener listener,
                   ItemReader<PlayerCSV> itemReader,
                   ItemProcessor<PlayerCSV, Player> itemProcessor,
                   ItemWriter<Player> itemWriter) {
        return jobBuilderFactory.get(jobBuilderName)
                .incrementer(new RunIdIncrementer())
                .start(step(itemReader, itemProcessor, itemWriter))
                .listener(listener)
                .build();
    }

    private Step step(ItemReader<PlayerCSV> itemReader, ItemProcessor<PlayerCSV, Player> itemProcessor,
                      ItemWriter<Player> itemWriter) {
        return stepBuilderFactory.get(jobName)
                .<PlayerCSV, Player>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<PlayerCSV> reader(@Value("${batch.source.file.name}") String sourceFileName) {
        return new FlatFileItemReaderBuilder<PlayerCSV>()
                .resource(new ClassPathResource(sourceFileName))
                .name(jobReaderName)
                .linesToSkip(HEADER_LINE)
                .lineMapper(lineMapper())
                .build();
    }

    @Bean
    public LineMapper<PlayerCSV> lineMapper() {
        DelimitedLineTokenizer lineTokenizer = createAndConfigureDelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<PlayerCSV> fieldSetMapper = createAndConfigureBeanWrapperFieldSetMapper();
        return createAndConfigureLineMapper(lineTokenizer, fieldSetMapper);
    }

    private DelimitedLineTokenizer createAndConfigureDelimitedLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(FILE_DELIMITER);
        lineTokenizer.setNames(FILE_HEADER);
        lineTokenizer.setStrict(false);
        return lineTokenizer;
    }

    private BeanWrapperFieldSetMapper<PlayerCSV> createAndConfigureBeanWrapperFieldSetMapper() {
        BeanWrapperFieldSetMapper<PlayerCSV> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(PlayerCSV.class);
        return fieldSetMapper;
    }

    private DefaultLineMapper<PlayerCSV> createAndConfigureLineMapper
            (DelimitedLineTokenizer lineTokenizer, BeanWrapperFieldSetMapper<PlayerCSV> fieldSetMapper) {
        DefaultLineMapper<PlayerCSV> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

}

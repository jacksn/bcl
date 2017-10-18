package ru.atc.bclient.repository;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atc.bclient.config.ApplicationConfig;

@SpringBootTest
@ContextConfiguration(classes = ApplicationConfig.class)
@RunWith(SpringRunner.class)
public abstract class AbstractRepositoryTest {
}

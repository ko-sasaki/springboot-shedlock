package net.ksasaki.sample.schedule;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@SpringBootApplication
public class ScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleApplication.class, args);
	}

}


@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
@Configuration
class ScheduleConfig {
	@Bean
	public LockProvider lockProvider(DataSource dataSource) {
		return new JdbcTemplateLockProvider(
				JdbcTemplateLockProvider.Configuration.builder()
						.withJdbcTemplate(new JdbcTemplate(dataSource))
						.withLockedByValue( "schedule")
						.usingDbTime()
						.build()
		);
	}
}

@Component
@Slf4j
class ScheduleComponent {

	@Scheduled(fixedRate = 1000, initialDelay = 1000)
	@SchedulerLock(name = "scheduleComponentA", lockAtLeastFor = "3s")
	public void runA() throws InterruptedException {
		log.info("Scheduled task is running... A {}", LocalDateTime.now());
	}

	@Scheduled(fixedRate = 1000, initialDelay = 1000)
	@SchedulerLock(name = "scheduleComponentB" , lockAtLeastFor = "4s")
	public void runB() {
		log.info("Scheduled task is running... B {}", LocalDateTime.now());
	}

	@Scheduled(fixedRate = 500, initialDelay = 1000)
	@SchedulerLock(name = "scheduleComponentC" , lockAtLeastFor = "5s")
	public void runC() {
		log.info("Scheduled task is running... C {}", LocalDateTime.now());
	}

	@Scheduled(fixedRate = 1000, initialDelay = 1000)
	@SchedulerLock(name = "scheduleComponentD", lockAtLeastFor = "2s")
	public void run0() throws InterruptedException {
		log.info("Scheduled task is running... D {}", LocalDateTime.now());
	}
}

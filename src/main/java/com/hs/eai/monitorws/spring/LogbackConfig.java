package com.hs.eai.monitorws.spring;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.ext.spring.ApplicationContextHolder;

import java.util.logging.LogManager;

import javax.annotation.Resource;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("file:henryschein/EaiMonitorWS.properties")
//@PropertySource("file:C:/glassfish4/glassfish/domains/domain1/config/henryschein/EaiMonitorWS.properties")
public class LogbackConfig {

	private static final String LOGBACK_FILE_DIR = "logbackFileDirection";
	private static final String LOGBACK_ENCODER_PATTERN = "logbackEncoderPattern";
	private static final String LOGBACK_ROLLING_POLICY ="logbackRollingPolicy";
	private static final String LOGBACK_ROLLING_POLICY_TIME_BASED_TRIGGERING_MAX_FILE_SIZE = "logbackTimeBasedTriggeringMaxFileSize";
	
	@Resource
	Environment env;
	
	@Bean
	public static ApplicationContextHolder applicationContextHolder() {
		return new ApplicationContextHolder();
	}

	@Bean
	public static LoggerContext loggerContext() {
		return (LoggerContext) LoggerFactory.getILoggerFactory();
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public PatternLayoutEncoder encoder() {
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(loggerContext() );
		encoder.setPattern(env.getRequiredProperty(LOGBACK_ENCODER_PATTERN));
		encoder.start();
		return encoder;
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public ConsoleAppender consoleAppender() {
		ConsoleAppender appender = new ConsoleAppender();
		appender.setContext(loggerContext() );
		appender.setEncoder( encoder());
		appender.start();
		return appender;
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public  FileAppender fileAppender() {

		FileAppender fileAppender = new FileAppender();
		fileAppender.setFile(env.getRequiredProperty(LOGBACK_FILE_DIR));
		fileAppender.setContext(loggerContext());
		fileAppender.setEncoder(encoder());
		
		
		//TimeBasedRollingPolicy
		TimeBasedRollingPolicy rollingPolicy  = new TimeBasedRollingPolicy(); 
		rollingPolicy.setFileNamePattern(env.getRequiredProperty(LOGBACK_ROLLING_POLICY));
		SizeAndTimeBasedFNATP sizeAandTimeBase = new SizeAndTimeBasedFNATP();
		sizeAandTimeBase.setMaxFileSize(env.getRequiredProperty(LOGBACK_ROLLING_POLICY_TIME_BASED_TRIGGERING_MAX_FILE_SIZE));
		rollingPolicy.setTimeBasedFileNamingAndTriggeringPolicy(sizeAandTimeBase);
		rollingPolicy.setParent(fileAppender);
		Logger rootLogger = loggerContext().getLogger(Logger.ROOT_LOGGER_NAME);
		rootLogger.setLevel(Level.ALL);
		rootLogger.addAppender(fileAppender);
		fileAppender.start(); 
		return fileAppender;

	}

}
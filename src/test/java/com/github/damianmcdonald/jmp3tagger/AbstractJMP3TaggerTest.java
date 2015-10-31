package com.github.damianmcdonald.jmp3tagger;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.JLineShellComponent;

import com.damianmcdonald.github.jmp3tagger.service.FileService;
import com.damianmcdonald.github.jmp3tagger.service.MP3TaggerService;

public abstract class AbstractJMP3TaggerTest {

	private static ApplicationContext context;
	private static FileService fileService;
	private static MP3TaggerService mp3TaggerService;
	private static String musicFolder;
	private static String musicCliFolder;
	private static JLineShellComponent shell;

	@BeforeClass
	public static void startUp() throws URISyntaxException {
		context = new ClassPathXmlApplicationContext(
				new String[] { "classpath*:/META-INF/spring/spring-shell-plugin.xml" });
		fileService = context.getBean(FileService.class);
		mp3TaggerService = context.getBean(MP3TaggerService.class);
		
		Bootstrap bootstrap = new Bootstrap();
        shell = bootstrap.getJLineShellComponent();
		
		URL resourceUrl = AbstractJMP3TaggerTest.class.getResource("/music");
		Path resourcePath = Paths.get(resourceUrl.toURI());
		musicFolder = resourcePath.toString();
		
		URL resourceCliUrl = AbstractJMP3TaggerTest.class.getResource("/music-cli");
		Path resourceCliPath = Paths.get(resourceCliUrl.toURI());
		musicCliFolder = resourceCliPath.toString();
	}

	@AfterClass
	public static void shutdown() {
		fileService = null;
		mp3TaggerService = null;
		musicFolder = null;
		musicCliFolder = null;
		context = null;
		shell.stop();
	}
	
	public String pathToParameter(String path) {
		return path.replace("\\", "/");
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static FileService getFileService() {
		return fileService;
	}

	public static MP3TaggerService getMp3TaggerService() {
		return mp3TaggerService;
	}

	public static String getMusicFolder() {
		return musicFolder;
	}

	public static String getMusicCliFolder() {
		return musicCliFolder;
	}

	public static JLineShellComponent getShell() {
		return shell;
	}

}

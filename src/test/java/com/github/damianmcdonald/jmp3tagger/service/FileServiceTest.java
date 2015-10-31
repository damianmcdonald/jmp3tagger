package com.github.damianmcdonald.jmp3tagger.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import com.damianmcdonald.github.jmp3tagger.domain.MP3Info;
import com.github.damianmcdonald.jmp3tagger.AbstractJMP3TaggerTest;

public class FileServiceTest extends AbstractJMP3TaggerTest {
	    
	 @Test
	 public void getAudioFilesTest() throws IOException {
		 List<MP3Info> mp3s = getFileService().getAudioFiles(getMusicFolder());
		 assertEquals("MP3 list size must equal 3", mp3s.size(), 3);
	 }
	 
	 @Test
	 public void renameAudioFileTest() throws IOException {
		 MP3Info mp3 = new MP3Info(getMusicFolder() + "/hip-hop/dr. dre/compton/DR. DRE - TALKING TO MY DIARY RENAME.mp3");
		 getFileService().renameAudioFile(mp3);
		 Path file = Paths.get(getMusicFolder() + "/hip-hop/dr. dre/compton/dr. dre - talking to my diary rename.mp3");
		 assertEquals("Renamed filename must be lowercase", file.getFileName().toString(), "dr. dre - talking to my diary rename.mp3");
	 }

}

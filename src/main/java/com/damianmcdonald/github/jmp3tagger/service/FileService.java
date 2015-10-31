package com.damianmcdonald.github.jmp3tagger.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.damianmcdonald.github.jmp3tagger.domain.MP3Info;
import com.damianmcdonald.github.jmp3tagger.utils.JMP3TaggerConstants;

@Component
public class FileService {
	
	final Logger log = Logger.getLogger("FileReaderService");

	public List<MP3Info> getAudioFiles(final String directory) throws IOException{		
		if(!new File(directory).isDirectory()){
			log.log(Level.SEVERE, "Error! Directory " + directory + " is not valid!");
			return null;
		}
		
		final Collection<File> audioFiles = FileUtils.listFiles(new File(directory), new String[]{JMP3TaggerConstants.AUDIO_FILE_EXTENSION}, true);
		
		log.log(Level.INFO, "Number of MP3 files to process: " + audioFiles.size());
		
		final List<MP3Info> mp3List = new ArrayList<MP3Info>();
		
		for(final File f : audioFiles){
			mp3List.add(new MP3Info(f.getAbsolutePath()));
		}

		return mp3List;
	}
	
	public boolean renameAudioFile(final MP3Info mp3Info) throws IOException{		
		if(!new File(mp3Info.getFilePath()).isFile()){
			log.log(Level.SEVERE, "Error! File " + mp3Info.getFilePath() + " is not valid!");
			return false;
		}
		
		final String newFileName = mp3Info.getArtist() + JMP3TaggerConstants.AUDIO_FILE_ALBUM_ARTIST_SEPARATOR + mp3Info.getTitle() + JMP3TaggerConstants.AUDIO_FILE_EXTENSION_WITH_DOT;
		
		final boolean isRenamed = new File(mp3Info.getFilePath()).renameTo(new File(mp3Info.getDirectoryPath() + File.separator + newFileName.toLowerCase()));

		if(!isRenamed){
			log.log(Level.SEVERE, "Error! File " + mp3Info.getFilePath() + " could not be renamed!");
		}
		
		return isRenamed;
	}
	
}

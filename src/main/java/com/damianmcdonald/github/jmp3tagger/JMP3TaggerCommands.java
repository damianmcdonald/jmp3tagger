package com.damianmcdonald.github.jmp3tagger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.damianmcdonald.github.jmp3tagger.domain.MP3Info;
import com.damianmcdonald.github.jmp3tagger.service.FileService;
import com.damianmcdonald.github.jmp3tagger.service.MP3TaggerService;
import com.damianmcdonald.github.jmp3tagger.utils.JMP3TaggerGenres;

@Component
public class JMP3TaggerCommands implements CommandMarker {

	@Autowired
	private FileService fileService;

	@Autowired
	private MP3TaggerService audioTaggerService;

	Logger log = Logger.getLogger("JMP3TaggerCommands");

	@CliCommand(value = "writefiles", help = "Write id3 tags to mp3 files")
	public String writeFiles(@CliOption(key = { "", "path" }, mandatory = true, help = "The directory path to the mp3 files") final String directoryPath)
					throws IOException, CannotReadException, TagException, ReadOnlyFileException,
					InvalidAudioFrameException, CannotWriteException {

		if (!new File(directoryPath).exists() || !new File(directoryPath).isDirectory()) {
			log.log(Level.SEVERE, "Warning! Directory path (" + directoryPath + ") does not exist.");
			return "Failure, directory path (" + directoryPath + ") does not exist.";
		}

		final List<MP3Info> mp3List = fileService.getAudioFiles(directoryPath);
		final List<String> tagErrors = new ArrayList<String>();

		for (MP3Info mp3Info : mp3List) {
			try {
				audioTaggerService.tagAudioFile(mp3Info);
			} catch (Exception ex) {
				ex.printStackTrace();
				log.log(Level.SEVERE, "Warning! Tagging of file (" + mp3Info.getFilePath() + ") failed.");
				tagErrors.add(mp3Info.getFilePath());
				continue;
			}

			try {
				fileService.renameAudioFile(mp3Info);
			} catch (Exception ex) {
				ex.printStackTrace();
				log.log(Level.SEVERE, "Warning! Renaming of file (" + mp3Info.getFilePath() + ") failed.");
			}
		}

		if (!tagErrors.isEmpty()) {
			for (String error : tagErrors) {
				System.err.println("Error tagging " + error);
			}
		}

		return "ID3 tags written and files renamed.";
	}

	@CliCommand(value = "listfiles", help = "List id3 tags of mp3 files")
	public String listFiles(@CliOption(key = { "", "path" }, mandatory = true, help = "The directory path to the mp3 files") final String directoryPath)
					throws IOException, CannotReadException, TagException, ReadOnlyFileException,
					InvalidAudioFrameException, CannotWriteException {

		if (!new File(directoryPath).exists() || !new File(directoryPath).isDirectory()) {
			log.log(Level.SEVERE, "Warning! Directory path (" + directoryPath + ") does not exist.");
			return "Failure, directory path (" + directoryPath + ") does not exist.";
		}

		final List<MP3Info> mp3List = fileService.getAudioFiles(directoryPath);

		for (MP3Info mp3Info : mp3List) {
			try {
				audioTaggerService.readAudioFile(mp3Info);
			} catch (Exception ex) {
				ex.printStackTrace();
				log.log(Level.SEVERE, "Warning! Error reading file (" + mp3Info.getFilePath() + ") .");
			}

		}
		return "ID3 tags read.";
	}

	@CliCommand(value = "printgenres", help = "Print the list of id3 genres")
	public String listFiles() {
		JMP3TaggerGenres.printGenres();
		return "ID3 genres printed.";
	}
}

package fileEncrypter;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.*;
import org.jaudiotagger.audio.exceptions.*;
import org.jaudiotagger.tag.*;

public class tagManager {
	AudioFile music_file;
	Tag music_file_tag;

	void tagWrite(String encoded_message) throws KeyNotFoundException, FieldDataInvalidException, CannotWriteException {
		music_file_tag.setField(FieldKey.LYRICS, encoded_message);
		AudioFileIO.write(music_file);
	}

	static String tagReader(File mp3_file)
			throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		AudioFile music_file = AudioFileIO.read(mp3_file);
		Tag music_file_tag = music_file.getTag();
		String read_tag = music_file_tag.getFirst(FieldKey.LYRICS);
		return read_tag;
	}

	public tagManager(File mp3file) throws KeyNotFoundException, CannotReadException, IOException, TagException,
			ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {

		music_file = AudioFileIO.read(mp3file);
		music_file_tag = music_file.getTag();

	}

}

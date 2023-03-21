package ASESpaghettiCode.TravelNoteServer;

import ASESpaghettiCode.TravelNoteServer.Model.Note;
import ASESpaghettiCode.TravelNoteServer.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelNoteServerApplication {

	@Autowired
	private NoteRepository noteRepository;

	public static void main(String[] args) {


		SpringApplication.run(TravelNoteServerApplication.class, args);

	}

}

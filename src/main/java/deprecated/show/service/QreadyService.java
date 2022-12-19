package deprecated.show.service;

import deprecated.show.entity.Show;
import org.springframework.http.ResponseEntity;

@Deprecated
public interface QreadyService {

    ResponseEntity  getShowList(String email);
    ResponseEntity  getShow(String showId);
    ResponseEntity  createShow(Show show);
    ResponseEntity deleteShow(String showId);

//    public DefaultRes showSave(Show show);
//
//    public DefaultRes getShowById(String id);
//
//
//    public DefaultRes searchShowByEmail(String email);
//
//    public DefaultRes searchQuiz(String id, String email);

}

package deprecated.show.repository;

import deprecated.show.entity.Show;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Deprecated
public interface QreadyRepository extends MongoRepository<Show, String> {
    Show save(Show show);
    Show findShowById(String Id);
    List<Show> findShowByQuizInfo_Email(String email);
    Show deleteShowById(String Id);

}

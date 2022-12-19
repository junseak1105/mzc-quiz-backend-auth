package deprecated.show.service;

import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import deprecated.show.entity.Show;
import deprecated.show.repository.QreadyRepository;
import deprecated.show.response.ShowListRes;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Deprecated
@Component
@Log4j2
public class QreadyServiceImpl implements QreadyService {

    private final MongoOperations operations;
    @Autowired
    QreadyRepository qreadyRepository;

    public QreadyServiceImpl(MongoOperations operations) {
        this.operations = operations;
    }

    @Override
    @Transactional
    public ResponseEntity getShowList(String email) {
        List<Show> shows = qreadyRepository.findShowByQuizInfo_Email(email);
        // Show의 QuizData를 제외한 나머지 데이터만 가져옴
        List<ShowListRes> showListRes = new ArrayList<>();
        for (Show show : shows) {
            ShowListRes res = new ShowListRes();
            res.setId(show.getId());
            res.setQuizInfo(show.getQuizInfo());
            showListRes.add(res);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, showListRes), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity getShow(String showId) {
        Show show = qreadyRepository.findShowById(showId);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, show), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity createShow(Show show) {
        Show savedShow = qreadyRepository.save(show);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, savedShow), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity deleteShow(String showId) {
        qreadyRepository.deleteShowById(showId);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS), HttpStatus.OK);

    }


}

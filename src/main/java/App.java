import model.dao.*;
import model.entities.Locales;
import model.entities.configuration.Configuration;
import model.entities.question.Question;
import model.entities.question.TypeOfQuestion;
import model.entities.user.LocalizedUser;
import model.entities.user.Status;
import model.entities.user.User;
import model.service.HintService;
import model.service.UserService;
import org.apache.log4j.Logger;

import java.util.Locale;

public class App {
    private static final Logger log = Logger.getLogger(App.class);
    public static void main(String[] args) {
        DaoFactory daoFactory = DaoFactory.getInstance();
        /*try (UserDao userDao = daoFactory.createUserDao()) {
            System.out.println(userDao.findByEmail("1"));
        }*/

        /*try (UserDao userDao = daoFactory.createUserDao()) {
            User user = userDao.findById(9);
            System.out.println(user.getStatistics().size());
            user.getStatistics().forEach(System.out::println);
            user.setPassword("222222");
            userDao.update(user);
        }*/

        try(ConfigurationDao configurationDao = daoFactory.createConfigurationDao()){
            Configuration configuration = configurationDao.findById(18);
            System.out.println(configuration);
            configuration.setTime(100);
            configurationDao.update(configuration);
            System.out.println(configurationDao.findById(18));
        }


       /* try(StatisticsDao statisticsDao = daoFactory.createStatisticsDao()){
            Statistics statistics = statisticsDao.findById(1);
            System.out.println(statistics);
            statisticsDao.create(statistics);
        } */

        /*try (QuestionDao questionDao = daoFactory.createQuestionDao()) {
            Question question = questionDao.findById(1);
            question.setFormulationEN("new formulation");
            questionDao.update(question);
        }*/



        /*try(HintDao hintDao =daoFactory.createHintDao()){

            System.out.println(hintDao.findAllHintsByTypeOfQuestion(TypeOfQuestion.WITHOUT_CHOICES));
        }*/
        /*HintService hintService = new HintService();
        System.out.println(hintService.getAllHintsByTypeOfQuestion(TypeOfQuestion.WITHOUT_CHOICES));

        User user = new LocalizedUser();
        user.getName();*/
        /*try(QuestionDao questionDao = daoFactory.createQuestionDao()){
            Question question = questionDao.findById(3);
            question.setId(1);
           // question.setPlayerAnswer("playerAnswer");
            System.out.println("PL NSWER : " + question.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getId());
        }*/

        /*try(UserDao userDao = daoFactory.createUserDao()){
            User user = userDao.findById(1);
            user.setEmail("2");
            userDao.update(user);
        }*/

       /* User user = new User();
        LocalizedUser user1 = new LocalizedUser();
        user1.setName("Andrew");
        user1.setSurname("Koval");

        LocalizedUser user2 = new LocalizedUser();
        user2.setName("вавапвап");
        user2.setSurname("Коваль");

        user.getLocalizedUsers().put(Locales.ENGLISH.getLocale(), user1);
        user.getLocalizedUsers().put(Locales.UKRAINIAN.getLocale(), user2);

        user.setEmail("andrew@gmail.com");
        user.setPassword("111111");



        System.out.println(new UserService().validateData(user)); */

    }
}

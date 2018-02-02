package edu.virginia.psyc.hobby.domain;

import edu.virginia.psyc.hobby.persistence.ExpectancyBias;
import lombok.Data;
import org.mindtrails.domain.BaseStudy;
import org.mindtrails.domain.Session;
import org.mindtrails.domain.Task;
import org.mindtrails.domain.tracking.TaskLog;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is where you define the sessions and tasks that make up the study.
 */
@Entity
@Data
@DiscriminatorValue("HOBBY")
public class HobbyStudy extends BaseStudy {

    // positive (all positive words)
    // positive (all postivie words, w/ negation statement
    // 50/50 (half positive, half negative, completely random)
    // 50/50 (half positive, half negative, chunks - first 5 pos, next x are negative ....)
    // Neutral condition (likely alternate content)
    public enum CONDITION {POSITIVE, POSITIVE_NEGATION, FIFTY_FIFTY_RANDOM, FIFTY_FIFTY_BLOCKED, NEUTRAL }
    //private boolean        increase30 = false;
    private boolean        increase50 = false;
    public static final String PRE_TEST = "preTest";

    public enum SESSION {preTest, firstSession, secondSession, PostFollowUp };

    public static final String FIRST_SESSION = "firstSession";
    public static final String SECOND_SESSION = "secondSession";
    public static final String POST_FOLLOWUP = "PostFollowUp";

    @Override
    public String getName() {return "Hobby";}

    public HobbyStudy() {
        this.currentSession = PRE_TEST;
    }

    public HobbyStudy(String currentSession, int taskIndex, Date lastSessionDate, List<TaskLog> taskLogs, boolean receiveGiftCards) {
        super(currentSession, taskIndex, lastSessionDate, taskLogs, receiveGiftCards);
    }

    public void completeEligibility(ExpectancyBias bias) {
        TaskLog t = new TaskLog();
        t.setDateCompleted(bias.getDate());
        t.setTaskName("ExpectancyBias");
        t.setSessionName("Eligibility");
        t.setStudy(this);
        taskLogs.add(t);
    }

    @Override
    /** Check out the Templeton Study for building a more complex setup. */
    public Map<String,Object> getPiPlayerParameters() {
        Map<String,Object> map = super.getPiPlayerParameters();
        String sessionName = this.getCurrentSession().getName();
        map.put("negate",(conditioning.equals(CONDITION.POSITIVE_NEGATION)));
        map.put("sessionIndex", this.getCurrentSession().getIndex());
        map.put("secondWordSet",false);
        map.put("question","yes_no");
        map.put("lettersToRemove",1);
        map.put("conditioning", this.conditioning.toString());
        return map;
    }

    public List<String>getConditions(){
        return Stream.of(CONDITION.values()) .map(Enum::name) .collect(Collectors.toList());
    }

    public boolean inSession() {
        return (!getCurrentSession().getName().equals(HobbyStudy.SESSION.preTest.toString()) &&
                !getCurrentSession().getName().equals(HobbyStudy.SESSION.firstSession.toString()) &&
                !getCurrentSession().getName().equals(HobbyStudy.SESSION.secondSession.toString()) &&
                !getCurrentSession().getName().equals(HobbyStudy.SESSION.PostFollowUp.toString()));

    }


    /**
     * Returns the list of sessions and tasks that define the study.
     * @return
     */
    @Override
    public List<Session> getStatelessSessions() {
        List<Session> sessions = new ArrayList<>();
        Session pretest, session1, session2, session3, session4, session5, post;

        pretest = new Session (PRE_TEST, "Initial Assessment", 0, 0);
        pretest.setIndex(0);
        pretest.addTask(new Task("OA","Anxiety Review", Task.TYPE.questions, 1 ));
        pretest.addTask(new Task("tstlunch","Lunch Question", Task.TYPE.questions, 1 ));
        sessions.add(pretest);

        session1 = new Session(FIRST_SESSION, "Level 1: Beginner", 0, 0);
        session1.setIndex(1);
        session1.addTask(new Task("tstlunch","Lunch Question", Task.TYPE.questions, 1 ));
        session1.addTask(new Task("JsPsychTrial", "Training Stories", Task.TYPE.jspsych, 20));
        session1.addTask(new Task("OA","Anxiety Review", Task.TYPE.questions, 1 ));
        sessions.add(session1);

        session2 = new Session(SECOND_SESSION, "Level 1: Beginner", 0, 0);
        session2.setIndex(2);
        session2.addTask(new Task("tstlunch","Lunch Question", Task.TYPE.questions, 1 ));
        session2.addTask(new Task("JsPsychTrial", "Training Stories", Task.TYPE.jspsych, 20));
        session2.addTask(new Task("OA","Anxiety Review", Task.TYPE.questions, 1 ));
        sessions.add(session2);

        post = new Session(POST_FOLLOWUP, "1 Month Post Training", 0, 30);
        post.setIndex(3);
        post.addTask(new Task("OA","Anxiety Review", Task.TYPE.questions, 1 ));
        sessions.add(post);

        return sessions;
    }
}

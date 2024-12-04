package controller;

import dao.SessionDAO;
import models.Session;
import utils.DatabaseUtil;

import java.sql.Timestamp;

public class SessionController {
    private final SessionDAO sessionDAO;

    public SessionController() {
        this.sessionDAO = new SessionDAO(DatabaseUtil.getConnection());
    }

    public boolean login(Integer userId, String token, Timestamp entryTimestamp) {
        Session session = sessionDAO.getSessionByUserId(userId);

        if(session == null) {
            session = new Session(0, userId, token, entryTimestamp);

            return sessionDAO.createSession(session);
        } else {
            session.setEntryTimestamp(entryTimestamp);
            session.setExitTimestamp(null);

            return sessionDAO.updateSession(session);
        }
    }

    public boolean logout(Integer userId, Timestamp exitTimestamp) {
        Session session = sessionDAO.getSessionByUserId(userId);

        session.setEntryTimestamp(null);
        session.setExitTimestamp(exitTimestamp);

        return sessionDAO.updateSession(session);
    }
}

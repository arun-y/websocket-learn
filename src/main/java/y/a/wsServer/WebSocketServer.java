package y.a.wsServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;

@ServerEndpoint(value = "/hello")
public class WebSocketServer {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	Random r = new Random();

	
	static final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<String, Session>();
	
	
	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
		sessions.put(session.getId(), session);
	}

	@OnMessage
	public String onMessage(String message, Session session) throws InterruptedException {
		switch (message) {
		case "quit":
			try {
				session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE,
						"closing session on peer request"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			break;
		}

		return message;
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s",
				session.getId(), closeReason));
	}
}

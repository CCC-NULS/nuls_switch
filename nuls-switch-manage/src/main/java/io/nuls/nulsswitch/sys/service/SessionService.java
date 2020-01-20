package io.nuls.nulsswitch.sys.service;

import java.util.Collection;
import java.util.List;

import io.nuls.nulsswitch.sys.domain.UserOnline;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public interface SessionService {
	List<UserOnline> list(String name);

	Collection<Session> sessionList();
	
	boolean forceLogout(String sessionId);
}

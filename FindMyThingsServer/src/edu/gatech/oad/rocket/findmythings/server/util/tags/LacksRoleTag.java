package edu.gatech.oad.rocket.findmythings.server.util.tags;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.LacksRoleTag}</p>
 */
public class LacksRoleTag extends RoleTag {
	protected boolean showTagBody(String roleName) {
		boolean hasRole = getSubject() != null && getSubject().hasRole(roleName);
		return !hasRole;
	}
}

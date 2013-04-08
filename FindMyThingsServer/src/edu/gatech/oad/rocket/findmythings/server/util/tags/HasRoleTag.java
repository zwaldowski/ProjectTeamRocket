package edu.gatech.oad.rocket.findmythings.server.util.tags;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.HasRoleTag}</p>
 */
public class HasRoleTag extends RoleTag {
	protected boolean showTagBody(String roleName) {
		return getSubject() != null && getSubject().hasRole(roleName);
	}
}

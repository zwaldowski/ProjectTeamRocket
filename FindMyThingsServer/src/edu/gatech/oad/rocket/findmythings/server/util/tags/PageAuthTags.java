/**
 * 
 */
package edu.gatech.oad.rocket.findmythings.server.util.tags;

import freemarker.template.SimpleHash;

/**
 * Injecting Shiro auth tags into FreeMarker
 *
 * <p>Usage: cfg.setSharedVariable("shiro", new PageAuthTags());</p>
 */
public class PageAuthTags extends SimpleHash {

	private static final long serialVersionUID = 3620647030615454373L;

	/**
	 * 
	 */
	public PageAuthTags() {
		put("authenticated", new AuthenticatedTag());
		put("guest", new GuestTag());
		put("hasAnyRoles", new HasAnyRolesTag());
		put("hasPermission", new HasPermissionTag());
		put("hasRole", new HasRoleTag());
		put("lacksPermission", new LacksPermissionTag());
		put("lacksRole", new LacksRoleTag());
		put("notAuthenticated", new NotAuthenticatedTag());
		put("principal", new PrincipalTag());
		put("user", new UserTag());
	}

}

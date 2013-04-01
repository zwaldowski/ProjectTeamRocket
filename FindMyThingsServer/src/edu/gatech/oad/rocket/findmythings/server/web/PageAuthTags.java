/**
 * 
 */
package edu.gatech.oad.rocket.findmythings.server.web;

import edu.gatech.oad.rocket.findmythings.server.web.tags.AuthenticatedTag;
import edu.gatech.oad.rocket.findmythings.server.web.tags.GuestTag;
import edu.gatech.oad.rocket.findmythings.server.web.tags.HasAnyRolesTag;
import edu.gatech.oad.rocket.findmythings.server.web.tags.HasPermissionTag;
import edu.gatech.oad.rocket.findmythings.server.web.tags.HasRoleTag;
import edu.gatech.oad.rocket.findmythings.server.web.tags.LacksPermissionTag;
import edu.gatech.oad.rocket.findmythings.server.web.tags.LacksRoleTag;
import edu.gatech.oad.rocket.findmythings.server.web.tags.NotAuthenticatedTag;
import edu.gatech.oad.rocket.findmythings.server.web.tags.PrincipalTag;
import edu.gatech.oad.rocket.findmythings.server.web.tags.UserTag;
import freemarker.template.SimpleHash;

/**
 * Injecting Shiro auth tags into FreeMarker
 *
 * <p>Usage: cfg.setSharedVeriable("shiro", new PageAuthTags());</p>
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
